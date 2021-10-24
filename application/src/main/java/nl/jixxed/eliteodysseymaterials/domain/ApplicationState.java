package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Slf4j
public class ApplicationState {
    private static ApplicationState applicationState;
    private final Function<WishlistRecipe, String> wishlistRecipeMapper = recipe -> recipe.getRecipeName().name() + ":" + recipe.isVisible();
    private final Map<Good, Storage> goods = new EnumMap<>(Good.class);
    private final Map<String, Storage> unknownGoods = new HashMap<>();
    private final Map<Asset, Storage> assets = new EnumMap<>(Asset.class);
    private final Map<Data, Storage> data = new EnumMap<>(Data.class);
    private final Map<String, Storage> unknownData = new HashMap<>();
    private final List<Material> favourites = new ArrayList<>();
    private final Set<Commander> commanders = new HashSet<>();
    private final Map<Engineer, EngineerState> engineerStates = new EnumMap<>(Map.ofEntries(
            Map.entry(Engineer.DOMINO_GREEN, EngineerState.UNKNOWN),
            Map.entry(Engineer.HERO_FERRARI, EngineerState.UNKNOWN),
            Map.entry(Engineer.JUDE_NAVARRO, EngineerState.UNKNOWN),
            Map.entry(Engineer.KIT_FOWLER, EngineerState.UNKNOWN),
            Map.entry(Engineer.ODEN_GEIGER, EngineerState.UNKNOWN),
            Map.entry(Engineer.TERRA_VELASQUEZ, EngineerState.UNKNOWN),
            Map.entry(Engineer.UMA_LASZLO, EngineerState.UNKNOWN),
            Map.entry(Engineer.WELLINGTON_BECK, EngineerState.UNKNOWN),
            Map.entry(Engineer.YARDEN_BOND, EngineerState.UNKNOWN),
            Map.entry(Engineer.BALTANOS, EngineerState.UNDEFINED),
            Map.entry(Engineer.ELEANOR_BRESA, EngineerState.UNDEFINED),
            Map.entry(Engineer.ROSA_DAYETTE, EngineerState.UNDEFINED),
            Map.entry(Engineer.YI_SHEN, EngineerState.UNDEFINED)
    ));

    private ApplicationState() {
        this.initCounts();
        final String fav = PreferencesService.getPreference("material.favourites", "");
        Arrays.stream(fav.split(","))
                .filter(material -> !material.isBlank())
                .map(Material::subtypeForName)
                .forEach(this.favourites::add);

        EventService.addListener(0, WishlistRecipeEvent.class,
                wishlistEvent -> Platform.runLater(() -> {
                    switch (wishlistEvent.getAction()) {
                        case ADDED -> addToWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getFid(), wishlistEvent.getWishlistRecipe().getRecipeName());
                        case REMOVED -> removeFromWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getFid(), wishlistEvent.getWishlistRecipe());
                        case VISIBILITY_CHANGED -> changeVisibility(wishlistEvent.getWishlistUUID(), wishlistEvent.getFid(), wishlistEvent.getWishlistRecipe());
                    }
                }));

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

    public boolean getSoloMode() {
        return PreferencesService.getPreference(PreferenceConstants.SOLO_MODE, Boolean.FALSE);
    }

    public Map<Material, Storage> getMaterials(final StorageType storageType) {
        return (Map<Material, Storage>) switch (storageType) {
            case GOOD -> this.goods;
            case DATA -> this.data;
            case ASSET -> this.assets;
            case OTHER -> Collections.emptyMap();
        };
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
        return EngineerState.INVITED.equals(engineerState) || EngineerState.UNLOCKED.equals(engineerState) || EngineerState.UNDEFINED.equals(engineerState);
    }

    public void setEngineerState(final Engineer engineer, final EngineerState engineerState) {
        this.engineerStates.put(engineer, engineerState);
    }


    public void resetEngineerStates() {
        this.engineerStates.forEach((engineer, engineerState) -> {
            if (!EngineerState.UNDEFINED.equals(engineerState)) {
                this.engineerStates.put(engineer, EngineerState.UNKNOWN);
            }
        });
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
        Arrays.stream(Asset.values()).forEach(material ->
                this.getAssets().put(material, new Storage())
        );
        Arrays.stream(Data.values()).forEach(material ->
                this.getData().put(material, new Storage())
        );
        Arrays.stream(Good.values()).forEach(material ->
                this.getGoods().put(material, new Storage())
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
        PreferencesService.setPreference("material.favourites", this.favourites, Material::toString);
        return newState;
    }

    public boolean isFavourite(final Material material) {
        return this.favourites.contains(material);
    }

    private void addToWishList(final String wishlistUUID, final String fid, final RecipeName recipe) {
        final Wishlists wishlists = getWishlists(fid);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        wishlist.getItems().add(new WishlistRecipe(recipe, true));
        saveWishlists(fid, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private void removeFromWishList(final String wishlistUUID, final String fid, final WishlistRecipe recipe) {
        final Wishlists wishlists = getWishlists(fid);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<WishlistRecipe> found = wishlist.getItems().stream().filter(wishlistRecipe -> wishlistRecipe.equals(recipe)).findFirst();
        found.ifPresent(wishlistRecipe -> wishlist.getItems().remove(wishlistRecipe));
        saveWishlists(fid, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private void changeVisibility(final String wishlistUUID, final String fid, final WishlistRecipe wishlistRecipe) {
        final Wishlists wishlists = getWishlists(fid);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<WishlistRecipe> existingRecipe = wishlist.getItems().stream().filter(recipe -> recipe.getRecipeName().equals(wishlistRecipe.getRecipeName()) && recipe.isVisible() == !wishlistRecipe.isVisible()).findFirst();
        existingRecipe.ifPresent(recipe -> recipe.setVisible(wishlistRecipe.isVisible()));
        saveWishlists(fid, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    public void selectWishlist(final String wishlistUUID, final String fid) {
        final Wishlists wishlists = getWishlists(fid);
        wishlists.setSelectedWishlistUUID(wishlistUUID);
        saveWishlists(fid, wishlists);
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Wishlists getWishlists(final String fid) {
        final String wishlists = PreferencesService.getPreference(PreferenceConstants.WISHLISTS_PREFIX + fid, "N/A");
        try {
            if (wishlists.equals("")) {
                return OBJECT_MAPPER.readValue(getOldStyleWishList2(fid), Wishlists.class);
            } else if (!wishlists.equals("N/A")) {
                return OBJECT_MAPPER.readValue(wishlists, Wishlists.class);
            } else {
                return OBJECT_MAPPER.readValue(getOldStyleWishList2(fid), Wishlists.class);
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to load wishlists", e);
        }
        throw new IllegalStateException("Unable to load wishlists from configuration.");
    }

    public void saveWishlists(final String fid, final Wishlists wishlists) {
        try {
            PreferencesService.setPreference(PreferenceConstants.WISHLISTS_PREFIX + fid, OBJECT_MAPPER.writeValueAsString(wishlists));
        } catch (final JsonProcessingException e) {
            log.error("Failed to save wishlists", e);
        }
    }

    public void deleteWishlist(final String activeWishlistUUID, final String fid) {
        final Wishlists wishlists = getWishlists(fid);
        wishlists.delete(activeWishlistUUID);
        saveWishlists(fid, wishlists);
    }

    private String getOldStyleWishList2(final String fid) {
        final String recipes = PreferencesService.getPreference(PreferenceConstants.WISHLIST_RECIPES_PREFIX + fid, "");
        //transfer old style to new style
        final Wishlists wishlists = new Wishlists();
        final Wishlist defaultWishlist = new Wishlist();
        defaultWishlist.setName("Default wishlist");
        defaultWishlist.setItems(parseFIDWishlist(recipes));
        wishlists.addWishlist(defaultWishlist);
        try {
            PreferencesService.setPreference(PreferenceConstants.WISHLISTS_PREFIX + fid, OBJECT_MAPPER.writeValueAsString(wishlists));
            //reset old style to empty
            PreferencesService.setPreference(PreferenceConstants.WISHLIST_RECIPES_PREFIX, new ArrayList<>(), this.wishlistRecipeMapper);
        } catch (final JsonProcessingException e) {
            log.error("Failed to save wishlists", e);
        }
        return PreferencesService.getPreference(PreferenceConstants.WISHLISTS_PREFIX + fid, "N/A");
    }

//    private List<WishlistRecipe> getOldStyleWishList(final String fid, final String recipes) {
//        final String oldRecipes = PreferencesService.getPreference(PreferenceConstants.WISHLIST_RECIPES, "");
//        final List<RecipeName> oldWishlist = new ArrayList<>();
//        Arrays.stream(oldRecipes.split(","))
//                .filter(recipe -> !recipes.isBlank())
//                .map(RecipeName::forName)
//                .filter(Objects::nonNull)
//                .forEach(oldWishlist::add);
//        final List<WishlistRecipe> wishlist = oldWishlist.stream().map(recipeName -> new WishlistRecipe(recipeName, true)).collect(Collectors.toList());
//        //transfer old style to new style
//        PreferencesService.setPreference(PreferenceConstants.WISHLIST_RECIPES_PREFIX + fid, wishlist, this.wishlistRecipeMapper);
//        //reset old style to empty
//        PreferencesService.setPreference(PreferenceConstants.WISHLIST_RECIPES, new ArrayList<>(), this.wishlistRecipeMapper);
//        return wishlist;
//    }

    private List<WishlistRecipe> parseFIDWishlist(final String recipes) {
        final List<WishlistRecipe> wishlist = new ArrayList<>();
        Arrays.stream(recipes.split(","))
                .filter(recipe -> !recipes.isBlank())
                .map(recipe -> {
                    final String[] splittedRecipe = recipe.split(":");
                    return new WishlistRecipe(RecipeName.forName(splittedRecipe[0]), Boolean.parseBoolean(splittedRecipe[1]));
                })
                .forEach(wishlist::add);
        return wishlist;
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
        recipe.getMaterialCollection(Material.class).forEach((material, amountRequired) -> {
            final int amountCraftable = getMaterialStorage(material).getTotalValue() / amountRequired;
            lowestAmount.set(Math.min(amountCraftable, lowestAmount.get()));
        });
        return lowestAmount.get();
    }

    private Storage getMaterialStorage(final Material material) {
        if (material instanceof Good) {
            return this.goods.get(material);
        } else if (material instanceof Asset) {
            return this.assets.get(material);
        } else if (material instanceof Data) {
            return this.data.get(material);
        }
        throw new IllegalArgumentException("Unknown material type");
    }


}
