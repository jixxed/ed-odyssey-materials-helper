package nl.jixxed.eliteodysseymaterials.service;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.AnyRelevantStorage;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.*;

public class StorageService {
    @Getter
    private static final Map<Raw, Integer> raw = new EnumMap<>(Raw.class);
    @Getter
    private static final Map<Encoded, Integer> encoded = new EnumMap<>(Encoded.class);
    @Getter
    private static final Map<Manufactured, Integer> manufactured = new EnumMap<>(Manufactured.class);
    @Getter
    private static final Map<Good, Storage> goods = new EnumMap<>(Good.class);
    @Getter
    private static final Map<Asset, Storage> assets = new EnumMap<>(Asset.class);
    @Getter
    private static final Map<Data, Storage> data = new EnumMap<>(Data.class);
    @Getter
    private static final Map<Consumable, Storage> consumables = new EnumMap<>(Consumable.class);
    @Getter
    private static final Map<String, Storage> unknownData = new HashMap<>();
    @Getter
    private static final Map<String, Storage> unknownGoods = new HashMap<>();

    static {
        initCounts();
    }

    public static Map<Material, Storage> getMaterials(final OdysseyStorageType storageType) {
        return (Map<Material, Storage>) switch (storageType) {
            case GOOD -> goods;
            case DATA -> data;
            case ASSET -> assets;
            case TRADE -> Map.of(TradeMaterial.ANY_RELEVANT, new AnyRelevantStorage(), TradeMaterial.NOTHING, new Storage(0, 0));
            case CONSUMABLE -> Collections.emptyMap();
            case OTHER -> consumables;
        };
    }

    public static Map<? extends HorizonsMaterial, Integer> getMaterials(final HorizonsStorageType storageType) {
        return switch (storageType) {
            case RAW -> raw;
            case ENCODED -> encoded;
            case MANUFACURED -> manufactured;
        };
    }

    public static Storage getMaterialStorage(final Material material) {
        if (material instanceof Good) {
            return goods.get(material);
        } else if (material instanceof Asset) {
            return assets.get(material);
        } else if (material instanceof Data) {
            return data.get(material);
        }
        throw new IllegalArgumentException("Unknown material type");
    }

    public static void addMaterial(final HorizonsMaterial material, final Integer amount) {
        if (material instanceof Raw rawMaterial) {
            raw.put(rawMaterial, raw.get(material) + amount);
        } else if (material instanceof Encoded encodedMaterial) {
            encoded.put(encodedMaterial, encoded.get(material) + amount);
        } else if (material instanceof Manufactured manufacturedMaterial) {
            manufactured.put(manufacturedMaterial, manufactured.get(material) + amount);
        }
    }

    public static Integer getMaterialCount(final HorizonsMaterial material) {
        if (material instanceof Raw) {
            return raw.get(material);
        } else if (material instanceof Encoded) {
            return encoded.get(material);
        } else if (material instanceof Manufactured) {
            return manufactured.get(material);
        }
        throw new IllegalArgumentException("Unknown material type");
    }

    public static void resetShipLockerCounts() {
        getAssets().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        getData().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        getGoods().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        getUnknownGoods().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        getUnknownData().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
    }

    public static void resetBackPackCounts() {
        getAssets().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
        getData().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
        getGoods().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
        getUnknownGoods().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
        getUnknownData().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
    }

    public static void resetHorizonsMaterialCounts() {
        Arrays.stream(Raw.values()).forEach(material ->
                getRaw().put(material, 0)
        );
        Arrays.stream(Encoded.values()).forEach(material ->
                getEncoded().put(material, 0)
        );
        Arrays.stream(Manufactured.values()).forEach(material ->
                getManufactured().put(material, 0)
        );
    }

    private static void initCounts() {
        Arrays.stream(Asset.values()).forEach(material ->
                getAssets().put(material, new Storage())
        );
        Arrays.stream(Data.values()).forEach(material ->
                getData().put(material, new Storage())
        );
        Arrays.stream(Good.values()).forEach(material ->
                getGoods().put(material, new Storage())
        );
        Arrays.stream(Raw.values()).forEach(material ->
                getRaw().put(material, 0)
        );
        Arrays.stream(Encoded.values()).forEach(material ->
                getEncoded().put(material, 0)
        );
        Arrays.stream(Manufactured.values()).forEach(material ->
                getManufactured().put(material, 0)
        );

    }
}
