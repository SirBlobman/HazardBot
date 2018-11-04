package xyz.hazardbot.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import xyz.hazardbot.HazardBot;

public class FileUtil extends Util {
    /**
     * This will attempt to copy a file named {@code fileName} from the Hazard Bot
     * jar file into {@code file}<br/>
     * If {@code file} already exists, the file in the jar will not be copied
     * 
     * @param fileName The name of the file in the jar
     * @param file     The file to copy it to
     */
    public static void copyFromJar(String fileName, File file) {
        try {
            Class<HazardBot> clazz = HazardBot.class;
            InputStream is = clazz.getResourceAsStream(fileName);
            if (is != null) {
                if (!file.exists()) {
                    Path path = Paths.get(file.getAbsolutePath());
                    Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                String error = "The file '" + fileName + " does not exist in the jar!";
                print(error);
            }
        } catch (Throwable ex) {
            String error = "Failed to copy '" + fileName + "' from jar to '" + file + "'";
            print(error);
            ex.printStackTrace();
        }
    }
    
    public static FileInputStream getFileInputStream(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            return fis;
        } catch (Throwable ex) {
            String error = "Failed to read file '" + fileName + "'";
            print(error);
            ex.printStackTrace();
            return null;
        }
    }
}