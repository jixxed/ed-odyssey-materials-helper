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
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.ARService;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

@Slf4j
public class DataportDownloadARMenu implements ARMenu {
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
}
