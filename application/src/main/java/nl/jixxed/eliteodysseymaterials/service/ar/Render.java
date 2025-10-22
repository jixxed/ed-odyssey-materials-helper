package nl.jixxed.eliteodysseymaterials.service.ar;


import javafx.scene.image.Image;

import java.util.Set;

public record Render(Set<Integer> renderedIndexes, boolean fullyRendered, Image render) {
}
