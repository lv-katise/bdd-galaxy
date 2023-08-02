package lv.katise.bdd_galaxy.integration.collector.graph.context;

import lv.katise.bdd_galaxy.integration.collector.action.IAction;

import java.util.List;

public interface IActionContext<T> {

    List<IAction<T>> getAllowedActions();

    Class<? extends T> getContextClass();
}
