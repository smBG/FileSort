import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.logging.Logger;

public class MoveFiles {
    private static Logger log = Logger.getLogger(MoveFiles.class.getName());


    private String dir = ".";
    private Properties properties;
    private HashMap<String, String> findInName = new HashMap<>();
    private HashMap<String, String> findInBody = new HashMap<>();


    public MoveFiles(Properties properties) {
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
        Iterator<String> iterator = findList.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
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
            allFiles = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String name = pathname.getName().toLowerCase();
                    return name.endsWith(properties.getProperty("TYPEFILES")) && pathname.isFile();
                }
            };
        } else {
            allFiles = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String name = pathname.getName().toLowerCase();
                    return pathname.isFile();
                }
            };
        }

        File[] files = file.listFiles(allFiles);
        return files;


    }

    private void MoveFilesByName() {
        File[] tmp = FindFiles();
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : findInName.keySet()) {
            for (File file : tmp) {
                stringBuilder.append(file.getName());
                if (stringBuilder.indexOf(key) != -1) {
                    if (file.renameTo(new File(findInName.get(key) + file.getName()))) {
                        log.info("The file " + file.getName() + " was moved to " + findInName.get(key) + " successfully.");
                    } else log.info("The file" + file.getName() + " was not moved.");
                }
                tmp = FindFiles();
            }
        }
    }

    private void MoveFilesByBody() {
//        File[] result = FindFiles();
//        MoveFiles(result);

    }


    private void printFindList(HashMap<String, String> findList) {
        Iterator<String> iterator = findList.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println("key:" + key + "value:" + findList.get(key));
        }
    }


}
