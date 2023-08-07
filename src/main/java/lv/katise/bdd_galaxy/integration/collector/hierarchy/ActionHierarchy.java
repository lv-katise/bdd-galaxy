package lv.katise.bdd_galaxy.integration.collector.hierarchy;

import lv.katise.bdd_galaxy.integration.collector.hierarchy.context.ITestStepGroup;

import java.util.ArrayList;
import java.util.List;

public class ActionHierarchy implements IActionHierarchy {

    private final List<ITestStepGroup> contexts = new ArrayList<>();
    private final String executorVersion;

    public ActionHierarchy(String executorVersion) {
        this.executorVersion = executorVersion;
    }

    @Override
    public String getVersion() {
        return executorVersion;
    }

    @Override
    public List<ITestStepGroup> getGroups() {
        return contexts;
    }

    public void addContext(ITestStepGroup context) {
        this.contexts.add(context);
    }
}
