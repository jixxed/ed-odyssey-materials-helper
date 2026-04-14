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
import nl.jixxed.eliteodysseymaterials.schemas.journal.UpgradeWeapon.UpgradeWeapon;
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
public class UpgradeWeaponMessageProcessor implements MessageProcessor<UpgradeWeapon> {
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
    public void process(final UpgradeWeapon event) {

        final String name = event.getName();
        final Integer targetClass = event.getClass_().intValue();
        final OdysseyBlueprint odysseyBlueprint;
        if (name.contains("laser")) {
            odysseyBlueprint = BLUEPRINT_MAPPING_TK.get(targetClass);
        } else if (name.contains("plasma")) {
            odysseyBlueprint = BLUEPRINT_MAPPING_MANTICORE.get(targetClass);
        } else {
            odysseyBlueprint = BLUEPRINT_MAPPING_KARMA.get(targetClass);
        }
        odysseyBlueprint.getMaterialCollection(OdysseyMaterial.class).forEach((key, value) -> {
            StorageService.removeMaterial(key,StoragePool.SHIPLOCKER, value);
        });
    }

    @Override
    public Class<UpgradeWeapon> getMessageClass() {
        return UpgradeWeapon.class;
    }
}
