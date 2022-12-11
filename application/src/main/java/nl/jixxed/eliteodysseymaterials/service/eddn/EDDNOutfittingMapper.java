package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.outfitting.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Outfitting.Outfitting;

import java.util.List;
import java.util.stream.Collectors;

public class EDDNOutfittingMapper extends EDDNMapper {
    private static final List<String> EXCLUDED_MODULES = List.of(
            "int_planetapproachsuite",
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
            "hpt_pulselaserburst_fixed_small_scatter");

    public static Message mapToEDDN(final Outfitting outfitting, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(outfitting.getTimestamp())
                .withEvent(outfitting.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withMarketId(outfitting.getMarketID())
                .withModules(outfitting.getItems()
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
