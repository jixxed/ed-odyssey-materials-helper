package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApplicationState {

    private static ApplicationState applicationState;
    private final Map<Good, Storage> goods = new HashMap<>();
    private final Map<String, Storage> unknownGoods = new HashMap<>();
    private final Map<Asset, Storage> assets = new HashMap<>();
    private final Map<Data, Storage> data = new HashMap<>();
    private final Map<String, Storage> unknownData = new HashMap<>();
    private Sort sort = Sort.ALPHABETICAL;
    private Show show = Show.ALL;
    private final Map<Engineer, EngineerState> engineerStates = new HashMap<>(Map.of(
            Engineer.DOMINO_GREEN, EngineerState.UNKNOWN,
            Engineer.HERO_FERRARI, EngineerState.UNKNOWN,
            Engineer.JUDE_NAVARRO, EngineerState.UNKNOWN,
            Engineer.KIT_FOWLER, EngineerState.UNKNOWN,
            Engineer.ODEN_GEIGER, EngineerState.UNKNOWN,
            Engineer.TERRA_VELASQUEZ, EngineerState.UNKNOWN,
            Engineer.UMA_LASZLO, EngineerState.UNKNOWN,
            Engineer.WELLINGTON_BECK, EngineerState.UNKNOWN,
            Engineer.YARDEN_BOND, EngineerState.UNKNOWN
    ));

    private ApplicationState() {
        this.initCounts();
    }

    public static ApplicationState getInstance() {
        if (applicationState == null) {
            applicationState = new ApplicationState();
        }
        return applicationState;
    }

    public Map<Good, Storage> getGoods() {
        return this.goods;
    }

    public Map<String, Storage> getUnknownGoods() {
        return this.unknownGoods;
    }

    public Map<Asset, Storage> getAssets() {
        return this.assets;
    }

    public Map<Data, Storage> getData() {
        return this.data;
    }

    public Map<String, Storage> getUnknownData() {
        return this.unknownData;
    }

    public boolean isEngineerKnown(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer);
        return EngineerState.KNOWN.equals(engineerState) || isEngineerUnlocked(engineer);

    }

    public boolean isEngineerUnlocked(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer);
        return EngineerState.INVITED.equals(engineerState) || EngineerState.UNLOCKED.equals(engineerState);
    }

    public void setEngineerState(final Engineer engineer, final EngineerState engineerState) {
        this.engineerStates.put(engineer, engineerState);
    }


    public void resetShipLockerCounts() {
        this.getAssets().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        this.getData().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        this.getGoods().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        this.getUnknownGoods().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
        this.getUnknownData().values().forEach(value -> value.setValue(0, StoragePool.SHIPLOCKER));
    }

    public void resetBackPackCounts() {
        this.getAssets().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
        this.getData().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
        this.getGoods().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
        this.getUnknownGoods().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
        this.getUnknownData().values().forEach(value -> value.setValue(0, StoragePool.BACKPACK));
    }

    public void initCounts() {
        Arrays.stream(Asset.values()).forEach(component ->
                this.getAssets().put(component, new Storage())
        );
        Arrays.stream(Data.values()).forEach(data ->
                this.getData().put(data, new Storage())
        );
        Arrays.stream(Good.values()).forEach(good ->
                this.getGoods().put(good, new Storage())
        );

    }

    public Sort getSort() {
        return this.sort;
    }

    public void setSort(final Sort sort) {
        this.sort = sort;
    }

    public Show getShow() {
        return this.show;
    }

    public void setShow(final Show show) {
        this.show = show;
    }
}
