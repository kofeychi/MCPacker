package dev.kofeychi.packer;


import dev.kofeychi.packer.generator.ModelFileFixer;
import dev.kofeychi.packer.util.Zipper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static dev.kofeychi.packer.Consts.gson;

public class Main {
    public static final Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        if(Paths.GEN.asPath().toFile().exists()){
            FileUtils.deleteDirectory(Paths.GEN.asPath().toFile());
        }
        for (Paths path : Paths.values()) {
            path.verify();
        }
        p(ANSI.Augment("Specify rename for your item, example: \"Ocean Seeker\"",ANSI.ANSI_BLUE));
        var rename = getLine();
        p(ANSI.Augment("Specify description for your pack, example: \"A funny texture pack that adds many of funny things\"",ANSI.ANSI_BLUE));
        var description = getLine();
        var doesContainLeftHands = Objects.equals(getString(ANSI.Augment("Does your model contain left-handed models? (you can skip this,but it will default to false)", ANSI.ANSI_BLUE), List.of("true", "false"), "false"), "true");

        if(doesContainLeftHands) {
            p(ANSI.Augment("Your model contains left-handed models. You will be asked to answer questions twice to pick them.",ANSI.ANSI_BLUE));
        }
        p(ANSI.Augment("Specify the model for your item.",ANSI.ANSI_BLUE));
        var Model = getFileFromList("json");
        p(ANSI.Augment("Specify the texture for your item.",ANSI.ANSI_BLUE));
        var ModelTexture = getFileFromList("png");
        File ModelRight = null;
        File ModelTextureRight = null;
        if (doesContainLeftHands) {
            p(ANSI.Augment("Now youre specifying the right hand data because you choose so.",ANSI.ANSI_BLUE));
            p(ANSI.Augment("Specify the model for your item.",ANSI.ANSI_BLUE));
            ModelRight = getFileFromList("json").toFile();
            p(ANSI.Augment("Specify the texture for your item.",ANSI.ANSI_BLUE));
            ModelTextureRight = getFileFromList("png").toFile();
        }
        Generator.generate(
                new PackGeneratorInfos(
                        rename,
                        description,
                        doesContainLeftHands,
                        Model.toFile(),
                        ModelTexture.toFile(),
                        ModelRight,
                        ModelTextureRight,
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
    public static String getString(String list,List<String> allowed,String defaultValue) {
        for(;;) {
            p(list);
            p("Allowed keys are: "+joinSeparate(allowed));
            p("Enter ID to read: ");
            String line = scan.nextLine();
            if(line.isEmpty()){
                return defaultValue;
            }
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
            String formatted = " |- ("+i+"): "+ANSI.Augment(line,ANSI.ANSI_CYAN);
            p(formatted);
        }
        return lines;
    }
    public static void ListFilesPrint(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String formatted = " |- ("+i+"): "+ANSI.Augment(line,ANSI.ANSI_CYAN);
            p(formatted);
        }
    }
    public static void p(String msg) {
        System.out.println(msg);
    }
}