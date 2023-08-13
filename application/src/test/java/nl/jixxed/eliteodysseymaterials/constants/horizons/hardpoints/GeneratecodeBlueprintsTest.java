package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class GeneratecodeBlueprintsTest {
    @Test
//    @Disabled
    void ExperimentalEffectBlueprints() {

        exp(ExperimentalEffectBlueprints.BEAM_LASER, "BEAM_LASER");
        exp(ExperimentalEffectBlueprints.BURST_LASER, "BURST_LASER");
        exp(ExperimentalEffectBlueprints.PULSE_LASER, "PULSE_LASER");
        exp(ExperimentalEffectBlueprints.MULTI_CANNON, "MULTI_CANNON");
        exp(ExperimentalEffectBlueprints.CANNON, "CANNON");

        exp(ExperimentalEffectBlueprints.FRAGMENT_CANNON, "FRAGMENT_CANNON");
        exp(ExperimentalEffectBlueprints.MISSILE_RACK, "MISSILE_RACK");
        exp(ExperimentalEffectBlueprints.TORPEDO_PYLON, "TORPEDO_PYLON");
        exp(ExperimentalEffectBlueprints.MINE_LAUNCHER, "MINE_LAUNCHER");
        exp(ExperimentalEffectBlueprints.PLASMA_ACCELERATOR, "PLASMA_ACCELERATOR");

        exp(ExperimentalEffectBlueprints.RAIL_GUN, "RAIL_GUN");
        exp(ExperimentalEffectBlueprints.POWER_PLANT, "POWER_PLANT");
        exp(ExperimentalEffectBlueprints.ARMOUR, "ARMOUR");
        exp(ExperimentalEffectBlueprints.HULL_REINFORCEMENT_PACKAGE, "HULL_REINFORCEMENT_PACKAGE");
        exp(ExperimentalEffectBlueprints.SHIELD_CELL_BANK, "SHIELD_CELL_BANK");

        exp(ExperimentalEffectBlueprints.SHIELD_BOOSTER, "SHIELD_BOOSTER");
        exp(ExperimentalEffectBlueprints.SHIELD_GENERATOR, "SHIELD_GENERATOR");
        exp(ExperimentalEffectBlueprints.FRAME_SHIFT_DRIVE, "FRAME_SHIFT_DRIVE");
        exp(ExperimentalEffectBlueprints.THRUSTERS, "THRUSTERS");
        exp(ExperimentalEffectBlueprints.POWER_DISTRIBUTOR, "POWER_DISTRIBUTOR");
    }
    void exp(final Map<HorizonsBlueprintType, HorizonsBlueprint> blueprints,String name) {
        var big = blueprints.size() > 10;
        System.out.println("public static final Map<HorizonsBlueprintType, HorizonsBlueprint> "+name+ (big?" = Map.ofEntries(":" = Map.of("));
        AtomicInteger bpcount = new AtomicInteger(0);
        blueprints.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey().name())).forEachOrdered((bpentry) -> {
            bpcount.getAndIncrement();
            var type = bpentry.getKey();
            var blueprint = bpentry.getValue();
//            public static final Map<HorizonsBlueprintType, HorizonsBlueprint> BEAM_LASER = Map.of(
//                    HorizonsBlueprintType.CONCORDANT_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.CONCORDANT_SEQUENCE,
//                            Map.of(
//                                    Manufactured.FOCUSCRYSTALS, 5,
//                                    Encoded.EMBEDDEDFIRMWARE, 3,
//                                    Raw.ZIRCONIUM, 1),
//                            Map.of(
//                                    HorizonsModifier.WING_SHIELD_REGENERATION_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
//                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+50%", false)),
//                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            if(big){
                System.out.println("Map.entry(");
            }
                System.out.println("HorizonsBlueprintType." + type.name() + ", " + "new HorizonsExperimentalEffectBlueprint(" + "HorizonsBlueprintName." + blueprint.getBlueprintName().name() + ", HorizonsBlueprintType." + blueprint.getHorizonsBlueprintType().name() + ",");
                System.out.println("Map.of(");
                final int amount = blueprint.getMaterialCollection(HorizonsMaterial.class).size();
                AtomicInteger count = new AtomicInteger(1);
                blueprint.getMaterialCollection(Raw.class).forEach((horizonsMaterial, integer) -> System.out.println("Raw." + horizonsMaterial + ", " + integer + ((count.getAndIncrement() < amount) ? "," : "")));
                blueprint.getMaterialCollection(Encoded.class).forEach((horizonsMaterial, integer) -> System.out.println("Encoded." + horizonsMaterial + ", " + integer + ((count.getAndIncrement() < amount) ? "," : "")));
                blueprint.getMaterialCollection(Manufactured.class).forEach((horizonsMaterial, integer) -> System.out.println("Manufactured." + horizonsMaterial + ", " + integer + ((count.getAndIncrement() < amount) ? "," : "")));
                System.out.println("),");
                System.out.println("Map.of(");
                final int amount2 = blueprint.getModifiers().size();
                AtomicInteger count2 = new AtomicInteger(1);
                blueprint.getModifiers().forEach((horizonsModifier, horizonsModifierValue) -> {
                    String method = determineMethod(horizonsModifierValue.modification());
                    double start = 0.0;
                    double end = 0.0;
                    if(UTF8Constants.CHECK_TRUE.equals(horizonsModifierValue.modification())){
                        System.out.println("HorizonsModifier." + horizonsModifier + ", new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, " + horizonsModifierValue.isPositive() + ", bool(Boolean.TRUE))" + ((count2.getAndIncrement() < amount2) ? "," : ""));

                    }else{
                        end = (double) Double.parseDouble(horizonsModifierValue.modification()
                                .replace("+", "")
                                .replace("-", "")
                                .replace("%", "")
                                .replace(",", ".")) / (!method.equals("constant") ? 100D : 1D);
                        System.out.println("HorizonsModifier." + horizonsModifier + ", new HorizonsNumberModifierValue(\"" + horizonsModifierValue.modification() + "\", " + horizonsModifierValue.isPositive() + ", " + method + "(" + (!method.equals("constant") ? String.valueOf(start) + ", " : "") + String.valueOf(end) + "))" + ((count2.getAndIncrement() < amount2) ? "," : ""));
                    }
                    });
                System.out.println("),");
                System.out.println("List.of(");
                for (Iterator<Engineer> iterator = blueprint.getEngineers().iterator(); iterator.hasNext(); ) {
                    Engineer engineer = iterator.next();
                    System.out.println("Engineer." + engineer + ((iterator.hasNext()) ? "," : ""));
                }
                System.out.println(")");
            if(big){
                System.out.println(")");
            }
            System.out.println(")" + (bpcount.get() < blueprints.size() ? "," : ""));
        });

        System.out.println(");");
    }
    void name(final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> blueprints) {
        System.out.println("public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(");
        AtomicInteger bpcount = new AtomicInteger(0);
        blueprints.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey().name())).forEachOrdered((bpentry) -> {
            bpcount.getAndIncrement();
            var type = bpentry.getKey();
            var gradeMap = bpentry.getValue();
            System.out.println("HorizonsBlueprintType." + type.name() + ",");
            System.out.println("Map.of(");
            gradeMap.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey().getGrade())).forEachOrdered((entry) -> {
                var grade = entry.getKey();
                var blueprint = entry.getValue();
                System.out.println("HorizonsBlueprintGrade." + grade + ", " + "new HorizonsModuleBlueprint(" + "HorizonsBlueprintName." + blueprint.getBlueprintName().name() + ", HorizonsBlueprintType." + blueprint.getHorizonsBlueprintType().name() + ", " + "HorizonsBlueprintGrade." + grade + ",");
                System.out.println("Map.of(");
                final int amount = blueprint.getMaterialCollection(HorizonsMaterial.class).size();
                AtomicInteger count = new AtomicInteger(1);
                blueprint.getMaterialCollection(Raw.class).forEach((horizonsMaterial, integer) -> System.out.println("Raw." + horizonsMaterial + ", " + integer + ((count.getAndIncrement() < amount) ? "," : "")));
                blueprint.getMaterialCollection(Encoded.class).forEach((horizonsMaterial, integer) -> System.out.println("Encoded." + horizonsMaterial + ", " + integer + ((count.getAndIncrement() < amount) ? "," : "")));
                blueprint.getMaterialCollection(Manufactured.class).forEach((horizonsMaterial, integer) -> System.out.println("Manufactured." + horizonsMaterial + ", " + integer + ((count.getAndIncrement() < amount) ? "," : "")));
                System.out.println("),");
                System.out.println("Map.of(");
                final int amount2 = blueprint.getModifiers().size();
                AtomicInteger count2 = new AtomicInteger(1);
                blueprint.getModifiers().forEach((horizonsModifier, horizonsModifierValue) -> {
                    String method = determineMethod(horizonsModifierValue.modification());
                    double start = (double) ((grade == HorizonsBlueprintGrade.GRADE_1) ? 0 : Double.parseDouble(gradeMap.get(HorizonsBlueprintGrade.forDigit(grade.getGrade() - 1)).getModifiers()
                            .getOrDefault(horizonsModifier, new HorizonsModifierValue("0", true))
                            .modification()
                            .replace("+", "")
                            .replace("-", "")
                            .replace("%", "")
                            .replace(",", "."))) / 100D;
                    double end = (double) Double.parseDouble(horizonsModifierValue.modification()
                            .replace("+", "")
                            .replace("-", "")
                            .replace("%", "")
                            .replace(",", ".")) / (!method.equals("constant") ? 100D : 1D);
                    System.out.println("HorizonsModifier." + horizonsModifier + ", new HorizonsNumberModifierValue(\"" + horizonsModifierValue.modification() + "\", " + horizonsModifierValue.isPositive() + ", " + method + "(" + (!method.equals("constant") ? String.valueOf(start) + ", " : "") + String.valueOf(end) + "))" + ((count2.getAndIncrement() < amount2) ? "," : ""));
                });
                System.out.println("),");
                System.out.println("List.of(");
                for (Iterator<Engineer> iterator = blueprint.getEngineers().iterator(); iterator.hasNext(); ) {
                    Engineer engineer = iterator.next();
                    System.out.println("Engineer." + engineer + ((iterator.hasNext()) ? "," : ""));
                }
                System.out.println(")");
                System.out.println(")" + (grade.getGrade() != gradeMap.size() ? "," : ""));
//                Map.of(
//                        Raw.CARBON, 1
//                ),
//                        Map.of(
//                                HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsModifierValue("+600%", true),
//                                HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("-2%", false),
//                                HorizonsModifier.BURST_SIZE, new HorizonsModifierValue("2", true),
//                                HorizonsModifier.CLIP_SIZE, new HorizonsModifierValue("4", true)
//                        ),
//                        List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            });
            System.out.println(")" + (bpcount.get() < blueprints.size() ? "," : ""));
        });

        System.out.println(");");
    }

    private String determineMethod(String modification) {
        if (modification.contains("%")) {
            if (modification.contains("-"))
                return "percentageNegative";
            if (modification.contains("+"))
                return "percentagePositive";
        } else {
            return "constant";
        }
        return "";
    }
}