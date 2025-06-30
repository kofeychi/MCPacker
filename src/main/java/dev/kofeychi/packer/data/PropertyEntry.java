package dev.kofeychi.packer.data;

public class PropertyEntry {
    public String id = "";
    public String val = "";

    public PropertyEntry(String id, String val) {
        this.id = id;
        this.val = val;
    }
    public PropertyEntry() {}

    public String toProp(){
        return id+"="+val;
    }
}
