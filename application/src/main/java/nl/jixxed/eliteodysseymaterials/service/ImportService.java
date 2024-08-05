package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.schemas.slef.Slef;
import nl.jixxed.eliteodysseymaterials.service.event.CapiOAuthCallbackEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.exception.*;
import nl.jixxed.eliteodysseymaterials.service.ships.LoadoutMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;

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

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String ERROR_IMPORT_STRING_NOT_DECODED = "String could not be decoded";

    public static ImportResult importDeeplink(final String deeplink) {
        final String content = deeplink.substring(8);
        final String[] split = content.split("\\?");
        final String type = split[0];
        final String data = split[1];
        if ("horizonswishlist/".equals(type)) {
            final String decoded = convertBase64CompressedToJson(data);
            return importHorizonsWishlist(decoded);
        } else if ("capi/".equals(type)) {
            final HashMap<String, String> params = convertToQueryStringToHashMap(data);

            EventService.publish(new CapiOAuthCallbackEvent(params.get("code"), params.get("state")));
            return new ImportResult(ImportResult.ResultType.CAPI_OAUTH_TOKEN);
        } else if ("wishlist/".equals(type)) {
            final String decoded = convertBase64CompressedToJson(data);
            return importOdysseyWishlist(decoded);
        } else if ("loadout/".equals(type)) {
            final String decoded = convertBase64CompressedToJson(data);
            return importLoadout(decoded);
        } else if ("ship/".equals(type)) {
            final String decoded = convertBase64CompressedToJson(data);
            return importShip(decoded);
        } else if ("edsy/".equals(type)) {
            final String decoded = convertBase64CompressedToJson(data);
            return importEdsy(decoded);
        } else if ("coriolis/".equals(type)) {
            final String decoded = convertBase64CompressedToJson(data);
            return importCoriolis(decoded);
        }
        return new ImportResult(ImportResult.ResultType.UNKNOWN_TYPE);
    }

    public static ImportResult[] importSlef(final String slefJson) {
        List<ImportResult> results = new ArrayList<>();
        try {
            List<Slef> slefArray = OBJECT_MAPPER.readValue(slefJson, new TypeReference<List<Slef>>() {
            });
            slefArray.forEach(slef -> {
                try {
                    final Ship ship = LoadoutMapper.toShip(slef.getData());
                    APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                        final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                        final String name = LocaleService.getLocalizedStringForCurrentLocale(ship.getShipType().getLocalizationKey()) + "(SLEF)";
                        final ShipConfiguration shipConfiguration = shipConfigurations.createShipConfiguration(name);
                        ShipMapper.toShipConfiguration(ship,shipConfiguration, name);
                        ShipService.saveShipConfigurations(commander, shipConfigurations);
                        results.add(new ImportResult(ImportResult.ResultType.SUCCESS_SLEF, name));
                    });
                } catch (Exception e) {
                    log.error("Failed to import slef", e);
                    results.add(new ImportResult(ImportResult.ResultType.ERROR_SLEF, LocaleService.getLocalizedStringForCurrentLocale("notification.import.slef.error.text")));
                }

            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return results.toArray(ImportResult[]::new);
    }

    private static ImportResult importShip(final String decoded) {
        log.info("Importing ship data: " + decoded);
        if (decoded.isEmpty()) {
            throw new IllegalArgumentException(ERROR_IMPORT_STRING_NOT_DECODED);
        }
        try {

            final ClipboardShip clipboardShip = OBJECT_MAPPER.readValue(decoded, ClipboardShip.class);
            if (Objects.equals(clipboardShip.getVersion(), 1)) {
                ShipConfiguration shipConfiguration = clipboardShip.getShipConfiguration();
                shipConfiguration.setUuid(UUID.randomUUID().toString());
                shipConfiguration.setName(shipConfiguration.getName() + " - Imported");
                final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                shipConfigurations.addShipConfiguration(shipConfiguration);
                shipConfigurations.setSelectedShipConfigurationUUID(shipConfiguration.getUuid());
                ShipService.saveShipConfigurations(commander, shipConfigurations);
                return new ImportResult(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP, shipConfiguration.getName());
//                return new ImportResult(ImportResult.ResultType.SUCCESS_EDSY_WISHLIST, name);
            } else {
                throw new ShipDeeplinkException("The ship could not be imported because the link was made with a newer version of the app.");
            }

        } catch (final RuntimeException | JsonProcessingException ex) {
            log.error("Failed to import ship", ex);
            throw new ShipDeeplinkException("Failed to parse deeplink");
        }
    }

    private static ImportResult importEdsy(final String decoded) {
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
                            bp = new HorizonsModuleWishlistBlueprint(horizonsModuleBlueprint.getHorizonsBlueprintType(), createGradeMap(horizonsModuleBlueprint.getHorizonsBlueprintType(), edsyWishlistItem.getGrade(), edsyWishlistItem.getHighestGradePercentage()));
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

    private static ImportResult importCoriolis(final String decoded) {
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
                            bp = new HorizonsModuleWishlistBlueprint(horizonsModuleBlueprint.getHorizonsBlueprintType(), createGradeMap(horizonsModuleBlueprint.getHorizonsBlueprintType(), coriolisWishlistItem.getGrade(), coriolisWishlistItem.getHighestGradePercentage()));
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

    private static EnumMap<HorizonsBlueprintGrade, Double> createGradeMap(HorizonsBlueprintType type, Integer grade, final Double fraction) {
        final HorizonsBlueprintGrade topGrade = HorizonsBlueprintGrade.valueOf("GRADE_" + grade--);
        final EnumMap<HorizonsBlueprintGrade, Double> gradeRolls = new EnumMap<>(HorizonsBlueprintGrade.class);
        gradeRolls.put(topGrade, fraction);
        for (int i = grade; i > 0; i--) {
            final HorizonsBlueprintGrade horizonsBlueprintGrade = HorizonsBlueprintGrade.valueOf("GRADE_" + i);
            gradeRolls.put(horizonsBlueprintGrade, 1D);
        }
        return gradeRolls;
    }

    private static ImportResult importLoadout(final String decoded) {

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

    private static ImportResult importOdysseyWishlist(final String decoded) {

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

    private static ImportResult importHorizonsWishlist(final String decoded) {

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
