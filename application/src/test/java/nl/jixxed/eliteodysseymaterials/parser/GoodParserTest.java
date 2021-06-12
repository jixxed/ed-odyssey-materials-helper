package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;

class GoodParserTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void parse_shiplocker() throws IOException {
        final Map<Good, Storage> goods = new HashMap<>();
        goods.put(Good.LARGECAPACITYPOWERREGULATOR, new Storage());
        goods.put(Good.GMEDS, new Storage());
        goods.put(Good.HEALTHMONITOR, new Storage());
        final Map<String, Storage> unknownGoods = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/shiplocker_good.json"));
        final Iterator<JsonNode> items = jsonNode.get("Items").elements();
        final GoodParser goodParser = new GoodParser();

        goodParser.parse(items, StoragePool.SHIPLOCKER, goods, unknownGoods);

        assertAll(
                () -> Assertions.assertThat(goods).containsExactlyInAnyOrderEntriesOf(Map.of(
                        Good.LARGECAPACITYPOWERREGULATOR, Storage.of(0, 4),
                        Good.GMEDS, Storage.of(0, 30),
                        Good.HEALTHMONITOR, Storage.of(0, 2))),
                () -> Assertions.assertThat(unknownGoods).isEmpty()
        );
    }

    @Test
    void parse_shiplocker_unknown() throws IOException {
        final Map<Good, Storage> goods = new HashMap<>();
        goods.put(Good.LARGECAPACITYPOWERREGULATOR, new Storage());
        goods.put(Good.HEALTHMONITOR, new Storage());
        final Map<String, Storage> unknownGoods = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/shiplocker_unknown.json"));
        final Iterator<JsonNode> items = jsonNode.get("Items").elements();
        final GoodParser goodParser = new GoodParser();

        goodParser.parse(items, StoragePool.SHIPLOCKER, goods, unknownGoods);

        Assertions.assertThat(goods).containsExactlyInAnyOrderEntriesOf(Map.of(
                Good.LARGECAPACITYPOWERREGULATOR, Storage.of(0, 1),
                Good.HEALTHMONITOR, Storage.of(0, 2)
        ));
        Assertions.assertThat(unknownGoods).containsExactlyInAnyOrderEntriesOf(Map.of(
                "banana:Banana", Storage.of(0, 3),
                "apple:apple", Storage.of(0, 30)
        ));
    }

    @Test
    void parse_backpack() throws IOException {
        final Map<Good, Storage> goods = new HashMap<>();
        goods.put(Good.LARGECAPACITYPOWERREGULATOR, new Storage());
        goods.put(Good.GMEDS, new Storage());
        goods.put(Good.HEALTHMONITOR, new Storage());
        final Map<String, Storage> unknownGoods = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/backpack_good.json"));
        final Iterator<JsonNode> items = jsonNode.get("Items").elements();
        final GoodParser goodParser = new GoodParser();

        goodParser.parse(items, StoragePool.BACKPACK, goods, unknownGoods);

        Assertions.assertThat(goods).containsExactlyInAnyOrderEntriesOf(Map.of(
                Good.LARGECAPACITYPOWERREGULATOR, Storage.of(4, 0),
                Good.GMEDS, Storage.of(30, 0),
                Good.HEALTHMONITOR, Storage.of(2, 0)
        ));
        Assertions.assertThat(unknownGoods).isEmpty();
    }

    @Test
    void parse_backpack_unknown() throws IOException {
        final Map<Good, Storage> goods = new HashMap<>();
        goods.put(Good.LARGECAPACITYPOWERREGULATOR, new Storage());
        goods.put(Good.HEALTHMONITOR, new Storage());
        final Map<String, Storage> unknownGoods = new HashMap<>();

        final JsonNode jsonNode = this.objectMapper.readTree(DataParserTest.class.getResourceAsStream("/parser/backpack_unknown.json"));
        final Iterator<JsonNode> items = jsonNode.get("Items").elements();
        final GoodParser goodParser = new GoodParser();

        goodParser.parse(items, StoragePool.BACKPACK, goods, unknownGoods);

        Assertions.assertThat(goods).containsExactlyInAnyOrderEntriesOf(Map.of(
                Good.LARGECAPACITYPOWERREGULATOR, Storage.of(1, 0),
                Good.HEALTHMONITOR, Storage.of(2, 0)
        ));
        Assertions.assertThat(unknownGoods).containsExactlyInAnyOrderEntriesOf(Map.of(
                "banana:Banana", Storage.of(3, 0),
                "apple:apple", Storage.of(30, 0)
        ));
    }
}