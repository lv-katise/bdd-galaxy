package lv.katise.bdd_galaxy.integration.collector.hierarchy.context;

import lv.katise.bdd_galaxy.core.UUIDGenerator;
import lv.katise.bdd_galaxy.integration.collector.action.ITestStep;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActionGroup implements ITestStepGroup {

    private final List<ITestStep> steps = new ArrayList<>();
    private Class<?> groupClass;
    private String groupName;

    public ActionGroup(Class<?> groupClass, String groupName) {
        this.groupClass = groupClass;
        this.groupName = groupName;
    }

    @Override
    public UUID getId() {
        return UUIDGenerator.generateUUIDFromString(String.valueOf(groupClass));
    }

    @Override
    public String getName() {
        return groupName;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<ITestStep> getSteps() {
        return steps;
    }

    @Override
    public void addStep(ITestStep action) {
        steps.add(action);
    }

    public void setClass(Class<?> groupClass) {
        this.groupClass = groupClass;
    }
}
