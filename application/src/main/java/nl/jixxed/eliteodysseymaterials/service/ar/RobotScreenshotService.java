package nl.jixxed.eliteodysseymaterials.service.ar;

import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RobotScreenshotService implements ScreenshotService {
    private final static RobotScreenshotService INSTANCE = new RobotScreenshotService();
    private final static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (final AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static ScreenshotService getInstance() {
        return INSTANCE;
    }

    @Override
    public BufferedImage getScreenshot(final Rectangle bounds, final BufferedImage output) {
        final BufferedImage screenCapture = robot.createScreenCapture(bounds);
        if (output != null) {
            output.setData(screenCapture.getData());
            return output;

        }
        return screenCapture;
    }
}
