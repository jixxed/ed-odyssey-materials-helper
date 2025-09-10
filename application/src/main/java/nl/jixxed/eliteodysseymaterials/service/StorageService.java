package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.*;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorageService {
    private static final Map<Raw, Integer> raw = new EnumMap<>(Raw.class);
    private static final Map<Encoded, Integer> encoded = new EnumMap<>(Encoded.class);
    private static final Map<Manufactured, Integer> manufactured = new EnumMap<>(Manufactured.class);
    private static final Map<Commodity, Integer> commoditiesShip = new HashMap<>();
    private static final Map<Commodity, Integer> commoditiesSrv = new HashMap<>();
    private static final Map<Commodity, Integer> commoditiesFleetcarrier = new HashMap<>();
    private static final Map<Commodity, Integer> commoditiesSquadroncarrier = new HashMap<>();
    private static final Map<Good, Storage> goods = new EnumMap<>(Good.class);
    private static final Map<Asset, Storage> assets = new EnumMap<>(Asset.class);
    private static final Map<Data, Storage> data = new EnumMap<>(Data.class);
    private static final Map<Consumable, Storage> consumables = new EnumMap<>(Consumable.class);

    static {
        initCounts();
    }

    public static Map<OdysseyMaterial, Storage> getMaterials(final OdysseyStorageType storageType) {
        return (Map<OdysseyMaterial, Storage>) switch (storageType) {
            case GOOD -> goods;
            case DATA -> data;
            case ASSET -> assets;
            case CONSUMABLE -> Collections.emptyMap();
            case OTHER -> consumables;
        };
    }

    public static Storage getMaterialStorage(final OdysseyMaterial odysseyMaterial) {
        if (odysseyMaterial instanceof Good) {
            return goods.get(odysseyMaterial);
        } else if (odysseyMaterial instanceof Asset) {
            return assets.get(odysseyMaterial);
        } else if (odysseyMaterial instanceof Data) {
            return data.get(odysseyMaterial);
        }
        throw new IllegalArgumentException("Unknown material type");
    }

    public static void removeMaterial(final HorizonsMaterial material, final Integer amount) {
        addMaterial(material, -amount);
    }

    public static void removeCommodity(final Commodity commodity, final StoragePool storagePool, final int amount) {
        addCommodity(commodity, storagePool, -amount);
    }

    public static void addMaterial(final HorizonsMaterial material, final Integer amount) {
        if (material instanceof Raw rawMaterial) {
            final int value = Math.min(raw.get(material) + amount, material.getMaxAmount());
            raw.put(rawMaterial, Math.max(value, 0));
        } else if (material instanceof Encoded encodedMaterial) {
            final int value = Math.min(encoded.get(material) + amount, material.getMaxAmount());
            encoded.put(encodedMaterial, Math.max(value, 0));
        } else if (material instanceof Manufactured manufacturedMaterial) {
            final int value = Math.min(manufactured.get(material) + amount, material.getMaxAmount());
            manufactured.put(manufacturedMaterial, Math.max(value, 0));
        } else if (material instanceof Commodity _) {
            throw new UnsupportedOperationException("use addCommodity instead");
        }
    }

    public static void removeMaterial(final OdysseyMaterial material, StoragePool storagePool, final Integer amount) {
        addMaterial(material, storagePool, -amount);
    }

    public static void addMaterial(OdysseyMaterial material, StoragePool storagePool, int amount) {
        if (material instanceof Asset assetMaterial) {
            final int value = assets.get(assetMaterial).getValue(storagePool) + amount;
            assets.get(assetMaterial).setValue(Math.max(value, 0), storagePool);
        } else if (material instanceof Good goodMaterial) {
            final int value = goods.get(goodMaterial).getValue(storagePool) + amount;
            goods.get(goodMaterial).setValue(Math.max(value, 0), storagePool);
        }else if (material instanceof Data dataMaterial) {
            final int value = data.get(dataMaterial).getValue(storagePool) + amount;
            data.get(dataMaterial).setValue(Math.max(value, 0), storagePool);
        }
    }

    public static void addCommodity(final Commodity commodity, final StoragePool storagePool, final Integer amount) {
        if (StoragePool.SQUADRONCARRIER.equals(storagePool)) {
            final int value = commoditiesSquadroncarrier.get(commodity) + amount;
            commoditiesSquadroncarrier.put(commodity, Math.max(value, 0));
        } else if (StoragePool.FLEETCARRIER.equals(storagePool)) {
            final int value = commoditiesFleetcarrier.get(commodity) + amount;
            commoditiesFleetcarrier.put(commodity, Math.max(value, 0));
        } else if (StoragePool.SHIP.equals(storagePool)) {
            final int value = commoditiesShip.get(commodity) + amount;
            commoditiesShip.put(commodity, Math.max(value, 0));
        } else if (StoragePool.SRV.equals(storagePool)) {
            final int value = commoditiesSrv.get(commodity) + amount;
            commoditiesSrv.put(commodity, Math.max(value, 0));
        } else {
            throw new IllegalArgumentException("storagePool not supported");
        }
    }

    public static Integer getMaterialCount(final HorizonsMaterial material) {
        return switch (material) {
            case Raw _ -> raw.get(material);
            case Encoded _ -> encoded.get(material);
            case Manufactured _ -> manufactured.get(material);
            case Commodity _ -> throw new UnsupportedOperationException("Use getCommodityCount instead");
        };
    }

    public static Integer getMaterialCount(final OdysseyMaterial material, AmountType amountType) {
        final Storage storage = getMaterialStorage(material);
        return switch (amountType) {
            case BACKPACK -> storage.getBackPackValue();
            case SHIPLOCKER -> storage.getShipLockerValue();
            case FLEETCARRIER -> storage.getFleetCarrierValue();
            case SQUADRONCARRIER -> storage.getSquadronCarrierValue();
            case TOTAL -> storage.getTotalValue();
            case AVAILABLE -> storage.getAvailableValue();
        };
    }

    public static Integer getCommodityCount(final Commodity commodity, final StoragePool storagePool) {
        if (StoragePool.SQUADRONCARRIER.equals(storagePool)) {
            return commoditiesSquadroncarrier.getOrDefault(commodity, 0);
        } else if (StoragePool.FLEETCARRIER.equals(storagePool)) {
            return commoditiesFleetcarrier.getOrDefault(commodity, 0);
        } else if (StoragePool.SHIP.equals(storagePool)) {
            return commoditiesShip.getOrDefault(commodity, 0);
        } else if (StoragePool.SRV.equals(storagePool)) {
            return commoditiesSrv.getOrDefault(commodity, 0);
        }
        throw new IllegalArgumentException("Unknown storagePool for commodity");
    }

    public static void resetShipLockerCounts() {
        assets.values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        data.values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        goods.values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
    }

    public static void resetFleetCarrierCounts() {
        assets.values().forEach(value -> value.setValue(0, StoragePool.FLEETCARRIER));
        data.values().forEach(value -> value.setValue(0, StoragePool.FLEETCARRIER));
        goods.values().forEach(value -> value.setValue(0, StoragePool.FLEETCARRIER));
        Stream.concat(Arrays.stream(RegularCommodity.values()), Arrays.stream(RareCommodity.values())).forEach(material ->
                commoditiesFleetcarrier.put(material, 0)
        );
    }

    public static void resetSquadronCarrierCounts() {
        assets.values().forEach(value -> value.setValue(0, StoragePool.SQUADRONCARRIER));
        data.values().forEach(value -> value.setValue(0, StoragePool.SQUADRONCARRIER));
        goods.values().forEach(value -> value.setValue(0, StoragePool.SQUADRONCARRIER));
        Stream.concat(Arrays.stream(RegularCommodity.values()), Arrays.stream(RareCommodity.values())).forEach(material ->
                commoditiesSquadroncarrier.put(material, 0)
        );
    }

    public static void resetSrvCounts() {
        Stream.concat(Arrays.stream(RegularCommodity.values()), Arrays.stream(RareCommodity.values())).forEach(material ->
                commoditiesSrv.put(material, 0)
        );
    }

    public static void resetBackPackCounts() {
         assets.values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
         data.values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
         goods.values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
    }

    public static void resetHorizonsMaterialCounts() {
        Arrays.stream(Raw.values()).forEach(material ->
                raw.put(material, 0)
        );
        Arrays.stream(Encoded.values()).forEach(material ->
                encoded.put(material, 0)
        );
        Arrays.stream(Manufactured.values()).forEach(material ->
                manufactured.put(material, 0)
        );
    }

    public static void resetHorizonsCommodityCounts() {
        Stream.concat(Arrays.stream(RegularCommodity.values()), Arrays.stream(RareCommodity.values())).forEach(material ->
                commoditiesShip.put(material, 0)
        );
    }

    private static void initCounts() {
        Arrays.stream(Asset.values()).forEach(material ->
                assets.put(material, new Storage())
        );
        Arrays.stream(Data.values()).forEach(material ->
                data.put(material, new Storage())
        );
        Arrays.stream(Good.values()).forEach(material ->
                goods.put(material, new Storage())
        );

        resetHorizonsMaterialCounts();

        Stream.concat(Arrays.stream(RegularCommodity.values()), Arrays.stream(RareCommodity.values())).forEach(material -> {
            commoditiesShip.put(material, 0);
            commoditiesFleetcarrier.put(material, 0);
            commoditiesSquadroncarrier.put(material, 0);
            commoditiesSrv.put(material, 0);
        });
    }

    public static Integer getStorageTotal(final OdysseyStorageType storageType, final StoragePool... storagePools) {
        return Arrays.stream(storagePools)
                .map(storagePool -> getMaterials(storageType).values().stream()
                        .map(storage -> storage.getValue(storagePool))
                        .mapToInt(Integer::intValue)
                        .sum())
                .mapToInt(Integer::intValue)
                .sum();
    }

}
