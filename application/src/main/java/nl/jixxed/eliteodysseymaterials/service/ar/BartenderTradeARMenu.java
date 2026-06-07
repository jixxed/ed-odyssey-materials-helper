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
import nl.jixxed.eliteodysseymaterials.enums.BartenderMenuType;
import nl.jixxed.eliteodysseymaterials.service.ARService;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

@Slf4j
public class BartenderTradeARMenu implements ARMenu {
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
        updateScaling();
        getBartenderMenu().setContentWidth(contentWidth);
        getBartenderMenu().setContentHeight(contentHeight);
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
}
