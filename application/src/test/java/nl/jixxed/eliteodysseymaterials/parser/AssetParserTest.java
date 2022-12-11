package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Backpack.Backpack;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ShipLocker.ShipLocker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class AssetParserTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    @Test
    void parse_shiplocker() throws IOException {
        final Map<Asset, Storage> assets = new HashMap<>();
        assets.put(Asset.GRAPHENE, new Storage());
        assets.put(Asset.AEROGEL, new Storage());
        assets.put(Asset.CARBONFIBREPLATING, new Storage());
        assets.put(Asset.CHEMICALCATALYST, new Storage());
        assets.put(Asset.CHEMICALSUPERBASE, new Storage());

        final ShipLocker shipLocker = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/shiplocker_good.json"), ShipLocker.class);
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(shipLocker.getComponents().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.SHIPLOCKER, assets);

        Assertions.assertThat(assets).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.GRAPHENE, Storage.of(0, 45),
                Asset.AEROGEL, Storage.of(0, 44),
                Asset.CARBONFIBREPLATING, Storage.of(0, 15),
                Asset.CHEMICALCATALYST, Storage.of(0, 47),
                Asset.CHEMICALSUPERBASE, Storage.of(0, 50)
        ));
    }

    @Test
    void parse_shiplocker_unknown() throws IOException {
        final Map<Asset, Storage> assets = new HashMap<>();
        assets.put(Asset.GRAPHENE, new Storage());
        assets.put(Asset.AEROGEL, new Storage());
        assets.put(Asset.CARBONFIBREPLATING, new Storage());
        assets.put(Asset.CHEMICALCATALYST, new Storage());

        final ShipLocker shipLocker = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/shiplocker_unknown.json"), ShipLocker.class);
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(shipLocker.getComponents().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.SHIPLOCKER, assets);

        Assertions.assertThat(assets).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.GRAPHENE, Storage.of(0, 45),
                Asset.AEROGEL, Storage.of(0, 44),
                Asset.CARBONFIBREPLATING, Storage.of(0, 15),
                Asset.CHEMICALCATALYST, Storage.of(0, 47)
        ));
    }

    @Test
    void parse_backpack() throws IOException {
        final Map<Asset, Storage> assets = new HashMap<>();
        assets.put(Asset.GRAPHENE, new Storage());
        assets.put(Asset.AEROGEL, new Storage());
        assets.put(Asset.CARBONFIBREPLATING, new Storage());
        assets.put(Asset.CHEMICALCATALYST, new Storage());
        assets.put(Asset.CHEMICALSUPERBASE, new Storage());

        final Backpack backpack = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/backpack_good.json"), Backpack.class);
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(backpack.getComponents().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.BACKPACK, assets);

        Assertions.assertThat(assets).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.GRAPHENE, Storage.of(45, 0),
                Asset.AEROGEL, Storage.of(44, 0),
                Asset.CARBONFIBREPLATING, Storage.of(15, 0),
                Asset.CHEMICALCATALYST, Storage.of(47, 0),
                Asset.CHEMICALSUPERBASE, Storage.of(50, 0)
        ));
    }

    @Test
    void parse_backpack_unknown() throws IOException {
        final Map<Asset, Storage> assets = new HashMap<>();
        assets.put(Asset.GRAPHENE, new Storage());
        assets.put(Asset.AEROGEL, new Storage());
        assets.put(Asset.CARBONFIBREPLATING, new Storage());
        assets.put(Asset.CHEMICALCATALYST, new Storage());

        final Backpack backpack = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/backpack_unknown.json"), Backpack.class);
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(backpack.getComponents().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.BACKPACK, assets);

        Assertions.assertThat(assets).containsExactlyInAnyOrderEntriesOf(Map.of(
                Asset.GRAPHENE, Storage.of(45, 0),
                Asset.AEROGEL, Storage.of(44, 0),
                Asset.CARBONFIBREPLATING, Storage.of(15, 0),
                Asset.CHEMICALCATALYST, Storage.of(47, 0)
        ));
    }
}