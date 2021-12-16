package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Material {

    default StorageType getStorageType() {
        return StorageType.OTHER;
    }

    static Material subtypeForName(final String name) {
        final String fixedName = hotfixName(name);
        Material material = Data.forName(fixedName);
        if (material.isUnknown()) {
            material = Good.forName(fixedName);
            if (material.isUnknown()) {
                material = Asset.forName(fixedName);
                if (material.isUnknown()) {
                    material = Consumable.forName(fixedName);
                    if (material.isUnknown()) {
                        material = TradeMaterial.forName(fixedName);
                        if (material.isUnknown()) {
                            throw new IllegalArgumentException("Unknown material type for name: " + fixedName);
                        }
                    }
                }
            }
        }
        return material;
    }

    static String hotfixName(final String name) {
        if ("ENHANCEDINTERROGATION".equalsIgnoreCase(name)) {
            return "ENHANCEDINTERROGATIONRECORDINGS";
        } else if ("GEOGRAPHICALDATA".equalsIgnoreCase(name)) {
            return "GEOLOGICALDATA";
        }
        return name;
    }

    static List<Material> getAllMaterials() {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter(material -> !material.isUnknown())
                .map(Material.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static List<Material> getAllRelevantMaterials() {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter(material -> !material.isUnknown())
                .filter(RecipeConstants::isEngineeringOrBlueprintIngredient)
                .map(Material.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static Material forLocalizedName(final String name) {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Stream.concat(Arrays.stream(Good.values()), Arrays.stream(TradeMaterial.values()))))
                .filter((Material material) -> LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()).equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    String getLocalizationKey();

    boolean isUnknown();

    boolean isIllegal();
}
