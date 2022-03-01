package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ImportResultEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImportService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String ERROR_IMPORT_STRING_NOT_DECODED = "String could not be decoded";

    public static void importDeeplink(final String deeplink) {
        // edomh://wishlist=
        final String content = deeplink.substring(8);
        final String[] split = content.split("\\?");
        final String type = split[0];
        final String data = split[1];
        if ("wishlist/".equals(type)) {
            final String decoded = convertBase64CompressedToJson(data);

            if (decoded.isEmpty()) {
                throw new IllegalArgumentException(ERROR_IMPORT_STRING_NOT_DECODED);
            }
            try {
                final ClipboardWishlist clipboardWishlist = OBJECT_MAPPER.readValue(decoded, ClipboardWishlist.class);
                if (Objects.equals(clipboardWishlist.getVersion(), 1)) {

                    final Wishlist wishlist = new Wishlist();
                    final String name = clipboardWishlist.getWishlist().getName() + " - Imported";
                    wishlist.setName(name);
                    wishlist.setItems(clipboardWishlist.getWishlist().getItems());

                    final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                    final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
                    wishlists.addWishlist(wishlist);
                    wishlists.setSelectedWishlistUUID(wishlist.getUuid());
                    APPLICATION_STATE.saveWishlists(commander.getFid(), wishlists);
                    EventService.publish(new ImportResultEvent(ImportResult.SUCCESS_WISHLIST));
                    NotificationService.showInformation("Imported wishlist", name);
                } else {
                    NotificationService.showError("Failed to import wishlist", "The wishlist could not be imported because the link was made with a newer version of the app.");
                }
            } catch (final RuntimeException | JsonProcessingException ex) {
                log.error("Failed to import wishlist", ex);
                NotificationService.showError("Failed to import wishlist", ex.getMessage());
                EventService.publish(new ImportResultEvent(ImportResult.ERROR_WISHLIST));
            }
        } else if ("loadout/".equals(type)) {
            final String decoded = convertBase64CompressedToJson(data);

            if (decoded.isEmpty()) {
                throw new IllegalArgumentException(ERROR_IMPORT_STRING_NOT_DECODED);
            }
            try {
                final ClipboardLoadout clipboardLoadout = OBJECT_MAPPER.readValue(decoded, ClipboardLoadout.class);
                if (Objects.equals(clipboardLoadout.getVersion(), 1)) {
                    final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                    final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                    final LoadoutSet loadoutSet = clipboardLoadout.getLoadoutSet();
                    final String loadoutSetName = loadoutSet.getName() + " - Imported";
                    loadoutSet.setName(loadoutSetName);
                    loadoutSetList.addLoadoutSet(loadoutSet);
                    loadoutSetList.setSelectedLoadoutSetUUID(loadoutSet.getUuid());
                    APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                    EventService.publish(new ImportResultEvent(ImportResult.SUCCESS_LOADOUT));
                    NotificationService.showInformation("Imported loadout", loadoutSetName);

                } else {
                    NotificationService.showError("Failed to import loadout", "The loadout could not be imported because the link was made with a newer version of the app.");
                }
            } catch (final RuntimeException | JsonProcessingException ex) {
                log.error("Failed to import loadout", ex);
                NotificationService.showError("Failed to import loadout", ex.getMessage());
                EventService.publish(new ImportResultEvent(ImportResult.ERROR_LOADOUT));
            }
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
            }
            return bos.toString();
        } catch (final IOException | DataFormatException e) {
            log.error("Failed to decompress data", e);
        }
        return "";
    }
}
