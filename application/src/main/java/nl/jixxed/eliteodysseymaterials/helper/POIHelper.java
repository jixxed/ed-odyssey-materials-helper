package nl.jixxed.eliteodysseymaterials.helper;

import java.util.Map;

public class POIHelper {
    private static final Map<String, String> TRANSLATION = Map.ofEntries(
            Map.entry("Smugglers_Cache_01", "Irregular Marker (Smugglers Cache, 2 Container + Skimmer)"),
            Map.entry("Smugglers_Cache_02", "Irregular Marker (Smugglers Cache, 3 Container + Buggy)"),
            Map.entry("Wreckage_Cargo_01", "Minor Wreckage (Cargo Type 1)"),
            Map.entry("Wreckage_Cargo_02", "Minor Wreckage (Cargo Type 2)"),
            Map.entry("Wreckage_Cargo_03", "Minor Wreckage (Cargo Type 3)"),
            Map.entry("Wreckage_Cargo_04", "Minor Wreckage (Cargo Type 4)"),
            Map.entry("Wreckage_Buggy_01", "Minor Wreckage (Buggy Type 1)"),
            Map.entry("Wreckage_Buggy_02", "Minor Wreckage (Buggy Type 2)"),
            Map.entry("Wreckage_BuggyDrone", "Minor Wreckage (Buggy with skimmers)"),
            Map.entry("Wreckage_Satellite_01", "Impact Site (Satellite)"),
            Map.entry("Wreckage_Probe_01", "Impact Site (Probe)"),
            Map.entry("Wreckage_Probe_Skimmers", "Impact Site (Probe with skimmers)"),
            Map.entry("Trap_Cargo_01", "Irregular Marker (Cargo Trap)"),
            Map.entry("Trap_Cargo_02", "Artificial Structure (Cargo Trap)"),
            Map.entry("Trap_Data_01", "Artificial Structure (Data Trap)"),
            Map.entry("Trap_Data_02", "Artificial Structure (Data Trap)"),
            Map.entry("Wrecks_Anaconda_01", "Crash Site"),
            Map.entry("Wrecks_Sidewinder_01", "Crash Site"),
            Map.entry("Wrecks_Eagle_01", "Crash Site"),
            Map.entry("Wrecks_Fighter_01", "Crash Site"),
            Map.entry("Wrecks_FedFighter_01_Skimmers", "Crash Site"),
            Map.entry("Damaged_Eagle_01_Assassination", "Encrypted Signal"),
            Map.entry("Damaged_Eagle_01", "Distress Beacon"),
            Map.entry("Damaged_Sidewinder_01_Assassination", "Encrypted Signal"),
            Map.entry("Damaged_Sidewinder_01", "Distress Beacon"),
            Map.entry("Abandoned_Buggy_01", "Distress Beacon"),
            Map.entry("Perimeter_01", "Active Power Source (Type 1)"),
            Map.entry("Perimeter_02", "Active Power Source (Type 2)"),
            Map.entry("Perimeter_03", "Active Power Source (Type 3)"),
            Map.entry("Escape_Pod_01", "Escape Pod (Type 1)"),
            Map.entry("Escape_Pod_02", "Escape Pod (Type 2)"),
            Map.entry("Escape_Pod_03", "Escape Pod (Type 3)"),
            Map.entry("SAA_SignalType_Human", "Human Signal"),
            Map.entry("SAA_SignalType_Thargoid", "Thargoid Signal"),
            Map.entry("SAA_SignalType_Guardian", "Guardian Signal"),
            Map.entry("SAA_SignalType_Geological", "Geological Signal"),
            Map.entry("SAA_SignalType_Biological", "Biological Signal"),
            Map.entry("SAA_SignalType_Other", "Signal (Other)"),
            Map.entry("CrashedShip", "Crashed Ship")
    );

    public static String map(final String poi) {
        if (poi.startsWith("$")) {
            String tidiedPoi = poi.replace("$POIScene_", "")
                    .replace("$POIScenario_", "")
                    .replace("$SAA_Unknown_Signal:#type=$", "")
                    .replace("$POI_", "")
                    .replace("Watson_", "")
                    .replace("_Heist", "")
                    .replace("_Salvage", "")
                    .replace(";", "");

            final StringBuilder poiBuilder = new StringBuilder();
            if (poi.contains("_Easy")) {
                poiBuilder.append(" [Threat 1]");
                tidiedPoi = tidiedPoi.replace("_Easy", "");
            }
            if (poi.contains("_Medium")) {
                poiBuilder.append(" [Threat 2]");
                tidiedPoi = tidiedPoi.replace("_Medium", "");
            }
            if (poi.contains("_Hard")) {
                poiBuilder.append(" [Threat 3]");
                tidiedPoi = tidiedPoi.replace("_Hard", "");
            }
            if (poi.contains(":#index=")) {
                poiBuilder.append(" (")
                        .append(tidiedPoi.substring(tidiedPoi.indexOf(":#index=") + 8))
                        .append(")");
                tidiedPoi = tidiedPoi.substring(0, tidiedPoi.indexOf(":#index="));
            }
            if (poi.contains("$USS_ThreatLevel")) {
                poiBuilder.append(" (Mission)");
                tidiedPoi = tidiedPoi.substring(0, tidiedPoi.indexOf("$USS_ThreatLevel"));
            }

            final String cleanPoiName = TRANSLATION.get(tidiedPoi.trim());
            if (cleanPoiName != null) {
                poiBuilder.insert(0, cleanPoiName);
            } else {
                return poi;
            }
            return poiBuilder.toString();
        }
        return poi;
    }
}
