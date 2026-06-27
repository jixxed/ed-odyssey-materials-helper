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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
public enum Currency implements Material {
    MERC_COIN(new EdAwesomeIcon[]{EdAwesomeIcon.OTHER_MERCCOIN}),
    ARX(new EdAwesomeIcon[]{EdAwesomeIcon.OTHER_ARX}),
    CREDITS(new EdAwesomeIcon[]{EdAwesomeIcon.OTHER_CREDITS}),
    UNKNOWN(new EdAwesomeIcon[]{EdAwesomeIcon.OTHER_CREDITS});
    @Getter
    private final EdAwesomeIcon[] icons;

    @Override
    public String getLocalizationKey() {
        return "material.currency." + this.name().toLowerCase();
    }

    @Override
    public String getCategoryLocalizationKey(){
        return getMaterialType().getLocalizationKey();
    }

    @Override
    public boolean isUnknown() {
        return this == Currency.UNKNOWN;
    }

    public int getMaxAmount() {
        return 9999;
    }

    public static Currency forName(final String name) {
        try {
            return Currency.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Currency.UNKNOWN;
        }
    }

    public static Currency forLocalizedName(final String name) {
        Optional<Currency> first = Arrays.stream(values())
                .filter(raw -> LocaleService.getLocalizedStringForLocale(Locale.ENGLISH, raw.getLocalizationKey()).equalsIgnoreCase(name))
                .findFirst();
        return first.orElse(Currency.UNKNOWN);
    }

    public HorizonsMaterialType getMaterialType() {
        return HorizonsMaterialType.CURRENCY;
    }

    public static Currency[] materialsForType(final HorizonsMaterialType materialType) {
        return Arrays.stream(Currency.values())
                .filter(currency -> currency.getMaterialType().equals(materialType))
                .toList().toArray(Currency[]::new);
    }

    public HorizonsStorageType getStorageType() {
        return HorizonsStorageType.OTHER;
    }

}
