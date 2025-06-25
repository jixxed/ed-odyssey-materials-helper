package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.ShipModuleButton;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.SlotBox;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShipModuleButtonBuilder extends AbstractButtonBaseBuilder<ShipModuleButtonBuilder> {
    private ShipModule shipModule;
    private SlotBox slotBox;

    public static ShipModuleButtonBuilder builder() {
        return new ShipModuleButtonBuilder();
    }


    public ShipModuleButtonBuilder withShipModule(final ShipModule shipModule) {
        this.shipModule = shipModule;
        return this;
    }

    public ShipModuleButtonBuilder withSlotBox(final SlotBox slotBox) {
        this.slotBox = slotBox;
        return this;
    }

    @SuppressWarnings("unchecked")
    public ShipModuleButton build() {
        final ShipModuleButton button = new ShipModuleButton(this.slotBox, this.shipModule);
        super.build(button);
        return button;
    }

}
