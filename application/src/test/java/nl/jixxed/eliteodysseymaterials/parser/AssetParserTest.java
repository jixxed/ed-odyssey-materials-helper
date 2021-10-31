package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class AssetParserTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void parse_shiplocker() throws IOException {
        final Map<Asset, Storage> assets = new HashMap<>();
        assets.put(Asset.GRAPHENE, new Storage());
        assets.put(Asset.AEROGEL, new Storage());
        assets.put(Asset.CARBONFIBREPLATING, new Storage());
        assets.put(Asset.CHEMICALCATALYST, new Storage());
        assets.put(Asset.CHEMICALSUPERBASE, new Storage());
        final Map<String, Storage> unknownAssets = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/shiplocker_good.json"));
        final Iterator<JsonNode> items = jsonNode.get("Components").elements();
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(items, StoragePool.SHIPLOCKER, assets, unknownAssets);

        Assertions.assertThat(assets).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.GRAPHENE, Storage.of(0, 45),
                Asset.AEROGEL, Storage.of(0, 44),
                Asset.CARBONFIBREPLATING, Storage.of(0, 15),
                Asset.CHEMICALCATALYST, Storage.of(0, 47),
                Asset.CHEMICALSUPERBASE, Storage.of(0, 50)
        ));
        Assertions.assertThat(unknownAssets).isEmpty();
    }

    @Test
    void parse_shiplocker_unknown() throws IOException {
        final Map<Asset, Storage> assets = new HashMap<>();
        assets.put(Asset.GRAPHENE, new Storage());
        assets.put(Asset.AEROGEL, new Storage());
        assets.put(Asset.CARBONFIBREPLATING, new Storage());
        assets.put(Asset.CHEMICALCATALYST, new Storage());
        final Map<String, Storage> unknownAssets = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/shiplocker_unknown.json"));
        final Iterator<JsonNode> items = jsonNode.get("Components").elements();
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(items, StoragePool.SHIPLOCKER, assets, unknownAssets);

        Assertions.assertThat(assets).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.GRAPHENE, Storage.of(0, 45),
                Asset.AEROGEL, Storage.of(0, 44),
                Asset.CARBONFIBREPLATING, Storage.of(0, 15),
                Asset.CHEMICALCATALYST, Storage.of(0, 47)
        ));
        Assertions.assertThat(unknownAssets).isEmpty();
    }

    @Test
    void parse_backpack() throws IOException {
        final Map<Asset, Storage> assets = new HashMap<>();
        assets.put(Asset.GRAPHENE, new Storage());
        assets.put(Asset.AEROGEL, new Storage());
        assets.put(Asset.CARBONFIBREPLATING, new Storage());
        assets.put(Asset.CHEMICALCATALYST, new Storage());
        assets.put(Asset.CHEMICALSUPERBASE, new Storage());
        final Map<String, Storage> unknownAssets = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/backpack_good.json"));
        final Iterator<JsonNode> items = jsonNode.get("Components").elements();
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(items, StoragePool.BACKPACK, assets, unknownAssets);

        Assertions.assertThat(assets).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.GRAPHENE, Storage.of(45, 0),
                Asset.AEROGEL, Storage.of(44, 0),
                Asset.CARBONFIBREPLATING, Storage.of(15, 0),
                Asset.CHEMICALCATALYST, Storage.of(47, 0),
                Asset.CHEMICALSUPERBASE, Storage.of(50, 0)
        ));
        Assertions.assertThat(unknownAssets).isEmpty();
    }

    @Test
    void parse_backpack_unknown() throws IOException {
        final Map<Asset, Storage> assets = new HashMap<>();
        assets.put(Asset.GRAPHENE, new Storage());
        assets.put(Asset.AEROGEL, new Storage());
        assets.put(Asset.CARBONFIBREPLATING, new Storage());
        assets.put(Asset.CHEMICALCATALYST, new Storage());
        final Map<String, Storage> unknownAssets = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/backpack_unknown.json"));
        final Iterator<JsonNode> items = jsonNode.get("Components").elements();
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(items, StoragePool.BACKPACK, assets, unknownAssets);

        Assertions.assertThat(assets).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.GRAPHENE, Storage.of(45, 0),
                Asset.AEROGEL, Storage.of(44, 0),
                Asset.CARBONFIBREPLATING, Storage.of(15, 0),
                Asset.CHEMICALCATALYST, Storage.of(47, 0)
        ));
        Assertions.assertThat(unknownAssets).isEmpty();
    }
}