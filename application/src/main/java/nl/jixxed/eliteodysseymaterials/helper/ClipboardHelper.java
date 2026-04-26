/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.service.shortlink.ShortLinkService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.Deflater;

import static nl.jixxed.eliteodysseymaterials.helper.DeeplinkHelper.deeplinkConsumer;

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
                final ClipboardHorizonsWishlist wishlist = new ClipboardHorizonsWishlist("wishlist", 1, WishlistService.getHorizonsWishlists(commander).getSelectedWishlist());
                wishlist.getWishlist().optimizeUUIDs();
                final String wishlistJson = OBJECT_MAPPER.writeValueAsString(wishlist);
                final String wishlist64 = convertJsonToBase64Compressed(wishlistJson);
                final String url = "edomh://horizonswishlist/?" + wishlist64;
                if (url.length() > 2048) {
                    NotificationService.showWarning(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.url.length.text"));
                }
                return url;
            } catch (final JsonProcessingException e) {
                log.error("failed to process wishlist", e);
            }
            return "";
        }).orElse("");
    }

    public static String createClipboardOdysseyWishlist() {
        return APPLICATION_STATE.getPreferredCommander().map(commander -> {
            try {
                final String wishlistJson = OBJECT_MAPPER.writeValueAsString(new ClipboardWishlist("wishlist", 1, WishlistService.getOdysseyWishlists(commander).getSelectedWishlist()));
                final String wishlist64 = convertJsonToBase64Compressed(wishlistJson);
                final String url = "edomh://wishlist/?" + wishlist64;
                if (url.length() > 2048) {
                    NotificationService.showWarning(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.url.length.text"));
                }
                return url;
            } catch (final JsonProcessingException e) {
                log.error("failed to process wishlist", e);
            }
            return "";
        }).orElse("");
    }

    public static String createClipboardLoadout() {
        return APPLICATION_STATE.getPreferredCommander().map(commander -> {
            try {
                final String loadoutJson = OBJECT_MAPPER.writeValueAsString(new ClipboardLoadout("loadout", 1, LoadoutService.getLoadoutSetList(commander).getSelectedLoadoutSet()));
                final String loadout64 = convertJsonToBase64Compressed(loadoutJson);
                final String url = "edomh://loadout/?" + loadout64;
                if (url.length() > 2048) {
                    NotificationService.showWarning(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.url.length.text"));
                }
                return url;
            } catch (final JsonProcessingException e) {
                log.error("failed to process loadout", e);
            }
            return "";
        }).orElse("");
    }

    public static String convertJsonToBase64Compressed(final String wishlistJson) {
        final Deflater def = new Deflater(9);
        def.setInput(wishlistJson.getBytes(StandardCharsets.UTF_8));
        def.finish();
        final byte[] compressedBuffer = new byte[wishlistJson.length() * 2];
        final int numberOfBytesAfterCompression = def.deflate(compressedBuffer, 0, compressedBuffer.length, Deflater.FULL_FLUSH);
        final byte[] wishListBytes = new byte[numberOfBytesAfterCompression];
        System.arraycopy(compressedBuffer, 0, wishListBytes, 0, numberOfBytesAfterCompression);
        return Base64.getUrlEncoder().encodeToString(wishListBytes);
    }

    public static String createClipboardShipConfiguration() {
        return APPLICATION_STATE.getPreferredCommander().map(commander ->
                ShipService.getShipConfigurations(commander).getSelectedShipConfiguration().map(configuration -> {
                    try {
                        final String shipJson = OBJECT_MAPPER.writeValueAsString(new ClipboardShip("ship", 1, configuration));
                        final String ship64 = convertJsonToBase64Compressed(shipJson);
                        final String url = "edomh://ship/?" + ship64;
                        if (url.length() > 2048) {
                            NotificationService.showWarning(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.url.length.text"));
                        }
                        return url;
                    } catch (final JsonProcessingException e) {
                        log.error("failed to process ship", e);
                    }
                    return "";
                }).orElse("")
        ).orElse("");
    }

    public static void copyToClipboard(final String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text.startsWith("edomh://") ? ShortLinkService.requestShortLink(text) : text);
        clipboard.setContent(content);
    }

    public static void importFromClipboard() {
        Platform.runLater(() -> {
            final String clipboard = Clipboard.getSystemClipboard().getString();
            if (clipboard != null && clipboard.startsWith("edomh://")) {
                deeplinkConsumer.accept(clipboard);
            }
            if (clipboard != null && clipboard.startsWith("https://link.edomh.nl/")) {
                try (HttpClient httpClient = HttpClient.newBuilder()
                        .followRedirects(HttpClient.Redirect.NEVER)
                        .build()) {
                    final HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(clipboard))
                            .header("User-Agent", VersionService.getUserAgent())
                            .GET()
                            .build();
                    final HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
                    String edomhUrl = response.headers()
                            .firstValue("location")
                            .orElse(null);
                    deeplinkConsumer.accept(edomhUrl);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
