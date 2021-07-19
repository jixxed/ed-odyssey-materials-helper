package nl.jixxed.eliteodysseymaterials.domain;

import javafx.application.Platform;
import nl.jixxed.eliteodysseymaterials.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.*;

public class ApplicationState {

    private static ApplicationState applicationState;
    private final Map<Good, Storage> goods = new HashMap<>();
    private final Map<String, Storage> unknownGoods = new HashMap<>();
    private final Map<Asset, Storage> assets = new HashMap<>();
    private final Map<Data, Storage> data = new HashMap<>();
    private final Map<String, Storage> unknownData = new HashMap<>();
    private final List<Material> favourites = new ArrayList<>();
    private final List<RecipeName> wishlist = new ArrayList<>();
    private final Set<String> commanders = new HashSet<>();
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
        final String fav = PreferencesService.getPreference("material.favourites", "");
        Arrays.stream(fav.split(","))
                .filter(material -> !material.isBlank())
                .map(Material::subtypeForName)
                .forEach(this.favourites::add);
        final String recipes = PreferencesService.getPreference("wishlist.recipes", "");
        Arrays.stream(recipes.split(","))
                .filter(recipe -> !recipes.isBlank())
                .map(RecipeName::forName)
                .filter(Objects::nonNull)
                .forEach(this.wishlist::add);

        EventService.addListener(WishlistEvent.class,
                (wishlistEvent) -> {
                    Platform.runLater(() -> {
                        switch (wishlistEvent.getAction()) {
                            case ADDED -> addToWishList(wishlistEvent.getRecipeName());
                            case REMOVED -> removeFromWishList(wishlistEvent.getRecipeName());
                        }
                    });
                });
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


    public void resetEngineerStates() {
        this.engineerStates.forEach((engineer, engineerState) -> this.engineerStates.put(engineer, EngineerState.UNKNOWN));
        EventService.publish(new EngineerEvent());
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

    private void initCounts() {
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

    public <T extends Material> boolean toggleFavourite(final T material) {
        final boolean newState;
        if (this.favourites.contains(material)) {
            this.favourites.remove(material);
            newState = false;
        } else {
            this.favourites.add(material);
            newState = true;
        }
        PreferencesService.setPreference("material.favourites", this.favourites);
        return newState;
    }

    public boolean isFavourite(final Material material) {
        return this.favourites.contains(material);
    }

    private void addToWishList(final RecipeName recipe) {
        this.wishlist.add(recipe);
        PreferencesService.setRecipePreference("wishlist.recipes", this.wishlist);
        EventService.publish(new WishlistChangedEvent(this.wishlist.size()));
    }

    private void removeFromWishList(final RecipeName recipe) {
        this.wishlist.remove(recipe);
        PreferencesService.setRecipePreference("wishlist.recipes", this.wishlist);
        EventService.publish(new WishlistChangedEvent(this.wishlist.size()));
    }

    public List<RecipeName> getWishlist() {
        return this.wishlist;
    }

    public Set<String> getCommanders() {
        return this.commanders;
    }

    public Optional<String> getPreferredCommander() {
        final String preferredCommander = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
        if (!preferredCommander.isBlank() && this.commanders.contains(preferredCommander)) {
            return Optional.of(preferredCommander);
        }
        final Iterator<String> commanderIterator = this.commanders.iterator();
        if (commanderIterator.hasNext()) {
            final String commander = commanderIterator.next();
            PreferencesService.setPreference(PreferenceConstants.COMMANDER, commander);
            return Optional.of(commander);
        }
        return Optional.empty();
    }

    public void addCommander(final String name) {
        if (!this.commanders.contains(name)) {
            this.commanders.add(name);
            final String preferredCommander = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
            if (preferredCommander.isBlank()) {
                PreferencesService.setPreference(PreferenceConstants.COMMANDER, name);
            }
            EventService.publish(new CommanderAddedEvent(name));
        }
    }

    public void resetCommanders() {
        this.commanders.clear();
    }
}
