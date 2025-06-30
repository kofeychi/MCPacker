package dev.kofeychi.packer.generator;

import dev.kofeychi.packer.ModelFile;

import java.util.HashMap;

public class ModelFileFixer {
    public static void fix(ModelFile mf) {
        var replaces = new HashMap<String, String>();
        mf.textures.forEach((k, v) -> {
            if(!v.contains("./")){
                replaces.put(k, "./"+v);
            } else {
                replaces.put(k, v);
            }
        });
        mf.textures.clear();
        mf.textures = replaces;
    }
}
