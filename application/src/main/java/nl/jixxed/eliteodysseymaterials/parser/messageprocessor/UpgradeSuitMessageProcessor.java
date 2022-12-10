package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.UpgradeSuit.UpgradeSuit;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.Map;
//This event is logged when the player upgrades their flight suit
//        Parameters:
//         Name
//         SuitID
//         Class
//         Cost
//        { "timestamp":"2020-10-06T09:37:49Z", "event":"UpgradeSuit", "Name":"utilitysuit_class1",
//        "Name_Localised":"Utility Suit", "Class":2, "Cost":100000 }

public class UpgradeSuitMessageProcessor implements MessageProcessor<UpgradeSuit> {
    private static final Map<String, OdysseyBlueprint> BLUEPRINT_MAPPING = Map.ofEntries(
            Map.entry("utilitysuit_class1", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.MAVERICK_SUIT_GRADE_1_2)),
            Map.entry("utilitysuit_class2", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.MAVERICK_SUIT_GRADE_2_3)),
            Map.entry("utilitysuit_class3", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.MAVERICK_SUIT_GRADE_3_4)),
            Map.entry("utilitysuit_class4", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.MAVERICK_SUIT_GRADE_4_5)),
            Map.entry("explorationsuit_class1", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_1_2)),
            Map.entry("explorationsuit_class2", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_2_3)),
            Map.entry("explorationsuit_class3", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_3_4)),
            Map.entry("explorationsuit_class4", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_4_5)),
            Map.entry("tacticalsuit_class1", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_1_2)),
            Map.entry("tacticalsuit_class2", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_2_3)),
            Map.entry("tacticalsuit_class3", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_3_4)),
            Map.entry("tacticalsuit_class4", OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_4_5))
    );

    @Override
    public void process(final UpgradeSuit event) {

        final String name = event.getName();
        final OdysseyBlueprint odysseyBlueprint = BLUEPRINT_MAPPING.get(name);
        odysseyBlueprint.getMaterialCollection(OdysseyMaterial.class).forEach((key, value) -> {
            final Storage materialStorage = StorageService.getMaterialStorage(key);
            materialStorage.setValue(materialStorage.getValue(StoragePool.SHIPLOCKER) - value, StoragePool.SHIPLOCKER);
        });
    }

    @Override
    public Class<UpgradeSuit> getMessageClass() {
        return UpgradeSuit.class;
    }

}
