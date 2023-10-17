package nl.jixxed.eliteodysseymaterials.service.ships;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsExperimentalEffectBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
public class ModuleAttributesTest {

    @Test
    void testBlueprintsAttributePresence(){
        Assertions.assertAll( Ship.getALL_MODULES().stream().flatMap(Collection::stream).map(shipModule -> () -> {
                    final Set<HorizonsModifier> shipModuleAttibutes = shipModule.getAttibutes();
                    Assertions.assertAll(
                            shipModule.getAllowedBlueprints().stream()
                                    .flatMap(type-> HorizonsBlueprintConstants.getBlueprintGrades(shipModule.getName().getPrimary(),type).stream()
                                            .map(grade -> new Pair<HorizonsBlueprintType, HorizonsBlueprintGrade>(type,grade)))
                                    .map(pair-> (HorizonsModuleBlueprint)HorizonsBlueprintConstants.getRecipe(shipModule.getName().getPrimary(),pair.getKey(),pair.getValue()))
                                    .map(blueprint-> blueprint.getModifiers().keySet())
                                    .map(attrs-> () -> Assertions.assertTrue(shipModuleAttibutes.containsAll(attrs), () -> "Failed to find fields for: " +shipModule.getName() + "\n"
                                            + shipModule.getAttibutes().stream().map(Enum::name).collect(Collectors.joining(","))
                                            + "\n" + attrs.stream().map(Enum::name).collect(Collectors.joining(","))))
                    );
                })
        );
    }
    @Test
    void testExperimentalEffectsAttributePresence(){
        Assertions.assertAll( Ship.getALL_MODULES().stream().flatMap(Collection::stream).map(shipModule -> () -> {
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
                                    .map(attrs-> () -> Assertions.assertTrue(shipModuleAttibutes.containsAll(attrs), () -> "Failed to find fields for: " +shipModule.getName() + "\n"
                                            + shipModule.getAttibutes().stream().map(Enum::name).collect(Collectors.joining(","))
                                            + "\n" + attrs.stream().map(Enum::name).collect(Collectors.joining(","))))
                    );
                })
        );
    }
}
