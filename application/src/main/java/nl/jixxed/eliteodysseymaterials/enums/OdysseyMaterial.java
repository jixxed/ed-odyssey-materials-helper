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

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

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

    default String getCategoryLocalizationKey(){
        return getStorageType().getLocalizationKey();
    }

    default EdAwesomeIcon[] getMaterialIcons() {
        final boolean isEngineerUnlockMaterial = (ApplicationState.getInstance().getSoloMode()) ? OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(this) : OdysseyBlueprintConstants.isEngineeringIngredient(this);

        List<EdAwesomeIcon> edAwesomeIcons = new ArrayList<>();
        if (this.isUnknown()) {
            edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_DATA);
        } else if (this instanceof Data) {
            edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_DATA);
        } else if (this instanceof Good) {
            edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_GOOD_1);
            edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_GOOD_2);
        } else if (this instanceof Asset asset) {
            switch (asset.getType()) {
                case TECH -> {
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_TECH_1);
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_TECH_2);
                }
                case CIRCUIT -> {
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_CIRCUIT_1);
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_CIRCUIT_2);
                }
                case CHEMICAL -> {
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_CHEMICAL_1);
                    edAwesomeIcons.add(EdAwesomeIcon.MATERIALS_CHEMICAL_2);
                }
            }
        }
        if (isEngineerUnlockMaterial) {
            edAwesomeIcons.add(EdAwesomeIcon.SHIPS_ENGINEER);
        }else if (this.isPowerplay()) {
            edAwesomeIcons.add(EdAwesomeIcon.OTHER_POWERPLAY_OPEN);
        }
        return edAwesomeIcons.toArray(EdAwesomeIcon[]::new);
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

    static OdysseyMaterial forLocalizedNameSpaceInsensitive(final String name, final Locale locale) {
        return Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values())))
                .filter((OdysseyMaterial odysseyMaterial) -> LocaleService.getLocalizedStringForLocale(locale, odysseyMaterial.getLocalizationKey()).replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", "")))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @SuppressWarnings("unchecked")
    static BoundExtractedResult<OdysseyMaterial> forLocalizedNameSpaceInsensitiveFuzzy(final String name, final Locale locale) {
       return (BoundExtractedResult<OdysseyMaterial>)(BoundExtractedResult<?>)FuzzySearch.extractOne(name.replaceAll("\\s", ""), Stream.concat(Arrays.stream(Data.values()), Stream.concat(Arrays.stream(Asset.values()), Arrays.stream(Good.values()))).toList(),
                odysseyMaterial -> LocaleService.getLocalizedStringForLocale(locale, odysseyMaterial.getLocalizationKey()).replaceAll("\\s", ""));
    }

    boolean isUnknown();

    boolean isIllegal();

    boolean isPowerplay();

    String name();

    String getTypeNameLocalized();
}
