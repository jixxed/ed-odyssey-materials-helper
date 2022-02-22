package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.BlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.WishlistHelper;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.event.trade.EnlistWebSocketEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Slf4j
public class ApplicationState {

    private static ApplicationState applicationState;
    private final Function<WishlistBlueprint, String> wishlistRecipeMapper = recipe -> recipe.getRecipeName().name() + ":" + recipe.isVisible();
    private final List<OdysseyMaterial> favourites = new ArrayList<>();
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
            Map.entry(Engineer.BALTANOS, EngineerState.UNKNOWN),
            Map.entry(Engineer.ELEANOR_BRESA, EngineerState.UNKNOWN),
            Map.entry(Engineer.ROSA_DAYETTE, EngineerState.UNKNOWN),
            Map.entry(Engineer.YI_SHEN, EngineerState.UNKNOWN)
    ));
    private GameMode gameMode = GameMode.NONE;

    private ApplicationState() {
        final String fav = PreferencesService.getPreference("material.favourites", "");
        Arrays.stream(fav.split(","))
                .filter(material -> !material.isBlank())
                .map(OdysseyMaterial::subtypeForName)
                .forEach(this.favourites::add);

        EventService.addListener(this, 0, WishlistBlueprintEvent.class,
                wishlistEvent -> Platform.runLater(() ->
                        wishlistEvent.getWishlistBlueprints().forEach(wishlistRecipe -> {
                            switch (wishlistEvent.getAction()) {
                                case ADDED -> addToWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getFid(), wishlistRecipe.getRecipeName());
                                case REMOVED -> removeFromWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getFid(), wishlistRecipe);
                                case VISIBILITY_CHANGED -> changeVisibility(wishlistEvent.getWishlistUUID(), wishlistEvent.getFid(), wishlistRecipe);
                            }
                        })));

        EventService.addListener(this, EnlistWebSocketEvent.class, event -> getPreferredCommander().ifPresent(commander -> PreferencesService.setPreference(PreferenceConstants.MARKETPLACE_TOKEN_PREFIX + commander.getFid(), event.getEnlistMessage().getTrace().getToken())));
        EventService.addListener(this, LoadGameEvent.class, event -> this.gameMode = event.getGameMode());
    }

    public static ApplicationState getInstance() {
        if (applicationState == null) {
            applicationState = new ApplicationState();
        }
        return applicationState;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public boolean getSoloMode() {
        return PreferencesService.getPreference(PreferenceConstants.SOLO_MODE, Boolean.FALSE);
    }

    public boolean isEngineerKnown(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer);
        return EngineerState.KNOWN.equals(engineerState) || isEngineerUnlocked(engineer);

    }

    public boolean isEngineerUnlocked(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer);
        return EngineerState.INVITED.equals(engineerState) || EngineerState.UNLOCKED.equals(engineerState);
    }

    public boolean isEngineerUnlockedExact(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer);
        return EngineerState.UNLOCKED.equals(engineerState);
    }

    public void setEngineerState(final Engineer engineer, final EngineerState engineerState) {
        this.engineerStates.put(engineer, engineerState);
    }


    public void resetEngineerStates() {
        this.engineerStates.forEach((engineer, engineerState) -> this.engineerStates.put(engineer, EngineerState.UNKNOWN));
        EventService.publish(new EngineerEvent());
    }

    public <T extends OdysseyMaterial> boolean toggleFavourite(final T material) {
        final boolean newState;
        if (this.favourites.contains(material)) {
            this.favourites.remove(material);
            newState = false;
        } else {
            this.favourites.add(material);
            newState = true;
        }
        PreferencesService.setPreference("material.favourites", this.favourites, OdysseyMaterial::name);
        return newState;
    }

    public boolean isFavourite(final OdysseyMaterial odysseyMaterial) {
        return this.favourites.contains(odysseyMaterial);
    }

    private void addToWishList(final String wishlistUUID, final String fid, final BlueprintName recipe) {
        final Wishlists wishlists = getWishlists(fid);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        wishlist.getItems().add(new WishlistBlueprint(recipe, true));
        saveWishlists(fid, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private void removeFromWishList(final String wishlistUUID, final String fid, final WishlistBlueprint recipe) {
        final Wishlists wishlists = getWishlists(fid);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<WishlistBlueprint> found = wishlist.getItems().stream().filter(wishlistRecipe -> wishlistRecipe.equals(recipe)).findFirst();
        found.ifPresent(wishlistRecipe -> wishlist.getItems().remove(wishlistRecipe));
        saveWishlists(fid, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private void changeVisibility(final String wishlistUUID, final String fid, final WishlistBlueprint wishlistBlueprint) {
        final Wishlists wishlists = getWishlists(fid);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<WishlistBlueprint> existingRecipe = wishlist.getItems().stream().filter(recipe -> recipe.getRecipeName().equals(wishlistBlueprint.getRecipeName()) && recipe.isVisible() == !wishlistBlueprint.isVisible()).findFirst();
        existingRecipe.ifPresent(recipe -> recipe.setVisible(wishlistBlueprint.isVisible()));
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

    private List<WishlistBlueprint> parseFIDWishlist(final String recipes) {
        return WishlistHelper.convertWishlist(recipes);
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

    public int amountCraftable(final BlueprintName blueprintName) {
        final Blueprint blueprint = BlueprintConstants.getRecipe(blueprintName);
        final AtomicInteger lowestAmount = new AtomicInteger(9999);
        blueprint.getMaterialCollection(OdysseyMaterial.class).forEach((material, amountRequired) -> {
            final int amountCraftable = StorageService.getMaterialStorage(material).getTotalValue() / amountRequired;
            lowestAmount.set(Math.min(amountCraftable, lowestAmount.get()));
        });
        return lowestAmount.get();
    }

    public Craftability getCraftability(final BlueprintName blueprintName) {
        final Blueprint blueprint = BlueprintConstants.getRecipe(blueprintName);
        final AtomicBoolean hasGoods = new AtomicBoolean(true);
        final AtomicBoolean hasData = new AtomicBoolean(true);
        final AtomicBoolean hasAssets = new AtomicBoolean(true);
        blueprint.getMaterialCollection(Good.class).forEach((material, amountRequired) -> hasGoods.set(hasGoods.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        blueprint.getMaterialCollection(Data.class).forEach((material, amountRequired) -> hasData.set(hasData.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        blueprint.getMaterialCollection(Asset.class).forEach((material, amountRequired) -> hasAssets.set(hasAssets.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        if (!hasGoods.get() || !hasData.get()) {
            return Craftability.NOT_CRAFTABLE;
        } else if (hasGoods.get() && hasData.get() && !hasAssets.get()) {
            return Craftability.CRAFTABLE_WITH_TRADE;
        } else {
            return Craftability.CRAFTABLE;
        }
    }

    public String getMarketPlaceToken() {
        return getPreferredCommander().map(commander -> PreferencesService.getPreference(PreferenceConstants.MARKETPLACE_TOKEN_PREFIX + commander.getFid(), "")).orElse("");
    }

    public LoadoutSetList getLoadoutSetList(final String fid) {
        final String loadoutSetList = PreferencesService.getPreference(PreferenceConstants.LOADOUTS_PREFIX + fid, "N/A");
        try {
            if (!loadoutSetList.equals("N/A")) {
                return OBJECT_MAPPER.readValue(loadoutSetList, LoadoutSetList.class);
            } else {
                return OBJECT_MAPPER.readValue(createLoadoutSetList(fid), LoadoutSetList.class);
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to load loadouts", e);
        }
        throw new IllegalStateException("Unable to load loadouts from configuration.");
    }

    private String createLoadoutSetList(final String fid) {
        final LoadoutSetList loadoutSetList = new LoadoutSetList();
        final LoadoutSet defaultLoadoutSet = new LoadoutSet();
        defaultLoadoutSet.setName("Default Loadout");
        defaultLoadoutSet.setLoadouts(List.of());
        loadoutSetList.addLoadoutSet(defaultLoadoutSet);
        saveLoadoutSetList(fid, loadoutSetList);
        return PreferencesService.getPreference(PreferenceConstants.LOADOUTS_PREFIX + fid, "N/A");
    }

    public void selectLoadoutSet(final String activeLoadoutSetUUID, final String fid) {
        final LoadoutSetList loadoutSetList = getLoadoutSetList(fid);
        loadoutSetList.setSelectedLoadoutSetUUID(activeLoadoutSetUUID);
        saveLoadoutSetList(fid, loadoutSetList);
    }

    public void deleteLoadoutSet(final String activeLoadoutSetUUID, final String fid) {
        final LoadoutSetList loadoutSetList = getLoadoutSetList(fid);
        loadoutSetList.delete(activeLoadoutSetUUID);
        saveLoadoutSetList(fid, loadoutSetList);
    }

    public void saveLoadoutSetList(final String fid, final LoadoutSetList loadoutSetList) {
        try {
            PreferencesService.setPreference(PreferenceConstants.LOADOUTS_PREFIX + fid, OBJECT_MAPPER.writeValueAsString(loadoutSetList));
        } catch (final JsonProcessingException e) {
            log.error("Failed to save loadouts", e);
        }
    }

    public void saveLoadoutSet(final String fid, final LoadoutSet loadoutSet) {
        final LoadoutSetList loadoutSetList = getLoadoutSetList(fid);
        loadoutSetList.updateLoadoutSet(loadoutSet);
        saveLoadoutSetList(fid, loadoutSetList);
    }

}
