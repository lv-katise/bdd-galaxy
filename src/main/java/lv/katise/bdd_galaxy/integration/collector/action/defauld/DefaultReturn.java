package lv.katise.bdd_galaxy.integration.collector.action.defauld;

import lv.katise.bdd_galaxy.integration.collector.action.IActionReturn;

import java.util.UUID;

public class DefaultReturn implements IActionReturn {

    private UUID groupId;
    private ActionArgument returnType;

    public DefaultReturn() {
    }

    public DefaultReturn(UUID groupId, ActionArgument returnType) {
        this.groupId = groupId;
        this.returnType = returnType;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public void setReturnType(ActionArgument type) {
        this.returnType = type;
    }

    @Override
    public UUID getGroupId() {
        return groupId;
    }

    @Override
    public ActionArgument getReturnType() {
        return returnType;
    }
}
