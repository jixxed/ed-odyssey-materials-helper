package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsTradeSuggestion;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialTraderService {
    private static final List<MaterialTrader> MATERIAL_TRADERS = new ArrayList<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {

        final InputStream inputStream = MaterialTraderService.class.getResourceAsStream("/materialtrader/traders.json");

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.ready()) {
                final String line = reader.readLine();
                final MaterialTraderJson jsonTrader = OBJECT_MAPPER.readValue(line, MaterialTraderJson.class);
                MATERIAL_TRADERS.add(new MaterialTrader(new StarSystem(jsonTrader.getName(), jsonTrader.getCoords().getX(), jsonTrader.getCoords().getY(), jsonTrader.getCoords().getZ()), jsonTrader.getStation().getName(), jsonTrader.getStation().getDistanceToArrival(), HorizonsStorageType.forName(jsonTrader.getStation().getType())));
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static MaterialTrader findClosest(final StarSystem currentLocation, final HorizonsStorageType type) {
        final Integer range = PreferencesService.getPreference(PreferenceConstants.HORIZONS_MATERIAL_TRADER_MAX_RANGE, 5000);
        return MATERIAL_TRADERS.stream()
                .filter(materialTrader -> materialTrader.getType().equals(type) && materialTrader.getDistanceFromStar() <= range)
                .min(Comparator.comparing(materialTrader -> getDistance(materialTrader.getStarSystem(), currentLocation)))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Double getDistance(final StarSystem location1, final StarSystem location2) {
        return Math.sqrt(Math.pow(location1.getX() - location2.getX(), 2) + Math.pow(location1.getY() - location2.getY(), 2) + Math.pow(location1.getZ() - location2.getZ(), 2));
    }

    public static List<HorizonsTradeSuggestion> getTradeSuggestions(final HorizonsMaterial horizonsMaterial) {
        return HorizonsMaterial.getAllMaterials().stream()
                .filter(mat -> mat.getClass().equals(horizonsMaterial.getClass()))//only same class materials can be traded
                .filter(mat -> mat.getMaterialType() != HorizonsMaterialType.GUARDIAN && mat.getMaterialType() != HorizonsMaterialType.THARGOID)//only human materials can be traded
                .sorted(getMaterialSorter(horizonsMaterial).thenComparing(Comparator.comparing(HorizonsMaterial::getRarity).reversed()))
                .map(material -> new HorizonsTradeSuggestion(material, horizonsMaterial, StorageService.getMaterialCount(material), WishlistService.getCurrentWishlistCount(material), WishlistService.getCurrentWishlistCount(horizonsMaterial) - StorageService.getMaterialCount(horizonsMaterial)))
                .filter(HorizonsTradeSuggestion::canCompleteTrade)
                .sorted(getSuggestionSorter(horizonsMaterial).thenComparing(Comparator.comparing((HorizonsTradeSuggestion o) -> o.getHorizonsMaterialFrom().getRarity()).reversed()).thenComparing(Comparator.comparing(HorizonsTradeSuggestion::getPercentageUsedOnTrade)))
                .limit(4)
                .toList();
    }

    private static Comparator<HorizonsMaterial> getMaterialSorter(final HorizonsMaterial horizonsMaterial) {
        return (mat1, mat2) -> {
            final int mat1Type = horizonsMaterial.getTradeType(mat1);
            final int mat2Type = horizonsMaterial.getTradeType(mat2);
            return Integer.compare(mat1Type, mat2Type);
        };
    }


    private static Comparator<HorizonsTradeSuggestion> getSuggestionSorter(final HorizonsMaterial horizonsMaterial) {
        return (sug1, sug2) -> {
            final var mat1 = sug1.getHorizonsMaterialFrom();
            final var mat2 = sug2.getHorizonsMaterialFrom();
            final int mat1Type = horizonsMaterial.getTradeType(mat1);
            final int mat2Type = horizonsMaterial.getTradeType(mat2);
            return Integer.compare(mat1Type, mat2Type);
        };
    }
}
