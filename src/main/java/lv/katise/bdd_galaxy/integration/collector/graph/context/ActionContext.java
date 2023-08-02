package lv.katise.bdd_galaxy.integration.collector.graph.context;

import lv.katise.bdd_galaxy.integration.collector.action.IAction;

import java.util.List;

public class ActionContext<T> implements IActionContext<T> {

    private List<IAction<T>> actions;
    private Class<? extends T> contextClass;

    public ActionContext() {
    }

    public ActionContext(List<IAction<T>> actions, Class<? extends T> contextClass) {
        this.actions = actions;
        this.contextClass = contextClass;
    }

    @Override
    public List<IAction<T>> getAllowedActions() {
        return null;
    }

    @Override
    public Class<? extends T> getContextClass() {
        return null;
    }

    public void setActions(List<IAction<T>> actions) {
        this.actions = actions;
    }

    public void setContextClass(Class<? extends T> contextClass) {
        this.contextClass = contextClass;
    }
}
