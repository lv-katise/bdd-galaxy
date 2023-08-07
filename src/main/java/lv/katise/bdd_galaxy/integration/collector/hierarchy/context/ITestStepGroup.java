package lv.katise.bdd_galaxy.integration.collector.hierarchy.context;

import lv.katise.bdd_galaxy.integration.collector.action.ITestStep;
import lv.katise.bdd_galaxy.properties.PropertiesService;

import java.util.List;
import java.util.UUID;

public interface ITestStepGroup {

    UUID getId();

    default UUID getProjectId() {
        return UUID.fromString(PropertiesService.getProperty("galaxy.project.id", ""));
    }

    String getName();

    String getDescription();

    default String getStatus() {
        return "ACTIVE";
    }

    List<ITestStep> getSteps();

    void addStep(ITestStep action);
}
