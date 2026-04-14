/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyBlueprint;
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
            StorageService.removeMaterial(key,StoragePool.SHIPLOCKER, value);
        });
    }

    @Override
    public Class<UpgradeSuit> getMessageClass() {
        return UpgradeSuit.class;
    }

}
