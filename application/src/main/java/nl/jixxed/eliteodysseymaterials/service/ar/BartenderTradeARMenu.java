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

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.BartenderMenuType;
import nl.jixxed.eliteodysseymaterials.service.ARService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.OCRService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.tess4j.TesseractException;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.opencv.imgproc.Imgproc.INTER_LANCZOS4;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;

@Slf4j
public class BartenderTradeARMenu implements ARMenu {
    private static final double BARTENDER_MATCHING_THRESHOLD = 0.65;
    private static final int MATCH_METHOD = Imgproc.TM_CCOEFF_NORMED;
    private static final AtomicBoolean BARTENDER_OVERLAY_ENABLED = new AtomicBoolean(PreferencesService.getPreference(PreferenceConstants.ENABLE_BARTENDER_AR, true));

    private final Map<String, Render> renderCache = new HashMap<>();
    private final ScreenshotService screenshotService = GDIScreenshotService.getInstance();
    private final AtomicInteger tradeHits = new AtomicInteger();
    private Mat cocktailTemplate;
    private Mat cocktailTemplateScaled;
    private Mat cocktailMask;
    private Mat cocktailMaskScaled;
    private double scaling = 1;
    private boolean debug = false;
    private BartenderTradeMenu bartenderTradeMenu;
    private int contentHeight;
    private int contentWidth;
    private int contentX;
    private int contentY;
    private double newScaling;
    private BufferedImage cocktailCapture;
    private Mat cocktailCaptureMat;
    private Mat cocktailCaptureMatGray = new Mat();
    private Mat bartenderMenuResult;
    private boolean previousBartenderMatch = false;
    private BufferedImage bartenderMenuCapture;
    private BartenderMenuType newBartenderSubMenu;
    private BartenderMenuType oldBartenderSubMenu;
    private Image overlayImage;
    @Getter
    private final AtomicBoolean visible = new AtomicBoolean(false);

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

    public BartenderTradeMenu getBartenderTradeMenu() {
        if (bartenderTradeMenu == null) {
            bartenderTradeMenu = new BartenderTradeMenu();
        }
        return bartenderTradeMenu;
    }

    public BartenderTradeMenu getBartenderTradeMenu(final BartenderMenuType submenu) {
        if (bartenderTradeMenu == null) {
            bartenderTradeMenu = new BartenderTradeMenu();
        }
        bartenderTradeMenu.setContentHeight(contentHeight);
        bartenderTradeMenu.setContentWidth(contentWidth);
        bartenderTradeMenu.setSubMenu(submenu);

        return bartenderTradeMenu;
    }

    private BufferedImage getCocktailCapture(WindowInfo targetWindowInfo) {
        if (targetWindowInfo.hwnd != 0 && User32.INSTANCE.GetForegroundWindow() == targetWindowInfo.hwnd) {
            return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getBartenderTradeMenu().getCocktail().getAwtRectangle().getBounds(), cocktailCapture);
        }
        return null;
    }

    public boolean isBartenderMenu(final BufferedImage capture, WindowInfo targetWindowInfo) {
        if (capture == null) {
            return false;
        }
        cocktailCaptureMat = CvHelper.convertToMat(capture, cocktailCaptureMat);
        final int result_cols = cocktailCaptureMat.cols() - cocktailTemplateScaled.cols() + 1;
        final int result_rows = cocktailCaptureMat.rows() - cocktailTemplateScaled.rows() + 1;
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
        Imgproc.threshold(cocktailTemplateScaled, temp2, 128, 255, Imgproc.THRESH_BINARY_INV + THRESH_OTSU);
        //matching
        Imgproc.matchTemplate(temp, temp2, bartenderMenuResult, MATCH_METHOD);
        //take the best result mmr.maxLoc
        Core.MinMaxLocResult mmr = Core.minMaxLoc(bartenderMenuResult);
        //cut out a mat with the same dimensions as cocktailMaskScaled
        final Mat cutOutMat = new Mat(temp, new Rect((int) mmr.maxLoc.x, (int) mmr.maxLoc.y, cocktailMaskScaled.cols(), cocktailMaskScaled.rows()));

        Imgproc.threshold(cutOutMat, cutOutMat, 128, 255, Imgproc.THRESH_BINARY_INV);
        Imgproc.threshold(temp2, temp2, 128, 255, Imgproc.THRESH_BINARY_INV);
        //apply mask to the cut out mat
        // Create destination Mat
        Mat maskedResult = new Mat();
        cutOutMat.copyTo(maskedResult, cocktailMaskScaled);
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
        BARTENDER_OVERLAY_ENABLED.set(PreferencesService.getPreference(PreferenceConstants.ENABLE_BARTENDER_AR, Boolean.TRUE));
    }

    private BufferedImage getBartenderMenuCapture(WindowInfo targetWindowInfo) {
        if (targetWindowInfo.hwnd != 0) {
            final int i = User32.INSTANCE.GetForegroundWindow();
            if (i == targetWindowInfo.hwnd) {
                return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getBartenderTradeMenu().getMenu().getAwtRectangle().getBounds(), bartenderMenuCapture);
            }
        }
        return null;
    }

    private void processTradeMenu(WindowInfo targetWindowInfo, Consumer<Image> resultConsumer) {
        newBartenderSubMenu = BartenderMenuHelper.getMenuType(bartenderMenuCapture, getBartenderTradeMenu());
//        log.info("detected menutype:" + menuType);
        if (oldBartenderSubMenu == null || newBartenderSubMenu != oldBartenderSubMenu) {
//            Thread.sleep(20);
            bartenderMenuCapture = getBartenderMenuCapture(targetWindowInfo);
            newBartenderSubMenu = BartenderMenuHelper.getMenuType(bartenderMenuCapture, getBartenderTradeMenu());
        }
        if (bartenderMenuCapture != null) {
            if (oldBartenderSubMenu == null || newBartenderSubMenu != oldBartenderSubMenu) {

                resultConsumer.accept(null);
                bartenderMenuCapture = getBartenderMenuCapture(targetWindowInfo);

                resultConsumer.accept(null);
                getBartenderTradeMenu(newBartenderSubMenu);
                final long timeProcessBefore = System.currentTimeMillis();
                processBartenderMenu(bartenderMenuCapture, getBartenderTradeMenu());
                final long timeProcessAfter = System.currentTimeMillis();
                log.debug("Menu processing time: " + (timeProcessAfter - timeProcessBefore));
                final long timeRenderBefore = System.currentTimeMillis();
                renderMenu(getBartenderTradeMenu());
//                arOverlay.getResizableImageView().setImage(overlayImage);

                resultConsumer.accept(overlayImage);
                final long timeRenderAfter = System.currentTimeMillis();
                log.debug("Total render time: " + (timeRenderAfter - timeRenderBefore));
//              log.debug("Total time to screen: " + ((timeProcessAfter - timeProcessBefore) + (timeRenderAfter - timeRenderBefore)));

            }
        }
        oldBartenderSubMenu = newBartenderSubMenu;
//        return overlayImage;
    }

    private void renderMenu(final BartenderTradeMenu bartenderTradeMenu) {
        final BufferedImage bufferedImage = MenuOverlayRenderer.renderMenu(bartenderTradeMenu);
        overlayImage = SwingFXUtils.toFXImage(bufferedImage, null);
    }

    private static void processBartenderMenu(final BufferedImage bartenderMenuCapture, final BartenderTradeMenu bartenderTradeMenu) {
        try {
            final Locale locale = ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "ENGLISH")).getLocale();

            final List<Asset> assets = new ArrayList<>();
            final int lines = switch (bartenderTradeMenu.getSubMenu()) {
                case SUBMENU, MAIN_CIRCUITS -> 12;
                case MAIN_TECH -> 11;
                case MAIN_CHEMICALS -> 10;
                case NONE -> 0;
            };
            boolean fuzzy = PreferencesService.getPreference(PreferenceConstants.AR_SEARCH_METHOD, "EXACT").equals("FUZZY");
            int fuzzyScore = PreferencesService.getPreference(PreferenceConstants.AR_FUZZY_SCORE, 90);
            for (int index = 0; index < lines; index++) {
                final nl.jixxed.eliteodysseymaterials.service.ar.Rectangle textArea = (bartenderTradeMenu.getSubMenu().equals(BartenderMenuType.SUBMENU)) ? bartenderTradeMenu.getSubMenuEntryText(index) : bartenderTradeMenu.getMenuEntryText(index);
                BufferedImage textImage = bartenderMenuCapture.getSubimage((int) textArea.getX(), (int) textArea.getY(), (int) textArea.getWidth(), (int) textArea.getHeight());

                final Mat image = CvHelper.convertToMat(textImage, null);
                Imgproc.resize(image, image, new Size(image.size().width * 4, image.size().height * 4), 4, 4, INTER_LANCZOS4);
                final Mat temp = image.clone();
                Imgproc.cvtColor(image, image, Imgproc.COLOR_BGRA2GRAY);
                Imgproc.threshold(image, temp, 50, 255, Imgproc.THRESH_BINARY_INV + THRESH_OTSU);
                textImage = CvHelper.createBufferedImage(temp);
                String textAsset = bartenderMenuItemToString(textImage);
                Asset asset;
                asset = getAsset(fuzzy, textAsset, locale, fuzzyScore);
                if (asset.isUnknown()) {
                    Imgproc.threshold(image, temp, 50, 255, Imgproc.THRESH_BINARY_INV + THRESH_OTSU);
                    textImage = CvHelper.createBufferedImage(temp);
                    textAsset = bartenderMenuItemToString(textImage);
                    asset = getAsset(fuzzy, textAsset, locale, fuzzyScore);
                }
                if (asset.isUnknown()) {
                    Imgproc.threshold(image, temp, 127, 255, Imgproc.THRESH_BINARY + THRESH_OTSU);
                    textImage = CvHelper.createBufferedImage(temp);
                    textAsset = bartenderMenuItemToString(textImage);
                    asset = getAsset(fuzzy, textAsset, locale, fuzzyScore);
                }
                image.release();
                temp.release();
                assets.add(asset);
            }
            bartenderTradeMenu.getVisibleAssets().clear();
            bartenderTradeMenu.getVisibleAssets().addAll(assets);
            log.info(assets.stream().map(Enum::name).collect(Collectors.joining(", ")));
        } catch (final TesseractException e) {
            throw new RuntimeException(e);
        }

    }

    private static Asset getAsset(boolean fuzzy, String textAsset, Locale locale, int fuzzyScore) {
        try {
            Asset asset;
            if (fuzzy) {
                BoundExtractedResult<Asset> extractedResult = Asset.forLocalizedNameSpaceInsensitiveFuzzy(textAsset, locale);
                log.info("Fuzzy matched '{}' to '{}' with score {}", textAsset, extractedResult.getReferent(), extractedResult.getScore());
                if (extractedResult.getScore() >= fuzzyScore) {
                    asset = extractedResult.getReferent();
                } else {
                    throw new IllegalArgumentException("Fuzzy score too low");
                }
            } else {
                asset = Asset.forLocalizedNameSpaceInsensitive(textAsset, locale);
            }
            return asset;
        } catch (IllegalArgumentException e) {
            log.info("Could not match '{}' to an asset for locale {}. Reason: {}", textAsset, locale, e.getMessage());
            return Asset.UNKNOWN;
        }
    }

    private static String bartenderMenuItemToString(final BufferedImage bartenderMenuCapture) throws TesseractException {
        final String assetCharacterForCurrentARLocale = LocaleService.getAssetCharacterForCurrentARLocale();
        OCRService.setCharWhitelist(assetCharacterForCurrentARLocale);
        final String assetCharacterForCurrentLocale = assetCharacterForCurrentARLocale;
        final String dataCharacterForCurrentLocaleWithoutSpace = assetCharacterForCurrentLocale.replace("\s", "");
        final String ocr = OCRService.imageToString(bartenderMenuCapture);
//        log.debug("ocr detected: " + ocr);
        String cleaned = ocr.replaceAll("[^" + assetCharacterForCurrentLocale + "]", "").replace("\s\s", "\s").trim();
        if (cleaned.matches("^[" + assetCharacterForCurrentLocale + "]*\s[" + dataCharacterForCurrentLocaleWithoutSpace + "]$")) {
            cleaned = cleaned.substring(0, cleaned.length() - 2);
        }
//        log.debug("ocr cleaned: " + cleaned);
        return cleaned;
    }

    @Override
    public void update(int x, int y, int w, int h) {
        contentHeight = h;
        contentWidth = w;
        contentX = x;
        contentY = y;
        newScaling = contentHeight / 1600D;
        getBartenderTradeMenu().setContentWidth(contentWidth);
        getBartenderTradeMenu().setContentHeight(contentHeight);
        updateScaling();
    }

    private void updateScaling() {
        if (newScaling != scaling) {
            log.debug("application scaling changed");
            log.debug("detected resolution: " + contentWidth + "x" + contentHeight);
            //scaling
            scaling = newScaling;
            Imgproc.resize(cocktailTemplate, cocktailTemplateScaled, new Size(), getBartenderTradeMenu().getScale(), getBartenderTradeMenu().getScale(), Imgproc.INTER_AREA);
            Imgproc.resize(cocktailMask, cocktailMaskScaled, new Size(), getBartenderTradeMenu().getScale(), getBartenderTradeMenu().getScale(), Imgproc.INTER_AREA);
            Imgproc.threshold(cocktailMaskScaled, cocktailMaskScaled, 50, 255, Imgproc.THRESH_BINARY);
        }
    }

//    @Override
//    public boolean isMenuVisible(WindowInfo targetWindowInfo) {
//        BufferedImage cocktailCapture = getCocktailCapture(targetWindowInfo);
//        return isBartenderMenu(cocktailCapture, targetWindowInfo);
//    }

    @Override
    public boolean isMenuVisible(WindowInfo targetWindowInfo) {
        BufferedImage cocktailCapture = getCocktailCapture(targetWindowInfo);
        boolean bartenderMenu = isBartenderMenu(cocktailCapture, targetWindowInfo);
        if (bartenderMenu) {
            bartenderMenuCapture = getBartenderMenuCapture(targetWindowInfo);
            return BartenderMenuHelper.isTradeMenu(bartenderMenuCapture, getBartenderTradeMenu());
        }
        return false;
    }

    @Override
    public boolean isEnabled() {
        return BARTENDER_OVERLAY_ENABLED.get();
    }

    @Override
    public void analyzeAndRender(WindowInfo targetWindowInfo, Consumer<Image> resultConsumer) {

        if (getVisible().get()) {
            bartenderMenuCapture = getBartenderMenuCapture(targetWindowInfo);
            if (BartenderMenuHelper.isTradeMenu(bartenderMenuCapture, getBartenderTradeMenu())) {
                if (tradeHits.get() <= 1) {
                    tradeHits.getAndIncrement();
                }
                if (tradeHits.get() > 1) {
                    processTradeMenu(targetWindowInfo, resultConsumer);
                }
            }
        }
    }

    public void clear() {
        visible.set(false);
        overlayImage = null;
        renderCache.clear();
        tradeHits.set(0);
        newBartenderSubMenu = null;
        oldBartenderSubMenu = null;
    }

}
