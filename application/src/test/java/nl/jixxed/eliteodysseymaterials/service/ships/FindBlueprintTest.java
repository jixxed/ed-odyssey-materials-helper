package nl.jixxed.eliteodysseymaterials.service.ships;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBiFunction;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class FindBlueprintTest {

    @Test
    public void testFindBlueprintHardpointStats() {
//        Power
//        ShipModule.getBasicModules();
        List<ShipModule> modules = ShipModule.getModules(SlotType.HARDPOINT);
        modules.forEach(module -> {
            if (module.getAttibutes().contains(HorizonsModifier.BREACH_DAMAGE)){
                double value = Math.round((double) module.getAttributeValue(HorizonsModifier.BREACH_DAMAGE) / (double) module.getAttributeValue(HorizonsModifier.DAMAGE)* 100D);
                double valueMin = Math.round(((double) module.getAttributeValue(HorizonsModifier.BREACH_DAMAGE) -0.05 )/ (double) module.getAttributeValue(HorizonsModifier.DAMAGE)* 100D);
                double valueMax =Math.round(( (double) module.getAttributeValue(HorizonsModifier.BREACH_DAMAGE) + 0.05) / (double) module.getAttributeValue(HorizonsModifier.DAMAGE)* 100D);
                log.info("module " + module.getId() + " does " + value + "% breach damage. min/max: " + valueMin + "%/" +valueMax+"%");
            }
//            if (!module.getAttibutes().contains(HorizonsModifier.DAMAGE_PER_SECOND)){
//                log.info("module " + module.getId() + " doe not contain DAMAGE_PER_SECOND" );
//            }
//            if (!module.getAttibutes().contains(HorizonsModifier.BURST_RATE_OF_FIRE)){
//                log.info("module " + module.getId() + " doe not contain BURST_RATE_OF_FIRE" );
//            }
//            if (!module.getAttibutes().contains(HorizonsModifier.BURST_SIZE)){
//                log.info("module " + module.getId() + " doe not contain BURST_SIZE" );
//            }
//            if (!module.getAttibutes().contains(HorizonsModifier.BURST_INTERVAL)){
//                log.info("module " + module.getId() + " doe not contain BURST_INTERVAL" );
//            }
//            if (!module.getAttibutes().contains(HorizonsModifier.AMMO_CLIP_SIZE)){
//                log.info("module " + module.getId() + " doe not contain AMMO_CLIP_SIZE" );
//            }
//            if (!module.getAttibutes().contains(HorizonsModifier.CHARGE_TIME)){
//                log.info("module " + module.getId() + " doe not contain CHARGE_TIME" );
//            }
//            if (!module.getAttibutes().contains(HorizonsModifier.DAMAGE)){
//                log.info("module " + module.getId() + " doe not contain DAMAGE" );
//            }
//            if (!module.getAttibutes().contains(HorizonsModifier.ROUNDS_PER_SHOT)){
//                log.info("module " + module.getId() + " doe not contain ROUNDS_PER_SHOT" );
//            }
        });


//        if (HorizonsModifier.DAMAGE_PER_SECOND.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.DAMAGE)) {
//            if((double)this.attributes.get(HorizonsModifier.BURST_INTERVAL) == 0D){
//                return getAttributeValue(HorizonsModifier.DAMAGE, completeness);
//            }
//            return (double) getAttributeValue(HorizonsModifier.DAMAGE, completeness) * getAttributeValueOrDefault(HorizonsModifier.RATE_OF_FIRE, completeness, 1.0) * getAttributeValueOrDefault(HorizonsModifier.ROUNDS_PER_SHOT, completeness, 1.0);
//        }
//        if (HorizonsModifier.RATE_OF_FIRE.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.BURST_INTERVAL)) {
//            // Rate of Fire = Ammo Clip / ((Charge time + (Burst Size - 1) / Burst Rate of Fire + Burst Interval) * ceil(Ammo Clip / Burst Size))
//            if((double)this.attributes.get(HorizonsModifier.BURST_INTERVAL) == 0D){
//                return Double.POSITIVE_INFINITY;
//            }
//            double burstSize = getAttributeValueOrDefault(HorizonsModifier.BURST_SIZE, completeness, 1.0);
//            double ammoClipSize = getAttributeValueOrDefault(HorizonsModifier.AMMO_CLIP_SIZE, completeness, burstSize);// default to burstSize if there is no ammo clip
//            double chargeTime = 0D;//getAttributeValueOrDefault(HorizonsModifier.CHARGE_TIME, completeness, 0.0);
//            double burstRateOfFire = getAttributeValueOrDefault(HorizonsModifier.BURST_RATE_OF_FIRE, completeness, -1.0);
//            double burstInterval = getAttributeValueOrDefault(HorizonsModifier.BURST_INTERVAL, completeness, 1.0);
//            log.info("BURST_INTERVAL: " + burstInterval + " @ " + completeness);
//            return ammoClipSize / ((chargeTime + (burstSize - 1) / burstRateOfFire + burstInterval) * Math.ceil(ammoClipSize / burstSize));
//        }
    }


    @Test
    public void testFindBlueprintHardpoint() {
        ShipModule.getBasicModules();
        HorizonsBlueprintConstants.getHardpointBlueprints().forEach((horizonsBlueprintName, horizonsBlueprintTypeMapMap) -> {
            horizonsBlueprintTypeMapMap.forEach((horizonsBlueprintType, horizonsBlueprintTypeMap) -> {
                if(horizonsBlueprintTypeMap.containsKey(HorizonsBlueprintGrade.GRADE_2)) {
                    HorizonsBlueprint horizonsBlueprint1 = horizonsBlueprintTypeMap.get(HorizonsBlueprintGrade.GRADE_1);
                    HorizonsBlueprint horizonsBlueprint2 = horizonsBlueprintTypeMap.get(HorizonsBlueprintGrade.GRADE_2);
                    horizonsBlueprint2.getModifiers().forEach((horizonsModifier, horizonsModifierValue) -> {
                        HorizonsBiFunction.CalculationType calculationType = horizonsModifierValue.getModifier().getCalculationType();
                        if(calculationType == HorizonsBiFunction.CalculationType.PERCENTAGE_POSITIVE || calculationType == HorizonsBiFunction.CalculationType.PERCENTAGE_NEGATIVE){
                            double end2 = horizonsModifierValue.getModifier().getEnd();
                            HorizonsModifierValue horizonsModifierValue1 = horizonsBlueprint1.getModifiers().get(horizonsModifier);
                            if(horizonsModifierValue1 != null) {
                                double end1 = horizonsModifierValue1.getModifier().getEnd();

                                if(!Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))).equals(Formatters.NUMBER_FORMAT_2.format(horizonsModifierValue1.getModifier().getStart()))){
                                    if(horizonsModifierValue.isPositive()) {
                                        log.debug("\u001B[32m" + horizonsBlueprintName + " " + horizonsBlueprintType + " " + horizonsModifier + " " + Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))) + "\u001B[0m");
                                    }else {
                                        log.debug("\u001B[31m" + horizonsBlueprintName + " " + horizonsBlueprintType + " " + horizonsModifier + " " + Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))) + "\u001B[0m");
                                    }
                                }
                            }
                        }
                    });
                }
            });
        });
    }
    @Test
    public void testFindBlueprintCore() {
        ShipModule.getBasicModules();
        HorizonsBlueprintConstants.getCoreInternalBlueprints().forEach((horizonsBlueprintName, horizonsBlueprintTypeMapMap) -> {
            horizonsBlueprintTypeMapMap.forEach((horizonsBlueprintType, horizonsBlueprintTypeMap) -> {
                if(horizonsBlueprintTypeMap.containsKey(HorizonsBlueprintGrade.GRADE_2)) {
                    HorizonsBlueprint horizonsBlueprint1 = horizonsBlueprintTypeMap.get(HorizonsBlueprintGrade.GRADE_1);
                    HorizonsBlueprint horizonsBlueprint2 = horizonsBlueprintTypeMap.get(HorizonsBlueprintGrade.GRADE_2);
                    horizonsBlueprint2.getModifiers().forEach((horizonsModifier, horizonsModifierValue) -> {
                        HorizonsBiFunction.CalculationType calculationType = horizonsModifierValue.getModifier().getCalculationType();
                        if(calculationType == HorizonsBiFunction.CalculationType.PERCENTAGE_POSITIVE || calculationType == HorizonsBiFunction.CalculationType.PERCENTAGE_NEGATIVE){
                            double end2 = horizonsModifierValue.getModifier().getEnd();
                            HorizonsModifierValue horizonsModifierValue1 = horizonsBlueprint1.getModifiers().get(horizonsModifier);
                            if(horizonsModifierValue1 != null) {
                                double end1 = horizonsModifierValue1.getModifier().getEnd();

                                if(!Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))).equals(Formatters.NUMBER_FORMAT_2.format(horizonsModifierValue1.getModifier().getStart()))){
                                    if(horizonsModifierValue.isPositive()) {
                                        log.debug("\u001B[32m" + horizonsBlueprintName + " " + horizonsBlueprintType + " " + horizonsModifier + " " + Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))) + "\u001B[0m");
                                    }else {
                                        log.debug("\u001B[31m" + horizonsBlueprintName + " " + horizonsBlueprintType + " " + horizonsModifier + " " + Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))) + "\u001B[0m");
                                    }
                                }
                            }
                        }
                    });
                }
            });
        });
    }

    @Test
    public void testFindBlueprintOpt() {
        ShipModule.getBasicModules();
        HorizonsBlueprintConstants.getOptionalInternalBlueprints().forEach((horizonsBlueprintName, horizonsBlueprintTypeMapMap) -> {
            horizonsBlueprintTypeMapMap.forEach((horizonsBlueprintType, horizonsBlueprintTypeMap) -> {
                if(horizonsBlueprintTypeMap.containsKey(HorizonsBlueprintGrade.GRADE_2)) {
                    HorizonsBlueprint horizonsBlueprint1 = horizonsBlueprintTypeMap.get(HorizonsBlueprintGrade.GRADE_1);
                    HorizonsBlueprint horizonsBlueprint2 = horizonsBlueprintTypeMap.get(HorizonsBlueprintGrade.GRADE_2);
                    horizonsBlueprint2.getModifiers().forEach((horizonsModifier, horizonsModifierValue) -> {
                        HorizonsBiFunction.CalculationType calculationType = horizonsModifierValue.getModifier().getCalculationType();
                        if(calculationType == HorizonsBiFunction.CalculationType.PERCENTAGE_POSITIVE || calculationType == HorizonsBiFunction.CalculationType.PERCENTAGE_NEGATIVE){
                            double end2 = horizonsModifierValue.getModifier().getEnd();
                            HorizonsModifierValue horizonsModifierValue1 = horizonsBlueprint1.getModifiers().get(horizonsModifier);
                            if(horizonsModifierValue1 != null) {
                                double end1 = horizonsModifierValue1.getModifier().getEnd();

                                if(!Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))).equals(Formatters.NUMBER_FORMAT_2.format(horizonsModifierValue1.getModifier().getStart()))){
                                    if(horizonsModifierValue.isPositive()) {
                                        log.debug("\u001B[32m" + horizonsBlueprintName + " " + horizonsBlueprintType + " " + horizonsModifier + " " + Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))) + "\u001B[0m");
                                    }else {
                                        log.debug("\u001B[31m" + horizonsBlueprintName + " " + horizonsBlueprintType + " " + horizonsModifier + " " + Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))) + "\u001B[0m");
                                    }
                                }
                            }
                        }
                    });
                }
            });
        });
    }

    @Test
    public void testFindBlueprintUtil() {
        ShipModule.getBasicModules();
        HorizonsBlueprintConstants.getUtilityMountBlueprints().forEach((horizonsBlueprintName, horizonsBlueprintTypeMapMap) -> {
            horizonsBlueprintTypeMapMap.forEach((horizonsBlueprintType, horizonsBlueprintTypeMap) -> {
                if(horizonsBlueprintTypeMap.containsKey(HorizonsBlueprintGrade.GRADE_2)) {
                    HorizonsBlueprint horizonsBlueprint1 = horizonsBlueprintTypeMap.get(HorizonsBlueprintGrade.GRADE_1);
                    HorizonsBlueprint horizonsBlueprint2 = horizonsBlueprintTypeMap.get(HorizonsBlueprintGrade.GRADE_2);
                    horizonsBlueprint2.getModifiers().forEach((horizonsModifier, horizonsModifierValue) -> {
                        HorizonsBiFunction.CalculationType calculationType = horizonsModifierValue.getModifier().getCalculationType();
                        if(calculationType == HorizonsBiFunction.CalculationType.PERCENTAGE_POSITIVE || calculationType == HorizonsBiFunction.CalculationType.PERCENTAGE_NEGATIVE){
                            double end2 = horizonsModifierValue.getModifier().getEnd();
                            HorizonsModifierValue horizonsModifierValue1 = horizonsBlueprint1.getModifiers().get(horizonsModifier);
                            if(horizonsModifierValue1 != null) {
                                double end1 = horizonsModifierValue1.getModifier().getEnd();

                                if(!Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))).equals(Formatters.NUMBER_FORMAT_2.format(horizonsModifierValue1.getModifier().getStart()))){
                                    if(horizonsModifierValue.isPositive()) {
                                        log.debug("\u001B[32m" + horizonsBlueprintName + " " + horizonsBlueprintType + " " + horizonsModifier + " " + Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))) + "\u001B[0m");
                                    }else {
                                        log.debug("\u001B[31m" + horizonsBlueprintName + " " + horizonsBlueprintType + " " + horizonsModifier + " " + Formatters.NUMBER_FORMAT_2.format((end1 - (end2 - end1))) + "\u001B[0m");
                                    }
                                }
                            }
                        }
                    });
                }
            });
        });
    }
}
