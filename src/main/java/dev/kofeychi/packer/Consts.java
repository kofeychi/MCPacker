package dev.kofeychi.packer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.kofeychi.packer.data.ListPropEntry;

import java.util.List;

public class Consts {
    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();
    public static final ListPropEntry DEFAULT_SWORDS = new ListPropEntry("items", List.of(
            "netherite_sword",
            "diamond_sword",
            "golden_sword",
            "iron_sword",
            "stone_sword",
            "wooden_sword"
    ));
}
