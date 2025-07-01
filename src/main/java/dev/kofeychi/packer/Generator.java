package dev.kofeychi.packer;

import com.google.common.hash.Hashing;
import dev.kofeychi.packer.data.EmissiveProp;
import dev.kofeychi.packer.data.HandModelDefinitionProp;
import dev.kofeychi.packer.data.ModelDefinitionProp;
import dev.kofeychi.packer.generator.ModelFileFixer;
import dev.kofeychi.packer.generator.PropertyGenerator;
import dev.kofeychi.packer.util.FileUtils;
import dev.kofeychi.packer.util.Zipper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static dev.kofeychi.packer.Consts.gson;
import static dev.kofeychi.packer.Main.p;

public class Generator {
    public static void generate(PackGeneratorInfos infos) {
        var hash = infos.rename().toLowerCase();

        var pathToProfileRoot = Paths.CIT.withPath(hash);
        Paths.verifyPath(pathToProfileRoot.toAbsolutePath().toString());

        copyFiles(infos,pathToProfileRoot,"packer_texture");
        copyModels(infos,pathToProfileRoot,"packer_model","packer_texture");
        postGenerate(infos);

        if(infos.containsLeftFiles()){
            FileUtils.writeFile(pathToProfileRoot.resolve(hash+".properties").toAbsolutePath().toFile(), PropertyGenerator.generateProperty(
                    new HandModelDefinitionProp()
                            .withHand("main")
                            .withItems(Consts.DEFAULT_SWORDS)
                            .withModel("packer_model_right.json")
                            .withPredicate(infos.rename())
            ));
            FileUtils.writeFile(pathToProfileRoot.resolve(hash+"_left.properties").toAbsolutePath().toFile(), PropertyGenerator.generateProperty(
                    new HandModelDefinitionProp()
                            .withHand("off")
                            .withItems(Consts.DEFAULT_SWORDS)
                            .withModel("packer_model_left.json")
                            .withPredicate(infos.rename())
            ));
        } else {
            FileUtils.writeFile(pathToProfileRoot.resolve(hash+".properties").toAbsolutePath().toFile(), PropertyGenerator.generateProperty(
                    new ModelDefinitionProp()
                            .withItems(Consts.DEFAULT_SWORDS)
                            .withModel(hash+"_right.json")
                            .withPredicate(infos.rename())
            ));
        }


        finalize(infos);
    }
    public static void postGenerate(PackGeneratorInfos infos) {
        FileUtils.writeFile(Paths.EMISSIVE.asFile(), PropertyGenerator.generateProperty(
                new EmissiveProp(infos.emissive_prefix())
        ));
        FileUtils.writeFile(Paths.PACK.asFile(), gson.toJson(
                new PackMetaFile().description(infos.description()+" -+- this was generated using kofeychi/MCPacker").withFormat(6)
        ));
    }
    public static void copyModels(PackGeneratorInfos infos,Path dest,String hash,String texture) {
        try {
            String datafixedModelRight = "";
            String datafixedModel = datafixModel(infos.Model(),texture,"_right");
            FileUtils.writeFile(dest.resolve(hash+"_right.json").toAbsolutePath().toFile(),
                    datafixedModel
                    );
            if(infos.containsLeftFiles()){
                datafixedModelRight = datafixModel(infos.ModelRight(),texture,"_left");
                FileUtils.writeFile(dest.resolve(hash+"_left.json").toAbsolutePath().toFile(),
                        datafixedModelRight
                );
            }
        } catch (Exception e){
            e.printStackTrace();
            p("Error copying files from " + infos.Model() + " to " + dest+" , halt.");
            System.exit(1);
        }
    }
    public static void copyFiles(PackGeneratorInfos infos,Path dest,String hash) {
        try {
            copyFile(infos.ModelTexture(), dest,hash,"_right.png");
            if(infos.containsLeftFiles()){
                copyFile(infos.ModelTextureRight(), dest,hash,"_left.png");
            }
        } catch (Exception e){
            e.printStackTrace();
            p("Error copying files from " + infos.Model() + " to " + dest+" , halt.");
            System.exit(1);
        }
    }
    private static void copyFile(File from, Path to,String hash,String additional) throws IOException {
        p("Copying " + from.getAbsoluteFile().toPath()+ " to " + to.resolve(from.getName()).toAbsolutePath());
        Files.copy(from.getAbsoluteFile().toPath(), to.resolve(from.getName()).toAbsolutePath().resolveSibling(hash+additional), StandardCopyOption.REPLACE_EXISTING);
    }
    public static String datafixModel(File path,String hash,String add) {
        String model = FileUtils.readFile(path.getAbsoluteFile().toPath());
        ModelFile modelFile = gson.fromJson(model, ModelFile.class);
        ModelFileFixer.fix(modelFile,hash,add);
        return gson.toJson(modelFile, ModelFile.class);
    }
    public static void finalize(PackGeneratorInfos infos) {
        Zipper.zipFolder(Paths.GEN.asPath(),Paths.OUT.asPath());
    }
    public static Path resolve(Path old,String path) {
        return old.resolve(path).toAbsolutePath();
    }
}
