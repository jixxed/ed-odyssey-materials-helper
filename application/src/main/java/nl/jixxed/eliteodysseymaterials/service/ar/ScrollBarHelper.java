package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollBarHelper {

    public static final int SCROLLBAR_X = 1615;
    public static final int SCROLLBAR_Y_BOTTOM = 841;
    public static final int SCROLLBAR_Y_TOP_WARNING = 344;
    public static final int SCROLLBAR_Y_TOP = 271;

    public static ScrollBarV2 getScrollBarV2(final BufferedImage downloadMenuCapture, final boolean hasWarning, final double scaling) {
        if (getScrollBarHeight(downloadMenuCapture, hasWarning, scaling) > 0) {
            return new ScrollBarV2(true, getProgress(downloadMenuCapture, hasWarning, scaling), getListSize(downloadMenuCapture, hasWarning, scaling));
        }
        return new ScrollBarV2(false, 0, 0);
    }

    public static int getScrollBarHeight(final BufferedImage downloadMenuCapture, final boolean hasWarning, final double scaling) {
        final int scrollbarX = SCROLLBAR_X;
        final int scrollbarYTop = hasWarning ? SCROLLBAR_Y_TOP_WARNING : SCROLLBAR_Y_TOP;//no warning
        final int scrollbarYBottom = SCROLLBAR_Y_BOTTOM;
        final WritableRaster dataColorPixel = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (855 * scaling),
                (int) (215 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);

        BufferedImage scrollbarLine = downloadMenuCapture.getSubimage((int) (scrollbarX * scaling), (int) (scrollbarYTop * scaling), 1, (int) ((scrollbarYBottom - scrollbarYTop) * scaling));
//        try {
//            saveImage(scrollbarLine, "scrollbarline", (int) (scaling * 100));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        byte[] color = DataBufferHelper.getData(dataColorPixel.getDataBuffer());
        //count pixels in scrollbar line that match the color pixel
        int count = 0;
        for (int y = 0; y < scrollbarLine.getHeight(); y++) {
            final WritableRaster dataPixel = ((WritableRaster) scrollbarLine.getData(new java.awt.Rectangle(0, y, 1, 1))).createWritableTranslatedChild(0, 0);
            final byte[] pixel = DataBufferHelper.getData(dataPixel.getDataBuffer());
            if (matchesColor(color, pixel)) {
                count++;
            }
        }

        return (int) (count / scaling);
    }

    private static boolean matchesColor(byte[] colorToTest, byte[] colorToMatch) {
        int range = 8;
        return colorToTest[0] >= colorToMatch[0] - range && colorToTest[0] <= colorToMatch[0] + range
                && colorToTest[1] >= colorToMatch[1] - range && colorToTest[1] <= colorToMatch[1] + range
                && colorToTest[2] >= colorToMatch[2] - range && colorToTest[2] <= colorToMatch[2] + range
                && colorToTest[3] >= colorToMatch[3] - range && colorToTest[3] <= colorToMatch[3] + range;
    }


    public static int getListSize(final BufferedImage downloadMenuCapture, final boolean hasWarning, final double scaling) {
        final int scrollbarX = SCROLLBAR_X;
        final int scrollbarYTop = hasWarning ? SCROLLBAR_Y_TOP_WARNING : SCROLLBAR_Y_TOP;//no warning
        final int scrollbarYBottom = SCROLLBAR_Y_BOTTOM;
        final WritableRaster dataColorPixel = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (855 * scaling),
                (int) (215 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);

        BufferedImage scrollbarLine = downloadMenuCapture.getSubimage((int) (scrollbarX * scaling), (int) (scrollbarYTop * scaling), 1, (int) ((scrollbarYBottom - scrollbarYTop) * scaling));
//        try {
//            saveImage(scrollbarLine, "scrollbarline", (int) (scaling * 100));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        byte[] color =  DataBufferHelper.getData(dataColorPixel.getDataBuffer());
        //count pixels in scrollbar line that match the color pixel
        int count = 0;
        for (int y = 0; y < scrollbarLine.getHeight(); y++) {
            final WritableRaster dataPixel = ((WritableRaster) scrollbarLine.getData(new java.awt.Rectangle(0, y, 1, 1))).createWritableTranslatedChild(0, 0);
            final byte[] pixel =DataBufferHelper.getData(dataPixel.getDataBuffer());
            if (Arrays.equals(color, pixel)) {
                count++;
            }
        }
        var barHeight = count / scaling;
        var barFill = barHeight / (scrollbarYBottom - scrollbarYTop);

        var listSize = hasWarning ? 4.65 : 5.32;
//        log.debug("size " + listSize / barFill);
        return (int) Math.round(listSize / barFill);
    }

//    private static void saveImage(BufferedImage image, String name, int index) throws IOException {
//        File output = new File("target/" + name + "." + index + ".png");
//        output.mkdirs();
//        Files.deleteIfExists(output.toPath());
//        ImageIO.write(image, "png", output);
//    }

    public static double getProgress(BufferedImage downloadMenuCapture, boolean hasWarning, double scaling) {
        final int scrollbarX = SCROLLBAR_X;
        final int scrollbarYTop = hasWarning ? SCROLLBAR_Y_TOP_WARNING : SCROLLBAR_Y_TOP;//no warning
        final int scrollbarYBottom = SCROLLBAR_Y_BOTTOM;
        final WritableRaster dataColorPixel = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (855 * scaling),
                (int) (215 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
        int scrollBarHeight = getScrollBarHeight(downloadMenuCapture, hasWarning, scaling);
        BufferedImage scrollbarLine = downloadMenuCapture.getSubimage((int) (scrollbarX * scaling), (int) (scrollbarYTop * scaling), 1, (int) ((scrollbarYBottom - scrollbarYTop) * scaling));
//        try {
//            saveImage(scrollbarLine, "scrollbarline" + new Random(100), 1);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        byte[] color = DataBufferHelper.getData(dataColorPixel.getDataBuffer());
        //count pixels in scrollbar line that match the color pixel
        int count = 0;
        for (int y = 0; y < scrollbarLine.getHeight(); y++) {
            final WritableRaster dataPixel = ((WritableRaster) scrollbarLine.getData(new java.awt.Rectangle(0, y, 1, 1))).createWritableTranslatedChild(0, 0);
            final byte[] pixel = DataBufferHelper.getData(dataPixel.getDataBuffer());
            if (Arrays.equals(color, pixel)) {
                break;
            }
            count++;
        }
        double start = count / scaling;
        double freeSpace = ((scrollbarYBottom - scrollbarYTop)) - (scrollBarHeight) - 1;

//        log.debug("progress (%s/%s) %s".formatted(start, freeSpace, (double) start / freeSpace));
        return (start / freeSpace) * 100;
    }
}
