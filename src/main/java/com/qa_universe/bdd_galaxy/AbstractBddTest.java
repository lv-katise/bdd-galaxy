package com.qa_universe.bdd_galaxy;

import com.qa_universe.bdd_galaxy.runner.BddRunner;
import com.qa_universe.bdd_galaxy.runner.FeatureWrapper;
import com.qa_universe.bdd_galaxy.runner.IBddRunner;
import com.qa_universe.bdd_galaxy.runner.PickleWrapper;
import io.cucumber.core.gherkin.Feature;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.Map;

public abstract class AbstractBddTest {

    private IBddRunner bddRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext context) {
        XmlTest currentXmlTest = context.getCurrentXmlTest();
        Map<String, String> allParameters = currentXmlTest.getAllParameters();
        allParameters.put("cucumber.glue", getGluesPath());
        allParameters.put("cucumber.features", getFeaturesPath());
        allParameters.put("cucumber.object-factory", getObjectFactoryPath());

        this.bddRunner = new BddRunner(allParameters);
        init();
    }

    @Test(groups = {"BDD"}, description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickle, FeatureWrapper feature) {
        workWithFeature(feature.getFeature());
        this.bddRunner.runScenario(pickle.getPickle());
    }

    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return this.bddRunner == null ? new Object[0][0] : this.bddRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (this.bddRunner != null) {
            this.bddRunner.finish();
        }
    }

    public abstract void init();

    public abstract void workWithFeature(Feature feature);

    public abstract String getGluesPath();

    public abstract String getFeaturesPath();

    public abstract String getObjectFactoryPath();

}
