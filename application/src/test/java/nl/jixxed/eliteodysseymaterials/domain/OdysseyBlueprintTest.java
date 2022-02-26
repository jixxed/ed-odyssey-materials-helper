package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class OdysseyBlueprintTest {

    @Test
    void getMaterialCollection() {
        final OdysseyBlueprint blueprint = new ModuleBlueprint(OdysseyBlueprintName.ADDED_MELEE_DAMAGE, Map.of(
                Good.IONISEDGAS, 10,
                Data.BIOMETRICDATA, 5,
                Data.COMBATANTPERFORMANCE, 10,
                Asset.VISCOELASTICPOLYMER, 10,
                Asset.RDX, 10
        ), List.of(Engineer.TERRA_VELASQUEZ));
        Assertions.assertThat((Map<OdysseyMaterial, Integer>) blueprint.getMaterialCollection(Asset.class)).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.VISCOELASTICPOLYMER, 10,
                Asset.RDX, 10
        ));
        Assertions.assertThat((Map<OdysseyMaterial, Integer>) blueprint.getMaterialCollection(Data.class)).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.BIOMETRICDATA, 5,
                Data.COMBATANTPERFORMANCE, 10
        ));
        Assertions.assertThat((Map<OdysseyMaterial, Integer>) blueprint.getMaterialCollection(Good.class)).containsExactlyInAnyOrderEntriesOf(Map.of(
                Good.IONISEDGAS, 10
        ));
        Assertions.assertThat((Map<OdysseyMaterial, Integer>) blueprint.getMaterialCollection(OdysseyMaterial.class)).containsExactlyInAnyOrderEntriesOf(Map.of(
                Good.IONISEDGAS, 10,
                Data.BIOMETRICDATA, 5,
                Data.COMBATANTPERFORMANCE, 10,
                Asset.VISCOELASTICPOLYMER, 10,
                Asset.RDX, 10
        ));
    }
}