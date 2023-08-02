package lv.katise.bdd_galaxy.runner;

import io.cucumber.core.eventbus.EventBus;
import io.cucumber.plugin.event.EventPublisher;
import org.testng.SkipException;

import java.util.function.Function;

public class BddObserver implements AutoCloseable {
    private static final String SKIP_MESSAGE = "This scenario is skipped";
    private final io.cucumber.core.runtime.TestCaseResultObserver delegate;

    private BddObserver(EventPublisher bus) {
        this.delegate = new io.cucumber.core.runtime.TestCaseResultObserver(bus);
    }

    public static BddObserver observe(EventBus bus) {
        return new BddObserver(bus);
    }

    public void assertTestCasePassed() {
        this.delegate.assertTestCasePassed(() -> {
            return new SkipException("This scenario is skipped");
        }, (exception) -> {
            return (Throwable) (exception instanceof SkipException ? exception : new SkipException(exception.getMessage(), exception));
        }, UndefinedStepException::new, Function.identity());
    }

    public void close() {
        this.delegate.close();
    }
}
