package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Arrays;
import java.util.Locale;

public enum DataPortType implements SpawnLocation {
    AGR,
    CMD,
    EXT,
    HAB,
    IND,
    LAB,
    MED,
    PWR,
    SEC;


    public static DataPortType forName(final String name) {
        return DataPortType.valueOf(name.toUpperCase());
    }

    public static DataPortType forLocalizedName(final String name, final Locale locale) {
        return Arrays.stream(DataPortType.values())
                .filter((DataPortType dataPortType) -> LocaleService.getLocalizedStringForLocale(locale, dataPortType.getLocalizationKey()).equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


    @Override
    public String getLocalizationKey() {
        return "spawn.location.type.dataport." + this.name().toLowerCase();
    }
}
