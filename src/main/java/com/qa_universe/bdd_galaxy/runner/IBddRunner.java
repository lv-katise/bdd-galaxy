package com.qa_universe.bdd_galaxy.runner;

import io.cucumber.core.gherkin.Pickle;

public interface IBddRunner {

    void runScenario(Pickle pickle);

    void finish();

    Object[][] provideScenarios();
}
