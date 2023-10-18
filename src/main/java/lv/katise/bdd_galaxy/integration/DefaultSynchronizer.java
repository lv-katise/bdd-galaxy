package lv.katise.bdd_galaxy.integration;

import lv.katise.bdd_galaxy.integration.collector.ActionCollector;
import lv.katise.bdd_galaxy.integration.collector.IActionCollector;
import lv.katise.bdd_galaxy.integration.collector.hierarchy.IActionHierarchy;
import lv.katise.bdd_galaxy.properties.PropertiesService;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class DefaultSynchronizer implements ISynchronizer {

    private final IActionCollector collector;

    public DefaultSynchronizer() {
        collector = new ActionCollector();
    }

    @Override
    public void synchronize(String... gluePaths) {
        IActionHierarchy hierarchy = collector.buildActionsHierarchy(gluePaths);
        writeToFile(new JSONObject(hierarchy).toString());
    }

    private void writeToFile(String json) {
        try {
            FileWriter myWriter = new FileWriter(PropertiesService.getProperty("galaxy.sync.path", "bdd-galaxy.json"));
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
