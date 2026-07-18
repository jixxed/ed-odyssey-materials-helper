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
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.tess4j.TesseractException;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.opencv.imgproc.Imgproc.INTER_LANCZOS4;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;

@Slf4j
public class BartenderSellARMenu implements ARMenu {
    private static final double BARTENDER_MATCHING_THRESHOLD = 0.65;
    private static final int MATCH_METHOD = Imgproc.TM_CCOEFF_NORMED;
    private static final AtomicBoolean BARTENDER_OVERLAY_ENABLED = new AtomicBoolean(PreferencesService.getPreference(PreferenceConstants.ENABLE_BARTENDER_SELL_AR, true));

    private final Map<String, Render> renderCache = new HashMap<>();
    private final ScreenshotService screenshotService = GDIScreenshotService.getInstance();
    private final AtomicInteger tradeHits = new AtomicInteger();
    @Getter
    private final AtomicBoolean visible = new AtomicBoolean(false);
    private Mat cocktailTemplate;
    private Mat cocktailTemplateScaled;
    private Mat cocktailMask;
    private Mat cocktailMaskScaled;
    private double scaling = 1;
    private boolean debug = false;
    private BartenderSellMenu bartenderSellMenu;
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
    private BufferedImage bartenderScrollbarCapture;
    private BufferedImage bartenderMenuItemsCapture;

    private ScrollBarV2 scrollBar;
    //    private BartenderMenuType newBartenderSubMenu;
//    private BartenderMenuType oldBartenderSubMenu;
    private Image overlayImage;

    public BartenderSellARMenu() {

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

    public static void bartenderToggle() {
        BARTENDER_OVERLAY_ENABLED.set(PreferencesService.getPreference(PreferenceConstants.ENABLE_BARTENDER_SELL_AR, Boolean.TRUE));
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
                final Rectangle textArea = (bartenderTradeMenu.getSubMenu().equals(BartenderMenuType.SUBMENU)) ? bartenderTradeMenu.getSubMenuEntryText(index) : bartenderTradeMenu.getMenuEntryText(index);
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

    public BartenderSellMenu getBartenderSellMenu() {
        if (bartenderSellMenu == null) {
            bartenderSellMenu = new BartenderSellMenu();
        }
        return bartenderSellMenu;
    }

    public BartenderSellMenu getBartenderSellMenu(final BartenderMenuType submenu) {
        if (bartenderSellMenu == null) {
            bartenderSellMenu = new BartenderSellMenu();
        }
        bartenderSellMenu.setContentHeight(contentHeight);
        bartenderSellMenu.setContentWidth(contentWidth);
//        bartenderSellMenu.setSubMenu(submenu);

        return bartenderSellMenu;
    }

    private BufferedImage getCocktailCapture(WindowInfo targetWindowInfo) {
        if (targetWindowInfo.hwnd != 0 && User32.INSTANCE.GetForegroundWindow() == targetWindowInfo.hwnd) {
            return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getBartenderSellMenu().getCocktail().getAwtRectangle().getBounds(), cocktailCapture);
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

    private BufferedImage getBartenderMenuCapture(WindowInfo targetWindowInfo) {
        if (targetWindowInfo.hwnd != 0) {
            final int i = User32.INSTANCE.GetForegroundWindow();
            if (i == targetWindowInfo.hwnd) {
                return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getBartenderSellMenu().getMenu().getAwtRectangle().getBounds(), bartenderMenuCapture);
            }
        }
        return null;
    }
    private BufferedImage getBartenderSellMenuScrollbarCapture(WindowInfo targetWindowInfo) {
        if (targetWindowInfo.hwnd != 0) {
            final int i = User32.INSTANCE.GetForegroundWindow();
            if (i == targetWindowInfo.hwnd) {
                return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getBartenderSellMenu().getScrollbar().getAwtRectangle().getBounds(), bartenderScrollbarCapture);
            }
        }
        return null;
    }
    private BufferedImage getBartenderSellMenuItemsCapture(WindowInfo targetWindowInfo, List<OdysseyMaterial> goods, List<OdysseyMaterial> assets, List<OdysseyMaterial> datas) {
        if (targetWindowInfo.hwnd != 0) {
            final int i = User32.INSTANCE.GetForegroundWindow();
            if (i == targetWindowInfo.hwnd) {
                return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getBartenderSellMenu().getMenuItemsRect(goods, assets, datas).getAwtRectangle().getBounds(), bartenderMenuItemsCapture);
            }
        }
        return null;
    }

    private ScrollBarV2 getScrollBar(final BufferedImage bartenderSellMenuScrollbarCapture) {
        if (bartenderSellMenuScrollbarCapture == null) {
            return scrollBar;
        }
        return ScrollBarHelper.getScrollBarV2BartenderSell(bartenderSellMenuScrollbarCapture, scaling);
    }
    private void processSellMenu(WindowInfo targetWindowInfo, Consumer<Image> resultConsumer) {
        BufferedImage bartenderSellMenuScrollbarCapture = getBartenderSellMenuScrollbarCapture(targetWindowInfo);
        final ScrollBarV2 newScrollBar = getScrollBar(bartenderSellMenuScrollbarCapture);
        byte[] colorBG = getColorBG(bartenderSellMenuScrollbarCapture);
        getBartenderSellMenu().setColorBG(colorBG);
        final boolean render = !Objects.equals(newScrollBar, scrollBar);//initialRender.get();//only render on change
        scrollBar = newScrollBar;
        if (render) {
            java.util.List<OdysseyMaterial> assets = StorageService.getRawShipLocker().getComponents()
                    .map(list -> list.stream()
                            .map(item -> OdysseyMaterial.subtypeForName(item.getName()))
                            .filter(mat -> !mat.isIllegal() || LocationService.getStationGovernment().equals(Government.ANARCHY))
                            .toList()
                    ).orElse(Collections.emptyList());
            java.util.List<OdysseyMaterial> goods = StorageService.getRawShipLocker().getItems()
                    .map(list -> list.stream()
                            .map(item -> OdysseyMaterial.subtypeForName(item.getName()))
                            .filter(mat -> !mat.isIllegal() || LocationService.getStationGovernment().equals(Government.ANARCHY))
                            .toList()
                    ).orElse(Collections.emptyList());
            java.util.List<OdysseyMaterial> datas = StorageService.getRawShipLocker().getData()
                    .map(list -> list.stream()
                            .map(item -> OdysseyMaterial.subtypeForName(item.getName()))
                            .filter(mat -> !mat.isIllegal() || LocationService.getStationGovernment().equals(Government.ANARCHY))
                            .toList()
                    ).orElse(Collections.emptyList());
            getBartenderSellMenu().setScrollBarV2(scrollBar);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
            BufferedImage bartenderSellMenuItemsCapture = getBartenderSellMenuItemsCapture(targetWindowInfo, goods, assets, datas);
            getBartenderSellMenu().setMenuItemsCapture(bartenderSellMenuItemsCapture);
            renderMenu(getBartenderSellMenu(), goods, assets, datas);
            resultConsumer.accept(overlayImage);
        }
//        newBartenderSubMenu = BartenderMenuHelper.getMenuType(bartenderMenuCapture, getBartenderTradeMenu());
////        log.info("detected menutype:" + menuType);
//        if (oldBartenderSubMenu == null || newBartenderSubMenu != oldBartenderSubMenu) {
////            Thread.sleep(20);
//            bartenderMenuCapture = getBartenderMenuCapture(targetWindowInfo);
//            newBartenderSubMenu = BartenderMenuHelper.getMenuType(bartenderMenuCapture, getBartenderTradeMenu());
//        }
//        if (bartenderMenuCapture != null) {
//            if (oldBartenderSubMenu == null || newBartenderSubMenu != oldBartenderSubMenu) {
//
//                resultConsumer.accept(null);
//                bartenderMenuCapture = getBartenderMenuCapture(targetWindowInfo);
//
//                resultConsumer.accept(null);
//                getBartenderTradeMenu(newBartenderSubMenu);
//                final long timeProcessBefore = System.currentTimeMillis();
//                processBartenderMenu(bartenderMenuCapture, getBartenderTradeMenu());
//                final long timeProcessAfter = System.currentTimeMillis();
//                log.debug("Menu processing time: " + (timeProcessAfter - timeProcessBefore));
//                final long timeRenderBefore = System.currentTimeMillis();
//                renderMenu(getBartenderTradeMenu());
////                arOverlay.getResizableImageView().setImage(overlayImage);
//
//                resultConsumer.accept(overlayImage);
//                final long timeRenderAfter = System.currentTimeMillis();
//                log.debug("Total render time: " + (timeRenderAfter - timeRenderBefore));
////              log.debug("Total time to screen: " + ((timeProcessAfter - timeProcessBefore) + (timeRenderAfter - timeRenderBefore)));
//
//            }
//        }
//        oldBartenderSubMenu = newBartenderSubMenu;
////        return overlayImage;
    }

    private byte[] getColorBG(BufferedImage bartenderSellMenuScrollbarCapture) {
        if (bartenderSellMenuScrollbarCapture == null) {
            return new byte[4];
        }
        final WritableRaster backgroundColorPixel = ((WritableRaster) bartenderSellMenuScrollbarCapture.getData(new java.awt.Rectangle((int) (0D * scaling),
                (int) (215D * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
        return DataBufferHelper.getData(backgroundColorPixel.getDataBuffer());
    }

    private void renderMenu(final BartenderSellMenu bartenderSellMenu, List<OdysseyMaterial> goods, List<OdysseyMaterial> assets, List<OdysseyMaterial> datas) {
        final BufferedImage bufferedImage = MenuOverlayRenderer.renderMenu(bartenderSellMenu, goods, assets, datas);
        overlayImage = SwingFXUtils.toFXImage(bufferedImage, null);
    }

    @Override
    public void update(int x, int y, int w, int h) {
        contentHeight = h;
        contentWidth = w;
        contentX = x;
        contentY = y;
        newScaling = contentHeight / 1600D;
        getBartenderSellMenu().setContentWidth(contentWidth);
        getBartenderSellMenu().setContentHeight(contentHeight);
        updateScaling();
    }

    private void updateScaling() {
        if (newScaling != scaling) {
            log.debug("application scaling changed");
            log.debug("detected resolution: " + contentWidth + "x" + contentHeight);
            //scaling
            scaling = newScaling;
            Imgproc.resize(cocktailTemplate, cocktailTemplateScaled, new Size(), getBartenderSellMenu().getScale(), getBartenderSellMenu().getScale(), Imgproc.INTER_AREA);
            Imgproc.resize(cocktailMask, cocktailMaskScaled, new Size(), getBartenderSellMenu().getScale(), getBartenderSellMenu().getScale(), Imgproc.INTER_AREA);
            Imgproc.threshold(cocktailMaskScaled, cocktailMaskScaled, 50, 255, Imgproc.THRESH_BINARY);
        }
    }

    @Override
    public boolean isMenuVisible(WindowInfo targetWindowInfo) {
        BufferedImage cocktailCapture = getCocktailCapture(targetWindowInfo);
        boolean bartenderMenu = isBartenderMenu(cocktailCapture, targetWindowInfo);
        if (bartenderMenu) {
            bartenderMenuCapture = getBartenderMenuCapture(targetWindowInfo);
            return BartenderMenuHelper.isSellMenu(bartenderMenuCapture, getBartenderSellMenu());
        }
        return false;
    }

    @Override
    public boolean isEnabled() {
        return BARTENDER_OVERLAY_ENABLED.get() && !Government.PRIVATEOWNERSHIP.equals(LocationService.getStationGovernment());
    }

    @Override
    public void analyzeAndRender(WindowInfo targetWindowInfo, Consumer<Image> resultConsumer) {

        if (getVisible().get()) {
            bartenderMenuCapture = getBartenderMenuCapture(targetWindowInfo);
            if (BartenderMenuHelper.isSellMenu(bartenderMenuCapture, getBartenderSellMenu())) {
                if (tradeHits.get() <= 1) {
                    tradeHits.getAndIncrement();
                }
                if (tradeHits.get() > 1) {
                    processSellMenu(targetWindowInfo, resultConsumer);
                }
            }
        }
    }

    public void clear() {
        visible.set(false);
        overlayImage = null;
        renderCache.clear();
        tradeHits.set(0);
        scrollBar = null;
//        newBartenderSubMenu = null;
//        oldBartenderSubMenu = null;
    }

}
