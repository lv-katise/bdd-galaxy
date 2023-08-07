package lv.katise.bdd_galaxy.integration.collector.action.defauld;

import lv.katise.bdd_galaxy.integration.collector.action.IActionReturn;
import lv.katise.bdd_galaxy.integration.collector.action.ITestStep;

import java.util.ArrayList;
import java.util.UUID;

public class BDDStep implements ITestStep {

    private final boolean isEntrypoint;
    private final String name;
    private final String body;
    private UUID groupId;
    private final ArrayList<ActionArgument> arguments = new ArrayList<>();
    private final IActionReturn _return;

    public BDDStep(boolean isEntrypoint, String name, String body, IActionReturn returnInstance) {
        this.isEntrypoint = isEntrypoint;
        this.name = name;
        this.body = body;
        this._return = returnInstance;
    }

    @Override
    public boolean isEntrypoint() {
        return isEntrypoint;
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
}
