package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollBarHelper {
    public static ScrollBar getScrollBar(final BufferedImage downloadMenuCapture, final boolean hasWarning, final double scaling) {
        final WritableRaster dataColorPixel = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (855 * scaling),
                (int) (215 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
        final int colorPixel = ((DataBufferInt) dataColorPixel.getDataBuffer()).getData()[0];

        final int scrollbarX = 1613;
        if (!hasWarning) {//no warning
            //test for scrollbar
            final WritableRaster dataTop = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (scrollbarX * scaling),
                    (int) (300 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final int dataTopPixel = ((DataBufferInt) dataTop.getDataBuffer()).getData()[0];
            if (dataTopPixel == colorPixel) {
                return new ScrollBar(ScrollPosition.UP, 6);
            }
            final WritableRaster dataBottom = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (scrollbarX * scaling),
                    (int) (820 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final int dataBottomPixel = ((DataBufferInt) dataBottom.getDataBuffer()).getData()[0];
            if (dataBottomPixel == colorPixel) {
                return new ScrollBar(ScrollPosition.DOWN, 6);
            }
            return new ScrollBar(ScrollPosition.NONE, 5);
        } else {//warning
            final WritableRaster dataTop = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (scrollbarX * scaling),
                    (int) (365 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final WritableRaster dataTopMiddle = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (scrollbarX * scaling),
                    (int) (387 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final WritableRaster dataMiddle = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (scrollbarX * scaling),
                    (int) (605 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final WritableRaster dataDownMiddle = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (scrollbarX * scaling),
                    (int) (795 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final WritableRaster dataDown = ((WritableRaster) downloadMenuCapture.getData(new Rectangle((int) (scrollbarX * scaling),
                    (int) (830 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final int dataTopPixel = ((DataBufferInt) dataTop.getDataBuffer()).getData()[0];
            final int dataTopMiddlePixel = ((DataBufferInt) dataTopMiddle.getDataBuffer()).getData()[0];
            final int dataMiddlePixel = ((DataBufferInt) dataMiddle.getDataBuffer()).getData()[0];
            final int dataDownMiddlePixel = ((DataBufferInt) dataDownMiddle.getDataBuffer()).getData()[0];
            final int dataDownPixel = ((DataBufferInt) dataDown.getDataBuffer()).getData()[0];
            if (dataMiddlePixel != colorPixel) {
                return new ScrollBar(ScrollPosition.NONE, 5);
            }
            if (dataTopPixel == colorPixel) {
                if (dataDownMiddlePixel == colorPixel) {//bigger bar
                    return new ScrollBar(ScrollPosition.UP, 5);
                } else {
                    return new ScrollBar(ScrollPosition.UP, 6);
                }
            }
            if (dataDownPixel == colorPixel) {
                if (dataTopMiddlePixel == colorPixel) {//bigger bar
                    return new ScrollBar(ScrollPosition.DOWN, 5);
                } else {
                    return new ScrollBar(ScrollPosition.DOWN, 6);
                }
            }
            if (dataTopMiddlePixel == colorPixel) {
                return new ScrollBar(ScrollPosition.UP_MIDDLE, 6);
            }
            if (dataDownMiddlePixel == colorPixel) {
                return new ScrollBar(ScrollPosition.DOWN_MIDDLE, 6);
            }
            return new ScrollBar(ScrollPosition.MIDDLE, 6);


        }
    }
}
