package nl.jixxed.eliteodysseymaterials.domain;

import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class ApplicationState {

    private static ApplicationState applicationState;
    private final Map<Good, Storage> goods = new HashMap<>();
    private final Map<String, Storage> unknownGoods = new HashMap<>();
    private final Map<Asset, Storage> assets = new HashMap<>();
    private final Map<Data, Storage> data = new HashMap<>();
    private final Map<String, Storage> unknownData = new HashMap<>();
    private final List<Material> favourites = new ArrayList<>();
    private final Set<Commander> commanders = new HashSet<>();
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

        EventService.addListener(WishlistRecipeEvent.class,
                (wishlistEvent) -> {
                    Platform.runLater(() -> {
                        switch (wishlistEvent.getAction()) {
                            case ADDED -> addToWishList(wishlistEvent.getFid(), wishlistEvent.getWishlistRecipe().getRecipeName());
                            case REMOVED -> removeFromWishList(wishlistEvent.getFid(), wishlistEvent.getWishlistRecipe());
                            case VISIBILITY_CHANGED -> changeVisibility(wishlistEvent.getFid(), wishlistEvent.getWishlistRecipe());
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

    private void addToWishList(final String fid, final RecipeName recipe) {
        final List<WishlistRecipe> wishlist = getWishlist(fid);
        wishlist.add(new WishlistRecipe(recipe, true));
        PreferencesService.setRecipePreference("wishlist.recipes." + fid, wishlist);
        EventService.publish(new WishlistChangedEvent(wishlist.size()));
    }

    private void removeFromWishList(final String fid, final WishlistRecipe recipe) {
        final List<WishlistRecipe> wishlist = getWishlist(fid);
        final Optional<WishlistRecipe> found = wishlist.stream().filter(wishlistRecipe -> wishlistRecipe.equals(recipe)).findFirst();
        found.ifPresent(wishlist::remove);
        PreferencesService.setRecipePreference("wishlist.recipes." + fid, wishlist);
        EventService.publish(new WishlistChangedEvent(wishlist.size()));
    }

    private void changeVisibility(final String fid, final WishlistRecipe wishlistRecipe) {
        final List<WishlistRecipe> wishlist = getWishlist(fid);
        final Optional<WishlistRecipe> existingRecipe = wishlist.stream().filter(recipe -> recipe.getRecipeName().equals(wishlistRecipe.getRecipeName()) && recipe.isVisible() == !wishlistRecipe.isVisible()).findFirst();
        existingRecipe.ifPresent(recipe -> {
            recipe.setVisible(wishlistRecipe.isVisible());
        });
        PreferencesService.setRecipePreference("wishlist.recipes." + fid, wishlist);
        EventService.publish(new WishlistChangedEvent(wishlist.size()));
    }

    public List<WishlistRecipe> getWishlist(final String fid) {
        final String recipes = PreferencesService.getPreference("wishlist.recipes." + fid, "N/A");
        if (!recipes.equals("N/A")) {
            final List<WishlistRecipe> wishlist = new ArrayList<>();
            Arrays.stream(recipes.split(","))
                    .filter(recipe -> !recipes.isBlank())
                    .map(recipe -> {
                        final String[] splittedRecipe = recipe.split(":");
                        return new WishlistRecipe(RecipeName.forName(splittedRecipe[0]), Boolean.parseBoolean(splittedRecipe[1]));
                    })
                    .filter(Objects::nonNull)
                    .forEach(wishlist::add);
            return wishlist;
        } else {
            final String oldRecipes = PreferencesService.getPreference("wishlist.recipes", "");
            final List<RecipeName> oldWishlist = new ArrayList<>();
            Arrays.stream(oldRecipes.split(","))
                    .filter(recipe -> !recipes.isBlank())
                    .map(RecipeName::forName)
                    .filter(Objects::nonNull)
                    .forEach(oldWishlist::add);
            final List<WishlistRecipe> wishlist = oldWishlist.stream().map(recipeName -> new WishlistRecipe(recipeName, true)).collect(Collectors.toList());
            PreferencesService.setRecipePreference("wishlist.recipes." + fid, wishlist);
            PreferencesService.setRecipePreference("wishlist.recipes", new ArrayList<>());
            return wishlist;
        }
    }

    public Set<Commander> getCommanders() {
        return this.commanders;
    }

    public Optional<Commander> getPreferredCommander() {
        final String preferredCommander = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
        if (!preferredCommander.isBlank() && this.commanders.stream().anyMatch(commander -> commander.getName().equals(preferredCommander))) {
            return this.commanders.stream().filter(commander -> commander.getName().equals(preferredCommander)).findFirst();
        }
        final Iterator<Commander> commanderIterator = this.commanders.iterator();
        if (commanderIterator.hasNext()) {
            final Commander commander = commanderIterator.next();
            PreferencesService.setPreference(PreferenceConstants.COMMANDER, commander.getName());
            return Optional.of(commander);
        }
        return Optional.empty();
    }

    public void addCommander(final String name, final String fid) {
        if (this.commanders.stream().noneMatch(commander -> commander.getName().equals(name))) {
            final Commander commander = new Commander(name, fid);
            this.commanders.add(commander);
            final String preferredCommander = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
            if (preferredCommander.isBlank()) {
                PreferencesService.setPreference(PreferenceConstants.COMMANDER, name);
            }
            EventService.publish(new CommanderAddedEvent(commander));
        }
    }

    public void resetCommanders() {
        this.commanders.clear();
    }

    public int amountCraftable(final RecipeName recipeName) {
        final Recipe recipe = RecipeConstants.getRecipe(recipeName);
        final AtomicInteger lowestAmount = new AtomicInteger(9999);
        recipe.getMaterialCollection(Good.class).forEach((good, integer) -> {
            final int amountCraftable = this.goods.get(good).getTotalValue() / integer;
            lowestAmount.set(Math.min(amountCraftable, lowestAmount.get()));
        });
        recipe.getMaterialCollection(Asset.class).forEach((asset, integer) -> {
            final int amountCraftable = this.assets.get(asset).getTotalValue() / integer;
            lowestAmount.set(Math.min(amountCraftable, lowestAmount.get()));
        });
        recipe.getMaterialCollection(Data.class).forEach((data, integer) -> {
            final int amountCraftable = this.data.get(data).getTotalValue() / integer;
            lowestAmount.set(Math.min(amountCraftable, lowestAmount.get()));
        });
        return lowestAmount.get();
    }
}
