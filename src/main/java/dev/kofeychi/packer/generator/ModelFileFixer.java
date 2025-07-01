package dev.kofeychi.packer.generator;

import dev.kofeychi.packer.ANSI;
import dev.kofeychi.packer.ModelFile;

import java.util.Arrays;
import java.util.HashMap;

import static dev.kofeychi.packer.Main.p;

public class ModelFileFixer {
    public static ModelFile fix(ModelFile mf,String hashedNames,String additional) {
        var replaces = new HashMap<String, String>();
        mf.textures.forEach((k, v) -> {
            if(v.contains("/")){
                var split = v.split("/");
                v = Arrays.stream(split).toList().getLast();
            }
            if(!v.contains("./")){
                replaces.put(k, "./"+hashedNames+additional);
                p(ANSI.Augment("THIS PACK MAY CONTAIN ERRORS IN JSON,JSON WAS AUTOMATICALLY REFIXED BUT YOU MAY STILL NEED TO FIX TEXTURE LOCATIONS!",ANSI.ANSI_RED));
            } else {
                replaces.put(k, hashedNames+additional);
            }
        });
        mf.textures.clear();
        mf.textures = replaces;
        return mf;
    }
}
