package dev.kofeychi.packer;

import java.io.File;

public record PackGeneratorInfos(
    String rename,
    String description,
    boolean containsLeftFiles,

    File Model,
    File ModelTexture,

    File ModelRight,
    File ModelTextureRight,

    String emissive_prefix
){}
