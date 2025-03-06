package nl.jixxed.eliteodysseymaterials.constants.horizons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsEngineerBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EngineerBlueprints {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    public static final HorizonsEngineerBlueprint ENGINEER_A = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_A,
            Map.of(RareCommodity.SOONTILLRELICS, 3),
            List.of("blueprint.description.horizons.engineer_a_learn",
                    "blueprint.description.horizons.engineer_a_invite",
                    "blueprint.description.horizons.engineer_a_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellexploration"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.ELVIRA_MARTUUK)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_A1 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_A1,
            List.of("blueprint.description.horizons.engineer_a1_learn",
                    "blueprint.description.horizons.engineer_a1_invite",
                    "blueprint.description.horizons.engineer_a1_unlock"),
            List.of("blueprint.label.leveling.crafting"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.MEL_BRANDON)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_A2 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_A2,
            Map.of(RareCommodity.XIHECOMPANIONS, 25),
            List.of("blueprint.description.horizons.engineer_a2_learn",
                    "blueprint.description.horizons.engineer_a2_invite",
                    "blueprint.description.horizons.engineer_a2_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.ZACARIAH_NEMO)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_A3 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_A3,
            Map.of(RegularCommodity.MODULARTERMINALS, 25),
            List.of("blueprint.description.horizons.engineer_a3_learn",
                    "blueprint.description.horizons.engineer_a3_invite",
                    "blueprint.description.horizons.engineer_a3_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.MARCO_QWENT)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_A3A = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_A3A,
            Map.of(Manufactured.UNKNOWNENERGYSOURCE, 25),
            List.of("blueprint.description.horizons.engineer_a3a_learn",
                    "blueprint.description.horizons.engineer_a3a_invite",
                    "blueprint.description.horizons.engineer_a3a_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellexploration"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.PROFESSOR_PALIN)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_A3B = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_A3B,
            Map.of(RareCommodity.KONGGAALE, 25),
            List.of("blueprint.description.horizons.engineer_a3b_learn",
                    "blueprint.description.horizons.engineer_a3b_invite",
                    "blueprint.description.horizons.engineer_a3b_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellexploration"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.LORI_JAMESON)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_A3C = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_A3C,
            Map.of(Manufactured.UNKNOWNENERGYSOURCE, 25),
            List.of("blueprint.description.horizons.engineer_a3c_learn",
                    "blueprint.description.horizons.engineer_a3c_invite",
                    "blueprint.description.horizons.engineer_a3c_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellexploration"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.CHLOE_SEDESI)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_B = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_B,
            List.of("blueprint.description.horizons.engineer_b_learn",
                    "blueprint.description.horizons.engineer_b_invite",
                    "blueprint.description.horizons.engineer_b_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.THE_DWELLER)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_B1 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_B1,
            Map.of(RegularCommodity.OSMIUM, 10),
            List.of("blueprint.description.horizons.engineer_b1_learn",
                    "blueprint.description.horizons.engineer_b1_invite",
                    "blueprint.description.horizons.engineer_b1_unlock"),
            List.of("blueprint.label.leveling.crafting"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.MARSHA_HICKS)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_B2 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_B2,
            Map.of(RegularCommodity.GOLD, 200),
            List.of("blueprint.description.horizons.engineer_b2_learn",
                    "blueprint.description.horizons.engineer_b2_invite",
                    "blueprint.description.horizons.engineer_b2_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.LEI_CHEUNG)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_B2A = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_B2A,
            Map.of(Encoded.SCANDATABANKS, 50),
            List.of("blueprint.description.horizons.engineer_b2a_learn",
                    "blueprint.description.horizons.engineer_b2a_invite",
                    "blueprint.description.horizons.engineer_b2a_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellexploration"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.RAM_TAH)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_C = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_C,
            Map.of(RegularCommodity.METAALLOYS, 1),
            List.of("blueprint.description.horizons.engineer_c_learn",
                    "blueprint.description.horizons.engineer_c_invite",
                    "blueprint.description.horizons.engineer_c_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellexploration"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.FELICITY_FARSEER)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_C1 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_C1,
            List.of("blueprint.description.horizons.engineer_c1_learn",
                    "blueprint.description.horizons.engineer_c1_invite",
                    "blueprint.description.horizons.engineer_c1_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellbounties",
                    "blueprint.label.leveling.sellbonds"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.JURI_ISHMAAK)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_C1A = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_C1A,
            List.of("blueprint.description.horizons.engineer_c1a_learn",
                    "blueprint.description.horizons.engineer_c1a_invite",
                    "blueprint.description.horizons.engineer_c1a_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellbounties"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.COLONEL_BRIS_DEKKER)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_C1B = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_C1B,
            Map.of(Encoded.SHIELDPATTERNANALYSIS, 50),
            List.of("blueprint.description.horizons.engineer_c1b_learn",
                    "blueprint.description.horizons.engineer_c1b_invite",
                    "blueprint.description.horizons.engineer_c1b_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellexploration",
                    "blueprint.label.leveling.sellbonds"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.THE_SARGE)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_D = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_D,
            List.of("blueprint.description.horizons.engineer_d_learn",
                    "blueprint.description.horizons.engineer_d_invite",
                    "blueprint.description.horizons.engineer_d_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellbounties"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.TOD_THE_BLASTER_MCQUINN)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_D1 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_D1,
            Map.of(RegularCommodity.PROGENITORCELLS, 200),
            List.of("blueprint.description.horizons.engineer_d1_learn",
                    "blueprint.description.horizons.engineer_d1_invite",
                    "blueprint.description.horizons.engineer_d1_unlock"),
            List.of("blueprint.label.leveling.crafting"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.PETRA_OLMANOVA)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_D2 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_D2,
            Map.of(RegularCommodity.PAINITE, 10),
            List.of("blueprint.description.horizons.engineer_d2_learn",
                    "blueprint.description.horizons.engineer_d2_invite",
                    "blueprint.description.horizons.engineer_d2_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities",
                    "blueprint.label.leveling.sellexploration"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.SELENE_JEAN)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_D2A = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_D2A,
            Map.of(RareCommodity.LAVIANBRANDY, 50),
            List.of("blueprint.description.horizons.engineer_d2a_learn",
                    "blueprint.description.horizons.engineer_d2a_invite",
                    "blueprint.description.horizons.engineer_d2a_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.DIDI_VATERMANN)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_D2B = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_D2B,
            Map.of(RegularCommodity.BROMELLITE, 50),
            List.of("blueprint.description.horizons.engineer_d2b_learn",
                    "blueprint.description.horizons.engineer_d2b_invite",
                    "blueprint.description.horizons.engineer_d2b_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.BILL_TURNER)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_E = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_E,
            Map.of(RegularCommodity.LANDMINES, 200),
            List.of("blueprint.description.horizons.engineer_e_learn",
                    "blueprint.description.horizons.engineer_e_invite",
                    "blueprint.description.horizons.engineer_e_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.LIZ_RYDER)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_E1 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_E1,
            Map.of(RegularCommodity.OCCUPIEDCRYOPOD, 25),
            List.of("blueprint.description.horizons.engineer_e1_learn",
                    "blueprint.description.horizons.engineer_e1_invite",
                    "blueprint.description.horizons.engineer_e1_unlock"),
            List.of("blueprint.label.leveling.crafting"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.ETIENNE_DORN)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_E2 = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_E2,
            Map.of(RareCommodity.KAMITRACIGARS, 50),
            List.of("blueprint.description.horizons.engineer_e2_learn",
                    "blueprint.description.horizons.engineer_e2_invite",
                    "blueprint.description.horizons.engineer_e2_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.HERA_TANI)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_E2A = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_E2A,
            Map.of(RareCommodity.FUJINTEA, 50),
            List.of("blueprint.description.horizons.engineer_e2a_learn",
                    "blueprint.description.horizons.engineer_e2a_invite",
                    "blueprint.description.horizons.engineer_e2a_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellbounties"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.BROO_TARQUIN)
    );
    public static final HorizonsEngineerBlueprint ENGINEER_E2B = new HorizonsEngineerBlueprint(
            HorizonsBlueprintName.ENGINEER_E2B,
            Map.of(Encoded.DECODEDEMISSIONDATA, 50),
            List.of("blueprint.description.horizons.engineer_e2b_learn",
                    "blueprint.description.horizons.engineer_e2b_invite",
                    "blueprint.description.horizons.engineer_e2b_unlock"),
            List.of("blueprint.label.leveling.crafting",
                    "blueprint.label.leveling.sellcommodities"),
            () -> APPLICATION_STATE.isEngineerUnlockedExact(Engineer.TIANA_FORTUNE)
    );
}
