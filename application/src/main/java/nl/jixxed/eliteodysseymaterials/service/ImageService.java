package nl.jixxed.eliteodysseymaterials.service;

import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageService {
    private static final Map<String, Image> imageCache = new HashMap<>();

    public static Image getImage(final String image) {
        return imageCache.computeIfAbsent(image, img -> {
            log.debug("Loading image: " + image);
            return new Image(ImageService.class.getResourceAsStream(image));
        });
    }
}
