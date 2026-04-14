/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
