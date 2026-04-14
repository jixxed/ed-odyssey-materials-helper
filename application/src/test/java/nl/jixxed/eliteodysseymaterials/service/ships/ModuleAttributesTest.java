/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.ships;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsExperimentalEffectBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.ShipModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
public class ModuleAttributesTest {

    @Test
    void testBlueprintsAttributePresence(){
        Assertions.assertAll(  ShipModuleService.getAllModules().stream().map(shipModule -> () -> {
                    final Set<HorizonsModifier> shipModuleAttibutes = shipModule.getAttibutes();
                    Assertions.assertAll(
                            shipModule.getAllowedBlueprints().stream()
                                    .flatMap(type-> HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(),type).stream()
                                            .map(grade -> new Pair<HorizonsBlueprintType, HorizonsBlueprintGrade>(type,grade)))
                                    .map(pair-> (HorizonsModuleBlueprint)HorizonsBlueprintConstants.getRecipe(shipModule.getName().getPrimary(),pair.getKey(),pair.getValue()))
                                    .map(blueprint-> blueprint.getModifiers().keySet())
                                    .map(attrs-> () -> Assertions.assertTrue(shipModuleAttibutes.containsAll(attrs.stream().filter(attr-> !ignored(attr)).toList()), () -> "Failed to find fields for: " +shipModule.getName() + "\n"
                                            + shipModule.getAttibutes().stream().map(Enum::name).collect(Collectors.joining(","))
                                            + "\n" + attrs.stream().map(Enum::name).collect(Collectors.joining(","))))
                    );
                })
        );
    }
    @Test
    void testExperimentalEffectsAttributePresence(){
        Assertions.assertAll( ShipModuleService.getAllModules().stream().map(shipModule -> () -> {
                    final Set<HorizonsModifier> shipModuleAttibutes = shipModule.getAttibutes();
                    Assertions.assertAll(
                            shipModule.getAllowedExperimentalEffects().stream()
                                    .map(type-> {
                                        final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> experimentalEffects = HorizonsBlueprintConstants.getExperimentalEffects();
                                        final Map<HorizonsBlueprintType, HorizonsBlueprint> horizonsBlueprintTypeHorizonsBlueprintMap = experimentalEffects.get(shipModule.getName().getPrimary());
                                        if(horizonsBlueprintTypeHorizonsBlueprintMap == null){
                                            log.debug("" + shipModule.getName().getPrimary() + " -> " + type);
                                        }
                                        return (HorizonsExperimentalEffectBlueprint) horizonsBlueprintTypeHorizonsBlueprintMap.get(type);
                                    })
                                    .map(blueprint-> blueprint.getModifiers().keySet())
                                    .map(attrs-> () -> Assertions.assertTrue(shipModuleAttibutes.containsAll(attrs.stream().filter(attr-> !ignored(attr)).toList()), () -> "Failed to find fields for: " +shipModule.getName() + "\n"
                                            + shipModule.getAttibutes().stream().map(Enum::name).collect(Collectors.joining(","))
                                            + "\n" + attrs.stream().map(Enum::name).collect(Collectors.joining(","))))
                    );
                })
        );
    }

    private boolean ignored(HorizonsModifier attr) {
        return List.of(
                HorizonsModifier.OPTIMAL_MULTIPLIER_ACCELERATION,
                HorizonsModifier.MINIMUM_MULTIPLIER_ACCELERATION,
                HorizonsModifier.MAXIMUM_MULTIPLIER_ACCELERATION,
                HorizonsModifier.OPTIMAL_MULTIPLIER_SPEED,
                HorizonsModifier.MINIMUM_MULTIPLIER_SPEED,
                HorizonsModifier.MAXIMUM_MULTIPLIER_SPEED,
                HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION,
                HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION,
                HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION,
                HorizonsModifier.OPTIMAL_BOOSTED_MULTIPLIER,
                HorizonsModifier.MINIMUM_BOOSTED_MULTIPLIER,
                HorizonsModifier.MAXIMUM_BOOSTED_MULTIPLIER
        ).contains(attr);
    }
}
