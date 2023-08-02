package lv.katise.bdd_galaxy.integration.collector;

import lv.katise.bdd_galaxy.integration.collector.action.IAction;
import lv.katise.bdd_galaxy.integration.collector.graph.IActionGraph;
import lv.katise.bdd_galaxy.integration.collector.graph.context.IActionContext;

import java.util.List;

public interface IActionCollector<T> {

    List<IAction<T>> collectEntryPoints();

    List<IAction<T>> collectSteps(Class<? extends T> rootClass);

    IActionGraph<T> buildStepGraph();

    IActionContext<T> provideContext(Class<? extends T> returnType);

}
