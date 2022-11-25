package nl.jixxed.eliteodysseymaterials.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.Deflater;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClipboardHelper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    static {
        OBJECT_MAPPER.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(final AnnotatedMember m) {
                return _findAnnotation(m, ClipboardJsonIgnore.class) != null;
            }
        });
    }

    public static String createClipboardHorizonsWishlist() {
        return APPLICATION_STATE.getPreferredCommander().map(commander -> {
            try {
                final ClipboardHorizonsWishlist wishlist = new ClipboardHorizonsWishlist("wishlist", 1, APPLICATION_STATE.getHorizonsWishlists(commander).getSelectedWishlist());
                wishlist.getWishlist().optimizeUUIDs();
                final String wishlistJson = OBJECT_MAPPER.writeValueAsString(wishlist);
                final String wishlist64 = convertJsonToBase64Compressed(wishlistJson);
                return "edomh://horizonswishlist/?" + wishlist64;
            } catch (final JsonProcessingException e) {
                log.error("failed to process wishlist", e);
            }
            return "";
        }).orElse("");
    }

    public static String createClipboardWishlist() {
        return APPLICATION_STATE.getPreferredCommander().map(commander -> {
            try {
                final String wishlistJson = OBJECT_MAPPER.writeValueAsString(new ClipboardWishlist("wishlist", 1, APPLICATION_STATE.getWishlists(commander).getSelectedWishlist()));
                final String wishlist64 = convertJsonToBase64Compressed(wishlistJson);
                return "edomh://wishlist/?" + wishlist64;
            } catch (final JsonProcessingException e) {
                log.error("failed to process wishlist", e);
            }
            return "";
        }).orElse("");
    }

    public static String createClipboardLoadout() {
        return APPLICATION_STATE.getPreferredCommander().map(commander -> {
            try {
                final String loadoutJson = OBJECT_MAPPER.writeValueAsString(new ClipboardLoadout("loadout", 1, APPLICATION_STATE.getLoadoutSetList(commander).getSelectedLoadoutSet()));
                final String loadout64 = convertJsonToBase64Compressed(loadoutJson);
                return "edomh://loadout/?" + loadout64;
            } catch (final JsonProcessingException e) {
                log.error("failed to process loadout", e);
            }
            return "";
        }).orElse("");
    }

    static String convertJsonToBase64Compressed(final String wishlistJson) {
        final Deflater def = new Deflater(9);
//        def.setStrategy(Deflater.FILTERED);
        def.setInput(wishlistJson.getBytes(StandardCharsets.UTF_8));
        def.finish();
        final byte[] compressedBuffer = new byte[wishlistJson.length() * 2];
        final int numberOfBytesAfterCompression = def.deflate(compressedBuffer, 0, compressedBuffer.length, Deflater.FULL_FLUSH);
        final byte[] wishListBytes = new byte[numberOfBytesAfterCompression];
        System.arraycopy(compressedBuffer, 0, wishListBytes, 0, numberOfBytesAfterCompression);
        return Base64.getUrlEncoder().encodeToString(wishListBytes);
    }

}
