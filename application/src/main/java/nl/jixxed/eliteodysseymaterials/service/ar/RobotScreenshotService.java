package nl.jixxed.eliteodysseymaterials.service.ar;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.awt.Rectangle;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;
import java.util.Optional;

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
    public BufferedImage getScreenshot(final Point offset, final Rectangle bounds, final BufferedImage output) {
//        final Point scaledPointForRealPoint = ScreenHelper.getScaledPointForRealPoint(offset.x, offset.y);
        final Optional<BufferedImage> bufferedImage = Screen.getScreensForRectangle(new Rectangle2D(bounds.x, bounds.y, 1, 1)).stream().findFirst().map(screen -> {
            final Scaling windowsScaling = new Scaling(screen.getOutputScaleX(), screen.getOutputScaleY());
//            final int x = scaledPointForRealPoint.x + (int) (screen.getBounds().getMinX() + (bounds.x - screen.getBounds().getMinX()) / windowsScaling.x());
//            final int y = scaledPointForRealPoint.y + (int) (screen.getBounds().getMinY() + (bounds.y - screen.getBounds().getMinY()) / windowsScaling.y());
//            final Rectangle scaled = new Rectangle(x, y, (int) (bounds.width / windowsScaling.x()), (int) (bounds.height / windowsScaling.y()));
            final nl.jixxed.eliteodysseymaterials.service.ar.Rectangle scaledPointForRealPoint = ScreenHelper.getScaledRectForRealRect(
                    offset.x + bounds.x,
                    offset.y + bounds.y,
                    offset.x + bounds.x + bounds.width,
                    offset.y + bounds.y + bounds.height);
            if (scaledPointForRealPoint == null) {
                return null;
            }
            final Rectangle scaled = scaledPointForRealPoint.getAwtRectangle().getBounds();//new Rectangle((int) scaledPointForRealPoint.getX(), (int) scaledPointForRealPoint.getY(), (int) (scaledPointForRealPoint.getWidth()), (int) (scaledPointForRealPoint.getHeight()));
//            final Rectangle scaled = new Rectangle(offset.x + bounds.x, offset.y + bounds.y, bounds.width, bounds.height);
            final MultiResolutionImage multiResolutionScreenCapture = robot.createMultiResolutionScreenCapture(scaled);
            final BufferedImage screenCapture = (BufferedImage) multiResolutionScreenCapture.getResolutionVariant(bounds.width, bounds.height);

            final int w = screenCapture.getWidth();
            final int h = screenCapture.getHeight();
            BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            final AffineTransform at = new AffineTransform();
            at.scale(windowsScaling.x(), windowsScaling.y());
            final AffineTransformOp scaleOp =
                    new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            after = scaleOp.filter(screenCapture, after);
            //        final BufferedImage screenCapture = robot.createScreenCapture(bounds);
            if (output != null) {
                output.setData(after.getData());
                return output;

            }
            return screenCapture;
        });
        return bufferedImage.orElse(null);
    }
}
