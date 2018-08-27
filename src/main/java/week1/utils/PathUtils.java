package week1.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PathUtils {
    private static Properties appProperties = new Properties();
    private static String pathToProps = "src/main/resources/app.properties";

    public static String getDbPath() {

        loadProperty(pathToProps);
        return appProperties.getProperty("pathToDb");
    }

    public static String getDbLogin() {
        return appProperties.getProperty("loginToDb");
    }


    public static String getDbPass() {
        return appProperties.getProperty("passToDb");
    }

    private static void loadProperty (String pathToProps){
        try (InputStream io = new FileInputStream(pathToProps)) {
            appProperties.load(io);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
