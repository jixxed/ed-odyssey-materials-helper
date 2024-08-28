package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jixxed.eliteodysseymaterials.enums.AmountType;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Backpack.Backpack;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ShipLocker.ShipLocker;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AssetParserTest {
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
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(shipLocker.getComponents().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.SHIPLOCKER);
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.GRAPHENE, AmountType.SHIPLOCKER)).isEqualTo(45),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.AEROGEL, AmountType.SHIPLOCKER)).isEqualTo(44),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CARBONFIBREPLATING, AmountType.SHIPLOCKER)).isEqualTo(15),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CHEMICALCATALYST, AmountType.SHIPLOCKER)).isEqualTo(47),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CHEMICALSUPERBASE, AmountType.SHIPLOCKER)).isEqualTo(50));

    }

    @Test
    void parse_shiplocker_unknown() throws IOException {

        final ShipLocker shipLocker = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/shiplocker_unknown.json"), ShipLocker.class);
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(shipLocker.getComponents().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.SHIPLOCKER);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.GRAPHENE, AmountType.SHIPLOCKER)).isEqualTo(45),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.AEROGEL, AmountType.SHIPLOCKER)).isEqualTo(44),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CARBONFIBREPLATING, AmountType.SHIPLOCKER)).isEqualTo(15),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CHEMICALCATALYST, AmountType.SHIPLOCKER)).isEqualTo(47));
    }

    @Test
    void parse_backpack() throws IOException {

        final Backpack backpack = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/backpack_good.json"), Backpack.class);
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(backpack.getComponents().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.BACKPACK);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.GRAPHENE, AmountType.BACKPACK)).isEqualTo(45),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.AEROGEL, AmountType.BACKPACK)).isEqualTo(44),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CARBONFIBREPLATING, AmountType.BACKPACK)).isEqualTo(15),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CHEMICALCATALYST, AmountType.BACKPACK)).isEqualTo(47),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CHEMICALSUPERBASE, AmountType.BACKPACK)).isEqualTo(50));
    }

    @Test
    void parse_backpack_unknown() throws IOException {

        final Backpack backpack = this.objectMapper.readValue(DataParserTest.class.getResourceAsStream("/parser/backpack_unknown.json"), Backpack.class);
        final AssetParser goodParser = new AssetParser();

        goodParser.parse(backpack.getComponents().map(components -> components.stream().map(MaterialMapping::map).toList()).orElseThrow(IllegalArgumentException::new), StoragePool.BACKPACK);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.GRAPHENE, AmountType.BACKPACK)).isEqualTo(45),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.AEROGEL, AmountType.BACKPACK)).isEqualTo(44),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CARBONFIBREPLATING, AmountType.BACKPACK)).isEqualTo(15),
                () -> Assertions.assertThat(StorageService.getMaterialCount(Asset.CHEMICALCATALYST, AmountType.BACKPACK)).isEqualTo(47));
    }
}