package dev.kofeychi.packer;

import dev.kofeychi.packer.util.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public enum Paths {
    ROOTDIR(Path.of(".").toAbsolutePath().normalize()),
    GEN(ROOTDIR,"gen"),
    ASSETS(GEN,"assets"),
    MC(ASSETS,"minecraft"),
    OPTIFINE(MC,"optifine"),
    CIT(OPTIFINE,"cit"),
    EMISSIVE(OPTIFINE,"emissive.properties",true),
    PACK(GEN,"pack.mcmeta",true),
    OUT(ROOTDIR,"out.zip",true)
    ;
    private String path;
    public boolean isFile = false;
    Paths(String path){
        this.path = path;
        verifyPath();
    }
    Paths(Paths parent,String path){
        String parentPath = parent.path;
        if(!(parent.path.endsWith("\\")||parent.path.endsWith("/"))){
            parentPath += "/";
        }
        if(path.startsWith("\\")||path.startsWith("/")){
            path = path.substring(1);
        }
        this.path = parentPath + path;
        isFile = new File(path).isFile();
        verifyPath();
    }
    Paths(Paths parent,String path,boolean isFIle){
        String parentPath = parent.path;
        if(!(parent.path.endsWith("\\")||parent.path.endsWith("/"))){
            parentPath += "/";
        }
        if(path.startsWith("\\")||path.startsWith("/")){
            path = path.substring(1);
        }
        this.path = parentPath + path;
        if(isFIle){
            isFile = true;
            verifyFile();
        }
    }
    Paths(Path path){
        this.path = path.toString();
        verifyPath();
    }
    Paths(Paths parent,Path path){
        String parentPath = parent.path;
        if(!(parent.path.endsWith("\\")||parent.path.endsWith("/"))){
            parentPath += "/";
        }
        this.path = parentPath + path;
        verifyPath();
    }
    Paths(Paths parent,Path path,boolean dontVerify){
        String parentPath = parent.path;
        if(!(parent.path.endsWith("\\")||parent.path.endsWith("/"))){
            parentPath += "/";
        }
        this.path = parentPath + path;
        if(!dontVerify) {
            verifyPath();
        }
    }
    public Path asPath(){
        return Path.of(path).toAbsolutePath();
    }
    public Path asPath(String additional){
        return Path.of(path+"_"+additional).toAbsolutePath();
    }
    public Path withPath(String path){
        return Path.of(this.path).resolve(path).toAbsolutePath();
    }
    public String asString(){
        return path;
    }
    public void verifyPath(){
        if (Files.notExists(Path.of(path))) {
            try {
                Files.createDirectories(Path.of(path));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void verifyPath(String additional){
        if (Files.notExists(Path.of(additional))) {
            try {
                Files.createDirectories(Path.of(additional));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void verifyFile(){
        if (Files.notExists(Path.of(path))) {
            try {
                Files.createFile(Path.of(path));
                FileUtils.writeFile(Path.of(path).toFile(), "{}");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void verifyFile(String path){
        if (Files.notExists(Path.of(path))) {
            try {
                Files.createFile(Path.of(path));
                FileUtils.writeFile(Path.of(path).toFile(), "{}");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public File withFile(String name){
        return asPath().resolve(name).toFile();
    }
    public File asAugmentedFile(String name){
        verifyFile(name);
        return asPath(name).toFile();
    }
    public File asFile(){
        verifyFile();
        return asPath().toFile();
    }
    public void verify(){
        if(isFile){
            verifyFile();
        } else {
            verifyPath();
        }
    }
    public static List<File> listFiles(File dir) {
        return Stream.of(dir.listFiles())
                .filter(file -> !file.isDirectory())
                .toList();
    }
}
