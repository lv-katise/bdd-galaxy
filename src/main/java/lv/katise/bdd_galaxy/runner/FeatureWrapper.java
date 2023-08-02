package lv.katise.bdd_galaxy.runner;

import io.cucumber.core.gherkin.Feature;

public class FeatureWrapper {
    private final Feature feature;

    FeatureWrapper(Feature feature) {
        this.feature = feature;
    }

    public Feature getFeature() {
        return this.feature;
    }

    public String toString() {
        return "\"" + this.feature.getName() + "\"";
    }
}
