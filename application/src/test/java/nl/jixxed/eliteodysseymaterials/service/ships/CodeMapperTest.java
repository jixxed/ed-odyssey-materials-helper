package nl.jixxed.eliteodysseymaterials.service.ships;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeMapperTest {


    Map<String, String> mapping = Map.ofEntries(
            Map.entry("topspd", ""),
            Map.entry("bstspd", ""),
            Map.entry("minthrust", ""),
            Map.entry("boostcost", ""),
            Map.entry("mnv", ""),
            Map.entry("pitch", ""),
            Map.entry("yaw", ""),
            Map.entry("roll", ""),
            Map.entry("minpitch", ""),
            Map.entry("minyaw", ""),
            Map.entry("minroll", ""),
            Map.entry("shields", ""),
            Map.entry("armour", ""),
            Map.entry("hardness", ""),
            Map.entry("heatcap", ""),
            Map.entry("heatdismin", ""),
            Map.entry("heatdismax", ""),
            Map.entry("missile", ""),
            Map.entry("mass", "Mass"),
            Map.entry("masslock", ""),
            Map.entry("fuelcost", ""),
            Map.entry("fuelreserve", ""),
            Map.entry("integ", "Integrity"),
            Map.entry("pwrdraw", "PowerDraw"),
            Map.entry("boottime", "BootTime"),
            Map.entry("spinup", "ShieldBankSpinUp"),
            Map.entry("scbdur", "ShieldBankDuration"),
            Map.entry("shieldrnfps", "ShieldBankReinforcement"),
            Map.entry("scbheat", "ShieldBankHeat"),
            Map.entry("dps", "DamagePerSecond"),
            Map.entry("sdps", ""),
            Map.entry("damage", "Damage"),
            Map.entry("duration", ""),
            Map.entry("dmgmul", ""),
            Map.entry("distdraw", "DistributorDraw"),
            Map.entry("eps", ""),
            Map.entry("seps", ""),
            Map.entry("thmload", "ThermalLoad"),
            Map.entry("hps", ""),
            Map.entry("shps", ""),
            Map.entry("pierce", "ArmourPenetration"),
            Map.entry("maximumrng", "MaximumRange"),
            Map.entry("dmgfall", "FalloffRange"),
            Map.entry("shotspd", "ShotSpeed"),
            Map.entry("rof", "RateOfFire"),
            Map.entry("srof", ""),
            Map.entry("bstint", ""),
            Map.entry("bstrof", "BurstRateOfFire"),
            Map.entry("bstsize", "BurstSize"),
            Map.entry("ammoclip", "AmmoClipSize"),
            Map.entry("ammomax", "AmmoMaximum"),
            Map.entry("rounds", "RoundsPerShot"),
            Map.entry("rldtime", "ReloadTime"),
            Map.entry("brcdmg", "BreachDamage"),
            Map.entry("minbrc", "MinBreachChance"),
            Map.entry("maxbrc", "MaxBreachChance"),
            Map.entry("jitter", "Jitter"),
            Map.entry("kinwgt", ""),
            Map.entry("thmwgt", ""),
            Map.entry("expwgt", ""),
            Map.entry("abswgt", ""),
            Map.entry("cauwgt", ""),
            Map.entry("axewgt", ""),
            Map.entry("genminmass", "ShieldGenMinimumMass"),
            Map.entry("genoptmass", "ShieldGenOptimalMass"),
            Map.entry("genmaxmass", "ShieldGenMaximumMass"),
            Map.entry("genminmul", "ShieldGenMinStrength"),
            Map.entry("genoptmul", "ShieldGenStrength"),
            Map.entry("genmaxmul", "ShieldGenMaxStrength"),
            Map.entry("genrate", "RegenRate"),
            Map.entry("bgenrate", "BrokenRegenRate"),
            Map.entry("genpwr", "EnergyPerRegen"),
            Map.entry("fsdoptmass", "FSDOptimalMass"),
            Map.entry("fsdheat", "FSDHeatRate"),
            Map.entry("maxfuel", "MaxFuelPerJump"),
            Map.entry("engminmass", "EngineMinimumMass"),
            Map.entry("engoptmass", "EngineOptimalMass"),
            Map.entry("engmaxmass", "MaximumMass"),
            Map.entry("engminmul", "EngineMinPerformance"),
            Map.entry("engoptmul", "EngineOptPerformance"),
            Map.entry("engmaxmul", "EngineMaxPerformance"),
            Map.entry("minmulspd", ""),
            Map.entry("optmulspd", ""),
            Map.entry("maxmulspd", ""),
            Map.entry("minmulacc", ""),
            Map.entry("optmulacc", ""),
            Map.entry("maxmulacc", ""),
            Map.entry("minmulrot", ""),
            Map.entry("optmulrot", ""),
            Map.entry("maxmulrot", ""),
            Map.entry("engheat", "EngineHeatRate"),
            Map.entry("pwrcap", "PowerCapacity"),
            Map.entry("pwrbst", ""),
            Map.entry("heateff", "HeatEfficiency"),
            Map.entry("wepcap", "WeaponsCapacity"),
            Map.entry("wepchg", "WeaponsRecharge"),
            Map.entry("engcap", "EnginesCapacity"),
            Map.entry("engchg", "EnginesRecharge"),
            Map.entry("syscap", "SystemsCapacity"),
            Map.entry("syschg", "SystemsRecharge"),
            Map.entry("hullbst", "DefenceModifierHealthMultiplier"),
            Map.entry("hullrnf", "DefenceModifierHealthAddition"),
            Map.entry("shieldbst", "DefenceModifierShieldMultiplier"),
            Map.entry("shieldrnf", "DefenceModifierShieldAddition"),
            Map.entry("absres", "CollisionResistance"),
            Map.entry("kinres", "KineticResistance"),
            Map.entry("thmres", "ThermicResistance"),
            Map.entry("expres", "ExplosiveResistance"),
            Map.entry("caures", "CausticResistance"),
            Map.entry("axeres", ""),
            Map.entry("timerng", "FSDInterdictorRange"),
            Map.entry("facinglim", "FSDInterdictorFacingLimit"),
            Map.entry("scanrng", "ScannerRange"),
            Map.entry("maxangle", "MaxAngle"),
            Map.entry("scantime", "ScannerTimeToScan"),
            Map.entry("jamdur", "ChaffJamDuration"),
            Map.entry("ecmrng", "ECMRange"),
            Map.entry("ecmdur", "ECMTimeToCharge"),
            Map.entry("ecmpwr", "ECMActivePowerConsumption"),
            Map.entry("ecmheat", "ECMHeat"),
            Map.entry("ecmcool", "ECMCooldown"),
            Map.entry("hsdur", "HeatSinkDuration"),
            Map.entry("thmdrain", "ThermalDrain"),
            Map.entry("vslots", "NumBuggySlots"),
            Map.entry("vcount", ""),
            Map.entry("cargocap", "CargoCapacity"),
            Map.entry("maxlimpet", "MaxActiveDrones"),
            Map.entry("lpactrng", ""),
            Map.entry("targetrng", "DroneTargetRange"),
            Map.entry("limpettime", "DroneLifeTime"),
            Map.entry("maxspd", "DroneSpeed"),
            Map.entry("multispd", "DroneMultiTargetSpeed"),
            Map.entry("fuelxfer", "DroneFuelCapacity"),
            Map.entry("lmprepcap", "DroneRepairCapacity"),
            Map.entry("hacktime", "DroneHackingTime"),
            Map.entry("mincargo", "DroneMinJettisonedCargo"),
            Map.entry("maxcargo", "DroneMaxJettisonedCargo"),
            Map.entry("minebonus", ""),
            Map.entry("scooprate", "FuelScoopRate"),
            Map.entry("fuelcap", "FuelCapacity"),
            Map.entry("emgcylife", "OxygenTimeCapacity"),
            Map.entry("bins", "RefineryBins"),
            Map.entry("afmrepcap", "AFMRepairCapacity"),
            Map.entry("repaircon", "AFMRepairConsumption"),
            Map.entry("repairrtg", "AFMRepairPerAmmo"),
            Map.entry("maxrng", "MaxRange"),
            Map.entry("scanangle", "SensorTargetScanAngle"),
            Map.entry("typemis", "Range"),
            Map.entry("cabincap", "CabinCapacity"),
            Map.entry("cabincls", "CabinClass"),
            Map.entry("barrierrng", "DisruptionBarrierRange"),
            Map.entry("barrierdur", "DisruptionBarrierChargeDuration"),
            Map.entry("barrierpwr", "DisruptionBarrierActivePower"),
            Map.entry("barriercool", "DisruptionBarrierCooldown"),
            Map.entry("jumpbst", "FSDJumpRangeBoost"),
            Map.entry("dmgprot", "ModuleDefenceAbsorption"),
//            Map.entry("scanrngmod", "DSS_RangeMult"),
//            Map.entry("scanangmod", "DSS_AngleMult"),
//            Map.entry("scanratemod", "DSS_RateMult"),
            Map.entry("proberad", "DSS_PatchRadius"),
            Map.entry("mlctype", "")
    );

    @Test
    void name() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
        objectMapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS,true);
//        String name = "hardpoints";
        String name = "optional";
//        String name = "utilities";
//        String name = "hardpoints";
        try (InputStream in = CodeMapperTest.class.getResourceAsStream("/ships/"+name+".json")) {

            File file = new File("C:\\Users\\jixxed\\IdeaProjects\\ed-odyssey-materials\\application\\src\\test\\resources\\ships\\"+name+".out");
            file.createNewFile();
            OutputStream output = new FileOutputStream(file);
            new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines().forEach(line -> {
                try {
                    final JsonNode node = objectMapper.readTree(line);
                    String linez = mapOptional(line, node);
                    output.write(linez.getBytes(StandardCharsets.UTF_8));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            output.flush();
            output.close();
        }
    }
//    { mtype:'hfc', cost:   36000, name:'Fragment Cannon',             mount:'F',              class:1, rating:'E', mass: 2.00, integ:40, pwrdraw:0.45, boottime:0, dps:95.333, damage: 1.430, distdraw:0.210, thmload:0.41, pierce: 20, maximumrng:2000, shotspd: 667, rof:5.556, bstint:0.180,                      ammoclip: 3, ammomax: 180, rounds:12, rldtime:5.0, brcdmg: 1.3, minbrc:40, maxbrc:80, jitter:5.0, kinwgt:100, thmwgt:0, dmgfall:1800, ammocost:17, fdid:128049448, fdname:'Hpt_Slugshot_Fixed_Small', eddbid:860 }

//    public static final FragmentCannon FRAGMENT_CANNON_1_E_F       = new FragmentCannon("CANNON_1_E_F"              , HorizonsBlueprintName.FRAGMENT_CANNON, ModuleSize.SIZE_1, ModuleClass.E, false, Mounting.FIXED    ,      36000, "Hpt_Slugshot_Fixed_Small"      , Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.45), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 95.333), Map.entry(HorizonsModifier.DAMAGE,  1.430), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.210), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.41), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  20.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 2000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  667.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 5.556), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.180), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  3.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT, 12.0), Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  1.3), Map.entry(HorizonsModifier.BREACH_CHANCE_MIN, 40.0), Map.entry(HorizonsModifier.BREACH_CHANCE_MAX, 80.0), Map.entry(HorizonsModifier.JITTER, 5.0), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 1800.0), Map.entry(HorizonsModifier.AMMO_COST, 17.0)));

    private String mapHardpoint(String line, JsonNode node) throws URISyntaxException, IOException {
        String type = node.get("name").asText().replace(" ", "");
        String name = node.get("name").asText().toUpperCase().replace(" ", "_");
        String constName = node.get("name").asText().toUpperCase().replace(" ", "_") + "_" + node.get("class").asText() + "_" + node.get("rating").asText() + "_" + node.get("mount").asText();
        List<Pair> entries = new ArrayList<>();
        mapping.forEach((key, fdName) -> {
            if (node.has(key)) {
                String keyName = "";
                try {
                    keyName = fdName.isBlank() ? key : HorizonsModifier.forInternalName(fdName).name();

                } catch (IllegalArgumentException ex) {

                    keyName = key + "EXC";
                }
                final String text = node.get(key).asText();
                try {
                    final double v = Double.parseDouble(text);
                    entries.add(new Pair(line.indexOf(key), key, "Map.entry(HorizonsModifier." + keyName + ",  " + v + ")"));
                } catch (NumberFormatException | NullPointerException ex) {
                    entries.add(new Pair(line.indexOf(key), key, "Map.entry(HorizonsModifier." + keyName + ", FAIL \"" + text + "\")"));
                }

            }
        });
        final String linez = "public static final " + type + " " + constName + " = new " + type + "(\"" + constName + "\", HorizonsBlueprintName."
                + name + ", ModuleSize.SIZE_" + node.get("class").asText() + ", ModuleClass." + node.get("rating").asText() + ", "
                + ((node.has("tag")) ? "Origin." + mapOrigin(node.get("tag").asText()) + ", " : "")
                + node.get("mount").asText().equals("T") + ", Mounting." + mapMount(node.get("mount").asText()) + ", "
                + node.get("cost").asText() + ", \""
                + node.get("fdname").asText() + "\", Map.ofEntries("
                + entries.stream().sorted(Comparator.comparing(Pair::getIndex)).map(Pair::getData).collect(Collectors.joining(", ")) + "));" + "\n";

        System.out.print(linez);
        return linez;
    }


    private String mapUtility(String line, JsonNode node) throws URISyntaxException, IOException {
        String type = node.get("name").asText().replace(" ", "");
        String name = node.get("name").asText().toUpperCase().replace(" ", "_");
        String constName = node.get("name").asText().toUpperCase().replace(" ", "_") + "_" + node.get("class").asText() + "_" + node.get("rating").asText();
        List<Pair> entries = new ArrayList<>();
        mapping.forEach((key, fdName) -> {
            if (node.has(key)) {
                String keyName = "";
                try {
                    keyName = fdName.isBlank() ? key : HorizonsModifier.forInternalName(fdName).name();

                } catch (IllegalArgumentException ex) {

                    keyName = key + "EXC";
                }
                final String text = node.get(key).asText();
                try {
                    final double v = Double.parseDouble(text);
                    entries.add(new Pair(line.indexOf(key), key, "Map.entry(HorizonsModifier." + keyName + ",  " + v + ")"));
                } catch (NumberFormatException | NullPointerException ex) {
                    entries.add(new Pair(line.indexOf(key), key, "Map.entry(HorizonsModifier." + keyName + ", FAIL \"" + text + "\")"));
                }

            }
        });
        final String linez = "public static final " + type + " " + constName + " = new " + type + "(\"" + constName + "\", HorizonsBlueprintName."
                + name + ", ModuleSize.SIZE_" + node.get("class").asText() + ", ModuleClass." + node.get("rating").asText() + ", "
                + ((node.has("tag")) ? "Origin." + mapOrigin(node.get("tag").asText()) + ", " : "")
                +( node.get("name").asText().equals("Point Defence") || node.get("name").asText().equals("Shield Booster")) + (node.has("mount") ?", Mounting." + mapMount(node.get("mount").asText()) : "") + ", "
                + node.get("cost").asText() + ", \""
                + node.get("fdname").asText() + "\", Map.ofEntries("
                + entries.stream().sorted(Comparator.comparing(Pair::getIndex)).map(Pair::getData).collect(Collectors.joining(", ")) + "));" + "\n";

        System.out.print(linez);
        return linez;
    }



    private String mapOptional(String line, JsonNode node) throws URISyntaxException, IOException {
        String type = node.get("name").asText().replace(" ", "");
        String name = node.get("name").asText().toUpperCase().replace(" ", "_");
        String constName = node.get("name").asText().toUpperCase().replace(" ", "_") + "_" + node.get("class").asText() + "_" + node.get("rating").asText();
        List<Pair> entries = new ArrayList<>();
        mapping.forEach((key, fdName) -> {
            if (node.has(key)) {
                String keyName = "";
                try {
                    keyName = fdName.isBlank() ? key : HorizonsModifier.forInternalName(fdName).name();

                } catch (IllegalArgumentException ex) {

                    keyName = key + "EXC";
                }
                final String text = node.get(key).asText();
                try {
                    final double v = Double.parseDouble(text);
                    entries.add(new Pair(line.indexOf(key), key, "Map.entry(HorizonsModifier." + keyName + ",  " + v + ")"));
                } catch (NumberFormatException | NullPointerException ex) {
                    entries.add(new Pair(line.indexOf(key), key, "Map.entry(HorizonsModifier." + keyName + ", FAIL \"" + text + "\")"));
                }

            }
        });
        final String linez = "public static final " + type + " " + constName + " = new " + type + "(\"" + constName + "\", HorizonsBlueprintName."
                + name + ", ModuleSize.SIZE_" + node.get("class").asText() + ", ModuleClass." + node.get("rating").asText() + ", "
                + ((node.has("tag")) ? "Origin." + mapOrigin(node.get("tag").asText()) + ", " : "")
                +( (node.get("name").asText().contains("Limpet") && !node.get("name").asText().contains("Prospec")) || node.get("name").asText().equals("Detailed Surface Scanner") || node.get("name").asText().equals("Shield Cell Bank")) + (node.has("mount") ?", Mounting." + mapMount(node.get("mount").asText()) : "") + ", "
                + node.get("cost").asText() + ", \""
                + node.get("fdname").asText() + "\", Map.ofEntries("
                + entries.stream().sorted(Comparator.comparing(Pair::getIndex)).map(Pair::getData).collect(Collectors.joining(", ")) + "));" + "\n";

        System.out.print(linez);
        return linez;
    }
    @Data
    @AllArgsConstructor
class Pair{
        int index;
        String key;
        String data;
}
    private String mapOrigin(String tag) {
        return switch (tag) {
            case "P" -> "POWERPLAY";
            case "G" -> "GUARDIAN";
            default -> "";
        };
    }

    private String mapMount(String mount) {
        return switch (mount) {
            case "F" -> "FIXED";
            case "G" -> "GIMBALLED";
            case "T" -> "TURRETED";
            default -> "";
        };
    }

}
