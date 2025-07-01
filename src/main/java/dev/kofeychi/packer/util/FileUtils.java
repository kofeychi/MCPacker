package dev.kofeychi.packer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static String readFile(Path path) {
        String content = "";
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static void writeFile(File path,String content) {
        try {
            System.out.println("Writing to: "+path.getAbsolutePath());
            new FileWriter(path, false).close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            String[] words = content.split("[^.]/n");
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
