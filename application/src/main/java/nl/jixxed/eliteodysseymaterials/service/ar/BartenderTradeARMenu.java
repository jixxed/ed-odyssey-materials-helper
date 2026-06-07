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

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.BartenderMenuType;
import nl.jixxed.eliteodysseymaterials.service.ARService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.opencv.imgproc.Imgproc.THRESH_OTSU;

@Slf4j
public class BartenderTradeARMenu implements ARMenu {
    private static final AtomicBoolean bartenderOverlayEnabled = new AtomicBoolean(PreferencesService.getPreference(PreferenceConstants.ENABLE_BARTENDER_AR, true));
    @Getter
    private final AtomicBoolean VISIBLE = new AtomicBoolean(false);
    private static final ScreenshotService screenshotService = GDIScreenshotService.getInstance();
    public static Mat cocktailTemplate;
    public static Mat cocktailTemplateScaled;
    public static Mat cocktailMask;
    public static Mat cocktailMaskScaled;
    private static BartenderMenu bartenderMenu;
    private static int contentHeight;
    private static int contentWidth;
    private static int contentX;
    private static int contentY;
    private static double newScaling;
    public static double scaling = 0;

    public BartenderTradeARMenu() {

        cocktailTemplate = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_cocktail.png")));
        cocktailTemplateScaled = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_cocktail.png")));
        cocktailMask = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_cocktail_tp.png")));
        cocktailMaskScaled = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_cocktail_tp.png")));
        //greyscale templates
        Imgproc.cvtColor(cocktailTemplate, cocktailTemplate, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.cvtColor(cocktailTemplateScaled, cocktailTemplateScaled, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.cvtColor(cocktailMask, cocktailMask, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.cvtColor(cocktailMaskScaled, cocktailMaskScaled, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.threshold(cocktailMaskScaled, cocktailMaskScaled, 50, 255, Imgproc.THRESH_BINARY);
    }

    public static BartenderMenu getBartenderMenu() {
        if (bartenderMenu == null) {
            bartenderMenu = new BartenderMenu();
        }
        return bartenderMenu;
    }

    public static BartenderMenu getBartenderMenu(final BartenderMenuType submenu) {
        if (bartenderMenu == null) {
            bartenderMenu = new BartenderMenu();
        }
        bartenderMenu.setContentHeight(contentHeight);
        bartenderMenu.setContentWidth(contentWidth);
        bartenderMenu.setSubMenu(submenu);

        return bartenderMenu;
    }
    @Override
    public void update(int x, int y, int w, int h) {
        contentHeight = h;
        contentWidth = w;
        contentX = x;
        contentY = y;
        newScaling = contentHeight / 1600D;
        getBartenderMenu().setContentWidth(contentWidth);
        getBartenderMenu().setContentHeight(contentHeight);
        updateScaling();
    }
    private void updateScaling(){
        if (newScaling != scaling) {
            log.debug("application scaling changed");
            log.debug("detected resolution: " + contentWidth + "x" + contentHeight);
            //scaling
            scaling = newScaling;
            Imgproc.resize(cocktailTemplate, cocktailTemplateScaled, new Size(), getBartenderMenu().getScale(), getBartenderMenu().getScale(), Imgproc.INTER_AREA);
            Imgproc.resize(cocktailMask, cocktailMaskScaled, new Size(), getBartenderMenu().getScale(), getBartenderMenu().getScale(), Imgproc.INTER_AREA);
            Imgproc.threshold(cocktailMaskScaled, cocktailMaskScaled, 50, 255, Imgproc.THRESH_BINARY);
        }
    }
    private static BufferedImage getCocktailCapture(WindowInfo targetWindowInfo ) {
        if (targetWindowInfo.hwnd != 0 && User32.INSTANCE.GetForegroundWindow() == targetWindowInfo.hwnd) {
            return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), BartenderTradeARMenu.getBartenderMenu().getCocktail().getAwtRectangle().getBounds(), cocktailCapture);
        }
        return null;
    }

    @Override
    public boolean isMenuVisible(WindowInfo targetWindowInfo) {
        BufferedImage cocktailCapture = getCocktailCapture(targetWindowInfo);
        return isBartenderMenu(cocktailCapture, targetWindowInfo);
    }

    private static BufferedImage cocktailCapture;
    private static Mat cocktailCaptureMat;
    private static Mat cocktailCaptureMatGray = new Mat();
    private static Mat bartenderMenuResult;
    private static final double BARTENDER_MATCHING_THRESHOLD = 0.65;
    private static final int MATCH_METHOD = Imgproc.TM_CCOEFF_NORMED;

    private static boolean previousBartenderMatch = false;
    public static boolean debug = false;
    public static boolean isBartenderMenu(final BufferedImage capture, WindowInfo targetWindowInfo) {
        if (capture == null) {
            return false;
        }
        cocktailCaptureMat = CvHelper.convertToMat(capture, cocktailCaptureMat);
        final int result_cols = cocktailCaptureMat.cols() - BartenderTradeARMenu.cocktailTemplateScaled.cols() + 1;
        final int result_rows = cocktailCaptureMat.rows() - BartenderTradeARMenu.cocktailTemplateScaled.rows() + 1;
        if (result_cols <= 0 || result_rows <= 0) {
            return false;
        }
        if (bartenderMenuResult == null || bartenderMenuResult.cols() != result_cols || bartenderMenuResult.rows() != result_rows) {
            if (bartenderMenuResult != null) {
                bartenderMenuResult.release();
            }
            bartenderMenuResult = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        }
        if (cocktailCaptureMatGray == null || cocktailCaptureMat.cols() != cocktailCaptureMatGray.cols() || cocktailCaptureMat.rows() != cocktailCaptureMatGray.rows()) {
            if (cocktailCaptureMatGray != null) {
                cocktailCaptureMatGray.release();
            }
            cocktailCaptureMatGray = new Mat();
        }
        //grayscale capture
        Imgproc.cvtColor(cocktailCaptureMat, cocktailCaptureMatGray, Imgproc.COLOR_BGRA2GRAY);
        Mat temp = new Mat();
        Imgproc.threshold(cocktailCaptureMatGray, temp, 128, 255, Imgproc.THRESH_BINARY_INV + THRESH_OTSU);

        // Apply mask using copyTo
//        cocktailTemplateScaled.copyTo(maskedResult, cocktailMaskScaled);
        Mat temp2 = new Mat();
        Imgproc.threshold(BartenderTradeARMenu.cocktailTemplateScaled, temp2, 128, 255, Imgproc.THRESH_BINARY_INV + THRESH_OTSU);
        //matching
        Imgproc.matchTemplate(temp, temp2, bartenderMenuResult, MATCH_METHOD);
        //take the best result mmr.maxLoc
        Core.MinMaxLocResult mmr = Core.minMaxLoc(bartenderMenuResult);
        //cut out a mat with the same dimensions as cocktailMaskScaled
        final Mat cutOutMat = new Mat(temp, new Rect((int) mmr.maxLoc.x, (int) mmr.maxLoc.y, BartenderTradeARMenu.cocktailMaskScaled.cols(), BartenderTradeARMenu.cocktailMaskScaled.rows()));

        Imgproc.threshold(cutOutMat, cutOutMat, 128, 255, Imgproc.THRESH_BINARY_INV);
        Imgproc.threshold(temp2, temp2, 128, 255, Imgproc.THRESH_BINARY_INV);
        //apply mask to the cut out mat
        // Create destination Mat
        Mat maskedResult = new Mat();
        cutOutMat.copyTo(maskedResult, BartenderTradeARMenu.cocktailMaskScaled);
        //match again
        Imgproc.matchTemplate(maskedResult, temp2, bartenderMenuResult, MATCH_METHOD);

        mmr = Core.minMaxLoc(bartenderMenuResult);


        final double matchValue = mmr.maxVal;
        if (debug) {
            log.debug("bartendermenu detected. Confidence(" + BARTENDER_MATCHING_THRESHOLD + "): " + matchValue);
        }
        if (matchValue > BARTENDER_MATCHING_THRESHOLD) {
            if (!previousBartenderMatch) {
                previousBartenderMatch = true;
                log.debug("bartendermenu detected. Confidence(" + BARTENDER_MATCHING_THRESHOLD + "): " + matchValue);
            }
            return true;
        }
        if (previousBartenderMatch) {

            previousBartenderMatch = false;
            log.debug("bartendermenu detected 2. Confidence(" + BARTENDER_MATCHING_THRESHOLD + "): " + matchValue);
            try {
                Thread.sleep(20);
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
            cocktailCapture = getCocktailCapture(targetWindowInfo);
            return isBartenderMenu(cocktailCapture, targetWindowInfo);
        }
        return false;
    }

    public static void bartenderToggle() {
        bartenderOverlayEnabled.set(PreferencesService.getPreference(PreferenceConstants.ENABLE_BARTENDER_AR, Boolean.TRUE));
    }

    @Override
    public boolean isEnabled() {
        return bartenderOverlayEnabled.get();
    }
}
