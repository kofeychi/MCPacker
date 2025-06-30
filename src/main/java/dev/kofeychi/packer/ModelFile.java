package dev.kofeychi.packer;

import dev.kofeychi.packer.data.Display;
import dev.kofeychi.packer.data.Element;

import java.util.HashMap;

public class ModelFile {
    public String credit;
    public float[] texture_size;
    public HashMap<String,String> textures;
    public Element[] elements;
    public HashMap<String, Display> display;
}
