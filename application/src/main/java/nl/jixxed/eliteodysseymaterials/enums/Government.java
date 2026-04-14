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
public enum Government {
    ANARCHY("$government_Anarchy;", "Anarchy"),
    COMMUNISM("$government_Communism;", "Communism"),
    CONFEDERACY("$government_Confederacy;", "Confederacy"),
    COOPERATIVE("$government_Cooperative;", "Cooperative"),
    CORPORATE("$government_Corporate;", "Corporate"),
    DEMOCRACY("$government_Democracy;", "Democracy"),
    DICTATORSHIP("$government_Dictatorship;", "Dictatorship"),
    FEUDAL("$government_Feudal;", "Feudal"),
    IMPERIAL("$government_Imperial;", "Imperial"),
    PATRONAGE("$government_Patronage;", "Patronage"),
    PRISONCOLONY("$government_PrisonColony;", "PrisonColony"),
    PRISON("$government_Prison;", "Prison"),
    THEOCRACY("$government_Theocracy;", "Theocracy"),
    ENGINEER("$government_Engineer;", "Engineer"),
    PRIVATEOWNERSHIP("$government_Carrier;", "PrivateOwnership"),
    MEGACONSTRUCTION("$government_Megaconstruction;", "Megaconstruction"),
    NONE("$government_None;", "None"),
    UNKNOWN("", "Unknown");
    private final String id;
    private final String key;

    public static Government forKey(final String key) {
        return Arrays.stream(Government.values()).filter(factionState -> factionState.key.equalsIgnoreCase(key)).findFirst().orElse(Government.UNKNOWN);
    }

    public static Government forId(final String id) {
        return Arrays.stream(Government.values()).filter(factionState -> factionState.id.equalsIgnoreCase(id)).findFirst().orElse(Government.UNKNOWN);
    }

    public String getLocalizationKey() {
        return "government." + this.name().toLowerCase();
    }
}
