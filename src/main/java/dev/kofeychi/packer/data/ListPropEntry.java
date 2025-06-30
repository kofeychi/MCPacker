package dev.kofeychi.packer.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ListPropEntry extends PropertyEntry{
    public List<String> props = new ArrayList<>();

    public ListPropEntry(String id, Collection<String> props) {
        this.id = id;
        this.props.addAll(props);
    }

    @Override
    public String toProp() {
        return id+"="+joinSeparate();
    }
    private String joinSeparate(){
        AtomicReference<String> out = new AtomicReference<>("");
        props.forEach(prop->{
            out.set(out.get()+" "+prop);
        });
        out.set(out.get().substring(1,out.get().length()));
        return out.get();
    }
}
