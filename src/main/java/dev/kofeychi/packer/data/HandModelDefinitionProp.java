package dev.kofeychi.packer.data;

import java.util.List;

public class HandModelDefinitionProp extends ModelDefinitionProp {
    public PropertyEntry hand = new PropertyEntry("hand","main");
    public HandModelDefinitionProp withHand(String hand) {
        this.hand.val = hand;
        return this;
    }

    @Override
    public List<PropertyEntry> getProps() {
        return List.of(
                entry,
                model,
                predicate,
                hand
        );
    }
}
