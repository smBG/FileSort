import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class FileSort {
    private static Logger log = Logger.getLogger(MoveFiles.class.getName());

    static {
        log.setUseParentHandlers(false);
        MyFormatter formatter = new MyFormatter();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        log.addHandler(handler);
    }


    public static void main(String[] args) {
        String settingFile = "filesort.properties";
        Settings settings = new Settings(settingFile);
        Properties properties = settings.getProperties();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-c":
                    settingFile = args[i + 1];
                    settings = new Settings(settingFile);
                    properties = settings.getProperties();
                    break;
                case "-f":
                    properties.setProperty("TYPEFILES", args[i + 1]);
                    break;
                case "-d":
                    properties.setProperty("DIR", args[i + 1]);
                    break;
            }
        }


        new MoveFiles(properties);
    }

}
