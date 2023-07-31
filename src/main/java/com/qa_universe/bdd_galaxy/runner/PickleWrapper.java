package com.qa_universe.bdd_galaxy.runner;

import io.cucumber.core.gherkin.Pickle;

public class PickleWrapper {
    private final Pickle pickle;

    PickleWrapper(Pickle pickle) {
        this.pickle = pickle;
    }

    public Pickle getPickle() {
        return this.pickle;
    }

    public String toString() {
        return "\"" + this.pickle.getName() + "\"";
    }
}
