package lv.katise.bdd_galaxy.integration.collector.graph;

import lv.katise.bdd_galaxy.integration.collector.action.IAction;

import java.util.ArrayList;
import java.util.List;

public class ActionGraph<T> implements IActionGraph<T> {

    private final List<IAction<T>> entryPoints = new ArrayList<>();

    @Override
    public List<IAction<T>> getEntryPoints() {
        return entryPoints;
    }

    public void addEntryPoints(List<IAction<T>> entryPoints) {
        this.entryPoints.addAll(entryPoints);
    }
}
