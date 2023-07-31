package com.qa_universe.bdd_galaxy.runner;

import io.cucumber.core.eventbus.EventBus;
import io.cucumber.core.exception.CucumberException;
import io.cucumber.core.feature.FeatureParser;
import io.cucumber.core.filter.Filters;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.core.gherkin.Pickle;
import io.cucumber.core.options.CucumberProperties;
import io.cucumber.core.options.CucumberPropertiesParser;
import io.cucumber.core.options.RuntimeOptions;
import io.cucumber.core.plugin.PluginFactory;
import io.cucumber.core.plugin.Plugins;
import io.cucumber.core.resource.ClassLoaders;
import io.cucumber.core.runtime.*;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BddRunner implements IBddRunner {
    private final Predicate<Pickle> filters;
    private final List<Feature> features;
    private final CucumberExecutionContext context;

    public BddRunner(Map<String, String> properties) {
        Objects.requireNonNull(properties);
        CucumberPropertiesParser propertiesParser = new CucumberPropertiesParser();

        RuntimeOptions runtimeOptions = propertiesParser.parse(CucumberProperties.fromPropertiesFile()).build();
        propertiesParser.parse(properties).build(runtimeOptions);
        propertiesParser.parse(CucumberProperties.fromEnvironment()).build(runtimeOptions);
        propertiesParser.parse(CucumberProperties.fromSystemProperties()).enablePublishPlugin().build(runtimeOptions);

        EventBus bus = SynchronizedEventBus.synchronize(new TimeServiceEventBus(Clock.systemUTC(), UUID::randomUUID));
        Supplier<ClassLoader> classLoader = ClassLoaders::getDefaultClassLoader;
        FeatureParser parser = new FeatureParser(bus::generateId);
        FeaturePathFeatureSupplier featureSupplier = new FeaturePathFeatureSupplier(classLoader, runtimeOptions, parser);
        Plugins plugins = new Plugins(new PluginFactory(), runtimeOptions);
        ExitStatus exitStatus = new ExitStatus(runtimeOptions);
        plugins.addPlugin(exitStatus);
        ObjectFactoryServiceLoader objectFactoryServiceLoader = new ObjectFactoryServiceLoader(classLoader, runtimeOptions);
        ObjectFactorySupplier objectFactorySupplier = new ThreadLocalObjectFactorySupplier(objectFactoryServiceLoader);
        BackendServiceLoader backendSupplier = new BackendServiceLoader(classLoader, objectFactorySupplier);
        this.filters = new Filters(runtimeOptions);
        ThreadLocalRunnerSupplier runnerSupplier = new ThreadLocalRunnerSupplier(runtimeOptions, bus, backendSupplier, objectFactorySupplier);
        this.context = new CucumberExecutionContext(bus, exitStatus, runnerSupplier);
        plugins.setSerialEventBusOnEventListenerPlugins(bus);
        this.features = featureSupplier.get();
        this.context.startTestRun();
        this.context.runBeforeAllHooks();
        features.forEach(context::beforeFeature);


    }

    public void runScenario(Pickle pickle) {
        this.context.runTestCase((runner) -> {
            BddObserver observer = BddObserver.observe(runner.getBus());

            try {
                runner.runPickle(pickle);
                observer.assertTestCasePassed();
            } catch (Throwable e) {
                try {
                    observer.close();
                } catch (Throwable var5) {
                    e.addSuppressed(var5);
                }

                throw e;
            }
            observer.close();
        });
    }

    public void finish() {
        try {
            this.context.runAfterAllHooks();
        } finally {
            this.context.finishTestRun();
        }
    }

    public Object[][] provideScenarios() {
        try {
            return this.features
                    .stream()
                    .flatMap((feature) -> feature.getPickles().stream().filter(this.filters)
                            .map((cucumberPickle) -> new Object[]{new PickleWrapper(cucumberPickle), new FeatureWrapper(feature)}))
                    .collect(Collectors.toList()).toArray(new Object[0][0]);
        } catch (CucumberException var2) {
            throw new RuntimeException("");
        }
    }
}
