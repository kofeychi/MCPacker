package dev.kofeychi.packer.data;

import java.util.List;

public class ModelDefinitionProp implements Prop {
    public ListPropEntry entry = new ListPropEntry("items", List.of());
    public PropertyEntry model = new PropertyEntry("model", "undefined");
    public PropertyEntry predicate = new PropertyEntry("nbt.display.Name", "pattern:undefined");
    public ModelDefinitionProp withItems(List<String> items) {
        this.entry.props.clear();
        this.entry.props.addAll(items);
        return this;
    }
    public ModelDefinitionProp withItems(ListPropEntry items) {
        this.entry = items;
        return this;
    }
    public ModelDefinitionProp withModel(String model) {
        this.model.val = model;
        return this;
    }
    public ModelDefinitionProp withPredicate(String pred) {
        this.predicate.val = "pattern:"+pred;
        return this;
    }
    @Override
    public List<PropertyEntry> getProps() {
        return List.of(
                entry,
                model,
                predicate
        );
    }
}
