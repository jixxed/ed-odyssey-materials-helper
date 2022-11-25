package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.event.trade.EnlistWebSocketEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ApplicationState {
    @SuppressWarnings("java:S1068")
    private static PreferencesService preferencesService;//defined so app folder gets created for lockfile
    private static FileLock fileLock;
    private static ApplicationState applicationState;
    private final List<OdysseyMaterial> favourites = new ArrayList<>();
    private final Set<Commander> commanders = new HashSet<>();
    private final Map<Engineer, EngineerStatus> engineerStates = new EnumMap<>(Map.ofEntries(
            Map.entry(Engineer.DOMINO_GREEN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.HERO_FERRARI, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.JUDE_NAVARRO, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.KIT_FOWLER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ODEN_GEIGER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.TERRA_VELASQUEZ, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.UMA_LASZLO, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.WELLINGTON_BECK, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.YARDEN_BOND, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.BALTANOS, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ELEANOR_BRESA, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ROSA_DAYETTE, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.YI_SHEN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.COLONEL_BRIS_DEKKER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.MARCO_QWENT, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.THE_DWELLER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.LORI_JAMESON, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.THE_SARGE, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.SELENE_JEAN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.LIZ_RYDER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.BILL_TURNER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.TOD_THE_BLASTER_MCQUINN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.PROFESSOR_PALIN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.DIDI_VATERMANN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.JURI_ISHMAAK, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.LEI_CHEUNG, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.FELICITY_FARSEER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.TIANA_FORTUNE, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ZACARIAH_NEMO, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.RAM_TAH, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.BROO_TARQUIN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ELVIRA_MARTUUK, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.HERA_TANI, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.CHLOE_SEDESI, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.MARSHA_HICKS, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ETIENNE_DORN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.MEL_BRANDON, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.PETRA_OLMANOVA, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.REMOTE_WORKSHOP, new EngineerStatus(EngineerState.UNLOCKED, 5, 0)),
            Map.entry(Engineer.UNKNOWN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0))
    ));
    private GameMode gameMode = GameMode.NONE;
    @Getter
    private final GameVersion gameVersion = GameVersion.UNKNOWN;

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
                                case ADDED ->
                                        addToWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), wishlistRecipe.getRecipeName());
                                case REMOVED ->
                                        removeFromWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), wishlistRecipe);
                                case VISIBILITY_CHANGED ->
                                        changeVisibility(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), wishlistRecipe);
                                case MODIFY -> {
                                }
                            }
                        })));

        EventService.addListener(this, 0, HorizonsWishlistBlueprintEvent.class,
                wishlistEvent -> Platform.runLater(() ->
                        wishlistEvent.getWishlistBlueprints().forEach(horizonsWishlistBlueprint -> {
                            switch (wishlistEvent.getAction()) {
                                case ADDED ->
                                        addToHorizonsWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), horizonsWishlistBlueprint);
                                case REMOVED ->
                                        removeFromHorizonsWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), horizonsWishlistBlueprint);
                                case VISIBILITY_CHANGED ->
                                        changeHorizonsVisibility(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), horizonsWishlistBlueprint);
                                case MODIFY ->
                                        modifyHorizonsBlueprint(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), horizonsWishlistBlueprint);
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
        final EngineerState engineerState = this.engineerStates.get(engineer).getEngineerState();
        return EngineerState.KNOWN.equals(engineerState) || isEngineerUnlocked(engineer);

    }

    public boolean isEngineerUnlocked(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer).getEngineerState();
        return EngineerState.INVITED.equals(engineerState) || EngineerState.UNLOCKED.equals(engineerState);
    }

    public boolean isEngineerInvited(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer).getEngineerState();
        return EngineerState.INVITED.equals(engineerState);
    }

    public boolean isEngineerUnlockedExact(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer).getEngineerState();
        return EngineerState.UNLOCKED.equals(engineerState);
    }

    public void setEngineerStatus(final Engineer engineer, final EngineerState engineerState, final Integer rank, final Integer progress) {
        final EngineerStatus engineerStatus = this.engineerStates.get(engineer);
        engineerStatus.setEngineerState(engineerState);
        engineerStatus.setRank(rank);
        engineerStatus.setProgress(progress);
    }

    public void setEngineerState(final Engineer engineer, final EngineerState engineerState) {
        this.engineerStates.get(engineer).setEngineerState(engineerState);
    }

    public void setEngineerRank(final Engineer engineer, final Integer rank) {
        this.engineerStates.get(engineer).setRank(rank);
    }

    public Integer getEngineerRank(final Engineer engineer) {
        return this.engineerStates.get(engineer).getRank();
    }

    public void setEngineerProgress(final Engineer engineer, final Integer progress) {
        this.engineerStates.get(engineer).setProgress(progress);
    }

    public Integer getEngineerProgress(final Engineer engineer) {
        return this.engineerStates.get(engineer).getProgress();
    }


    public void resetEngineerStates() {
        this.engineerStates.forEach((engineer, engineerState) -> {
            this.engineerStates.get(engineer).setEngineerState(EngineerState.UNKNOWN);
            this.engineerStates.get(engineer).setProgress(0);
            this.engineerStates.get(engineer).setRank(0);
        });

        final EngineerStatus engineerStatus = this.engineerStates.get(Engineer.REMOTE_WORKSHOP);
        engineerStatus.setEngineerState(EngineerState.UNLOCKED);
        engineerStatus.setRank(5);
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

    private void addToWishList(final String wishlistUUID, final Commander commander, final BlueprintName recipe) {
        final Wishlists wishlists = getWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        wishlist.getItems().add(new OdysseyWishlistBlueprint((OdysseyBlueprintName) recipe, true));
        saveWishlists(commander, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private void addToHorizonsWishList(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint recipe) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        wishlist.getItems().add(recipe);
        saveHorizonsWishlists(commander, wishlists);
        EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
    }

    private void removeFromWishList(final String wishlistUUID, final Commander commander, final OdysseyWishlistBlueprint recipe) {
        final Wishlists wishlists = getWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<OdysseyWishlistBlueprint> found = wishlist.getItems().stream().filter(wishlistRecipe -> wishlistRecipe.equals(recipe)).findFirst();
        found.ifPresent(wishlistRecipe -> wishlist.getItems().remove(wishlistRecipe));
        saveWishlists(commander, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private void removeFromHorizonsWishList(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint recipe) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<WishlistBlueprint<HorizonsBlueprintName>> found = wishlist.getItems().stream().filter(wishlistRecipe -> ((HorizonsWishlistBlueprint) wishlistRecipe).getUuid().equals(recipe.getUuid())).findFirst();
        found.ifPresent(wishlistRecipe -> wishlist.getItems().remove(wishlistRecipe));
        saveHorizonsWishlists(commander, wishlists);
        EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
    }

    private void changeVisibility(final String wishlistUUID, final Commander commander, final OdysseyWishlistBlueprint wishlistBlueprint) {
        final Wishlists wishlists = getWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<OdysseyWishlistBlueprint> existingRecipe = wishlist.getItems().stream().filter(recipe -> recipe.getRecipeName().equals(wishlistBlueprint.getRecipeName()) && recipe.isVisible() == !wishlistBlueprint.isVisible()).findFirst();
        existingRecipe.ifPresent(recipe -> recipe.setVisible(wishlistBlueprint.isVisible()));
        saveWishlists(commander, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private void changeHorizonsVisibility(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint wishlistBlueprint) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<WishlistBlueprint<HorizonsBlueprintName>> existingRecipe = wishlist.getItems().stream().filter(recipe -> wishlistBlueprint.getUuid().equals(((HorizonsWishlistBlueprint) recipe).getUuid()) && recipe.isVisible() == !wishlistBlueprint.isVisible()).
                findFirst();
        existingRecipe.ifPresent(recipe -> recipe.setVisible(wishlistBlueprint.isVisible()));
        saveHorizonsWishlists(commander, wishlists);
        EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
    }

    private void modifyHorizonsBlueprint(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint wishlistBlueprint) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<HorizonsWishlistBlueprint> existingRecipe = wishlist.getItems().stream()
                .map(HorizonsWishlistBlueprint.class::cast)
                .filter(recipe -> recipe.getUuid().equals(wishlistBlueprint.getUuid()))
                .findFirst();
        existingRecipe.ifPresent(recipe -> {
            if (recipe instanceof HorizonsModuleWishlistBlueprint moduleWishlistBlueprint) {
                moduleWishlistBlueprint.setBlueprintGradeRolls(((HorizonsModuleWishlistBlueprint) wishlistBlueprint).getBlueprintGradeRolls());
            }
        });
        saveHorizonsWishlists(commander, wishlists);
        EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
    }

    public void selectHorizonsWishlist(final String wishlistUUID, final Commander commander) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        wishlists.setSelectedWishlistUUID(wishlistUUID);
        saveHorizonsWishlists(commander, wishlists);
    }

    public void selectWishlist(final String wishlistUUID, final Commander commander) {
        final Wishlists wishlists = getWishlists(commander);
        wishlists.setSelectedWishlistUUID(wishlistUUID);
        saveWishlists(commander, wishlists);
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public HorizonsWishlists getHorizonsWishlists(final Commander commander) {
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.HORIZONS_WISHLIST_FILE);
            String wishlistsFileContents;
            if (wishlistsFile.exists()) {//load from file if exists
                wishlistsFileContents = Files.readString(wishlistsFile.toPath());
                if (wishlistsFileContents.isEmpty()) {//create default if empty
                    createHorizonsWishlist(commander);
                }
            } else {//save to file from preferences
                final String wishlistsPreference = PreferencesService.getPreference(PreferenceConstants.HORIZONS_WISHLISTS_PREFIX + getFID(commander), "");
                if (wishlistsPreference.isBlank()) {
                    createHorizonsWishlist(commander);
                } else {
                    final HorizonsWishlists wishlists = OBJECT_MAPPER.readValue(wishlistsPreference, HorizonsWishlists.class);
                    saveHorizonsWishlists(commander, wishlists);
                }
                PreferencesService.removePreference(PreferenceConstants.HORIZONS_WISHLISTS_PREFIX + getFID(commander));
            }
            wishlistsFileContents = Files.readString(wishlistsFile.toPath());
            return OBJECT_MAPPER.readValue(wishlistsFileContents, HorizonsWishlists.class);
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to load horizons wishlists from configuration.", e);
        }
    }

    public Wishlists getWishlists(final Commander commander) {
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.ODYSSEY_WISHLIST_FILE);
            String wishlistsFileContents;
            if (wishlistsFile.exists()) {//load from file if exists
                wishlistsFileContents = Files.readString(wishlistsFile.toPath());
                if (wishlistsFileContents.isEmpty()) {//create default if empty
                    createWishlist(commander);
                }
            } else {//save to file from preferences
                final String wishlistsPreference = PreferencesService.getPreference(PreferenceConstants.WISHLISTS_PREFIX + getFID(commander), "");
                if (wishlistsPreference.isBlank()) {
                    createWishlist(commander);
                } else {
                    final Wishlists wishlists = OBJECT_MAPPER.readValue(wishlistsPreference, Wishlists.class);
                    saveWishlists(commander, wishlists);
                }
                PreferencesService.removePreference(PreferenceConstants.WISHLISTS_PREFIX + getFID(commander));
            }
            wishlistsFileContents = Files.readString(wishlistsFile.toPath());
            return OBJECT_MAPPER.readValue(wishlistsFileContents, Wishlists.class);
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to load wishlists from configuration.", e);
        }
    }

    private void createWishlist(final Commander commander) {
        final Wishlists wishlists = new Wishlists();
        final Wishlist defaultWishlist = new Wishlist();
        defaultWishlist.setName("Default wishlist");
        wishlists.addWishlist(defaultWishlist);
        saveWishlists(commander, wishlists);
    }

    public void saveHorizonsWishlists(final Commander commander, final HorizonsWishlists wishlists) {
        try {
            final String wishlistsJson = OBJECT_MAPPER.writeValueAsString(wishlists);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.HORIZONS_WISHLIST_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(wishlistsFile)) {
                fileOutputStream.write(wishlistsJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save horizons wishlists", e);
        } catch (final IOException e) {
            log.error("Failed to save horizons wishlists to wishlists file", e);
        }
    }

    public void saveWishlists(final Commander commander, final Wishlists wishlists) {
        try {
            final String wishlistsJson = OBJECT_MAPPER.writeValueAsString(wishlists);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.ODYSSEY_WISHLIST_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(wishlistsFile)) {
                fileOutputStream.write(wishlistsJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save odyssey wishlists", e);
        } catch (final IOException e) {
            log.error("Failed to save odyssey wishlists to wishlists file", e);
        }
    }

    public void deleteWishlist(final String activeWishlistUUID, final Commander commander) {
        final Wishlists wishlists = getWishlists(commander);
        wishlists.delete(activeWishlistUUID);
        saveWishlists(commander, wishlists);
    }

    public void deleteHorizonsWishlist(final String activeWishlistUUID, final Commander commander) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        wishlists.delete(activeWishlistUUID);
        saveHorizonsWishlists(commander, wishlists);
    }

    private void createHorizonsWishlist(final Commander commander) {

        final HorizonsWishlists wishlists = new HorizonsWishlists();
        final HorizonsWishlist defaultWishlist = new HorizonsWishlist();
        defaultWishlist.setName("Default wishlist");
        wishlists.addWishlist(defaultWishlist);
        saveHorizonsWishlists(commander, wishlists);
    }

    public Set<Commander> getCommanders() {
        return this.commanders;
    }

    public Optional<Commander> getPreferredCommander() {
        final String preferredCommander = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
        if (!preferredCommander.isBlank()) {
            final String[] commanderFidVersion = preferredCommander.split(":");
            final String name = commanderFidVersion[0];
            final String version = (commanderFidVersion.length > 2) ? commanderFidVersion[2] : "LIVE";
            final String fid = (commanderFidVersion.length > 2) ? commanderFidVersion[1] : "0";
            if (this.commanders.stream().anyMatch(commander -> commander.getName().equals(name) && commander.getFid().equals(fid) && commander.getGameVersion().name().equals(version))) {
                return this.commanders.stream().filter(commander -> commander.getName().equals(name) && commander.getFid().equals(fid) && commander.getGameVersion().name().equals(version)).findFirst();
            }
        }
        final Iterator<Commander> commanderIterator = this.commanders.iterator();
        if (commanderIterator.hasNext()) {
            final Commander commander = commanderIterator.next();
            PreferencesService.setPreference(PreferenceConstants.COMMANDER, commander.getName() + ":" + commander.getFid() + ":" + commander.getGameVersion().name());
            return Optional.of(commander);
        }
        return Optional.empty();
    }

    public void addCommander(final String name, final String fid, final GameVersion gameVersion) {
        if (this.commanders.stream().noneMatch(commander -> commander.getName().equals(name) && commander.getFid().equals(fid) && commander.getGameVersion().equals(gameVersion))) {
            final Commander commander = new Commander(name, fid, gameVersion);
            final boolean existingName = this.commanders.stream().anyMatch(commander1 -> commander1.getName().equals(name) && commander1.getGameVersion().equals(gameVersion));
            this.commanders.add(commander);
            if (existingName) {
                this.commanders.stream()
                        .filter(commander1 -> commander1.getName().equals(name) && commander1.getGameVersion().equals(gameVersion))
                        .forEach(commander1 -> commander1.setDuplicateName(true));
            }
            final String preferredCommander = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
            if (preferredCommander.isBlank()) {
                PreferencesService.setPreference(PreferenceConstants.COMMANDER, name + ":" + fid + ":" + gameVersion.name());
            }
            EventService.publish(new CommanderAddedEvent(commander));
        }
    }

    public void resetCommanders() {
        this.commanders.clear();
    }

    public int amountCraftable(final OdysseyBlueprintName odysseyBlueprintName) {
        final OdysseyBlueprint blueprint = OdysseyBlueprintConstants.getRecipe(odysseyBlueprintName);
        final AtomicInteger lowestAmount = new AtomicInteger(9999);
        blueprint.getMaterialCollection(OdysseyMaterial.class).forEach((material, amountRequired) -> {
            final int amountCraftable = StorageService.getMaterialStorage(material).getTotalValue() / amountRequired;
            lowestAmount.set(Math.min(amountCraftable, lowestAmount.get()));
        });
        return lowestAmount.get();
    }

    public Craftability getCraftability(final OdysseyBlueprintName odysseyBlueprintName) {
        final OdysseyBlueprint blueprint = OdysseyBlueprintConstants.getRecipe(odysseyBlueprintName);
        if (blueprint instanceof EngineerBlueprint engineerBlueprint) {
            return engineerBlueprint.getCraftability();
        } else {
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
    }

    public Craftability getCraftability(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade) {
        return getCraftability(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade == null ? Collections.emptyMap() : Map.of(horizonsBlueprintGrade, 1));
    }

    public Craftability getCraftability(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final Map<HorizonsBlueprintGrade, Integer> horizonsBlueprintGrades) {
        final List<Craftability> craftabilities = horizonsBlueprintGrades.entrySet().stream().map(horizonsBlueprintGradeRolls -> {
            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGradeRolls.getKey());
            final AtomicBoolean hasRaw = new AtomicBoolean(true);
            final AtomicBoolean hasEncoded = new AtomicBoolean(true);
            final AtomicBoolean hasManufactured = new AtomicBoolean(true);
            final AtomicBoolean hasCommodity = new AtomicBoolean(true);
            final Integer numberOfRolls = horizonsBlueprintGradeRolls.getValue();
            blueprint.getMaterialCollection(Raw.class).forEach((material, amountRequired) -> hasRaw.set(hasRaw.get() && (StorageService.getMaterialCount(material) - (amountRequired * numberOfRolls)) >= 0));
            blueprint.getMaterialCollection(Encoded.class).forEach((material, amountRequired) -> hasEncoded.set(hasEncoded.get() && (StorageService.getMaterialCount(material) - (amountRequired * numberOfRolls)) >= 0));
            blueprint.getMaterialCollection(Manufactured.class).forEach((material, amountRequired) -> hasManufactured.set(hasManufactured.get() && (StorageService.getMaterialCount(material) - (amountRequired * numberOfRolls)) >= 0));
            blueprint.getMaterialCollection(Commodity.class).forEach((material, amountRequired) -> hasCommodity.set(hasCommodity.get() && (StorageService.getCommodityCount((Commodity) material, StoragePool.SHIP) - (amountRequired * numberOfRolls)) >= 0));
            if (!hasRaw.get() || !hasEncoded.get() || !hasManufactured.get()) {
                return Craftability.NOT_CRAFTABLE;
            } else if (hasRaw.get() && hasEncoded.get() && hasManufactured.get() && !hasCommodity.get()) {
                return Craftability.CRAFTABLE_WITH_TRADE;
            } else {
                return Craftability.CRAFTABLE;
            }
        }).toList();
        if (craftabilities.stream().allMatch(Craftability.CRAFTABLE::equals)) {
            return Craftability.CRAFTABLE;
        } else if (craftabilities.stream().allMatch(craftability -> Craftability.CRAFTABLE.equals(craftability) || Craftability.CRAFTABLE_WITH_TRADE.equals(craftability))) {
            return Craftability.CRAFTABLE_WITH_TRADE;
        }
        return Craftability.NOT_CRAFTABLE;
    }

    public String getMarketPlaceToken() {
        return getPreferredCommander().map(commander -> PreferencesService.getPreference(PreferenceConstants.MARKETPLACE_TOKEN_PREFIX + commander.getFid(), "")).orElse("");
    }

    public LoadoutSetList getLoadoutSetList(final Commander commander) {
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File loadoutsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.ODYSSEY_LOADOUTS_FILE);
            String loadoutFileContents;
            if (loadoutsFile.exists()) {//load from file if exists
                loadoutFileContents = Files.readString(loadoutsFile.toPath());
                if (loadoutFileContents.isEmpty()) {//create default if empty
                    createLoadoutSetList(commander);
                }
            } else {//save to file from preferences
                final String loadoutSetPreference = PreferencesService.getPreference(PreferenceConstants.LOADOUTS_PREFIX + getFID(commander), "");
                if (loadoutSetPreference.isBlank()) {
                    createLoadoutSetList(commander);
                } else {
                    final LoadoutSetList loadoutSetList = OBJECT_MAPPER.readValue(loadoutSetPreference, LoadoutSetList.class);
                    saveLoadoutSetList(commander, loadoutSetList);
                }
                PreferencesService.removePreference(PreferenceConstants.LOADOUTS_PREFIX + getFID(commander));
            }
            loadoutFileContents = Files.readString(loadoutsFile.toPath());
            return OBJECT_MAPPER.readValue(loadoutFileContents, LoadoutSetList.class);
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to load loadouts from configuration.", e);
        }
    }

    private void createLoadoutSetList(final Commander commander) {
        final LoadoutSetList loadoutSetList = new LoadoutSetList();
        final LoadoutSet defaultLoadoutSet = new LoadoutSet();
        defaultLoadoutSet.setName("Default Loadout");
        defaultLoadoutSet.setLoadouts(List.of());
        loadoutSetList.addLoadoutSet(defaultLoadoutSet);
        saveLoadoutSetList(commander, loadoutSetList);
    }

    public void selectLoadoutSet(final String activeLoadoutSetUUID, final Commander commander) {
        final LoadoutSetList loadoutSetList = getLoadoutSetList(commander);
        loadoutSetList.setSelectedLoadoutSetUUID(activeLoadoutSetUUID);
        saveLoadoutSetList(commander, loadoutSetList);
    }

    public void deleteLoadoutSet(final String activeLoadoutSetUUID, final Commander commander) {
        final LoadoutSetList loadoutSetList = getLoadoutSetList(commander);
        loadoutSetList.delete(activeLoadoutSetUUID);
        saveLoadoutSetList(commander, loadoutSetList);
    }

    public void saveLoadoutSetList(final Commander commander, final LoadoutSetList loadoutSetList) {
        try {
            final String loadoutJson = OBJECT_MAPPER.writeValueAsString(loadoutSetList);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File loadoutsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.ODYSSEY_LOADOUTS_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(loadoutsFile)) {
                fileOutputStream.write(loadoutJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save loadouts", e);
        } catch (final IOException e) {
            log.error("Failed to save loadouts to loadouts file", e);
        }
    }

    public void saveLoadoutSet(final Commander commander, final LoadoutSet loadoutSet) {
        if (!loadoutSet.equals(LoadoutSet.CURRENT)) {
            final LoadoutSetList loadoutSetList = getLoadoutSetList(commander);
            loadoutSetList.updateLoadoutSet(loadoutSet);
            saveLoadoutSetList(commander, loadoutSetList);
        }
    }

    @SuppressWarnings("java:S2095")
    public boolean isLocked() {
        try {
            fileLock = new FileOutputStream(OsConstants.LOCK).getChannel().tryLock();
        } catch (final IOException exception) {
            log.error("error acquiring lock", exception);
            return true;
        }
        // null if the lock could not be acquired because another program holds an overlapping lock
        return (fileLock == null);
    }

    public void releaseLock() {
        try {
            fileLock.release();
        } catch (final IOException e) {
            log.error("error releasing lock", e);
        }
    }

    private String getFID(final Commander commander) {
        return (commander.getGameVersion().equals(GameVersion.LEGACY)) ? commander.getFid() + ".legacy" : commander.getFid();
    }
}
