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
