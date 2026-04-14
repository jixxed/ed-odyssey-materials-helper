/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract sealed class HorizonsWishlistBlueprint implements WishlistBlueprint<HorizonsBlueprintName> permits HorizonsExperimentalWishlistBlueprint,
        HorizonsModuleWishlistBlueprint,
        HorizonsSynthesisWishlistBlueprint,
        HorizonsTechBrokerWishlistBlueprint,
        HorizonsEngineerWishlistBlueprint {
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    @EqualsAndHashCode.Include
    private HorizonsBlueprintName recipeName;
    @ClipboardJsonIgnore
    private boolean visible = true;

    private int quantity = 1;

    public HorizonsWishlistBlueprint(final HorizonsBlueprintName recipeName, final boolean visible) {
        this.recipeName = recipeName;
        this.visible = visible;
    }

    public static HorizonsBlueprintType getBlueprintType(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
            return horizonsModuleWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsExperimentalWishlistBlueprint horizonsExperimentalWishlistBlueprint) {
            return horizonsExperimentalWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsTechBrokerWishlistBlueprint horizonsTechBrokerWishlistBlueprint) {
            return horizonsTechBrokerWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsEngineerWishlistBlueprint) {
            return HorizonsBlueprintType.ENGINEER;
        }
        return null;
    }

    public static HorizonsBlueprintGrade getBlueprintGrade(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsSynthesisWishlistBlueprint horizonsSynthesisWishlistBlueprint) {
            return horizonsSynthesisWishlistBlueprint.getBlueprintGrade();
        }
        return HorizonsBlueprintGrade.NONE;
    }
}
