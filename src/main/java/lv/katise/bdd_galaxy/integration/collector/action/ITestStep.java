package lv.katise.bdd_galaxy.integration.collector.action;

import lv.katise.bdd_galaxy.integration.collector.action.defauld.ActionArgument;
import lv.katise.bdd_galaxy.properties.PropertiesService;

import java.util.ArrayList;
import java.util.UUID;

public interface ITestStep {

    UUID getId();

    boolean getIsEntryPoint();

    String getName();

    String getBody();

    default UUID getProjectId() {
        return UUID.fromString(PropertiesService.getProperty("galaxy.project.id", ""));
    }

    UUID getGroupId();

    default String getStatus() {
        return "AUTOMATED";
    }

    ArrayList<ActionArgument> getArguments();

    IActionReturn getReturn();
}
