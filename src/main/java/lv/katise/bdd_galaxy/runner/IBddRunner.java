package lv.katise.bdd_galaxy.runner;

import io.cucumber.core.gherkin.Pickle;

public interface IBddRunner {

    void runScenario(Pickle pickle);

    void finish();

    Object[][] provideScenarios();
}
