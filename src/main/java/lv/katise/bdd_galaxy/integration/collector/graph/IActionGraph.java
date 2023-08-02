package lv.katise.bdd_galaxy.integration.collector.graph;

import lv.katise.bdd_galaxy.integration.collector.action.IAction;

import java.util.List;

public interface IActionGraph<T> {

    List<IAction<T>> getEntryPoints();
}
