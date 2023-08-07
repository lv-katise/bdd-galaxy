package lv.katise.bdd_galaxy.integration.collector.action.defauld;

import lv.katise.bdd_galaxy.integration.collector.action.IActionReturn;
import lv.katise.bdd_galaxy.integration.collector.action.ITestStep;

import java.util.ArrayList;
import java.util.UUID;

public class BDDStep implements ITestStep {

    private UUID id;
    private final boolean isEntryPoint;
    private final String name;
    private final String body;
    private UUID groupId;
    private final ArrayList<ActionArgument> arguments = new ArrayList<>();
    private final IActionReturn _return;

    public BDDStep(boolean isEntryPoint, String name, String body, IActionReturn returnInstance) {
        this.isEntryPoint = isEntryPoint;
        this.name = name;
        this.body = body;
        this._return = returnInstance;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean getIsEntryPoint() {
        return isEntryPoint;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public UUID getGroupId() {
        return groupId;
    }

    @Override
    public ArrayList<ActionArgument> getArguments() {
        return arguments;
    }

    @Override
    public IActionReturn getReturn() {
        return _return;
    }

    public void addArgument(ActionArgument argument) {
        this.arguments.add(argument);
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
