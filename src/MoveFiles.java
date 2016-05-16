import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

class MoveFiles {
    private static Logger log = Logger.getLogger(MoveFiles.class.getName());


    private String dir = ".";
    private Properties properties;
    private HashMap<String, String> findInName = new HashMap<>();
    private HashMap<String, String> findInBody = new HashMap<>();


    MoveFiles(Properties properties) {
        this.properties = properties;
        dir = properties.getProperty("DIR");
        log.info("Start find files in dirictory " + dir);

        createMoveMask();
        checkFolderExists(findInBody);
        checkFolderExists(findInName);
        if (findInName.size() != 0) MoveFilesByName();
        if (findInBody.size() != 0) MoveFilesByBody();

    }

    /**
     * Check folder exist and create if need
     *
     * @param findList list folders
     */
    private void checkFolderExists(HashMap<String, String> findList) {
        for (String key : findList.keySet()) {
            String value = findList.get(key);
            File dir = new File(value);
            if (!dir.exists()) {
                log.info("create dir " + dir + " " + dir.mkdirs());
            }
        }
    }

    /**
     * Read config and create move files mask
     */
    private void createMoveMask() {
        Enumeration<?> e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = ((String) e.nextElement()).toLowerCase();
            if (key.startsWith("name")) {
                findInName.put(key.replaceFirst("name:", ""), properties.getProperty(key));
            } else if (key.startsWith("body")) {
                findInBody.put(key.replaceFirst("body:", ""), properties.getProperty(key));
            }
        }
        log.info("Load name mask=" + findInName.size() + " and body mask=" + findInBody.size());
    }

    /**
     * Create file[]  with files on mask TYPEFILES
     */
    private File[] FindFiles() {
        File file = new File(dir);
        FileFilter allFiles;
        if (!properties.getProperty("TYPEFILES").equals("*")) {
            allFiles = pathname -> {
                String name = pathname.getName().toLowerCase();
                return name.endsWith(properties.getProperty("TYPEFILES")) && pathname.isFile();
            };
        } else {
            allFiles = File::isFile;
        }

        return file.listFiles(allFiles);

    }

    private void MoveFilesByName() {
        File[] fileList = FindFiles();

        for (String key : findInName.keySet()) {
            log.info(fileList.length + " files fount");
            log.info("find mask " + key + " from name");
            for (File file : fileList) {
                if (file.getName().contains(key)) {
                    if (file.renameTo(new File(findInName.get(key) + file.getName()))) {
                        log.info("The file " + file.getName() + " was moved to " + findInName.get(key) + " successfully.");
                    } else log.info("The file" + file.getName() + " was not moved.");
                }
                fileList = FindFiles();
            }
        }
    }

    private void MoveFilesByBody() {
        File[] fileList = FindFiles();

        for (String key : findInBody.keySet()) {
            log.info(fileList.length + " files fount");
            log.info("find mask " + key + " from body");
            for (File file : fileList) {
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                    while (scanner.hasNext()) {
                        if (scanner.nextLine().contains(key)) {
                            if (file.renameTo(new File(findInBody.get(key) + file.getName()))) {
                                log.info("The file " + file.getName() + " was moved to " + findInBody.get(key) + " successfully.");
                            } else log.info("The file" + file.getName() + " was not moved.");
                        }
                    }
                } catch (FileNotFoundException e) {
                    log.warning("File " + file + " not found");
                }
                assert scanner != null;
                scanner.close();
                fileList = FindFiles();
            }
        }

    }


}
