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
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.DataPortType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;
import nl.jixxed.tess4j.TesseractException;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.opencv.imgproc.Imgproc.THRESH_OTSU;

@Slf4j
public class DataportDownloadARMenu implements ARMenu {
    private static final double MATCHING_THRESHOLD = 0.75;
    private static final int MATCH_METHOD = Imgproc.TM_CCOEFF_NORMED;
    private static final Pattern DATA_PORT_NAME_PATTERN = Pattern.compile("^([A-Z]*[\s]?DATA PORT)[\s]?([\\d]{0,2})$");

    private final Map<String, Render> renderCache = new HashMap<>();
    private final ScreenshotService screenshotService = GDIScreenshotService.getInstance();
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private Mat arrowTemplate;
    private Mat arrowTemplateScaled;
    private ScrollBarV2 scrollBar;
    private Boolean hasWarning;
    private double scaling = 1;
    private Image overlayImage;
    private DownloadMenu downloadMenu;
    private int contentHeight;
    private int contentWidth;
    private int contentX;
    private int contentY;
    private double newScaling;
    private BufferedImage downloadMenuCapture;
    private BufferedImage arrowCapture;
    private Mat downloadMenuResult;
    private Mat arrowCaptureMat;
    private Mat arrowCaptureMatGray = new Mat();
    private boolean previousDataportMatch = false;
    @Getter
    private final AtomicBoolean visible = new AtomicBoolean(false);
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public DataportDownloadARMenu() {
        arrowTemplate = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_download.png")));
        arrowTemplateScaled = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_download.png")));
        Imgproc.cvtColor(arrowTemplate, arrowTemplate, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.cvtColor(arrowTemplateScaled, arrowTemplateScaled, Imgproc.COLOR_BGRA2GRAY);
        eventListeners.add(EventService.addListener(this, TerminateApplicationEvent.class, event -> {
            executorService.shutdownNow();
            log.info("DataportDownloadARMenu shutdown finished.");
        }));
    }

    public DownloadMenu getDownloadMenu() {
        if (downloadMenu == null) {
            downloadMenu = new DownloadMenu(downloadMenuCapture, scaling, Boolean.TRUE.equals(hasWarning), scrollBar, contentWidth, contentHeight);
        }
        return downloadMenu;
    }

    public DownloadMenu getDownloadMenu(final BufferedImage downloadMenuCapture, final boolean hasWarning, final ScrollBarV2 scrollBar) {
        if (downloadMenu == null) {
            downloadMenu = new DownloadMenu(downloadMenuCapture, scaling, hasWarning, scrollBar, contentWidth, contentHeight);
        } else {
            downloadMenu.setScale(scaling);
            downloadMenu.setContentHeight(contentHeight);
            downloadMenu.setContentWidth(contentWidth);
            downloadMenu.updateMenuState(downloadMenuCapture, scrollBar, hasWarning);
        }
        return downloadMenu;
    }

    private BufferedImage getArrowCapture(WindowInfo targetWindowInfo) {
        if (targetWindowInfo.hwnd != 0 && User32.INSTANCE.GetForegroundWindow() == targetWindowInfo.hwnd) {
            return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getDownloadMenu().getArrow().getAwtRectangle().getBounds(), arrowCapture);
        }
        return null;
    }

    private boolean isDownloadMenu(final BufferedImage capture, WindowInfo targetWindowInfo) {
        if (capture == null) {
            return false;
        }
        arrowCaptureMat = CvHelper.convertToMat(capture, arrowCaptureMat);
        final int result_cols = arrowCaptureMat.cols() - arrowTemplateScaled.cols() + 1;
        final int result_rows = arrowCaptureMat.rows() - arrowTemplateScaled.rows() + 1;
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
        Imgproc.matchTemplate(arrowCaptureMatGray, arrowTemplateScaled, downloadMenuResult, MATCH_METHOD);
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

    private double getMatchingThreshold() {
        return MATCHING_THRESHOLD;
    }

    private boolean canRenderMore(Render cachedImage, DownloadMenu downloadMenu) {
        if (cachedImage == null)
            return true;
        for (int index = 1; index <= downloadMenu.getMenuSize(); index++) {
            if ((downloadMenu.isMenuItemVisible(index) && downloadMenu.isScanned(index) && !cachedImage.renderedIndexes().contains(index)) || !cachedImage.renderedIndexes().contains(index) && downloadMenu.isMenuItemVisibleForOCR(index)) {
                return true;
            }
        }
        return false;
    }

    private void renderMenu(final DownloadMenu downloadMenu) {
        final BufferedImage bufferedImage = MenuOverlayRenderer.renderMenu(downloadMenu);
        final AtomicBoolean isFullyRendered = new AtomicBoolean(true);
        final Set<Integer> renderedIndexes = new HashSet();
        for (int index = 1; index <= downloadMenu.getMenuSize(); index++) {
            if (downloadMenu.isMenuItemVisible(index) && !downloadMenu.isScanned(index)) {
                log.debug("not scanned index:" + index);
                isFullyRendered.set(false);
            } else if (downloadMenu.isMenuItemVisible(index)) {
                renderedIndexes.add(index);
            }
        }
        overlayImage = SwingFXUtils.toFXImage(bufferedImage, null);
        renderCache.put(String.valueOf(downloadMenu.isHasWarning()) + downloadMenu.getScrollBar().getPosition(), new Render(renderedIndexes, isFullyRendered.get(), overlayImage));
    }

    private ScrollBarV2 getScrollBar(final BufferedImage downloadMenuCapture, final boolean hasWarning) {
        if (downloadMenuCapture == null) {
            return scrollBar;
        }
        return ScrollBarHelper.getScrollBarV2(downloadMenuCapture, hasWarning, scaling);
    }

    private BufferedImage getDownloadMenuCapture(WindowInfo targetWindowInfo) {
        if (targetWindowInfo.hwnd != 0) {
            final int i = User32.INSTANCE.GetForegroundWindow();
            if (i == targetWindowInfo.hwnd) {
                return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getDownloadMenu().getMenu().getAwtRectangle().getBounds(), downloadMenuCapture);
            }
        }
        return null;
    }

    private void processMenuType(final BufferedImage downloadMenuCapture, final DownloadMenu downloadMenu) {
        if (downloadMenu.getType() == null) {
            try {

                long timeRenderBefore = System.currentTimeMillis();
                final BufferedImage warped = ImageTransformHelper.transformForSelection(downloadMenuCapture, new java.awt.Rectangle(
                        (int) (downloadMenu.getTerminalType().getX() - 10),
                        (int) (downloadMenu.getTerminalType().getY() - 10),
                        (int) (downloadMenu.getTerminalType().getWidth() + 10),
                        (int) (downloadMenu.getTerminalType().getHeight() + 10))

                );
                long timeRenderAfter = System.currentTimeMillis();
                log.debug("Transform terminal time: " + (timeRenderAfter - timeRenderBefore));
                final BufferedImage typeLabelCaptureOriginalColor = warped.getSubimage(
                        (int) (downloadMenu.getTerminalType().getX()),
                        (int) (downloadMenu.getTerminalType().getY()),
                        (int) downloadMenu.getTerminalType().getWidth(),
                        (int) downloadMenu.getTerminalType().getHeight()
                );

                final Mat matColor = CvHelper.convertToMat(typeLabelCaptureOriginalColor, null);
                final Mat matGray = new Mat(matColor.size(), CvType.CV_8UC1);
                final Mat lookUpTable = new Mat(1, 256, CvType.CV_8U);
                final double gammaValue = 1.5;
                final byte[] lookUpTableData = new byte[(int) (lookUpTable.total() * lookUpTable.channels())];
                for (int i = 0; i < lookUpTable.cols(); i++) {
                    lookUpTableData[i] = saturate(Math.pow(i / 255.0, gammaValue) * 255.0);
                }
                lookUpTable.put(0, 0, lookUpTableData);
                final Mat img = new Mat();
                Core.LUT(matColor, lookUpTable, img);
                final Mat m = new Mat(matColor.size(), matColor.type(), new Scalar(255, 255, 255, 255));
                final Mat mMul = img.mul(m, 0.05);
                Imgproc.cvtColor(mMul, matGray, Imgproc.COLOR_RGB2GRAY);

                Imgproc.threshold(matGray, matGray, 0, 255, Imgproc.THRESH_BINARY_INV + THRESH_OTSU);

                final BufferedImage typeLabelCaptureOriginalGray = CvHelper.mat2Img(matGray);
                matColor.release();
                lookUpTable.release();
                matGray.release();
                m.release();
                mMul.release();
                timeRenderBefore = System.currentTimeMillis();
                String cleaned = imageToTerminalType(typeLabelCaptureOriginalGray);
                final Locale locale = ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "ENGLISH")).getLocale();
                try {
                    final Matcher matcher = DATA_PORT_NAME_PATTERN.matcher(cleaned);
                    if (matcher.matches()) {
                        DataPortType.forLocalizedName(matcher.group(1), locale);
                    } else {
                        throw new IllegalArgumentException("no match");
                    }
                } catch (final Exception e) {
                    final Mat normal = CvHelper.convertToMat(typeLabelCaptureOriginalGray, null);
                    final Mat inverted = new Mat();
                    Core.bitwise_not(normal, inverted);
                    final BufferedImage typeLabelCaptureInverted = CvHelper.mat2Img(inverted);
                    normal.release();
                    inverted.release();
                    log.debug("Attempt OCR inverted");
                    cleaned = imageToTerminalType(typeLabelCaptureInverted);
                }
                try {
                    if (!cleaned.isBlank()) {
                        final Matcher matcher = DATA_PORT_NAME_PATTERN.matcher(cleaned);
                        if (matcher.matches()) {
                            final DataPortType dataPortType = DataPortType.forLocalizedName(matcher.group(1), locale);
                            log.debug("detected terminal: " + cleaned);
                            downloadMenu.setType(dataPortType);
                            downloadMenu.setDataPortName(cleaned);
                        } else {
                            throw new IllegalArgumentException("no match");
                        }
                    }

                } catch (final IllegalArgumentException ex) {
                    log.debug("detected terminal: UNKNOWN");
                    downloadMenu.setDataPortName("UNKNOWN");
                }

                timeRenderAfter = System.currentTimeMillis();
                log.debug("OCR terminal time: " + (timeRenderAfter - timeRenderBefore));
            } catch (final TesseractException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static byte saturate(final double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }

    private void processMenu(final BufferedImage downloadMenuCapture, final DownloadMenu downloadMenu) {

        final long timeRenderBeforeMenu = System.currentTimeMillis();
        final List<Future> tasks = new ArrayList<>();
        for (int index = 1; index <= downloadMenu.getMenuSize(); index++) {
            final int finalIndex = index;
            final Future<?> future = executorService.submit(() -> {
                if (Boolean.TRUE.equals(!downloadMenu.getScanned().getOrDefault(finalIndex, false)) && downloadMenu.isMenuItemVisibleForOCR(finalIndex)) {
                    try {
                        final double menuItemY = downloadMenu.getMenuItem(finalIndex).getY();

                        long timeRenderBefore = System.currentTimeMillis();
                        final BufferedImage warped = ImageTransformHelper.transformForSelection(downloadMenuCapture, new java.awt.Rectangle(
                                (int) (downloadMenu.getMenuItem(finalIndex).getX() + downloadMenu.getMenuTextReadOffset().getX() - 10),
                                (int) (menuItemY + downloadMenu.getMenuTextReadOffset().getY() - 50),
                                (int) downloadMenu.getMenuTextReadOffset().getWidth() + 20,
                                (int) downloadMenu.getMenuTextReadOffset().getHeight() + 100)
                        );
                        long timeRenderAfter = System.currentTimeMillis();
                        log.debug("Transform menu item time: " + (timeRenderAfter - timeRenderBefore));
//                        final int y = (int) (menuItemY + downloadMenu.getMenuTextReadOffset().getY() - downloadMenu.getMenu().getY());
                        final BufferedImage menuItemLabelCaptureOriginalColor = warped.getSubimage(
                                (int) (downloadMenu.getMenuItem(finalIndex).getX() + downloadMenu.getMenuTextReadOffset().getX()),
                                (int) (downloadMenu.getMenuItem(finalIndex).getY() + downloadMenu.getMenuTextReadOffset().getY()),
                                (int) downloadMenu.getMenuTextReadOffset().getWidth(),
                                (int) downloadMenu.getMenuTextReadOffset().getHeight()
                        );

                        final Mat matColor = CvHelper.convertToMat(menuItemLabelCaptureOriginalColor, null);
                        final Mat matShiftedColor = new Mat(matColor.size(), CvType.CV_8UC4);
                        Map<RGB, Integer> dominantColors = findDominantColors(matColor);
                        RGB dominant = Collections.max(dominantColors.entrySet(), Map.Entry.comparingByValue()).getKey();
                        //remove the dominant color(background) value from each pixel
                        for (int y = 0; y < matColor.rows(); y++) {
                            for (int x = 0; x < matColor.cols(); x++) {
                                double[] bgr = matColor.get(y, x);
                                double b = bgr[0];
                                double g = bgr[1];
                                double r = bgr[2];

                                b = Math.abs(b - dominant.b());
                                g = Math.abs(g - dominant.g());
                                r = Math.abs(r - dominant.r());

                                matShiftedColor.put(y, x, b, g, r, 255.0);

                            }
                        }

                        final BufferedImage menuItemLabelCaptureOriginalShifted = CvHelper.mat2Img(matShiftedColor);
                        matColor.release();
                        matShiftedColor.release();
                        timeRenderBefore = System.currentTimeMillis();
                        String cleaned = imageToString(finalIndex, menuItemLabelCaptureOriginalShifted);
                        final Locale locale = ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "ENGLISH")).getLocale();
                        boolean fuzzy = PreferencesService.getPreference(PreferenceConstants.AR_SEARCH_METHOD, "EXACT").equals("FUZZY");
                        int fuzzyScore = PreferencesService.getPreference(PreferenceConstants.AR_FUZZY_SCORE, 90);
                        OdysseyMaterial odysseyMaterial;
                        try {
                            if (fuzzy) {
                                BoundExtractedResult<OdysseyMaterial> extractedResult = OdysseyMaterial.forLocalizedNameSpaceInsensitiveFuzzy(cleaned, locale);
                                log.info("Fuzzy matched '{}' to '{}' with score {}", cleaned, extractedResult.getReferent(), extractedResult.getScore());
                                if (extractedResult.getScore() >= fuzzyScore) {
                                    odysseyMaterial = extractedResult.getReferent();
                                } else {
                                    throw new IllegalArgumentException("Fuzzy score too low");
                                }
                            } else {
                                odysseyMaterial = OdysseyMaterial.forLocalizedNameSpaceInsensitive(cleaned, locale);
                            }
                            downloadMenu.getDownloadData().put(finalIndex, odysseyMaterial);
                            downloadMenu.getScanned().put(finalIndex, true);
                        } catch (final Exception e) {
                            final Mat normal = CvHelper.convertToMat(menuItemLabelCaptureOriginalShifted, null);
                            final Mat inverted = new Mat();
                            Core.bitwise_not(normal, inverted);
                            final BufferedImage menuItemLabelCaptureInverted = CvHelper.mat2Img(inverted);
                            normal.release();
                            inverted.release();
                            log.debug("Attempt OCR inverted");
                            cleaned = imageToString(finalIndex, menuItemLabelCaptureInverted);
                            try {
                                if (cleaned.isBlank()) {
                                    downloadMenu.getDownloadData().put(finalIndex, Data.UNKNOWN);
                                    downloadMenu.getScanned().put(finalIndex, true);
                                } else {
                                    if (fuzzy) {
                                        BoundExtractedResult<OdysseyMaterial> extractedResult = OdysseyMaterial.forLocalizedNameSpaceInsensitiveFuzzy(cleaned, locale);
                                        log.info("Fuzzy matched '{}' to '{}' with score {}", cleaned, extractedResult.getReferent(), extractedResult.getScore());
                                        if (extractedResult.getScore() >= fuzzyScore) {
                                            odysseyMaterial = extractedResult.getReferent();
                                        } else {
                                            throw new IllegalArgumentException("Fuzzy score too low");
                                        }
                                    } else {
                                        odysseyMaterial = OdysseyMaterial.forLocalizedNameSpaceInsensitive(cleaned, locale);
                                    }
                                    if (odysseyMaterial instanceof Data data && PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "").equals("ENGLISH") && downloadMenu.getDataPortName() != null && !downloadMenu.getDataPortName().equals("UNKNOWN")) {
                                        MaterialTrackingService.registerData(downloadMenu.getDataPortName(), downloadMenu.getType(), data, finalIndex);
                                    }
                                    downloadMenu.getDownloadData().put(finalIndex, odysseyMaterial);
                                    downloadMenu.getScanned().put(finalIndex, true);
                                }

                            } catch (final IllegalArgumentException ex) {
                                log.debug("detected material: UNKNOWN");
                                downloadMenu.getDownloadData().put(finalIndex, Data.UNKNOWN);
                                downloadMenu.getScanned().put(finalIndex, true);
                            }
                        }

                        timeRenderAfter = System.currentTimeMillis();
                        log.debug("OCR menu item time: " + (timeRenderAfter - timeRenderBefore));
                    } catch (final TesseractException e) {
                        log.error("", e);
                        throw new RuntimeException(e);
                    }
                }
            });
            tasks.add(future);
        }
        tasks.forEach(future -> {
            try {
                future.get();
            } catch (final InterruptedException | ExecutionException e) {
                log.error("", e);
                throw new RuntimeException(e);
            }
        });
        final long timeRenderAfterMenu = System.currentTimeMillis();
        log.debug("OCR menu full time: " + (timeRenderAfterMenu - timeRenderBeforeMenu));
    }

    private static String imageToString(final int index, final BufferedImage menuItemLabelCapture) throws TesseractException {
        final String dataCharacterForCurrentARLocale = LocaleService.getDataCharacterForCurrentARLocale();
        OCRService.setCharWhitelist(dataCharacterForCurrentARLocale);
        final String dataCharacterForCurrentLocale = dataCharacterForCurrentARLocale;
        final String dataCharacterForCurrentLocaleWithoutSpace = dataCharacterForCurrentLocale.replace("\s", "");
        final String ocr = OCRService.imageToString(menuItemLabelCapture);
        log.debug("ocr detected " + index + ": " + ocr);
        String cleaned = ocr.replaceAll("[^" + dataCharacterForCurrentLocale + "]", "").replace("\s\s", "\s").trim();
        if (cleaned.matches("^[" + dataCharacterForCurrentLocale + "]*\s[" + dataCharacterForCurrentLocaleWithoutSpace + "]$")) {
            cleaned = cleaned.substring(0, cleaned.length() - 2);
        }
        log.debug("ocr cleaned " + index + ": " + cleaned);
        return cleaned;
    }

    private static String imageToTerminalType(final BufferedImage terminalLabelCapture) throws TesseractException {
        final String dataCharacterForCurrentLocale = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ";
        OCRService.setCharWhitelist(dataCharacterForCurrentLocale);
        final String dataCharacterForCurrentLocaleWithoutSpace = dataCharacterForCurrentLocale.replace("\s", "");
        final String ocr = OCRService.imageToString(terminalLabelCapture);
        log.debug("ocr detected terminal: " + ocr);
        final String cleaned = ocr.replaceAll("[^" + dataCharacterForCurrentLocale + "]", "").replace("\s\s", "\s").trim();
//        if (cleaned.matches("^[" + dataCharacterForCurrentLocale + "]*\s[" + dataCharacterForCurrentLocaleWithoutSpace + "]$")) {
//            cleaned = cleaned.substring(0, cleaned.length() - 2);
//        }
        log.debug("ocr cleaned terminal: " + cleaned);
        return cleaned;
    }

    private static Map<RGB, Integer> findDominantColors(Mat img) {
        Map<RGB, Integer> colorCount = new HashMap<>();

        for (int y = 0; y < img.rows(); y++) {
            for (int x = 0; x < img.cols(); x++) {
                double[] bgr = img.get(y, x);
                int b = (int) bgr[0];
                int g = (int) bgr[1];
                int r = (int) bgr[2];

                RGB key = new RGB(r, g, b);
                colorCount.put(key, colorCount.getOrDefault(key, 0) + 1);
            }
        }
        return colorCount;
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

    private void updateScaling() {
        if (newScaling != scaling) {
            log.debug("application scaling changed");
            log.debug("detected resolution: " + contentWidth + "x" + contentHeight);
            //scaling
            scaling = newScaling;
            Imgproc.resize(arrowTemplate, arrowTemplateScaled, new Size(), scaling, scaling, Imgproc.INTER_AREA);
            WarningHelper.updateScale(scaling);

            ImageTransformHelper.init(getDownloadMenu(), scaling);
        }
    }

    @Override
    public boolean isMenuVisible(WindowInfo targetWindowInfo) {
        BufferedImage arrowCapture = getArrowCapture(targetWindowInfo);
        return isDownloadMenu(arrowCapture, targetWindowInfo);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void analyzeAndRender(WindowInfo targetWindowInfo, Consumer<Image> resultConsumer) {
        if (this.getVisible().get()) {
            downloadMenuCapture = getDownloadMenuCapture(targetWindowInfo);
            final Boolean newHasWarning = WarningHelper.menuHasWarning(downloadMenuCapture, scaling, MATCHING_THRESHOLD);
            final ScrollBarV2 newScrollBar = getScrollBar(downloadMenuCapture, newHasWarning);
            final boolean render = !Objects.equals(newScrollBar, scrollBar) || newHasWarning.equals(hasWarning);//initialRender.get();//only render on change
            scrollBar = newScrollBar;
            hasWarning = newHasWarning;
            if (downloadMenuCapture != null) {
                if (render) {
                    final Render cachedImage = renderCache.get(String.valueOf(hasWarning) + newScrollBar.getPosition());
                    if (cachedImage != null && cachedImage.fullyRendered()) {
                        overlayImage = cachedImage.render();
//                        arOverlay.getResizableImageView().setImage(overlayImage);
                        resultConsumer.accept(overlayImage);
                    } else if (canRenderMore(cachedImage, getDownloadMenu())) {
//                        arOverlay.getResizableImageView().setImage(null);
                        resultConsumer.accept(null);
                        log.debug("render required for Warning: " + getDownloadMenu().isHasWarning() + ". Scrollbar: " + scrollBar.getPosition() + " size:" + scrollBar.getSize());
                        getDownloadMenu(downloadMenuCapture, hasWarning, scrollBar);
                        if (PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "").equals("ENGLISH")) {
                            processMenuType(downloadMenuCapture, getDownloadMenu());
                        }
                        ImageTransformHelper.init(getDownloadMenu(), scaling);
                        final long timeProcessBefore = System.currentTimeMillis();
                        processMenu(downloadMenuCapture, getDownloadMenu());
                        final long timeProcessAfter = System.currentTimeMillis();
                        log.debug("Menu processing time: " + (timeProcessAfter - timeProcessBefore));
                        final long timeRenderBefore = System.currentTimeMillis();
                        renderMenu(getDownloadMenu());
//                        arOverlay.getResizableImageView().setImage(overlayImage);
                        final long timeRenderAfter = System.currentTimeMillis();
                        log.debug("Total render time: " + (timeRenderAfter - timeRenderBefore));
                        log.debug("Total time to screen: " + ((timeProcessAfter - timeProcessBefore) + (timeRenderAfter - timeRenderBefore)));

                        resultConsumer.accept(overlayImage);
                    }
                }
            }
        }


    }

    public void clear() {
        getDownloadMenu().getScanned().clear();
        getDownloadMenu().setType(null);
        getDownloadMenu().setDataPortName(null);
        getDownloadMenu().getDownloadData().clear();
        renderCache.clear();
        overlayImage = null;
        scrollBar = null;
        hasWarning = null;
//            arOverlay.getResizableImageView().setImage(null);
    }

    record RGB(int r, int g, int b) {
    }

}
