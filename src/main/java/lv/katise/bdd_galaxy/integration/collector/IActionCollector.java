package lv.katise.bdd_galaxy.integration.collector;

import lv.katise.bdd_galaxy.integration.collector.hierarchy.IActionHierarchy;

public interface IActionCollector {

    IActionHierarchy buildActionsHierarchy(String... gluePaths);
}
