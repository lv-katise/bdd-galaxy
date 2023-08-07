package lv.katise.bdd_galaxy.properties;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {

    private final static Properties properties = new Properties();
    private static Model model;

    static {
        try (InputStream input = new FileInputStream("bdd-galaxy.properties")) {
            properties.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        }
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            model = reader.read(new FileReader("pom.xml"));
        } catch (IOException io) {
            io.printStackTrace();
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String propertyName, String defaultValue) {
        return properties.getProperty(propertyName, defaultValue);
    }

    public static Model getPOMProperties() {
        return model;
    }
}
