package nl.jixxed.eliteodysseymaterials.service.ar;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface ScreenshotService {

    BufferedImage getScreenshot(final Rectangle bounds, final BufferedImage output);
}
