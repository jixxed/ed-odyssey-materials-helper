package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jixxed.eliteodysseymaterials.enums.AmountType;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Backpack.Backpack;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ShipLocker.ShipLocker;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DataParserTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    @BeforeEach
    void setUp() {
        StorageService.resetBackPackCounts();
        StorageService.resetShipLockerCounts();
    }

    @Test
    void parse_shiplocker() throws IOException {

        final ShipLocker shipLocker = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/shiplocker_good.json"), ShipLocker.class);
        final DataParser dataParser = new DataParser();

        dataParser.parse(shipLocker.getData().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.SHIPLOCKER);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.VIRUS, AmountType.SHIPLOCKER)).isEqualTo(1),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.SLUSHFUNDLOGS, AmountType.SHIPLOCKER)).isEqualTo(1),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.BIOMETRICDATA, AmountType.SHIPLOCKER)).isEqualTo(6),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.NOCDATA, AmountType.SHIPLOCKER)).isEqualTo(3));
    }

    @Test
    void parse_shiplocker_unknown() throws IOException {

        final ShipLocker shipLocker = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/shiplocker_unknown.json"), ShipLocker.class);
        final DataParser dataParser = new DataParser();

        dataParser.parse(shipLocker.getData().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.SHIPLOCKER);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.SLUSHFUNDLOGS, AmountType.SHIPLOCKER)).isEqualTo(1),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.NOCDATA, AmountType.SHIPLOCKER)).isEqualTo(3));

    }

    @Test
    void parse_backpack() throws IOException {

        final Backpack backpack = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/backpack_good.json"), Backpack.class);
        final DataParser dataParser = new DataParser();

        dataParser.parse(backpack.getData().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.BACKPACK);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.VIRUS, AmountType.BACKPACK)).isEqualTo(1),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.SLUSHFUNDLOGS, AmountType.BACKPACK)).isEqualTo(1),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.BIOMETRICDATA, AmountType.BACKPACK)).isEqualTo(6),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.NOCDATA, AmountType.BACKPACK)).isEqualTo(3));
    }

    @Test
    void parse_backpack_unknown() throws IOException {

        final Backpack backpack = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/backpack_unknown.json"), Backpack.class);
        final DataParser dataParser = new DataParser();

        dataParser.parse(backpack.getData().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.BACKPACK);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.SLUSHFUNDLOGS, AmountType.BACKPACK)).isEqualTo(1),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Data.NOCDATA, AmountType.BACKPACK)).isEqualTo(3));
    }
}