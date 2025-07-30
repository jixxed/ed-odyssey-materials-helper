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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class WishlistService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {

        EVENT_LISTENERS.add(EventService.addStaticListener(0, OdysseyWishlistBlueprintEvent.class,
                wishlistEvent -> Platform.runLater(() ->
                        wishlistEvent.getWishlistBlueprints().forEach(wishlistRecipe -> {
                            switch (wishlistEvent.getAction()) {
                                case ADDED ->
                                        addToOdysseyWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), wishlistRecipe);
                                case REMOVED ->
                                        removeFromOdysseyWishList(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), wishlistRecipe);
                                case VISIBILITY_CHANGED ->
                                        changeOdysseyVisibility(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), wishlistRecipe);
                                case MODIFY ->
                                        modifyOdysseyBlueprint(wishlistEvent.getWishlistUUID(), wishlistEvent.getCommander(), wishlistRecipe);

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

    private static void modifyOdysseyBlueprint(String wishlistUUID, Commander commander, OdysseyWishlistBlueprint wishlistBlueprint) {
        final Wishlists wishlists = getOdysseyWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        if (wishlist != null) {
            final Optional<OdysseyWishlistBlueprint> existingRecipe = wishlist.getItems().stream()
                    .filter(recipe -> recipe.getUuid().equals(wishlistBlueprint.getUuid()))
                    .findFirst();
            existingRecipe.ifPresent(recipe -> {
                recipe.setQuantity(wishlistBlueprint.getQuantity());
                saveOdysseyWishlists(commander, wishlists);
                EventService.publish(new OdysseyWishlistChangedEvent(wishlistUUID));
            });
        }
    }

    public static Integer getAllWishlistsCount(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                        getOdysseyWishlists(commander).getWishlists().stream().mapToInt(wishlist -> wishlist.getItems().stream()
                                        .map(odysseyWishlistBlueprint -> odysseyWishlistBlueprint.getQuantity() * OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(odysseyMaterial))
                                        .mapToInt(Integer::intValue)
                                        .sum())
                                .sum())
                .orElse(0);

    }

    public static Integer getCurrentWishlistCount(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                getOdysseyWishlists(commander).getSelectedWishlist().getItems().stream()
                        .map(odysseyWishlistBlueprint -> odysseyWishlistBlueprint.getQuantity() * OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(odysseyMaterial))
                        .mapToInt(Integer::intValue)
                        .sum()
        ).orElse(0);

    }

    /**
     * Get the current wishlist count for a specific material.
     * Minimum amounts assume engineer ranks of 5.
     * Maximum amounts are based on the level of the lowest ranking engineer.
     *
     * @param horizonsMaterial
     * @return a range of integers representing the minimum and maximum amount of the material needed for the current wishlist.
     */
    public static IntegerRange getCurrentWishlistCount(final HorizonsMaterial horizonsMaterial) {
        return getCurrentWishlistCount(horizonsMaterial, true);
    }
    /**
     * Get the current wishlist count for a specific material.
     * Minimum amounts assume engineer ranks of 5.
     * Maximum amounts are based on the level of the lowest ranking engineer.
     *
     * @param horizonsMaterial
     * @return a range of integers representing the minimum and maximum amount of the material needed for the current wishlist.
     */
    public static IntegerRange getCurrentWishlistCount(final HorizonsMaterial horizonsMaterial, boolean includeInvisible) {
        return APPLICATION_STATE.getPreferredCommander().map(commander -> {
                    final HorizonsWishlist selectedWishlist = getHorizonsWishlists(commander).getSelectedWishlist();
                    return getWishlistCount(horizonsMaterial, selectedWishlist, includeInvisible);
                }
        ).orElse(new IntegerRange(0, 0));
    }

    /**
     * Get the count for a specific material on all wishlists.
     * Minimum amounts assume engineer ranks of 5.
     * Maximum amounts are based on the level of the lowest ranking engineer.
     *
     * @param horizonsMaterial
     * @return a range of integers representing the minimum and maximum amount of the material needed for the current wishlist.
     */
    public static IntegerRange getAllWishlistsCount(final HorizonsMaterial horizonsMaterial) {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander ->
                        getHorizonsWishlists(commander).getWishlists().stream()
                                .map(wishlist -> getWishlistCount(horizonsMaterial, wishlist, true))
                                .reduce(new IntegerRange(0, 0), IntegerRange::sum))
                .orElse(new IntegerRange(0, 0));
    }

    private static IntegerRange getWishlistCount(HorizonsMaterial horizonsMaterial, HorizonsWishlist wishlist, boolean includeInvisible) {
        return wishlist.getItems().stream()
                .filter(bp-> includeInvisible || bp.isVisible())
                .map(horizonsWishlistBlueprint -> {
                    final int quantity = horizonsWishlistBlueprint.getQuantity();
                    if (horizonsWishlistBlueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {//modules
                        return horizonsModuleWishlistBlueprint.getPercentageToComplete().entrySet().stream().map(entry -> {
                            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsModuleWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsModuleWishlistBlueprint), entry.getKey());
                            final Integer lowestRank = blueprint.getEngineers().stream()
                                    .min(Comparator.comparing(engineer -> ApplicationState.getInstance().getEngineerRank(engineer)))
                                    .map(engineer -> ApplicationState.getInstance().getEngineerRank(engineer))
                                    .orElse(0);
                            return new IntegerRange(
                                    quantity * blueprint.getRequiredAmount(horizonsMaterial, null) * entry.getKey().getNumberOfRolls(5, getBlueprintType(horizonsModuleWishlistBlueprint)),
                                    quantity * blueprint.getRequiredAmount(horizonsMaterial, null) * entry.getKey().getNumberOfRolls(lowestRank, getBlueprintType(horizonsModuleWishlistBlueprint))
                            );
                        }).reduce(new IntegerRange(0, 0), IntegerRange::sum);
                    } else {//other
                        final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe((HorizonsBlueprintName) horizonsWishlistBlueprint.getRecipeName(), getBlueprintType(horizonsWishlistBlueprint), getBlueprintGrade(horizonsWishlistBlueprint));
                        return new IntegerRange(quantity * blueprint.getRequiredAmount(horizonsMaterial, null), quantity * blueprint.getRequiredAmount(horizonsMaterial, null));
                    }
                })
                .reduce(new IntegerRange(0, 0), IntegerRange::sum);
    }

    public static HorizonsBlueprintType getBlueprintType(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
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

    public static HorizonsBlueprintGrade getBlueprintGrade(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsSynthesisWishlistBlueprint horizonsSynthesisWishlistBlueprint) {
            return horizonsSynthesisWishlistBlueprint.getBlueprintGrade();
        }
        return HorizonsBlueprintGrade.NONE;
    }

    public static boolean isMaterialOnWishlist(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> getOdysseyWishlists(commander).getWishlists().stream()
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
                                        return horizonsModuleWishlistBlueprint.getPercentageToComplete().entrySet().stream().anyMatch(entry -> {
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


    private static void addToOdysseyWishList(final String wishlistUUID, final Commander commander, final OdysseyWishlistBlueprint blueprint) {
        final Wishlists wishlists = getOdysseyWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        if (wishlist != null) {
            wishlist.getItems().add(blueprint);
            saveOdysseyWishlists(commander, wishlists);
            EventService.publish(new OdysseyWishlistChangedEvent(wishlistUUID));
        }
    }

    private static void addToHorizonsWishList(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint blueprint) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        if (wishlist != null) {
            wishlist.getItems().add(blueprint);
            saveHorizonsWishlists(commander, wishlists);
            EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
        }
    }

    private static void removeFromOdysseyWishList(final String wishlistUUID, final Commander commander, final OdysseyWishlistBlueprint blueprint) {
        final Wishlists wishlists = getOdysseyWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        final Optional<OdysseyWishlistBlueprint> found = wishlist.getItems().stream().filter(wishlistRecipe -> wishlistRecipe.equals(blueprint)).findFirst();
        found.ifPresent(wishlistRecipe -> wishlist.getItems().remove(wishlistRecipe));
        saveOdysseyWishlists(commander, wishlists);
        EventService.publish(new OdysseyWishlistChangedEvent(wishlistUUID));
    }

    private static void removeFromHorizonsWishList(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint recipe) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        if (wishlist != null) {
            final Optional<WishlistBlueprint<HorizonsBlueprintName>> found = wishlist.getItems().stream().filter(wishlistRecipe -> ((HorizonsWishlistBlueprint) wishlistRecipe).getUuid().equals(recipe.getUuid())).findFirst();
            found.ifPresent(wishlistRecipe -> wishlist.getItems().remove(wishlistRecipe));
            saveHorizonsWishlists(commander, wishlists);
            EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
        }
    }

    private static void changeOdysseyVisibility(final String wishlistUUID, final Commander commander, final OdysseyWishlistBlueprint wishlistBlueprint) {
        final Wishlists wishlists = getOdysseyWishlists(commander);
        final Wishlist wishlist = wishlists.getWishlist(wishlistUUID);
        if (wishlist != null) {
            final Optional<OdysseyWishlistBlueprint> existingRecipe = wishlist.getItems().stream().filter(recipe -> recipe.getRecipeName().equals(wishlistBlueprint.getRecipeName()) && recipe.isVisible() == !wishlistBlueprint.isVisible()).findFirst();
            existingRecipe.ifPresent(recipe -> recipe.setVisible(wishlistBlueprint.isVisible()));
            saveOdysseyWishlists(commander, wishlists);
            EventService.publish(new OdysseyWishlistChangedEvent(wishlistUUID));
        }
    }

    private static void changeHorizonsVisibility(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint wishlistBlueprint) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        if (wishlist != null) {
            final Optional<WishlistBlueprint<HorizonsBlueprintName>> existingRecipe = wishlist.getItems().stream().filter(recipe -> wishlistBlueprint.getUuid().equals(((HorizonsWishlistBlueprint) recipe).getUuid()) && recipe.isVisible() == !wishlistBlueprint.isVisible()).
                    findFirst();
            existingRecipe.ifPresent(recipe -> recipe.setVisible(wishlistBlueprint.isVisible()));
            saveHorizonsWishlists(commander, wishlists);
            EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
        }
    }

    private static void modifyHorizonsBlueprint(final String wishlistUUID, final Commander commander, final HorizonsWishlistBlueprint wishlistBlueprint) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        final HorizonsWishlist wishlist = wishlists.getWishlist(wishlistUUID);
        if (wishlist != null) {
            final Optional<HorizonsWishlistBlueprint> existingRecipe = wishlist.getItems().stream()
                    .map(HorizonsWishlistBlueprint.class::cast)
                    .filter(recipe -> recipe.getUuid().equals(wishlistBlueprint.getUuid()))
                    .findFirst();
            existingRecipe.ifPresent(recipe -> {
                if (recipe instanceof HorizonsModuleWishlistBlueprint moduleWishlistBlueprint) {
                    moduleWishlistBlueprint.setPercentageToComplete(((HorizonsModuleWishlistBlueprint) wishlistBlueprint).getPercentageToComplete());
                    moduleWishlistBlueprint.setExperimentalEffect(((HorizonsModuleWishlistBlueprint) wishlistBlueprint).getExperimentalEffect());
                    moduleWishlistBlueprint.setBlueprintType(((HorizonsModuleWishlistBlueprint) wishlistBlueprint).getBlueprintType());
                }
                recipe.setQuantity(wishlistBlueprint.getQuantity());
                saveHorizonsWishlists(commander, wishlists);
                EventService.publish(new HorizonsWishlistChangedEvent(wishlistUUID));
            });
        }
    }

    public static void selectHorizonsWishlist(final String wishlistUUID, final Commander commander) {
        final HorizonsWishlists wishlists = getHorizonsWishlists(commander);
        wishlists.setSelectedWishlistUUID(wishlistUUID);
        saveHorizonsWishlists(commander, wishlists);
    }

    public static void selectOdysseyWishlist(final String wishlistUUID, final Commander commander) {
        final Wishlists wishlists = getOdysseyWishlists(commander);
        wishlists.setSelectedWishlistUUID(wishlistUUID);
        saveOdysseyWishlists(commander, wishlists);
    }


    public static HorizonsWishlists getHorizonsWishlists(final Commander commander) {
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.HORIZONS_WISHLIST_FILE);
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

    public static Wishlists getOdysseyWishlists(final Commander commander) {
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.ODYSSEY_WISHLIST_FILE);
            String wishlistsFileContents;
            if (wishlistsFile.exists()) {//load from file if exists
                wishlistsFileContents = Files.readString(wishlistsFile.toPath());
                if (wishlistsFileContents.isEmpty()) {//create default if empty
                    createOdysseyWishlist(commander);
                }
            } else {//save to file from preferences
                createOdysseyWishlist(commander);
            }
            wishlistsFileContents = Files.readString(wishlistsFile.toPath());
            try {
                return OBJECT_MAPPER.readValue(wishlistsFileContents, Wishlists.class);
            } catch (final IOException e) {
                log.warn("Unable to load wishlists from configuration. Try to create new one.", e);
                createOdysseyWishlist(commander);
                wishlistsFileContents = Files.readString(wishlistsFile.toPath());
                return OBJECT_MAPPER.readValue(wishlistsFileContents, Wishlists.class);
            }
        } catch (final IOException e) {
            log.error("Unable to load wishlists from configuration.", e);
            throw new RuntimeException(e);
        }
    }

    private static void createOdysseyWishlist(final Commander commander) {
        final Wishlists wishlists = new Wishlists();
        final Wishlist defaultWishlist = new Wishlist();
        defaultWishlist.setName("Default wishlist");
        wishlists.addWishlist(defaultWishlist);
        saveOdysseyWishlists(commander, wishlists);
    }

    public static void saveHorizonsWishlists(final Commander commander, final HorizonsWishlists wishlists) {
        try {
            final String wishlistsJson = OBJECT_MAPPER.writeValueAsString(wishlists);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.HORIZONS_WISHLIST_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(wishlistsFile)) {
                fileOutputStream.write(wishlistsJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save horizons wishlists", e);
        } catch (final IOException e) {
            log.error("Failed to save horizons wishlists to wishlists file", e);
        }
    }

    public static void saveOdysseyWishlists(final Commander commander, final Wishlists wishlists) {
        try {
            final String wishlistsJson = OBJECT_MAPPER.writeValueAsString(wishlists);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.ODYSSEY_WISHLIST_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(wishlistsFile)) {
                fileOutputStream.write(wishlistsJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save odyssey wishlists", e);
        } catch (final IOException e) {
            log.error("Failed to save odyssey wishlists to wishlists file", e);
        }
    }

    public static void deleteOdysseyWishlist(final String activeWishlistUUID, final Commander commander) {
        final Wishlists wishlists = getOdysseyWishlists(commander);
        wishlists.delete(activeWishlistUUID);
        saveOdysseyWishlists(commander, wishlists);
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
