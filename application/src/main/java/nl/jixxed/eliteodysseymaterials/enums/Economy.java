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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Slf4j
public enum Economy {

    AGRI("$economy_Agri;", "Agriculture"),
    COLONY("$economy_Colony;", "Colony"),
    EXTRACTION("$economy_Extraction;", "Extraction"),
    HIGHTECH("$economy_HighTech;", "High Tech"),
    INDUSTRIAL("$economy_Industrial;", "Industrial"),
    MILITARY("$economy_Military;", "Military"),
    REFINERY("$economy_Refinery;", "Refinery"),
    SERVICE("$economy_Service;", "Service"),
    TERRAFORMING("$economy_Terraforming;", "Terraforming"),
    TOURISM("$economy_Tourism;", "Tourism"),
    PRISON("$economy_Prison;", "Prison"),
    DAMAGED("$economy_Damaged;", "Damaged"),
    RESCUE("$economy_Rescue;", "Rescue"),
    REPAIR("$economy_Repair;", "Repair"),
    CARRIER("$economy_Carrier;", "Private Enterprise"),
    ENGINEER("$economy_Engineer;", "Engineering"),
    NONE("$economy_None;", "None"),
    CONTRABAND("$economy_Contraband;", "Contraband"),
    UNKNOWN("", "Unknown");

    private final String key;
    private final String friendlyName;


    public static Economy forName(final String search) {
        return Arrays.stream(Economy.values()).filter(economy -> economy.friendlyName.equalsIgnoreCase(search)).findFirst().orElse(Economy.UNKNOWN);
    }

    public static Economy forKey(final String search) {
        return Arrays.stream(Economy.values()).filter(economy -> economy.key.equalsIgnoreCase(search)).findFirst().orElse(Economy.UNKNOWN);
    }

    public String getLocalizationKey() {
        return "economy." + this.name().toLowerCase();
    }
}
