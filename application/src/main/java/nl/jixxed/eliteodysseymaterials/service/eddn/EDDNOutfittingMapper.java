package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.outfitting.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Outfitting.Outfitting;

import java.util.Set;
import java.util.stream.Collectors;

public class EDDNOutfittingMapper extends EDDNMapper {
    private static final Set<String> EXCLUDED_MODULES = Set.of(
            //horizons
            "int_planetapproachsuite",
            "int_planetapproachsuite_advanced",
            "int_stellarbodydiscoveryscanner_advanced",
            "int_stellarbodydiscoveryscanner_intermediate",
            "int_stellarbodydiscoveryscanner_standard",
            //powerplay
            "int_shieldgenerator_size1_class5_strong",
            "int_shieldgenerator_size2_class5_strong",
            "int_shieldgenerator_size3_class5_strong",
            "int_shieldgenerator_size4_class5_strong",
            "int_shieldgenerator_size5_class5_strong",
            "int_shieldgenerator_size6_class5_strong",
            "int_shieldgenerator_size7_class5_strong",
            "int_shieldgenerator_size8_class5_strong",
            "hpt_plasmaaccelerator_fixed_large_advanced",
            "hpt_slugshot_fixed_large_range",
            "hpt_multicannon_fixed_small_strong",
            "hpt_drunkmissilerack_fixed_medium",
            "hpt_railgun_fixed_medium_burst",
            "hpt_mininglaser_fixed_small_advanced",
            "hpt_beamlaser_fixed_small_heat",
            "hpt_dumbfiremissilerack_fixed_medium_lasso",
            "hpt_pulselaser_fixed_medium_disruptor",
            "hpt_pulselaserburst_fixed_small_scatter",
            //techbroker
            "int_metaalloyhullreinforcement_size1_class1",
            "int_metaalloyhullreinforcement_size1_class2",
            "int_metaalloyhullreinforcement_size2_class1",
            "int_metaalloyhullreinforcement_size2_class2",
            "int_metaalloyhullreinforcement_size3_class1",
            "int_metaalloyhullreinforcement_size3_class2",
            "int_metaalloyhullreinforcement_size4_class1",
            "int_metaalloyhullreinforcement_size4_class2",
            "int_metaalloyhullreinforcement_size5_class1",
            "int_metaalloyhullreinforcement_size5_class2",
            "int_corrosionproofcargorack_size4_class1",
            "int_guardianhullreinforcement_size1_class1",
            "int_guardianhullreinforcement_size1_class2",
            "int_guardianhullreinforcement_size2_class1",
            "int_guardianhullreinforcement_size2_class2",
            "int_guardianhullreinforcement_size3_class1",
            "int_guardianhullreinforcement_size3_class2",
            "int_guardianhullreinforcement_size4_class1",
            "int_guardianhullreinforcement_size4_class2",
            "int_guardianhullreinforcement_size5_class1",
            "int_guardianhullreinforcement_size5_class2",
            "int_guardianmodulereinforcement_size1_class1",
            "int_guardianmodulereinforcement_size1_class2",
            "int_guardianmodulereinforcement_size2_class1",
            "int_guardianmodulereinforcement_size2_class2",
            "int_guardianmodulereinforcement_size3_class1",
            "int_guardianmodulereinforcement_size3_class2",
            "int_guardianmodulereinforcement_size4_class1",
            "int_guardianmodulereinforcement_size4_class2",
            "int_guardianmodulereinforcement_size5_class1",
            "int_guardianmodulereinforcement_size5_class2",
            "int_guardianshieldreinforcement_size1_class1",
            "int_guardianshieldreinforcement_size1_class2",
            "int_guardianshieldreinforcement_size2_class1",
            "int_guardianshieldreinforcement_size2_class2",
            "int_guardianshieldreinforcement_size3_class1",
            "int_guardianshieldreinforcement_size3_class2",
            "int_guardianshieldreinforcement_size4_class1",
            "int_guardianshieldreinforcement_size4_class2",
            "int_guardianshieldreinforcement_size5_class1",
            "int_guardianshieldreinforcement_size5_class2",
            "int_guardianfsdbooster_size1",
            "int_guardianfsdbooster_size2",
            "int_guardianfsdbooster_size3",
            "int_guardianfsdbooster_size4",
            "int_guardianfsdbooster_size5",
            "int_guardianpowerdistributor_size1",
            "int_guardianpowerdistributor_size2",
            "int_guardianpowerdistributor_size3",
            "int_guardianpowerdistributor_size4",
            "int_guardianpowerdistributor_size5",
            "int_guardianpowerdistributor_size6",
            "int_guardianpowerdistributor_size7",
            "int_guardianpowerdistributor_size8",
            "int_guardianpowerplant_size2",
            "int_guardianpowerplant_size3",
            "int_guardianpowerplant_size4",
            "int_guardianpowerplant_size5",
            "int_guardianpowerplant_size6",
            "int_guardianpowerplant_size7",
            "int_guardianpowerplant_size8",
            "hpt_guardian_gausscannon_fixed_small",
            "hpt_guardian_gausscannon_fixed_medium",
            "hpt_guardian_shardcannon_fixed_small",
            "hpt_guardian_shardcannon_fixed_medium",
            "hpt_guardian_shardcannon_fixed_large",
            "hpt_guardian_shardcannon_turret_small",
            "hpt_guardian_shardcannon_turret_medium",
            "hpt_guardian_shardcannon_turret_large",
            "hpt_guardian_plasmalauncher_fixed_small",
            "hpt_guardian_plasmalauncher_fixed_medium",
            "hpt_guardian_plasmalauncher_fixed_large",
            "hpt_guardian_plasmalauncher_turret_small",
            "hpt_guardian_plasmalauncher_turret_medium",
            "hpt_guardian_plasmalauncher_turret_large",
            "hpt_causticmissile_fixed_medium",
            "hpt_flechettelauncher_fixed_medium",
            "hpt_flechettelauncher_turret_medium",
            "hpt_plasmashockcannon_fixed_small",
            "hpt_plasmashockcannon_fixed_medium",
            "hpt_plasmashockcannon_fixed_large",
            "hpt_plasmashockcannon_gimbal_small",
            "hpt_plasmashockcannon_gimbal_medium",
            "hpt_plasmashockcannon_gimbal_large",
            "hpt_plasmashockcannon_turret_small",
            "hpt_plasmashockcannon_turret_medium",
            "hpt_plasmashockcannon_turret_large"
    );

    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final Outfitting outfitting, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(outfitting.getTimestamp())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withMarketId(outfitting.getMarketID())
                .withModules(mapToOptionalEmptyIfEmptyList(outfitting.getItems())
                        .map(items -> items.stream()
                                .map(item -> item.getName().toLowerCase())
                                .filter(name -> name.startsWith("hpt_") || name.startsWith("int_") || name.contains("_armour_"))
                                .filter(name -> !EXCLUDED_MODULES.contains(name))
                                .collect(Collectors.toSet()))
                        .orElse(null))
                .withStationName(outfitting.getStationName())
                .withSystemName(outfitting.getStarSystem())
                .build();
    }
}
