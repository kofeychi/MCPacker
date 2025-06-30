package dev.kofeychi.packer;

import dev.kofeychi.packer.data.EmissiveProp;
import dev.kofeychi.packer.generator.PropertyGenerator;
import dev.kofeychi.packer.util.FileUtils;
import dev.kofeychi.packer.util.Zipper;

import java.nio.file.Path;

import static dev.kofeychi.packer.Consts.gson;

public class Generator {
    public static void generate(PackGeneratorInfos infos) {



        postGenerate(infos);

        finalize(infos);
    }
    public static void postGenerate(PackGeneratorInfos infos) {
        FileUtils.writeFile(Paths.EMISSIVE.asFile(), PropertyGenerator.generateProperty(
                new EmissiveProp(infos.emissive_prefix())
        ));
        FileUtils.writeFile(Paths.PACK.asFile(), gson.toJson(
                new PackMetaFile().description(infos.description()+" - note that this was generated using ").withFormat(6)
        ));
    }
    public static void finalize(PackGeneratorInfos infos) {
        Zipper.zipFolder(Paths.GEN.asPath(),Paths.OUT.asPath());
    }
}
