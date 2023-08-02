package lv.katise.bdd_galaxy.integration.collector.action;

import lv.katise.bdd_galaxy.integration.collector.graph.context.IActionContext;

import java.util.UUID;

public interface IAction<T> {

    boolean isEntrypoint();

    Class<? extends T> getDefinedClass();

    String getMethodName();

    String getActionName();

    IActionContext<T> getReturnContext();

    void setReturnContext(IActionContext<T> returnContext);

    UUID getId();

    Class<?> getReturnType();
}
