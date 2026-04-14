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
    POI,
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
