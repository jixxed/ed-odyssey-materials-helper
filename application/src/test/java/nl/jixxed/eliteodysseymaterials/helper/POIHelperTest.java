package nl.jixxed.eliteodysseymaterials.helper;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class POIHelperTest {
    private final Map<String, String> testMappings = Map.ofEntries(
            Map.entry("$POI_CrashedShip:#index=1;", "Crashed Ship (1)"),
            Map.entry("$POIScenario_Watson_Abandoned_Buggy_01_Salvage_Medium; $USS_ThreatLevel:#threatLevel=2;", "Distress Beacon (Buggy) [Threat 2] (Mission)"),
            Map.entry("$POIScenario_Watson_Abandoned_Buggy_01_Easy;", "Distress Beacon (Buggy) [Threat 1]"),
            Map.entry("$POIScenario_Watson_Damaged_Eagle_01_Easy;", "Distress Beacon (Eagle) [Threat 1]"),
            Map.entry("$POIScenario_Watson_Damaged_Eagle_01_Assassination_Easy; $USS_ThreatLevel:#threatLevel=1;", "Encrypted Signal (Eagle) [Threat 1] (Mission)"),
            Map.entry("$POIScenario_Watson_Damaged_Sidewinder_01_Easy;", "Distress Beacon (Sidewinder) [Threat 1]"),
            Map.entry("$POIScenario_Watson_Smugglers_Cache_01_Hard;", "Irregular Marker (Smugglers Cache, 2 Container + Skimmer) [Threat 3]"),
            Map.entry("$POIScenario_Watson_Smugglers_Cache_02_Heist_Easy; $USS_ThreatLevel:#threatLevel=1;", "Irregular Marker (Smugglers Cache, 3 Container + Buggy) [Threat 1] (Mission)"),
            Map.entry("$POIScenario_Watson_Smugglers_Cache_02_Heist_Easy;", "Irregular Marker (Smugglers Cache, 3 Container + Buggy) [Threat 1]"),
            Map.entry("$POIScenario_Watson_Wreckage_Buggy_01_Easy;", "Minor Wreckage (Buggy Type 1) [Threat 1]"),
            Map.entry("$POIScenario_Watson_Wreckage_Buggy_01_Salvage_Easy; $USS_ThreatLevel:#threatLevel=1;", "Minor Wreckage (Buggy Type 1) [Threat 1] (Mission)"),
            Map.entry("$POIScenario_Watson_Wreckage_Buggy_01_Salvage_Easy;", "Minor Wreckage (Buggy Type 1) [Threat 1]"),
            Map.entry("$POIScenario_Watson_Wreckage_Probe_01_Hard;", "Impact Site (Probe) [Threat 3]"),
            Map.entry("$POIScene_Wreckage_Probe_Skimmers;", "Impact Site (Probe with skimmers)"),
            Map.entry("$POIScenario_Watson_Wreckage_Satellite_01_Salvage_Easy; $USS_ThreatLevel:#threatLevel=1;", "Impact Site (Satellite) [Threat 1] (Mission)"),
            Map.entry("$POIScenario_Watson_Wrecks_Anaconda_01_Easy;", "Crash Site (Anaconda) [Threat 1]"),
            Map.entry("$POIScenario_Watson_Wrecks_Eagle_01_Salvage_Easy; $USS_ThreatLevel:#threatLevel=1;", "Crash Site (Eagle) [Threat 1] (Mission)"),
            Map.entry("$POIScenario_Watson_Wrecks_Fighter_01_Salvage_Easy; $USS_ThreatLevel:#threatLevel=1;", "Crash Site (Fighter) [Threat 1] (Mission)"),
            Map.entry("$POIScenario_Watson_Wrecks_Sidewinder_01_Salvage_Easy; $USS_ThreatLevel:#threatLevel=1;", "Crash Site (Sidewinder) [Threat 1] (Mission)"),
            Map.entry("$POIScene_Escape_Pod_02;", "Escape Pod (Type 2)"),
            Map.entry("$POIScene_Wreckage_Buggy_02;", "Minor Wreckage (Buggy Type 2)"),
            Map.entry("$POIScene_Wrecks_Eagle_01;", "Crash Site (Eagle)"),
            Map.entry("$POIScene_Wrecks_FedFighter_01_Skimmers;", "Crash Site (FedFighter Skimmers)"),
            Map.entry("$POIScene_Wrecks_Sidewinder_01;", "Crash Site (Sidewinder)"),
            Map.entry("$SAA_Unknown_Signal:#type=$SAA_SignalType_Human;:#index=0;", "Human Signal (0)"),
            Map.entry("$Settlement_Tiny_BuggyWreck_CG:#index=3;", "$Settlement_Tiny_BuggyWreck_CG:#index=3;")
    );

    @Test
    void map() {
        assertAll(() -> this.testMappings.forEach((key, value) -> assertThat(POIHelper.map(key)).isEqualTo(POIHelper.map(value))));
    }
}