package dev.kofeychi.packer.generator;

import dev.kofeychi.packer.Main;
import dev.kofeychi.packer.data.Prop;

public class PropertyGenerator {
    public static String generateProperty(Prop property) {
        String val = "";
        var properties = property.getProps();
        for (var prop : properties) {
            val += prop.toProp() + System.lineSeparator();
        }
        return val;
    }
}
