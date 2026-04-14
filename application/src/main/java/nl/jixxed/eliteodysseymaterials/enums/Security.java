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

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Security {
    ANARCHY("$GAlAXY_MAP_INFO_state_anarchy;", "Anarchy"),
    LAWLESS("$GALAXY_MAP_INFO_state_lawless;", "Lawless"),
    HIGH("$SYSTEM_SECURITY_high;", "High"),
    LOW("$SYSTEM_SECURITY_low;", "Low"),
    MEDIUM("$SYSTEM_SECURITY_medium;", "Medium"),
    UNKNOWN("", "Unknown");

    private final String id;
    private final String key;

    public static Security forKey(final String key) {
        return Arrays.stream(Security.values()).filter(security -> security.key.equalsIgnoreCase(key)).findFirst().orElse(Security.UNKNOWN);
    }

    public static Security forId(final String id) {
        return Arrays.stream(Security.values()).filter(security -> security.id.equalsIgnoreCase(id)).findFirst().orElse(Security.UNKNOWN);
    }

    public String getLocalizationKey() {
        return "security." + this.name().toLowerCase();
    }
}
