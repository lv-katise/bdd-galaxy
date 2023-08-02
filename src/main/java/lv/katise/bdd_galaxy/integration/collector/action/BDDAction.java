package lv.katise.bdd_galaxy.integration.collector.action;

import lv.katise.bdd_galaxy.integration.collector.graph.context.IActionContext;

import java.util.UUID;

public class BDDAction<T> implements IAction<T> {

    private final boolean isEntrypoint;
    private final Class<T> definedClass;
    private final String methodName;
    private final String actionName;
    private final UUID id;
    private final Class<?> returnType;
    private IActionContext<T> returnContext;

    public BDDAction(boolean isEntrypoint, Class<T> definedClass, String methodName, String actionName, UUID id, Class<?> returnType) {
        this.isEntrypoint = isEntrypoint;
        this.definedClass = definedClass;
        this.methodName = methodName;
        this.actionName = actionName;
        this.id = id;
        this.returnType = returnType;
    }

    @Override
    public boolean isEntrypoint() {
        return isEntrypoint;
    }

    @Override
    public Class<? extends T> getDefinedClass() {
        return definedClass;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public String getActionName() {
        return actionName;
    }

    @Override
    public IActionContext<T> getReturnContext() {
        return returnContext;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Class<?> getReturnType() {
        return returnType;
    }

    @Override
    public void setReturnContext(IActionContext<T> returnContext) {
        this.returnContext = returnContext;
    }
}
