package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public sealed interface OdysseyMaterial extends Material permits Asset, Consumable, Data, Good {

    default OdysseyStorageType getStorageType() {
        return OdysseyStorageType.OTHER;
    }

    static OdysseyMaterial subtypeForName(final String name) {
        final String fixedName = hotfixName(name);
        OdysseyMaterial odysseyMaterial = Data.forName(fixedName);
        if (odysseyMaterial.isUnknown()) {
            odysseyMaterial = Good.forName(fixedName);
            if (odysseyMaterial.isUnknown()) {
                odysseyMaterial = Asset.forName(fixedName);
                if (odysseyMaterial.isUnknown()) {
                    odysseyMaterial = Consumable.forName(fixedName);
                    if (odysseyMaterial.isUnknown()) {
                        throw new IllegalArgumentException("Unknown material type for name: " + fixedName);
                    }
                }
            }
        }
        return odysseyMaterial;
    }

    static String hotfixName(final String name) {
        if ("ENHANCEDINTERROGATION".equalsIgnoreCase(name)) {
            return "ENHANCEDINTERROGATIONRECORDINGS";
        } else if ("GEOGRAPHICALDATA".equalsIgnoreCase(name)) {
            return "GEOLOGICALDATA";
        }
        return name;
    }

    static List<OdysseyMaterial> getAllMaterials() {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter(material -> !material.isUnknown())
                .map(OdysseyMaterial.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static List<OdysseyMaterial> getAllRelevantMaterialsWithoutOverride() {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter(material -> !material.isUnknown())
                .filter(OdysseyBlueprintConstants::isEngineeringOrBlueprintIngredient)
                .map(OdysseyMaterial.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static List<OdysseyMaterial> getAllIrrelevantMaterialsWithoutOverride() {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter(material -> !material.isUnknown())
                .filter(OdysseyBlueprintConstants::isNotRelevantAndNotRequiredEngineeringIngredient)
                .map(OdysseyMaterial.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static OdysseyMaterial forLocalizedName(final String name) {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter((OdysseyMaterial odysseyMaterial) -> LocaleService.getLocalizedStringForCurrentLocale(odysseyMaterial.getLocalizationKey()).equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    static OdysseyMaterial forLocalizedName(final String name, final Locale locale) {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter((OdysseyMaterial odysseyMaterial) -> LocaleService.getLocalizedStringForLocale(locale, odysseyMaterial.getLocalizationKey()).equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    String getLocalizationKey();

    boolean isUnknown();

    boolean isIllegal();

    String name();

    String getTypeNameLocalized();
}
