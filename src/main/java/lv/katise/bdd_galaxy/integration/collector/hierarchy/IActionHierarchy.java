package lv.katise.bdd_galaxy.integration.collector.hierarchy;

import lv.katise.bdd_galaxy.integration.collector.hierarchy.context.ITestStepGroup;

import java.util.List;

public interface IActionHierarchy {

    String getVersion();

    List<ITestStepGroup> getGroups();
}
