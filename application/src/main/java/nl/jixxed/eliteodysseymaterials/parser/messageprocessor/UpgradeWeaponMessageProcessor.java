package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.Map;

//This event is logged when the player upgrades a hand weapon
//        Parameters:
//         Name
//         SuitModuleID
//         Class
//         Cost
//        { "timestamp":"2020-10-06T09:38:23Z", "event":"UpgradeWeapon",
//        "Name":"wpn_s_pistol_plasma_charged", "Name_Localised":"Manticore Tormentor", "Class":2,
//        "Cost":0 }
public class UpgradeWeaponMessageProcessor implements MessageProcessor {
    private static final Map<Integer, OdysseyBlueprint> BLUEPRINT_MAPPING_KARMA = Map.ofEntries(
            Map.entry(2, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.KARMA_1_2)),
            Map.entry(3, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.KARMA_2_3)),
            Map.entry(4, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.KARMA_3_4)),
            Map.entry(5, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.KARMA_4_5))

    );
    private static final Map<Integer, OdysseyBlueprint> BLUEPRINT_MAPPING_TK = Map.ofEntries(
            Map.entry(2, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.TK_1_2)),
            Map.entry(3, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.TK_2_3)),
            Map.entry(4, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.TK_3_4)),
            Map.entry(5, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.TK_4_5))

    );
    private static final Map<Integer, OdysseyBlueprint> BLUEPRINT_MAPPING_MANTICORE = Map.ofEntries(
            Map.entry(2, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.MANTICORE_1_2)),
            Map.entry(3, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.MANTICORE_2_3)),
            Map.entry(4, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.MANTICORE_3_4)),
            Map.entry(5, OdysseyBlueprintConstants.getRecipe(OdysseyBlueprintName.MANTICORE_4_5))

    );

    @Override
    public void process(final JsonNode journalMessage) {
        final String name = journalMessage.get("Name").asText();
        final Integer targetClass = journalMessage.get("Class").asInt();
        final OdysseyBlueprint odysseyBlueprint;
        if (name.contains("laser")) {
            odysseyBlueprint = BLUEPRINT_MAPPING_TK.get(targetClass);
        } else if (name.contains("plasma")) {
            odysseyBlueprint = BLUEPRINT_MAPPING_MANTICORE.get(targetClass);
        } else {
            odysseyBlueprint = BLUEPRINT_MAPPING_KARMA.get(targetClass);
        }
        odysseyBlueprint.getMaterialCollection(OdysseyMaterial.class).forEach((key, value) -> {
            final Storage materialStorage = StorageService.getMaterialStorage(key);
            materialStorage.setValue(materialStorage.getValue(StoragePool.SHIPLOCKER) - value, StoragePool.SHIPLOCKER);
        });
    }
}
