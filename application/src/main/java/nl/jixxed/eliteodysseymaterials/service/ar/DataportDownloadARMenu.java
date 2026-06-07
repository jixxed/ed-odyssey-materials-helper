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
import nl.jixxed.eliteodysseymaterials.service.ARService;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class DataportDownloadARMenu implements ARMenu {
    @Getter
    private final AtomicBoolean VISIBLE = new AtomicBoolean(false);
    private static final ScreenshotService screenshotService = GDIScreenshotService.getInstance();
    public static Mat arrowTemplate;
    public static Mat arrowTemplateScaled;
    private static DownloadMenu downloadMenu;
    public static ScrollBarV2 scrollBar;
    public static Boolean hasWarning;
    private static int contentHeight;
    private static int contentWidth;
    private static int contentX;
    private static int contentY;
    private static double newScaling;
    public static double scaling = 0;
    private static BufferedImage downloadMenuCapture;

    public DataportDownloadARMenu() {
        arrowTemplate = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_download.png")));
        arrowTemplateScaled = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_download.png")));
        Imgproc.cvtColor(arrowTemplate, arrowTemplate, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.cvtColor(arrowTemplateScaled, arrowTemplateScaled, Imgproc.COLOR_BGRA2GRAY);
    }

    public static DownloadMenu getDownloadMenu() {
        if (downloadMenu == null) {
            downloadMenu = new DownloadMenu(downloadMenuCapture, scaling, Boolean.TRUE.equals(hasWarning), scrollBar, contentWidth, contentHeight);
        }
        return downloadMenu;
    }

    public static DownloadMenu getDownloadMenu(final BufferedImage downloadMenuCapture, final boolean hasWarning, final ScrollBarV2 scrollBar) {
        if (downloadMenu == null) {
            downloadMenu = new DownloadMenu(downloadMenuCapture, scaling, hasWarning, scrollBar, contentWidth, contentHeight);
        } else {
            downloadMenu.setScale(scaling);
            downloadMenu.setContentHeight(contentHeight);
            downloadMenu.setContentWidth(contentWidth);
//            downloadMenu.setScrollBar(scrollBar);
//            downloadMenu.setDownloadMenuCapture(downloadMenuCapture);
//            downloadMenu.setHasWarning(hasWarning);
            downloadMenu.updateMenuState(downloadMenuCapture, scrollBar, hasWarning);
        }
        return downloadMenu;
    }
    @Override
    public void update(int x, int y, int w, int h) {
        contentHeight = h;
        contentWidth = w;
        contentX = x;
        contentY = y;
        newScaling = contentHeight / 1600D;
        getDownloadMenu().setContentWidth(contentWidth);
        getDownloadMenu().setContentHeight(contentHeight);
        getDownloadMenu().setScale(newScaling);
        updateScaling();
    }

    private void updateScaling(){
        if (newScaling != scaling) {
            log.debug("application scaling changed");
            log.debug("detected resolution: " + contentWidth + "x" + contentHeight);
            //scaling
            scaling = newScaling;
            Imgproc.resize(arrowTemplate, arrowTemplateScaled, new Size(), scaling, scaling, Imgproc.INTER_AREA);
            WarningHelper.updateScale(scaling);

            ImageTransformHelper.init(DataportDownloadARMenu.getDownloadMenu(), scaling);
        }
    }
    private static BufferedImage getArrowCapture(WindowInfo targetWindowInfo) {
        if (targetWindowInfo.hwnd != 0 && User32.INSTANCE.GetForegroundWindow() == targetWindowInfo.hwnd) {
            return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), DataportDownloadARMenu.getDownloadMenu().getArrow().getAwtRectangle().getBounds(), arrowCapture);
        }
        return null;
    }

    @Override
    public boolean isMenuVisible(WindowInfo targetWindowInfo) {
        BufferedImage arrowCapture = getArrowCapture(targetWindowInfo);
        return isDownloadMenu(arrowCapture, targetWindowInfo);
    }



    private static boolean isDownloadMenu(final BufferedImage capture, WindowInfo targetWindowInfo) {
        if (capture == null) {
            return false;
        }
        arrowCaptureMat = CvHelper.convertToMat(capture, arrowCaptureMat);
        final int result_cols = arrowCaptureMat.cols() - DataportDownloadARMenu.arrowTemplateScaled.cols() + 1;
        final int result_rows = arrowCaptureMat.rows() - DataportDownloadARMenu.arrowTemplateScaled.rows() + 1;
        if (result_cols <= 0 || result_rows <= 0) {
            return false;
        }
        if (downloadMenuResult == null || downloadMenuResult.cols() != result_cols || downloadMenuResult.rows() != result_rows) {
            if (downloadMenuResult != null) {
                downloadMenuResult.release();
            }
            downloadMenuResult = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        }
        if (arrowCaptureMatGray == null || arrowCaptureMat.cols() != arrowCaptureMatGray.cols() || arrowCaptureMat.rows() != arrowCaptureMatGray.rows()) {
            if (arrowCaptureMatGray != null) {
                arrowCaptureMatGray.release();
            }
            arrowCaptureMatGray = new Mat();
        }
        //grayscale capture
        Imgproc.cvtColor(arrowCaptureMat, arrowCaptureMatGray, Imgproc.COLOR_BGRA2GRAY);
        //matching
        Imgproc.matchTemplate(arrowCaptureMatGray, DataportDownloadARMenu.arrowTemplateScaled, downloadMenuResult, MATCH_METHOD);
        final Core.MinMaxLocResult mmr = Core.minMaxLoc(downloadMenuResult);


        if (mmr.maxVal > getMatchingThreshold()) {
            if (!previousDataportMatch) {
                previousDataportMatch = true;
                log.debug("dataport downloadmenu detection confidence(" + getMatchingThreshold() + "): " + mmr.maxVal);
            }
            return true;
        }
        if (previousDataportMatch) {

            previousDataportMatch = false;
            log.debug("dataport downloadmenu detection confidence(" + getMatchingThreshold() + "): " + mmr.maxVal);
            arrowCapture = getArrowCapture(targetWindowInfo);
            return isDownloadMenu(arrowCapture, targetWindowInfo);
        }
        return false;
    }
    private static double getMatchingThreshold() {
        return MATCHING_THRESHOLD;
    }

    private static final double MATCHING_THRESHOLD = 0.75;
    private static BufferedImage arrowCapture;

    private static final int MATCH_METHOD = Imgproc.TM_CCOEFF_NORMED;
    private static Mat downloadMenuResult;
    private static Mat arrowCaptureMat;
    private static Mat arrowCaptureMatGray = new Mat();
    private static boolean previousDataportMatch = false;

    @Override
    public boolean isEnabled() {
        return true;
    }
}
