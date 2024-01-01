package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.event.CapiOAuthCallbackEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.exception.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImportService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String ERROR_IMPORT_STRING_NOT_DECODED = "String could not be decoded";

    public static ImportResult importDeeplink(final String deeplink) {
        final String content = deeplink.substring(8);
        final String[] split = content.split("\\?");
        final String type = split[0];
        final String data = split[1];
        if ("horizonswishlist/".equals(type)) {
            return importHorizonsWishlist(data);
        } else if ("capi/".equals(type)) {
            final HashMap<String, String> params = convertToQueryStringToHashMap(data);

            EventService.publish(new CapiOAuthCallbackEvent(params.get("code"), params.get("state")));
            return new ImportResult(ImportResult.ResultType.CAPI_OAUTH_TOKEN);
        } else if ("wishlist/".equals(type)) {
            return importOdysseyWishlist(data);
        } else if ("loadout/".equals(type)) {
            return importLoadout(data);
        } else if ("ship/".equals(type)) {
            return importShip(data);
        } else if ("edsy/".equals(type)) {
            return importEdsy(data);
        } else if ("coriolis/".equals(type)) {
            return importCoriolis(data);
        }
        return new ImportResult(ImportResult.ResultType.UNKNOWN_TYPE);
    }

    private static ImportResult importShip(final String data) {
        //TODO
        return new ImportResult(ImportResult.ResultType.UNKNOWN_TYPE);
    }

    private static ImportResult importEdsy(final String data) {
        final String decoded = convertBase64CompressedToJson(data);
        log.info("Importing edsy data: " + decoded);
        if (decoded.isEmpty()) {
            throw new IllegalArgumentException(ERROR_IMPORT_STRING_NOT_DECODED);
        }
        try {
            final EdsyWishlist edsyWishlist = OBJECT_MAPPER.readValue(decoded, EdsyWishlist.class);

            if (Objects.equals(edsyWishlist.getVersion(), 1)) {

                final HorizonsWishlist wishlist = new HorizonsWishlist();
                final String name = (edsyWishlist.getName() != null && !edsyWishlist.getName().isBlank()) ? edsyWishlist.getName() : "ED Shipyard - Imported";
                wishlist.setName(name);
                final List<WishlistBlueprint<HorizonsBlueprintName>> wishlistBlueprintList = edsyWishlist.getItems().stream().map(edsyWishlistItem -> {
                    try {
                        final HorizonsBlueprintGrade horizonsBlueprintGrade = edsyWishlistItem.getGrade() != null ? HorizonsBlueprintGrade.valueOf("GRADE_" + edsyWishlistItem.getGrade().toString()) : null;
                        final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipeByInternalName(edsyWishlistItem.getItem(), edsyWishlistItem.getBlueprint(), horizonsBlueprintGrade);
                        final HorizonsWishlistBlueprint bp;
                        if (blueprint instanceof HorizonsModuleBlueprint horizonsModuleBlueprint) {
                            bp = new HorizonsModuleWishlistBlueprint(horizonsModuleBlueprint.getHorizonsBlueprintType(), createGradeMap(edsyWishlistItem.getGrade(), edsyWishlistItem.getHighestGradePercentage()));
                        } else if (blueprint instanceof HorizonsExperimentalEffectBlueprint horizonsExperimentalEffectBlueprint) {
                            bp = new HorizonsExperimentalWishlistBlueprint(horizonsExperimentalEffectBlueprint.getHorizonsBlueprintType());
                        } else {
                            throw new EdsyDeeplinkException("Failed to parse deeplink");
                        }
                        bp.setRecipeName((blueprint.getBlueprintName()));
                        bp.setVisible(true);
                        return (WishlistBlueprint<HorizonsBlueprintName>) bp;
                    } catch (final IllegalArgumentException ex) {
                        log.error(ex.getMessage());
                        NotificationService.showWarning(NotificationType.IMPORT, "Unknown item", String.join(":\n", ex.getMessage().split(": ")), true);
                        return null;
                    }
                }).filter(Objects::nonNull).toList();
                wishlist.setItems(wishlistBlueprintList);

                final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
                wishlists.addWishlist(wishlist);
                wishlists.setSelectedWishlistUUID(wishlist.getUuid());
                WishlistService.saveHorizonsWishlists(commander, wishlists);
                return new ImportResult(ImportResult.ResultType.SUCCESS_EDSY_WISHLIST, name);
            } else {
                throw new EdsyDeeplinkException("The wishlist could not be imported because the link was made with a newer version of the app.");
            }

        } catch (final RuntimeException | JsonProcessingException ex) {
            log.error("Failed to import ED shipyard wishlist", ex);
            throw new EdsyDeeplinkException("Failed to parse deeplink");
        }

    }

    private static ImportResult importCoriolis(final String data) {
        final String decoded = convertBase64CompressedToJson(data);
        log.info("Importing coriolis data: " + decoded);
        if (decoded.isEmpty()) {
            throw new IllegalArgumentException(ERROR_IMPORT_STRING_NOT_DECODED);
        }
        try {
            final CoriolisWishlist coriolisWishlist = OBJECT_MAPPER.readValue(decoded, CoriolisWishlist.class);

            if (Objects.equals(coriolisWishlist.getVersion(), 1)) {

                final HorizonsWishlist wishlist = new HorizonsWishlist();
                final String name = (coriolisWishlist.getName() != null && !coriolisWishlist.getName().isBlank()) ? coriolisWishlist.getName() : "Coriolis - Imported";
                wishlist.setName(name);
                final List<WishlistBlueprint<HorizonsBlueprintName>> wishlistBlueprintList = coriolisWishlist.getItems().stream().map(coriolisWishlistItem -> {
                    try {
                        final HorizonsBlueprintGrade horizonsBlueprintGrade = coriolisWishlistItem.getGrade() != null ? HorizonsBlueprintGrade.valueOf("GRADE_" + coriolisWishlistItem.getGrade().toString()) : null;
                        final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipeByInternalName(coriolisWishlistItem.getItem(), coriolisWishlistItem.getBlueprint(), horizonsBlueprintGrade);
                        final HorizonsWishlistBlueprint bp;
                        if (blueprint instanceof HorizonsModuleBlueprint horizonsModuleBlueprint) {
                            bp = new HorizonsModuleWishlistBlueprint(horizonsModuleBlueprint.getHorizonsBlueprintType(), createGradeMap(coriolisWishlistItem.getGrade(), coriolisWishlistItem.getHighestGradePercentage()));
                        } else if (blueprint instanceof HorizonsExperimentalEffectBlueprint horizonsExperimentalEffectBlueprint) {
                            bp = new HorizonsExperimentalWishlistBlueprint(horizonsExperimentalEffectBlueprint.getHorizonsBlueprintType());
                        } else {
                            throw new CoriolisDeeplinkException("Failed to parse deeplink");
                        }
                        bp.setRecipeName((blueprint.getBlueprintName()));
                        bp.setVisible(true);
                        return (WishlistBlueprint<HorizonsBlueprintName>) bp;
                    } catch (final IllegalArgumentException ex) {
                        log.error(ex.getMessage());
                        NotificationService.showWarning(NotificationType.IMPORT, "Unknown item", ex.getMessage(), true);
                        return null;
                    }
                }).filter(Objects::nonNull).toList();
                wishlist.setItems(wishlistBlueprintList);

                final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
                wishlists.addWishlist(wishlist);
                wishlists.setSelectedWishlistUUID(wishlist.getUuid());
                WishlistService.saveHorizonsWishlists(commander, wishlists);
                return new ImportResult(ImportResult.ResultType.SUCCESS_CORIOLIS_WISHLIST, name);
            } else {
                throw new CoriolisDeeplinkException("The wishlist could not be imported because the link was made with a newer version of the app.");
            }

        } catch (final RuntimeException | JsonProcessingException ex) {
            log.error("Failed to import Coriolis wishlist", ex);
            throw new CoriolisDeeplinkException("Failed to parse deeplink");
        }

    }

    private static EnumMap<HorizonsBlueprintGrade, Integer> createGradeMap(Integer grade, final Double fraction) {
        final HorizonsBlueprintGrade topGrade = HorizonsBlueprintGrade.valueOf("GRADE_" + grade--);
        final EnumMap<HorizonsBlueprintGrade, Integer> gradeRolls = new EnumMap<>(HorizonsBlueprintGrade.class);
        gradeRolls.put(topGrade, (int) Math.ceil(getGradeRolls(topGrade) * fraction));
        for (int i = grade; i > 0; i--) {
            final HorizonsBlueprintGrade horizonsBlueprintGrade = HorizonsBlueprintGrade.valueOf("GRADE_" + i);
            gradeRolls.put(horizonsBlueprintGrade, getGradeRolls(horizonsBlueprintGrade));
        }
        return gradeRolls;
    }

    private static int getGradeRolls(final HorizonsBlueprintGrade horizonsBlueprintGrade) {
        return PreferencesService.getPreference(PreferenceConstants.WISHLIST_GRADE_ROLLS_PREFIX + horizonsBlueprintGrade.name(), horizonsBlueprintGrade.getDefaultNumberOfRolls());
    }

    private static ImportResult importLoadout(final String data) {
        final String decoded = convertBase64CompressedToJson(data);

        if (decoded.isEmpty()) {
            throw new IllegalArgumentException(ERROR_IMPORT_STRING_NOT_DECODED);
        }
        try {
            final ClipboardLoadout clipboardLoadout = OBJECT_MAPPER.readValue(decoded, ClipboardLoadout.class);
            if (Objects.equals(clipboardLoadout.getVersion(), 1)) {
                final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                final LoadoutSetList loadoutSetList = LoadoutService.getLoadoutSetList(commander);
                final LoadoutSet loadoutSet = clipboardLoadout.getLoadoutSet();
                final String loadoutSetName = loadoutSet.getName() + " - Imported";
                loadoutSet.setName(loadoutSetName);
                loadoutSetList.addLoadoutSet(loadoutSet);
                loadoutSetList.setSelectedLoadoutSetUUID(loadoutSet.getUuid());
                LoadoutService.saveLoadoutSetList(commander, loadoutSetList);
                return new ImportResult(ImportResult.ResultType.SUCCESS_LOADOUT, loadoutSetName);

            } else {
                throw new LoadoutDeeplinkException("The wishlist could not be imported because the link was made with a newer version of the app.");
            }
        } catch (final RuntimeException | JsonProcessingException ex) {
            log.error("Failed to import loadout", ex);
            throw new LoadoutDeeplinkException("Failed to parse deeplink");
        }
    }

    private static ImportResult importOdysseyWishlist(final String data) {
        final String decoded = convertBase64CompressedToJson(data);

        if (decoded.isEmpty()) {
            throw new OdysseyWishlistDeeplinkException(ERROR_IMPORT_STRING_NOT_DECODED);
        }
        try {
            final ClipboardWishlist clipboardWishlist = OBJECT_MAPPER.readValue(decoded, ClipboardWishlist.class);
            if (Objects.equals(clipboardWishlist.getVersion(), 1)) {

                final Wishlist wishlist = new Wishlist();
                final String name = clipboardWishlist.getWishlist().getName() + " - Imported";
                wishlist.setName(name);
                wishlist.setItems(clipboardWishlist.getWishlist().getItems());

                final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                final Wishlists wishlists = WishlistService.getWishlists(commander);
                wishlists.addWishlist(wishlist);
                wishlists.setSelectedWishlistUUID(wishlist.getUuid());
                WishlistService.saveWishlists(commander, wishlists);
                return new ImportResult(ImportResult.ResultType.SUCCESS_ODYSSEY_WISHLIST, name);
            } else {
                throw new OdysseyWishlistDeeplinkException("The wishlist could not be imported because the link was made with a newer version of the app.");
            }
        } catch (final RuntimeException | JsonProcessingException ex) {
            log.error("Failed to import wishlist", ex);
            throw new OdysseyWishlistDeeplinkException("Failed to parse deeplink");
        }
    }

    private static ImportResult importHorizonsWishlist(final String data) {
        final String decoded = convertBase64CompressedToJson(data);

        if (decoded.isEmpty()) {
            throw new OdysseyWishlistDeeplinkException(ERROR_IMPORT_STRING_NOT_DECODED);
        }
        try {
            final ClipboardHorizonsWishlist clipboardWishlist = OBJECT_MAPPER.readValue(decoded, ClipboardHorizonsWishlist.class);
            if (Objects.equals(clipboardWishlist.getVersion(), 1)) {

                final HorizonsWishlist wishlist = new HorizonsWishlist();
                final String name = clipboardWishlist.getWishlist().getName() + " - Imported";
                wishlist.setName(name);
                wishlist.setItems(clipboardWishlist.getWishlist().getItems());

                final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
                wishlists.addWishlist(wishlist);
                wishlists.setSelectedWishlistUUID(wishlist.getUuid());
                WishlistService.saveHorizonsWishlists(commander, wishlists);
                return new ImportResult(ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST, name);
            } else {
                throw new OdysseyWishlistDeeplinkException("The horizons wishlist could not be imported because the link was made with a newer version of the app.");
            }
        } catch (final RuntimeException | JsonProcessingException ex) {
            log.error("Failed to import horizons wishlist", ex);
            throw new HorizonsWishlistDeeplinkException("Failed to parse deeplink");
        }
    }

    private static String convertBase64CompressedToJson(final String compressedBase64) {
        final Inflater inflater = new Inflater();
        inflater.setInput(Base64.getUrlDecoder().decode(compressedBase64.getBytes(StandardCharsets.UTF_8)));
        final byte[] uncompressedBuffer = new byte[1024];
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            while (!inflater.finished()) {
                final int count = inflater.inflate(uncompressedBuffer);
                bos.write(uncompressedBuffer, 0, count);
                if (count == 0 && inflater.needsInput()) {
                    break;
                }
            }
            return bos.toString();
        } catch (final IOException | DataFormatException e) {
            log.error("Failed to decompress data", e);
        }
        return "";
    }

    private static HashMap<String, String> convertToQueryStringToHashMap(final String source) {

        final HashMap<String, String> data = new HashMap<>();

        final String[] arrParameters = source.split("&");
        for (final String tempParameterString : arrParameters) {

            final String[] arrTempParameter = tempParameterString
                    .split("=");

            if (arrTempParameter.length >= 2) {
                final String parameterKey = arrTempParameter[0];
                final String parameterValue = arrTempParameter[1];
                data.put(parameterKey, parameterValue);
            } else {
                final String parameterKey = arrTempParameter[0];
                data.put(parameterKey, "");
            }
        }

        return data;
    }
}
