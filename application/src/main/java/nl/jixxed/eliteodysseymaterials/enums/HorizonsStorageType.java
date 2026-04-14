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

public enum HorizonsStorageType implements StorageType {
    RAW,
    ENCODED,
    MANUFACTURED,
    COMMODITY,
    OTHER,
    UNKNOWN;

    public static HorizonsStorageType forMaterial(final HorizonsMaterial horizonsMaterial) {
        return HorizonsStorageType.valueOf(remapCommodity(horizonsMaterial.getClass().getSimpleName().toUpperCase()));
    }

    private static String remapCommodity(String type) {
        //if REGULARCOMMODITY or RARECOMMODITY, return COMMODITY
        if (type.contains("COMMODITY")) {
            return "COMMODITY";
        }
        return type;
    }

    public static HorizonsStorageType forName(final String name) {
        try {
            return HorizonsStorageType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return HorizonsStorageType.UNKNOWN;
        }

    }

    public String getLocalizationKey() {
        return "storage.type.horizons.name." + this.name().toLowerCase();
    }
}
