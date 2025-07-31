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
