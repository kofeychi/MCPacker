package dev.kofeychi.packer.data;

import java.util.List;

public class EmissiveProp implements Prop {
    public PropertyEntry entry = new PropertyEntry("suffix.emissive", "_e");
    @Override
    public List<PropertyEntry> getProps() {
        return List.of(entry);
    }
    public EmissiveProp() {}
    public EmissiveProp(String suffix) {
        entry = new PropertyEntry("suffix.emissive", suffix);
    }
}
