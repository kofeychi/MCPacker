package dev.kofeychi.packer;


import dev.kofeychi.packer.generator.ModelFileFixer;
import dev.kofeychi.packer.util.Zipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static dev.kofeychi.packer.Consts.gson;

public class Main {
    public static final Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        for (Paths path : Paths.values()) {
            path.verify();
        }
        p("Specify rename for your item, example: \"Ocean Seeker\"");
        var rename = getLine();
        p("Specify description for your pack, example: \"A funny texture pack that adds many of funny things\"");
        var description = getLine();
        var doesContainLeftHands = Boolean.getBoolean(getString("Does your model contain left-handed models?",List.of("true","false")));
        Generator.generate(
                new PackGeneratorInfos(
                        rename,
                        description,
                        doesContainLeftHands,
                        null,
                        null,
                        null,
                        null,
                        "_e"
                )
        );
    }

    public static Path getFileFromList(String allowedExtension) {
        List<String> list = new ArrayList<>();
        if(allowedExtension != null) {
            list = listFiles(Paths.ROOTDIR.asPath().toFile(),allowedExtension);
        } else {
            list = listFiles(Paths.ROOTDIR.asPath().toFile());
        }
        int line = getLine(list);

        String id = list.get(Math.abs(line));
        p("You have selected: "+id);

        return Paths.ROOTDIR.asPath().resolve(id);
    }
    public static int getLine(List<String> list) {
        for(;;) {
            ListFilesPrint(list);
            p("Enter ID of file to read: ");
            int line = scan.nextInt();
            if (list.size() - 1 < Math.abs(line)) {
                p("The specified file ID is incorrect.");
                continue;
            }
            return line;
        }
    }
    public static int getLine(String list) {
        for(;;) {
            p(list);
            p("Enter ID to read: ");
            int line = scan.nextInt();
            if(line < 0){
                p("The specified ID is incorrect.");
                continue;
            }
            return Math.abs(line);
        }
    }
    public static String getLine() {
        p("Enter ID: ");
        return scan.nextLine();
    }
    public static String getString(String list,List<String> allowed) {
        for(;;) {
            p(list);
            p("Allowed keys are: "+joinSeparate(allowed));
            p("Enter ID to read: ");
            String line = scan.nextLine();
            if(!allowed.contains(line)){
                p("The specified ID is incorrect.");
                continue;
            }
            return line;
        }
    }
    private static String joinSeparate(List<String> props){
        AtomicReference<String> out = new AtomicReference<>("");
        props.forEach(prop->{
            out.set(out.get()+","+prop);
        });
        out.set(out.get().substring(1,out.get().length()));
        return out.get();
    }
    public static List<String> listFiles(File dir) {
        return Stream.of(dir.listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .toList();
    }
    public static List<String> listFiles(File dir,String extension) {
        return listFiles(dir).stream()
                .filter(name -> name.endsWith(extension))
                .toList();
    }
    public static List<String> ListFilesPrint(Path dir) {
        File file = dir.toFile();
        List<String> lines = listFiles(file);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String formatted = " |- ("+i+"): "+line;
            p(formatted);
        }
        return lines;
    }
    public static void ListFilesPrint(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String formatted = " |- ("+i+"): "+line;
            p(formatted);
        }
    }
    public static void p(String msg) {
        System.out.println(msg);
    }
}