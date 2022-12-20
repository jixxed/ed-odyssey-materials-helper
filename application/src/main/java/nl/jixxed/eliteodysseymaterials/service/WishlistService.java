package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class WishlistService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {

        EVENT_LISTENERS.add(EventService.addStaticListener(0, WishlistBlueprintEvent.class,
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
                        }))));

        EVENT_LISTENERS.add(EventService.addStaticListener(0, HorizonsWishlistBlueprintEvent.class,
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
                        }))));
    }

    public static Integer getAllWishlistsCount(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                        getWishlists(commander).getWishlists().stream().mapToInt(wishlist -> wishlist.getItems().stream()
                                        .map(odysseyWishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(odysseyMaterial))
                                        .mapToInt(Integer::intValue)
                                        .sum())
                                .sum())
                .orElse(0);

    }

    public static Integer getCurrentWishlistCount(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                getWishlists(commander).getSelectedWishlist().getItems().stream()
                        .map(odysseyWishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(odysseyMaterial))
                        .mapToInt(Integer::intValue)
                        .sum()
        ).orElse(0);

    }

    public static Integer getCurrentWishlistCount(final HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                        .mapToInt(horizonsWishlistBlueprint -> {
                            if (horizonsWishlistBlueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
                                return horizonsModuleWishlistBlueprint.getBlueprintGradeRolls().entrySet().stream().mapToInt(entry -> {
                                    final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsModuleWishlistBlueprint), entry.getKey());
                                    return blueprint.getRequiredAmount(horizonsMaterial) * entry.getValue();
                                }).sum();
                            } else {//other
                                final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe((HorizonsBlueprintName) horizonsWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsWishlistBlueprint), getBlueprintGrade(horizonsWishlistBlueprint));
                                return blueprint.getRequiredAmount(horizonsMaterial);
                            }
                        })
                        .sum()
        ).orElse(0);
    }

    public static Integer getAllWishlistsCount(final HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                getHorizonsWishlists(commander).getWishlists().stream().mapToInt(wishlist -> wishlist.getItems().stream()
                        .mapToInt(horizonsWishlistBlueprint -> {
                            if (horizonsWishlistBlueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
                                return horizonsModuleWishlistBlueprint.getBlueprintGradeRolls().entrySet().stream().mapToInt(entry -> {
                                    final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsModuleWishlistBlueprint), entry.getKey());
                                    return blueprint.getRequiredAmount(horizonsMaterial) * entry.getValue();
                                }).sum();
                            } else {//other
                                final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe((HorizonsBlueprintName) horizonsWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsWishlistBlueprint), getBlueprintGrade(horizonsWishlistBlueprint));
                                return blueprint.getRequiredAmount(horizonsMaterial);
                            }
                        })
                        .sum()
                ).sum()).orElse(0);
    }

    private static HorizonsBlueprintType getBlueprintType(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
            return horizonsModuleWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsExperimentalWishlistBlueprint horizonsExperimentalWishlistBlueprint) {
            return horizonsExperimentalWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsTechBrokerWishlistBlueprint horizonsTechBrokerWishlistBlueprint) {
            return horizonsTechBrokerWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsEngineerWishlistBlueprint) {
            return HorizonsBlueprintType.ENGINEER;
        }
        return null;
    }

    private static HorizonsBlueprintGrade getBlueprintGrade(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsSynthesisWishlistBlueprint horizonsSynthesisWishlistBlueprint) {
            return horizonsSynthesisWishlistBlueprint.getBlueprintGrade();
        }
        return HorizonsBlueprintGrade.NONE;
    }

    public static boolean isMaterialOnWishlist(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> getWishlists(commander).getWishlists().stream()
                        .anyMatch(wishlist -> wishlist.getItems().stream()
                                .anyMatch(wishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(wishlistBlueprint.getRecipeName()).hasIngredient(odysseyMaterial))))
                .orElse(false);

    }

    public static boolean isMaterialOnWishlist(final HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> getHorizonsWishlists(commander).getWishlists().stream()
                        .anyMatch(wishlist -> wishlist.getItems().stream()
                                .anyMatch(wishlistBlueprint -> {
                                    if (wishlistBlueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
                                        return horizonsModuleWishlistBlueprint.getBlueprintGradeRolls().entrySet().stream().anyMatch(entry -> {
                                            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsModuleWishlistBlueprint), entry.getKey());
                                            return blueprint.hasIngredient(horizonsMaterial);
                                        });
                                    } else {//other
                                        final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe((HorizonsBlueprintName) wishlistBlueprint.getRecipeName(), getBlueprintType(wishlistBlueprint), getBlueprintGrade(wishlistBlueprint));
                                        return blueprint.hasIngredient(horizonsMaterial);
                                    }

                                })))
                .orElse(false);

    }


    private static void addToWishList(final String wishlistUUID, final Commander commander, final BlueprintName recipe) {
        final Wishlists wishlists = getWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        wishlist.getItems().add(new OdysseyWishlistBlueprint((OdysseyBlueprintName) recipe, true));
        saveWishlists(commander, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private static void addToHorizonsWishList(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint recipe) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        wishlist.getItems().add(recipe);
        saveHorizonsWishlists(commander, wishlists);
        EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
    }

    private static void removeFromWishList(final String wishlistUUID, final Commander commander, final OdysseyWishlistBlueprint recipe) {
        final Wishlists wishlists = getWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<OdysseyWishlistBlueprint> found = wishlist.getItems().stream().filter(wishlistRecipe -> wishlistRecipe.equals(recipe)).findFirst();
        found.ifPresent(wishlistRecipe -> wishlist.getItems().remove(wishlistRecipe));
        saveWishlists(commander, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private static void removeFromHorizonsWishList(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint recipe) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<WishlistBlueprint<HorizonsBlueprintName>> found = wishlist.getItems().stream().filter(wishlistRecipe -> ((HorizonsWishlistBlueprint) wishlistRecipe).getUuid().equals(recipe.getUuid())).findFirst();
        found.ifPresent(wishlistRecipe -> wishlist.getItems().remove(wishlistRecipe));
        saveHorizonsWishlists(commander, wishlists);
        EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
    }

    private static void changeVisibility(final String wishlistUUID, final Commander commander, final OdysseyWishlistBlueprint wishlistBlueprint) {
        final Wishlists wishlists = getWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<OdysseyWishlistBlueprint> existingRecipe = wishlist.getItems().stream().filter(recipe -> recipe.getRecipeName().equals(wishlistBlueprint.getRecipeName()) && recipe.isVisible() == !wishlistBlueprint.isVisible()).findFirst();
        existingRecipe.ifPresent(recipe -> recipe.setVisible(wishlistBlueprint.isVisible()));
        saveWishlists(commander, wishlists);
        EventService.publish(new WishlistChangedEvent(wishlistUUID));
    }

    private static void changeHorizonsVisibility(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint wishlistBlueprint) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<WishlistBlueprint<HorizonsBlueprintName>> existingRecipe = wishlist.getItems().stream().filter(recipe -> wishlistBlueprint.getUuid().equals(((HorizonsWishlistBlueprint) recipe).getUuid()) && recipe.isVisible() == !wishlistBlueprint.isVisible()).
                findFirst();
        existingRecipe.ifPresent(recipe -> recipe.setVisible(wishlistBlueprint.isVisible()));
        saveHorizonsWishlists(commander, wishlists);
        EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
    }

    private static void modifyHorizonsBlueprint(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint wishlistBlueprint) {
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

    public static void selectHorizonsWishlist(final String wishlistUUID, final Commander commander) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        wishlists.setSelectedWishlistUUID(wishlistUUID);
        saveHorizonsWishlists(commander, wishlists);
    }

    public static void selectWishlist(final String wishlistUUID, final Commander commander) {
        final Wishlists wishlists = getWishlists(commander);
        wishlists.setSelectedWishlistUUID(wishlistUUID);
        saveWishlists(commander, wishlists);
    }


    public static HorizonsWishlists getHorizonsWishlists(final Commander commander) {
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
                createHorizonsWishlist(commander);
            }
            wishlistsFileContents = Files.readString(wishlistsFile.toPath());
            try {
                return OBJECT_MAPPER.readValue(wishlistsFileContents, HorizonsWishlists.class);
            } catch (final IOException e) {
                log.warn("Unable to load horizons wishlists from configuration. Try to create new one.", e);
                createHorizonsWishlist(commander);
                wishlistsFileContents = Files.readString(wishlistsFile.toPath());
                return OBJECT_MAPPER.readValue(wishlistsFileContents, HorizonsWishlists.class);
            }
        } catch (final IOException e) {
            log.error("Unable to load horizons wishlists from configuration.", e);
            throw new RuntimeException(e);
        }
    }

    public static Wishlists getWishlists(final Commander commander) {
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
                createWishlist(commander);
            }
            wishlistsFileContents = Files.readString(wishlistsFile.toPath());
            try {
                return OBJECT_MAPPER.readValue(wishlistsFileContents, Wishlists.class);
            } catch (final IOException e) {
                log.warn("Unable to load wishlists from configuration. Try to create new one.", e);
                createWishlist(commander);
                wishlistsFileContents = Files.readString(wishlistsFile.toPath());
                return OBJECT_MAPPER.readValue(wishlistsFileContents, Wishlists.class);
            }
        } catch (final IOException e) {
            log.error("Unable to load wishlists from configuration.", e);
            throw new RuntimeException(e);
        }
    }

    private static void createWishlist(final Commander commander) {
        final Wishlists wishlists = new Wishlists();
        final Wishlist defaultWishlist = new Wishlist();
        defaultWishlist.setName("Default wishlist");
        wishlists.addWishlist(defaultWishlist);
        saveWishlists(commander, wishlists);
    }

    public static void saveHorizonsWishlists(final Commander commander, final HorizonsWishlists wishlists) {
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

    public static void saveWishlists(final Commander commander, final Wishlists wishlists) {
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

    public static void deleteWishlist(final String activeWishlistUUID, final Commander commander) {
        final Wishlists wishlists = getWishlists(commander);
        wishlists.delete(activeWishlistUUID);
        saveWishlists(commander, wishlists);
    }

    public static void deleteHorizonsWishlist(final String activeWishlistUUID, final Commander commander) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        wishlists.delete(activeWishlistUUID);
        saveHorizonsWishlists(commander, wishlists);
    }

    private static void createHorizonsWishlist(final Commander commander) {

        final HorizonsWishlists wishlists = new HorizonsWishlists();
        final HorizonsWishlist defaultWishlist = new HorizonsWishlist();
        defaultWishlist.setName("Default wishlist");
        wishlists.addWishlist(defaultWishlist);
        saveHorizonsWishlists(commander, wishlists);
    }


    private static String getFID(final Commander commander) {
        return (commander.getGameVersion().equals(GameVersion.LEGACY)) ? commander.getFid() + ".legacy" : commander.getFid();
    }
}
