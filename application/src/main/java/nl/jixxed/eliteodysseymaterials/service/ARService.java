package nl.jixxed.eliteodysseymaterials.service;

import com.sun.jna.NativeLibrary;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.ar.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;
import nl.jixxed.eliteodysseymaterials.templates.overlay.ar.AROverlay;
import nl.jixxed.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ARService {

    static {
        OpenCV.loadLocally();
    }

    private static final double MATCHING_THRESHOLD = 0.75;
    private static final String STYLESHEET = "/nl/jixxed/eliteodysseymaterials/style/ar.css";
    private static final String ELITE_DANGEROUS_CLIENT_WINDOW_NAME = "Elite - Dangerous (CLIENT)";
    private static AROverlay arOverlay;
    private static Scene arScene;
    private static Stage arStage;
    private static Mat arrowTemplate;
    private static Mat arrowTemplateScaled;
    private static Mat arrowCaptureMat;
    private static Mat arrowCaptureMatGray = new Mat();
    private static BufferedImage downloadMenuCapture;
    private static BufferedImage arrowCapture;
    private static Image overlayImage;
    private static final int MATCH_METHOD = Imgproc.TM_CCOEFF_NORMED;
    private static double scaling = 0;
    private static final AtomicBoolean MENU_VISIBLE = new AtomicBoolean(false);
    private static final AtomicBoolean REQUEST_SHOW = new AtomicBoolean(false);
    private static final AtomicBoolean REQUEST_HIDE = new AtomicBoolean(false);
    private static DownloadMenu downloadMenu;
    private static ScrollBar scrollBar;
    private static Boolean hasWarning;
    private static boolean enabled = false;
    private static AnimationTimer animationTimer;
    private static Timer timer;
    private static Timer timerDisplay;
    private static WindowInfo targetWindowInfo = new WindowInfo(0, new RECT(), "", 0);
    private static Borders windowBorders;
    private static int foregroundHwnd;
    private static int contentHeight;
    private static int contentWidth;
    private static int contentX;
    private static int contentY;
    private static double newScaling;
    private static Mat downloadMenuResult;
    private static final Map<String, Render> renderCache = new HashMap<>();
    private static final ScreenshotService screenshotService = GDIScreenshotService.getInstance();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

    static {
        EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            if (timer != null) {
                timer.cancel();
                timerDisplay.cancel();
                executorService.shutdownNow();
                animationTimer.stop();
                log.info("AR Service shutdown finished.");
            }
        });

        EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
                    NativeLibrary.getInstance("user32").dispose();
                    NativeLibrary.getInstance("gdi32").dispose();

                }
        );
    }

    public static void toggle() {
        if (!enabled) {
            enabled = true;
            log.debug("enabling AR Service");

            arrowTemplate = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_download.png")));
            arrowTemplateScaled = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_download.png")));
            //greyscale templates
            Imgproc.cvtColor(arrowTemplate, arrowTemplate, Imgproc.COLOR_BGRA2GRAY);
            Imgproc.cvtColor(arrowTemplateScaled, arrowTemplateScaled, Imgproc.COLOR_BGRA2GRAY);

            arStage = new Stage();
            arOverlay = new AROverlay(arStage);
            arScene = new Scene(arOverlay, 640, 480);
            arStage.initModality(Modality.WINDOW_MODAL);
            arStage.initStyle(StageStyle.TRANSPARENT);
            arScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            arScene.getStylesheets().add(ARService.class.getResource(STYLESHEET).toExternalForm());
            arStage.setScene(arScene);
            arStage.titleProperty().set("AR Overlay");
            setupTimerDisplay();
            setupTimerAnalyzeAndRender();

            arStage.setOnCloseRequest(event -> {
                if (timer != null) {
                    timer.cancel();
                }
                if (timerDisplay != null) {
                    timerDisplay.cancel();
                }
                executorService.shutdown();
                if (animationTimer != null) {
                    animationTimer.stop();
                }
            });

            setupAnimationTimer();
        } else {
            enabled = false;
            log.debug("disabling AR Service");
            if (animationTimer != null) {
                animationTimer.stop();
                animationTimer = null;

            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (timerDisplay != null) {
                timerDisplay.cancel();
                timer = null;
            }
        }
    }

    private static void setupTimerDisplay() {
        final TimerTask timerDisplayTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    //determine the window handle if it is unknown
                    if (targetWindowInfo.hwnd == 0 || !WindowInfoUtil.isWindow(targetWindowInfo.hwnd)) {
                        targetWindowInfo.hwnd = WindowInfoUtil.findWindow(windowTitle -> windowTitle.contains(ELITE_DANGEROUS_CLIENT_WINDOW_NAME));
                    }
                    if (targetWindowInfo.hwnd == 0) {
                        //application not running
//                        log.debug("application not running");
                        Thread.sleep(1000);
                        MENU_VISIBLE.set(false);
                        REQUEST_HIDE.set(true);
                        return;
                    }
                    //hide when game is not on the foreground
                    targetWindowInfo = WindowInfoUtil.getWindowInfo(targetWindowInfo);
                    foregroundHwnd = User32.INSTANCE.GetForegroundWindow();
                    if (foregroundHwnd != targetWindowInfo.hwnd) {

//                        log.debug("application not in foreground");
                        MENU_VISIBLE.set(false);
                        REQUEST_HIDE.set(true);
                        Thread.sleep(1000);
                        return;
                    }
                    //determine scaling used for further processing
                    final RECT rect = targetWindowInfo.rect;
                    contentWidth = rect.right - rect.left;
                    contentHeight = rect.bottom - rect.top;
                    if (windowBorders == null) {
                        windowBorders = WindowInfoUtil.getWindowBorders(targetWindowInfo.hwnd);
                    }
                    contentX = targetWindowInfo.rect.left;
                    contentY = targetWindowInfo.rect.top;

                    newScaling = contentHeight / 1600D;
                    final DownloadMenu downloadMenu1 = getDownloadMenu();
                    downloadMenu1.setContentWidth(contentWidth);
                    downloadMenu1.setContentHeight(contentHeight);
                    downloadMenu1.setScale(newScaling);
                    if (newScaling != scaling) {
                        log.debug("application scaling changed");
                        log.debug("detected resolution: " + contentWidth + "x" + contentHeight);
                        //scaling
                        scaling = newScaling;
                        Imgproc.resize(arrowTemplate, arrowTemplateScaled, new Size(), scaling, scaling, Imgproc.INTER_AREA);
                        WarningHelper.updateScale(scaling);

                        ImageTransformHelper.init(downloadMenu1, scaling);
                    }
                    //test if download image is present
                    arrowCapture = getArrowCapture();
                    final boolean menuPresent = isDownloadMenu(arrowCapture);
                    boolean pauseThread = false;
                    if (menuPresent) {
                        //show
                        if (!MENU_VISIBLE.get()) {
                            REQUEST_SHOW.set(true);
                            pauseThread = true;
                        }
                        MENU_VISIBLE.set(true);
                    } else {
                        //hide
                        if (MENU_VISIBLE.get()) {
                            REQUEST_HIDE.set(true);
                        }
                        MENU_VISIBLE.set(false);
                    }
                    if (pauseThread) {
                        Thread.sleep(2000);
                    }
                } catch (final Exception e) {
                    log.error("error", e);
                }
            }
        };
        timerDisplay = new Timer();
        timerDisplay.scheduleAtFixedRate(timerDisplayTask, 0, 50);//100fps
    }

    private static void setupTimerAnalyzeAndRender() {
        final TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                try {
                    if (MENU_VISIBLE.get()) {
                        downloadMenuCapture = getDownloadMenuCapture();
                        final Boolean newHasWarning = WarningHelper.menuHasWarning(downloadMenuCapture, scaling, MATCHING_THRESHOLD);
                        final ScrollBar newScrollBar = getScrollBar(downloadMenuCapture, newHasWarning);
                        final boolean render = !Objects.equals(newScrollBar, scrollBar) || newHasWarning.equals(hasWarning);//initialRender.get();//only render on change
                        scrollBar = newScrollBar;
                        hasWarning = newHasWarning;
                        if (downloadMenuCapture != null) {
                            if (render) {
                                final Render cachedImage = renderCache.get(String.valueOf(hasWarning) + newScrollBar.getPosition());
                                if (cachedImage != null && cachedImage.fullyRendered()) {
                                    overlayImage = cachedImage.render();
                                    arOverlay.getResizableImageView().setImage(overlayImage);
//                                    log.debug("render from cache. Warning: " + getDownloadMenu().isHasWarning() + ". Scrollbar: " + getDownloadMenu().getScrollBar().getPosition());
                                } else {
                                    arOverlay.getResizableImageView().setImage(null);
                                    log.debug("render required for Warning: " + getDownloadMenu().isHasWarning() + ". Scrollbar: " + scrollBar.getPosition() + " size:" + scrollBar.getSize());
                                    downloadMenu = getDownloadMenu(hasWarning, scrollBar);
                                    ImageTransformHelper.init(getDownloadMenu(), scaling);
                                    final long timeProcessBefore = System.currentTimeMillis();
                                    processMenu(downloadMenuCapture, getDownloadMenu());
                                    final long timeProcessAfter = System.currentTimeMillis();
                                    log.debug("Menu processing time: " + (timeProcessAfter - timeProcessBefore));
                                    final long timeRenderBefore = System.currentTimeMillis();
                                    renderMenu(getDownloadMenu());
                                    arOverlay.getResizableImageView().setImage(overlayImage);
                                    final long timeRenderAfter = System.currentTimeMillis();
                                    log.debug("Total render time: " + (timeRenderAfter - timeRenderBefore));
                                    log.debug("Total time to screen: " + ((timeProcessAfter - timeProcessBefore) + (timeRenderAfter - timeRenderBefore)));
                                }
                            }
                        }
                    } else {
                        getDownloadMenu().getScanned().clear();
                        getDownloadMenu().getDownloadData().clear();
                        renderCache.clear();
                        scrollBar = null;
                        hasWarning = null;
                        arOverlay.getResizableImageView().setImage(null);
                    }
                } catch (final Exception e) {
                    log.error("error", e);
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 50);//100fps
    }

    private static void setupAnimationTimer() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                if (arStage.isShowing()) {
                    positionWindow();
                }
                if (MENU_VISIBLE.get() && REQUEST_SHOW.get()) {
                    REQUEST_SHOW.set(false);
                    showOverlay();

                } else if (!MENU_VISIBLE.get() && REQUEST_HIDE.get()) {
                    REQUEST_HIDE.set(false);
                    hideOverlay();
                }
            }

            private static void showOverlay() {
                if (!arStage.isShowing()) {
                    positionWindow();
                    arStage.setAlwaysOnTop(true);
                    arStage.show();

                    if (targetWindowInfo.hwnd != 0) {
                        User32.INSTANCE.SetForegroundWindow(targetWindowInfo.hwnd);
                    }
                    log.debug("show overlay");
                }

            }

            private static void hideOverlay() {
                if (arStage.isShowing()) {
                    arOverlay.getResizableImageView().setImage(null);
                    arStage.hide();
                    arStage.setAlwaysOnTop(false);
                    log.debug("hide overlay");
                    System.gc();
                }
            }

            private static void positionWindow() {
                final nl.jixxed.eliteodysseymaterials.service.ar.Rectangle scaledRectForRealRect = ScreenHelper.getScaledRectForRealRect(contentX, contentY, contentX + contentWidth, contentY + contentHeight);
                if (scaledRectForRealRect != null) {
                    arStage.setX(scaledRectForRealRect.getX());
                    arStage.setY(scaledRectForRealRect.getY());
                    arStage.setHeight(scaledRectForRealRect.getHeight());
                    arStage.setWidth(scaledRectForRealRect.getWidth());
                }
            }
        };
        animationTimer.start();
    }

    private static ScrollBar getScrollBar(final BufferedImage downloadMenuCapture, final boolean hasWarning) {
        if (downloadMenuCapture == null) {
            return scrollBar;
        }
        return ScrollBarHelper.getScrollBar(downloadMenuCapture, hasWarning, scaling);
    }

    private static void renderMenu(final DownloadMenu downloadMenu) {
        final BufferedImage bufferedImage = MenuOverlayRenderer.renderMenu(downloadMenu);
        final AtomicBoolean isFullyRendered = new AtomicBoolean(true);
        for (int index = 1; index <= downloadMenu.menuSize(); index++) {
            if (downloadMenu.isMenuItemVisible(index) && !downloadMenu.isScanned(index)) {
                log.debug("not scanned index:" + index);
                isFullyRendered.set(false);
            }
        }
        overlayImage = SwingFXUtils.toFXImage(bufferedImage, null);
        renderCache.put(String.valueOf(downloadMenu.isHasWarning()) + downloadMenu.getScrollBar().getPosition(), new Render(isFullyRendered.get(), overlayImage));
    }

    private static void processMenu(final BufferedImage downloadMenuCapture, final DownloadMenu downloadMenu) {

        final long timeRenderBeforeMenu = System.currentTimeMillis();
        final List<Future> tasks = new ArrayList<>();
        for (int index = 1; index <= downloadMenu.menuSize(); index++) {
            final int finalIndex = index;
            final Future<?> future = executorService.submit(() -> {
                if (Boolean.TRUE.equals(!downloadMenu.getScanned().getOrDefault(finalIndex, false)) && downloadMenu.isMenuItemVisibleForOCR(finalIndex)) {
                    try {
                        final double menuItemY = downloadMenu.getMenuItem(finalIndex).getY() + downloadMenu.getMenuItemPositionYOffset();

                        long timeRenderBefore = System.currentTimeMillis();
                        final BufferedImage warped = ImageTransformHelper.transformForSelection(downloadMenuCapture, new Rectangle(
                                (int) (downloadMenu.getMenuItem(finalIndex).getX() + downloadMenu.getMenuTextReadOffset().getX() - 10),
                                (int) (menuItemY + downloadMenu.getMenuTextReadOffset().getY() - 50),
                                (int) downloadMenu.getMenuTextReadOffset().getWidth() + 20,
                                (int) downloadMenu.getMenuTextReadOffset().getHeight() + 100)
                        );
                        long timeRenderAfter = System.currentTimeMillis();
                        log.debug("Transform menu item time: " + (timeRenderAfter - timeRenderBefore));
                        final int y = (int) (menuItemY + downloadMenu.getMenuTextReadOffset().getY() - downloadMenu.getMenu().getY());
                        final BufferedImage menuItemLabelCaptureOriginalColor = warped.getSubimage(
                                (int) (downloadMenu.getMenuItem(finalIndex).getX() + downloadMenu.getMenuTextReadOffset().getX()),
                                (int) (downloadMenu.getMenuItem(finalIndex).getY() + downloadMenu.getMenuTextReadOffset().getY() + downloadMenu.getMenuItemPositionYOffset()),
                                (int) downloadMenu.getMenuTextReadOffset().getWidth(),
                                (int) downloadMenu.getMenuTextReadOffset().getHeight()
                        );

                        final Mat matColor = CvHelper.convertToMat(menuItemLabelCaptureOriginalColor, null);
                        final Mat matGray = new Mat(matColor.size(), CvType.CV_8UC1);
                        Imgproc.cvtColor(matColor, matGray, Imgproc.COLOR_RGB2GRAY);
                        final BufferedImage menuItemLabelCaptureOriginalGray = CvHelper.mat2Img(matGray);
                        matColor.release();
                        matGray.release();
                        timeRenderBefore = System.currentTimeMillis();
                        String cleaned = imageToString(finalIndex, menuItemLabelCaptureOriginalGray);
                        final Locale locale = ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "ENGLISH")).getLocale();
                        try {
                            OdysseyMaterial.forLocalizedName(cleaned, locale);
                        } catch (final Exception e) {
                            final Mat normal = CvHelper.convertToMat(menuItemLabelCaptureOriginalGray, null);
                            final Mat inverted = new Mat();
                            Core.bitwise_not(normal, inverted);
                            final BufferedImage menuItemLabelCaptureInverted = CvHelper.mat2Img(inverted);
                            normal.release();
                            inverted.release();
                            log.debug("Attempt OCR inverted");
                            cleaned = imageToString(finalIndex, menuItemLabelCaptureInverted);

                        }
                        try {
                            if (cleaned.isBlank()) {
                                downloadMenu.getDownloadData().put(finalIndex, Data.UNKNOWN);
                                downloadMenu.getScanned().put(finalIndex, true);
                            } else {
                                final OdysseyMaterial odysseyMaterial = OdysseyMaterial.forLocalizedName(cleaned, locale);
                                downloadMenu.getDownloadData().put(finalIndex, odysseyMaterial);
                                downloadMenu.getScanned().put(finalIndex, true);
                            }

                        } catch (final IllegalArgumentException ex) {
                            log.debug("detected material: UNKNOWN");
                            downloadMenu.getDownloadData().put(finalIndex, Data.UNKNOWN);
                            downloadMenu.getScanned().put(finalIndex, true);
                        }

                        timeRenderAfter = System.currentTimeMillis();
                        log.debug("OCR menu item time: " + (timeRenderAfter - timeRenderBefore));
                    } catch (final TesseractException e) {
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
                throw new RuntimeException(e);
            }
        });
        final long timeRenderAfterMenu = System.currentTimeMillis();
        log.debug("OCR menu full time: " + (timeRenderAfterMenu - timeRenderBeforeMenu));
    }

    private static void makeGray(final BufferedImage img) {
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                final int rgb = img.getRGB(x, y);
                final int r = (rgb >> 16) & 0xFF;
                final int g = (rgb >> 8) & 0xFF;
                final int b = (rgb & 0xFF);

                // Normalize and gamma correct:
                final double rr = Math.pow(r / 255.0, 2.2);
                final double gg = Math.pow(g / 255.0, 2.2);
                final double bb = Math.pow(b / 255.0, 2.2);

                // Calculate luminance:
                final double lum = 0.2126 * rr + 0.7152 * gg + 0.0722 * bb;

                // Gamma compand and rescale to byte range:
                final int grayLevel = (int) (255.0 * Math.pow(lum, 1.0 / 2.2));
                final int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                img.setRGB(x, y, gray);
            }
        }
    }

    private static BufferedImage getDownloadMenuCapture() {
        if (targetWindowInfo.hwnd != 0) {
            final int i = User32.INSTANCE.GetForegroundWindow();
            if (i == targetWindowInfo.hwnd) {
                return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getDownloadMenu().getMenu().getAwtRectangle(), downloadMenuCapture);
            }
        }
        return null;
    }

    private static BufferedImage getArrowCapture() {
        if (targetWindowInfo.hwnd != 0 && User32.INSTANCE.GetForegroundWindow() == targetWindowInfo.hwnd) {
            return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getDownloadMenu().getArrow().getAwtRectangle(), arrowCapture);
        }
        return null;
    }

    private static DownloadMenu getDownloadMenu() {
        if (downloadMenu == null) {
            downloadMenu = new DownloadMenu(scaling, Boolean.TRUE.equals(hasWarning), scrollBar, contentWidth, contentHeight);
        }
        return downloadMenu;
    }

    private static DownloadMenu getDownloadMenu(final boolean hasWarning, final ScrollBar scrollBar) {
        if (downloadMenu == null) {
            downloadMenu = new DownloadMenu(scaling, hasWarning, scrollBar, contentWidth, contentHeight);
        } else {
            downloadMenu.setScale(scaling);
            downloadMenu.setContentHeight(contentHeight);
            downloadMenu.setContentWidth(contentWidth);
            downloadMenu.setHasWarning(hasWarning);
            downloadMenu.setScrollBar(scrollBar);
        }
        return downloadMenu;
    }

    private static boolean isDownloadMenu(final BufferedImage capture) {
        if (capture == null) {
            return false;
        }
        arrowCaptureMat = CvHelper.convertToMat(capture, arrowCaptureMat);
        final int result_cols = arrowCaptureMat.cols() - arrowTemplateScaled.cols() + 1;
        final int result_rows = arrowCaptureMat.rows() - arrowTemplateScaled.rows() + 1;
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
            if (!previousMatch) {
                previousMatch = true;
                log.debug("dataport downloadmenu detected. Confidence(" + getMatchingThreshold() + "): " + mmr.maxVal);
            }
            return true;
        }
        if (previousMatch) {

            previousMatch = false;
            log.debug("dataport downloadmenu detected. Confidence(" + getMatchingThreshold() + "): " + mmr.maxVal);
            arrowCapture = getArrowCapture();
            return isDownloadMenu(arrowCapture);
        }
        return false;
    }

    private static boolean previousMatch = false;

    private static double getMatchingThreshold() {
        return MATCHING_THRESHOLD;
    }

    private static String imageToString(final int index, final BufferedImage menuItemLabelCapture) throws TesseractException {
        final String dataCharacterForCurrentLocale = LocaleService.getDataCharacterForCurrentARLocale();
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


}
