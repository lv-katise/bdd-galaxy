package lv.katise.bdd_galaxy.integration.collector.action;

import lv.katise.bdd_galaxy.integration.collector.action.defauld.ActionArgument;

import java.util.UUID;

public interface IActionReturn {

    UUID getGroupId();

    ActionArgument getReturnType();
}
