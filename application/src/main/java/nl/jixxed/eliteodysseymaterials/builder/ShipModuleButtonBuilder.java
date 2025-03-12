package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.ShipModuleButton;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShipModuleButtonBuilder extends AbstractButtonBaseBuilder<ShipModuleButtonBuilder> {
    private ShipModule shipModule;

    public static ShipModuleButtonBuilder builder() {
        return new ShipModuleButtonBuilder();
    }


    public ShipModuleButtonBuilder withShipModule(final ShipModule shipModule) {
        this.shipModule = shipModule;
        return this;
    }

    @SuppressWarnings("unchecked")
    public ShipModuleButton build() {
        final ShipModuleButton button = new ShipModuleButton(this.shipModule);
        super.build(button);
        return button;
    }

}
