package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class DataParserTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void parse_shiplocker() throws IOException {
        final Map<Data, Storage> data = new HashMap<>();
        data.put(Data.VIRUS, new Storage());
        data.put(Data.SLUSHFUNDLOGS, new Storage());
        data.put(Data.BIOMETRICDATA, new Storage());
        data.put(Data.NOCDATA, new Storage());
        final Map<String, Storage> unknownDatas = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/shiplocker_good.json"));
        final Iterator<JsonNode> items = jsonNode.get("Data").elements();
        final DataParser dataParser = new DataParser();

        dataParser.parse(items, StoragePool.SHIPLOCKER, data, unknownDatas);

        Assertions.assertThat(data).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.VIRUS, Storage.of(0, 1),
                Data.SLUSHFUNDLOGS, Storage.of(0, 1),
                Data.BIOMETRICDATA, Storage.of(0, 6),
                Data.NOCDATA, Storage.of(0, 3)
        ));
        Assertions.assertThat(unknownDatas).isEmpty();
    }

    @Test
    void parse_shiplocker_unknown() throws IOException {
        final Map<Data, Storage> data = new HashMap<>();
        data.put(Data.SLUSHFUNDLOGS, new Storage());
        data.put(Data.NOCDATA, new Storage());
        final Map<String, Storage> unknownData = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/shiplocker_unknown.json"));
        final Iterator<JsonNode> items = jsonNode.get("Data").elements();
        final DataParser dataParser = new DataParser();

        dataParser.parse(items, StoragePool.SHIPLOCKER, data, unknownData);

        Assertions.assertThat(data).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.SLUSHFUNDLOGS, Storage.of(0, 1),
                Data.NOCDATA, Storage.of(0, 3)
        ));
        Assertions.assertThat(unknownData).containsExactlyInAnyOrderEntriesOf(Map.of(
                "banana:Banana", Storage.of(0, 6),
                "apple:apple", Storage.of(0, 1)
        ));
    }

    @Test
    void parse_backpack() throws IOException {
        final Map<Data, Storage> data = new HashMap<>();
        data.put(Data.VIRUS, new Storage());
        data.put(Data.SLUSHFUNDLOGS, new Storage());
        data.put(Data.BIOMETRICDATA, new Storage());
        data.put(Data.NOCDATA, new Storage());
        final Map<String, Storage> unknownDatas = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/backpack_good.json"));
        final Iterator<JsonNode> items = jsonNode.get("Data").elements();
        final DataParser dataParser = new DataParser();

        dataParser.parse(items, StoragePool.BACKPACK, data, unknownDatas);

        Assertions.assertThat(data).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.VIRUS, Storage.of(1, 0),
                Data.SLUSHFUNDLOGS, Storage.of(1, 0),
                Data.BIOMETRICDATA, Storage.of(6, 0),
                Data.NOCDATA, Storage.of(3, 0)
        ));
        Assertions.assertThat(unknownDatas).isEmpty();
    }

    @Test
    void parse_backpack_unknown() throws IOException {
        final Map<Data, Storage> data = new HashMap<>();
        data.put(Data.SLUSHFUNDLOGS, new Storage());
        data.put(Data.NOCDATA, new Storage());
        final Map<String, Storage> unknownData = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/backpack_unknown.json"));
        final Iterator<JsonNode> items = jsonNode.get("Data").elements();
        final DataParser dataParser = new DataParser();

        dataParser.parse(items, StoragePool.BACKPACK, data, unknownData);

        Assertions.assertThat(data).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.SLUSHFUNDLOGS, Storage.of(1, 0),
                Data.NOCDATA, Storage.of(3, 0)
        ));
        Assertions.assertThat(unknownData).containsExactlyInAnyOrderEntriesOf(Map.of(
                "banana:Banana", Storage.of(6, 0),
                "apple:apple", Storage.of(1, 0)
        ));
    }
}