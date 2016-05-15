import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Settings {
    private static Logger log = Logger.getLogger(MoveFiles.class.getName());

    private String file = "filesort.properties";
    private Properties properties = new Properties();


    public Settings() {
        readConfig();
    }


    public Settings(String file) {
        this.file = file;
        readConfig();
    }

    public Properties getProperties() {
        return properties;
    }

    private void readConfig() {
        log.info("Read config file " + file + ".");
        try {
            properties.load(new FileInputStream(file));
        } catch (java.io.IOException e) {
            log.warning("File " + file + " not fount or incorrect.");
        }
    }

}
