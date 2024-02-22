package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Backpack.Backpack;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ShipLocker.ShipLocker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class DataParserTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    @Test
    void parse_shiplocker() throws IOException {
        final Map<Data, Storage> data = new HashMap<>();
        data.put(Data.VIRUS, new Storage());
        data.put(Data.SLUSHFUNDLOGS, new Storage());
        data.put(Data.BIOMETRICDATA, new Storage());
        data.put(Data.NOCDATA, new Storage());

        final ShipLocker shipLocker = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/shiplocker_good.json"), ShipLocker.class);
        final DataParser dataParser = new DataParser();

        dataParser.parse(shipLocker.getData().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.SHIPLOCKER, data);

        Assertions.assertThat(data).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.VIRUS, Storage.of(0, 1),
                Data.SLUSHFUNDLOGS, Storage.of(0, 1),
                Data.BIOMETRICDATA, Storage.of(0, 6),
                Data.NOCDATA, Storage.of(0, 3)
        ));
    }

    @Test
    void parse_shiplocker_unknown() throws IOException {
        final Map<Data, Storage> data = new HashMap<>();
        data.put(Data.SLUSHFUNDLOGS, new Storage());
        data.put(Data.NOCDATA, new Storage());

        final ShipLocker shipLocker = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/shiplocker_unknown.json"), ShipLocker.class);
        final DataParser dataParser = new DataParser();

        dataParser.parse(shipLocker.getData().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.SHIPLOCKER, data);

        Assertions.assertThat(data).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.SLUSHFUNDLOGS, Storage.of(0, 1),
                Data.NOCDATA, Storage.of(0, 3)
        ));
    }

    @Test
    void parse_backpack() throws IOException {
        final Map<Data, Storage> data = new HashMap<>();
        data.put(Data.VIRUS, new Storage());
        data.put(Data.SLUSHFUNDLOGS, new Storage());
        data.put(Data.BIOMETRICDATA, new Storage());
        data.put(Data.NOCDATA, new Storage());

        final Backpack backpack = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/backpack_good.json"), Backpack.class);
        final DataParser dataParser = new DataParser();

        dataParser.parse(backpack.getData().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.BACKPACK, data);

        Assertions.assertThat(data).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.VIRUS, Storage.of(1, 0),
                Data.SLUSHFUNDLOGS, Storage.of(1, 0),
                Data.BIOMETRICDATA, Storage.of(6, 0),
                Data.NOCDATA, Storage.of(3, 0)
        ));
    }

    @Test
    void parse_backpack_unknown() throws IOException {
        final Map<Data, Storage> data = new HashMap<>();
        data.put(Data.SLUSHFUNDLOGS, new Storage());
        data.put(Data.NOCDATA, new Storage());

        final Backpack backpack = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/backpack_unknown.json"), Backpack.class);
        final DataParser dataParser = new DataParser();

        dataParser.parse(backpack.getData().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.BACKPACK, data);

        Assertions.assertThat(data).containsExactlyInAnyOrderEntriesOf(Map.of(
                Data.SLUSHFUNDLOGS, Storage.of(1, 0),
                Data.NOCDATA, Storage.of(3, 0)
        ));
    }
}