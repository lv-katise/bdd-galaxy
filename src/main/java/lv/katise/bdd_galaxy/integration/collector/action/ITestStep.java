package lv.katise.bdd_galaxy.integration.collector.action;

import lv.katise.bdd_galaxy.core.UUIDGenerator;
import lv.katise.bdd_galaxy.integration.collector.action.defauld.ActionArgument;
import lv.katise.bdd_galaxy.properties.PropertiesService;

import java.util.ArrayList;
import java.util.UUID;

public interface ITestStep {

    boolean isEntrypoint();

    String getName();

    String getBody();

    default UUID getProjectId() {
        return UUIDGenerator.generateUUIDFromString(PropertiesService.getProperty("galaxy.project", ""));
    }

    UUID getGroupId();

    default String getStatus() {
        return "AUTOMATED";
    }

    ArrayList<ActionArgument> getArguments();

    IActionReturn getReturn();
}
