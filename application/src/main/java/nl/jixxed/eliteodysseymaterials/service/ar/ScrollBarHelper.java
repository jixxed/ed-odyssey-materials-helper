/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            if (matchesColor(color, pixel, 8)) {
                count++;
            }
        }
//ff9500
//c37100
        return (int) (count / scaling);
    }

    private static boolean matchesColor(byte[] colorToTest, byte[] colorToMatch, int range) {
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
        byte[] color = DataBufferHelper.getData(dataColorPixel.getDataBuffer());
        //count pixels in scrollbar line that match the color pixel
        int count = 0;
        for (int y = 0; y < scrollbarLine.getHeight(); y++) {
            final WritableRaster dataPixel = ((WritableRaster) scrollbarLine.getData(new java.awt.Rectangle(0, y, 1, 1))).createWritableTranslatedChild(0, 0);
            final byte[] pixel = DataBufferHelper.getData(dataPixel.getDataBuffer());
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
            if (matchesColor(color, pixel, 8)) {
                break;
            }
            count++;
        }
        double start = count / scaling;
        double freeSpace = ((scrollbarYBottom - scrollbarYTop)) - (scrollBarHeight) - 1;

//        log.debug("progress (%s/%s) %s".formatted(start, freeSpace, (double) start / freeSpace));
        return (start / freeSpace) * 100;
    }

    public static ScrollBarV2 getScrollBarV2BartenderSell(final BufferedImage sellMenuScrollbarCapture, final double scaling) {
        if (getScrollBarHeightBartenderSell(sellMenuScrollbarCapture, scaling) > 0) {
            return new ScrollBarV2(true, getProgress(sellMenuScrollbarCapture, scaling), 0);
        }
        return new ScrollBarV2(false, 0, 0);
    }

    public static double getScrollBarHeightBartenderSell(final BufferedImage sellMenuScrollbarCapture, final double scaling) {
        final int scrollbarX = 11;
        final int scrollbarYTop = 404;
        final int scrollbarYBottom = 1469;
        final WritableRaster thumbColorPixel = ((WritableRaster) sellMenuScrollbarCapture.getData(new java.awt.Rectangle((int) (0D * scaling),
                (int) (205D * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
        final WritableRaster backgroundColorPixel = ((WritableRaster) sellMenuScrollbarCapture.getData(new java.awt.Rectangle((int) (0D * scaling),
                (int) (215D * scaling), 1, 1))).createWritableTranslatedChild(0, 0);

        BufferedImage scrollbarLine = sellMenuScrollbarCapture.getSubimage((int) (scrollbarX * scaling), (int) (scrollbarYTop * scaling), 1, (int) ((scrollbarYBottom - scrollbarYTop) * scaling));
//        try {
//            saveImage(scrollbarLine, "scrollbarline", (int) (scaling * 100));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        byte[] colorBG = DataBufferHelper.getData(backgroundColorPixel.getDataBuffer());
        byte[] colorFG = DataBufferHelper.getData(thumbColorPixel.getDataBuffer());
        byte[] thumbColor = colorFG.clone();
        for (int i = 1; i < 4; i++) { // RGB channels
            int value = thumbColor[i] & 0xFF;
            thumbColor[i] = (byte) Math.floor(value * 0.765);
        }
        //count pixels in scrollbar line that match the color pixel
        double count = 0;
        List<byte[]> pixels = new ArrayList<>();
        for (int y = 0; y < scrollbarLine.getHeight(); y++) {
            final WritableRaster dataPixel = ((WritableRaster) scrollbarLine.getData(new java.awt.Rectangle(0, y, 1, 1))).createWritableTranslatedChild(0, 0);
            final byte[] pixel = DataBufferHelper.getData(dataPixel.getDataBuffer());

            if (!matchesColor(colorBG, pixel, 2) /*|| !matchesColor(darkenedColor, pixel, 2)*/) {
                pixels.add(pixel);
                if(matchesColor(colorFG, pixel, 2)){
                    thumbColor = colorFG;
                }
            }
        }
        for (byte[] pixel:pixels){
            double opacity = (double) ((pixel[1] & 0xFF) - (colorBG[1] & 0xFF)) / ((thumbColor[1] & 0xFF) - (colorBG[1] & 0xFF));
            double opacity2 = (double) ((pixel[2] & 0xFF) - (colorBG[2] & 0xFF)) / ((thumbColor[2] & 0xFF) - (colorBG[2] & 0xFF));
            double opacity3 = (double) ((pixel[3] & 0xFF) - (colorBG[3] & 0xFF)) / ((thumbColor[3] & 0xFF) - (colorBG[3] & 0xFF));
            count += (opacity + opacity2 + opacity3) / 3D;
        }
//        log.debug("thumb size {}", count);
        return (count / scaling);
    }

    public static double getProgress(BufferedImage sellMenuScrollbarCapture, double scaling) {
        final int scrollbarX = 11;
        final int scrollbarYTop = 404;
        final int scrollbarYBottom = 1469;
        final WritableRaster thumbColorPixel = ((WritableRaster) sellMenuScrollbarCapture.getData(new java.awt.Rectangle((int) (0D * scaling),
                (int) (205D * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
        final WritableRaster backgroundColorPixel = ((WritableRaster) sellMenuScrollbarCapture.getData(new java.awt.Rectangle((int) (0D * scaling),
                (int) (215D * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
        double scrollBarHeight = getScrollBarHeightBartenderSell(sellMenuScrollbarCapture, scaling);
        BufferedImage scrollbarLine = sellMenuScrollbarCapture.getSubimage((int) (scrollbarX * scaling), (int) (scrollbarYTop * scaling), 1, (int) ((scrollbarYBottom - scrollbarYTop) * scaling));
//        try {
//            saveImage(scrollbarLine, "scrollbarline" + new Random(100), 1);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        byte[] colorFG = DataBufferHelper.getData(thumbColorPixel.getDataBuffer());
        byte[] colorBG = DataBufferHelper.getData(backgroundColorPixel.getDataBuffer());
        final byte[] darkenedColor = colorFG.clone();
        for (int i = 1; i < 4; i++) { // RGB channels
            int value = darkenedColor[i] & 0xFF;
            darkenedColor[i] = (byte) Math.floor(value * 0.765);
        }
        //count pixels in scrollbar line that match the color pixel
        double count = 0;
        for (int y = 0; y < scrollbarLine.getHeight(); y++) {
            final WritableRaster dataPixel = ((WritableRaster) scrollbarLine.getData(new java.awt.Rectangle(0, y, 1, 1))).createWritableTranslatedChild(0, 0);
            final byte[] pixel = DataBufferHelper.getData(dataPixel.getDataBuffer());

            if (!matchesColor(colorBG, pixel, 2) /*|| matchesColor(darkenedColor, pixel, 2)*/) {
                double opacity = (double) ((pixel[1] & 0xFF) - (colorBG[1] & 0xFF)) / ((darkenedColor[1] & 0xFF) - (colorBG[1] & 0xFF));
                double opacity2 = (double) ((pixel[2] & 0xFF) - (colorBG[2] & 0xFF)) / ((darkenedColor[2] & 0xFF) - (colorBG[2] & 0xFF));
                double opacity3 = (double) ((pixel[3] & 0xFF) - (colorBG[3] & 0xFF)) / ((darkenedColor[3] & 0xFF) - (colorBG[3] & 0xFF));
                double avg = (opacity + opacity2 + opacity3) / 3D;
                if(avg < 1D){
                    count += 1D - avg;
                }
                break;
            }
            count++;
        }
        double start = count / scaling;
        double freeSpace = ((scrollbarYBottom - scrollbarYTop)) - (scrollBarHeight) - 1;

//        log.debug("progress (%s/%s) %s".formatted(start, freeSpace, (double) start / freeSpace));
        return Math.clamp((start / freeSpace) * 100, 0D, 100D);
    }
}
