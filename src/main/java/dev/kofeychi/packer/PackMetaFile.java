package dev.kofeychi.packer;

import dev.kofeychi.packer.data.Pack;

public class PackMetaFile {
    public Pack pack = new Pack(0,"");
    public PackMetaFile withFormat(int format) {
        pack.pack_format = format;
        return this;
    }
    public PackMetaFile description(String description) {
        pack.description = description;
        return this;
    }
}
