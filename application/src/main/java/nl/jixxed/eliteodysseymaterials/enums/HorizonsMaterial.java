/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public sealed interface HorizonsMaterial extends Material permits EngineeringMaterial, Commodity {

    default HorizonsStorageType getStorageType() {
        return HorizonsStorageType.OTHER;
    }

    static HorizonsMaterial subtypeForName(final String name) {

        HorizonsMaterial material = Raw.forName(name);
        if (material.isUnknown()) {
            material = Encoded.forName(name);
            if (material.isUnknown()) {
                material = Manufactured.forName(name);
                if (material.isUnknown()) {
                    material = Commodity.forName(name);
                    if (material.isUnknown()) {
                        throw new IllegalArgumentException("Unknown material type for name: " + name);
                    }
                }
            }
        }
        return material;
    }

    static List<HorizonsMaterial> getAllMaterials() {
        return Stream.concat(Arrays.stream(Raw.values()), Stream.concat(Arrays.stream(Encoded.values()), Arrays.stream(Manufactured.values())))
                .filter(material -> !material.isUnknown())
                .map(HorizonsMaterial.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static HorizonsMaterial forLocalizedName(final String name) {
        return Stream.concat(Arrays.stream(Raw.values()), Stream.concat(Arrays.stream(Encoded.values()), Arrays.stream(Manufactured.values())))
                .filter((HorizonsMaterial material) -> LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()).equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    String getLocalizationKey();

    HorizonsMaterialType getMaterialType();

    boolean isUnknown();

    Rarity getRarity();

    GameVersion getGameVersion();

    String name();

    default int getMaxAmount() {
        return this.getRarity().getMaxAmount();
    }

    default MaterialTradeType getTradeType(final HorizonsMaterial otherMaterial) {
        if (getMaterialType() == otherMaterial.getMaterialType()) {
            if (otherMaterial.getRarity().getLevel() >= getRarity().getLevel()) {
                return MaterialTradeType.DOWNTRADE;
            } else {
                return MaterialTradeType.UPTRADE;
            }
        }
        if (getClass() == otherMaterial.getClass() && otherMaterial.getMaterialType() != HorizonsMaterialType.THARGOID && otherMaterial.getMaterialType() != HorizonsMaterialType.GUARDIAN) {
            if (otherMaterial.getRarity().getLevel() >= getRarity().getLevel()) {
                return MaterialTradeType.CROSS_DOWNTRADE;
            } else {
                return MaterialTradeType.CROSS_UPTRADE;
            }
        }
        return MaterialTradeType.IMPOSSIBLE;
    }
}
