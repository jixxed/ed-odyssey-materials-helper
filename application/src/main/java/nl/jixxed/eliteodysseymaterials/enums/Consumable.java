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

public enum Consumable implements OdysseyMaterial {
    BYPASS(true),
    HEALTHPACK(false),
    ENERGYCELL(false),
    AMM_GRENADE_EMP(false),
    AMM_GRENADE_FRAG(false),
    AMM_GRENADE_SHIELD(false),
    UNKNOWN(false);

    private final boolean illegal;

    Consumable(final boolean illegal) {
        this.illegal = illegal;
    }

    public static Consumable forName(final String name) {
        try {
            return Consumable.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Consumable.UNKNOWN;
        }
    }

    @Override
    public OdysseyStorageType getStorageType() {
        return OdysseyStorageType.CONSUMABLE;
    }


    @Override
    public String getLocalizationKey() {
        return "material.consumable." + this.toString().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Consumable.UNKNOWN;
    }

    @Override
    public boolean isIllegal() {
        return this.illegal;
    }

    @Override
    public boolean isPowerplay() {
        return false;
    }

    @Override
    public String getTypeNameLocalized() {
        return LocaleService.getLocalizedStringForCurrentLocale("material.asset.type.consumable");
    }

}
