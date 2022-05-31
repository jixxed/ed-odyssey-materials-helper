package nl.jixxed.eliteodysseymaterials.service.ar;

import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.BufferedImage;

public interface ScreenshotService {

    BufferedImage getScreenshot(final Point offset, final Rectangle bounds, final BufferedImage output);
}
