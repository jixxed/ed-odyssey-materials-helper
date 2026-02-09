package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.BuyOrder;
import nl.jixxed.eliteodysseymaterials.domain.SellOrder;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderService {
    private static final Map<StoragePool, Map<Material, BuyOrder>>  BUY_ORDERS = new HashMap<>();
    private static final Map<StoragePool, Map<Material, SellOrder>>  SELL_ORDERS = new HashMap<>();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    public static void clearOrders(StoragePool storagePool) {
        BUY_ORDERS.get(storagePool).clear();
        SELL_ORDERS.get(storagePool).clear();
    }

    static {
        BUY_ORDERS.put(StoragePool.FLEETCARRIER, new HashMap<>());
        BUY_ORDERS.put(StoragePool.SQUADRONCARRIER, new HashMap<>());
        SELL_ORDERS.put(StoragePool.FLEETCARRIER, new HashMap<>());
        SELL_ORDERS.put(StoragePool.SQUADRONCARRIER, new HashMap<>());
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, event -> {
            if (event.isInitialised()) {
                clearOrders(StoragePool.FLEETCARRIER);
                clearOrders(StoragePool.SQUADRONCARRIER);
            }
        }));
    }

    public static void addBuyOrder(final Material material, final Integer price, final Integer total,
                                   final Integer outstanding, StoragePool storagePool) {
        if (price == 0 || total == 0) {
            removeBuyOrder(material, storagePool);
        } else {
            BUY_ORDERS.get(storagePool).put(material, new BuyOrder(price, total, outstanding));
        }
        removeSellOrder(material, storagePool);
    }

    public static void addSellOrder(final Material material, final Integer price, final Integer stock, StoragePool storagePool) {
        if (price == 0 || stock == 0) {
            removeSellOrder(material, storagePool);
        } else {
            SELL_ORDERS.get(storagePool).put(material, new SellOrder(price, stock));
        }
        removeBuyOrder(material, storagePool);
    }

    public static void removeSellOrder(Material material, StoragePool storagePool) {
        SELL_ORDERS.get(storagePool).remove(material);
    }

    public static void removeBuyOrder(Material material, StoragePool storagePool) {
        BUY_ORDERS.get(storagePool).remove(material);
    }

    static BuyOrder getBuyOrder(final Material material, StoragePool storagePool) {
        return BUY_ORDERS.get(storagePool).get(material);
    }

    static SellOrder getSellOrder(final Material material, StoragePool storagePool) {
        return SELL_ORDERS.get(storagePool).get(material);
    }

    public static Integer getBuyOrderOutstandingCount(final StoragePool storagePool) {
        return BUY_ORDERS.get(storagePool).entrySet().stream().filter(entry -> entry.getKey() instanceof Commodity).map(Map.Entry::getValue).mapToInt(BuyOrder::getOutstanding).sum();
    }

}
