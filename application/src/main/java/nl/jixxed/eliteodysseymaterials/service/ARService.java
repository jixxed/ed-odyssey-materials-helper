package nl.jixxed.eliteodysseymaterials.service;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.ar.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;
import nl.jixxed.eliteodysseymaterials.templates.ARDebugOverlay;
import nl.jixxed.eliteodysseymaterials.templates.AROverlay;
import nu.pattern.OpenCV;
import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.Rectangle;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
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

    private static Scaling windowsScaling;

    static {
        OpenCV.loadLocally();
    }

    private static final double MATCHING_THRESHOLD = 0.75;
    private static final String STYLESHEET = "/nl/jixxed/eliteodysseymaterials/style/ar.css";
    //    private static final String ELITE_DANGEROUS_CLIENT_WINDOW_NAME = "Snagit Editor";
    private static final String ELITE_DANGEROUS_CLIENT_WINDOW_NAME = "Elite - Dangerous (CLIENT)";
    private static AROverlay arOverlay;
    private static Scene arScene;
    private static Stage arStage;
    private static ARDebugOverlay arDebugOverlay;
    private static Scene arDebugScene;
    private static Stage arDebugStage;
    private static Mat arrowTemplate;
    private static Mat arrowTemplateScaled;
    private static Mat alertTemplate;
    private static Mat alertTemplateScaled;
    private static Mat arrowCaptureMat;
    private static Mat arrowCaptureMatGray = new Mat();
    private static BufferedImage downloadMenuCapture;
    private static BufferedImage arrowCapture;
    private static Image overlayImage;
    private static final int match_method = Imgproc.TM_CCOEFF_NORMED;
    private static final boolean debugEnabled = false;
    private static final boolean arDebugEnabled = false;
    private static int frameCounter = 0;
    private static double scaling = 0;
    //    private static Point lastDetectedArrowPosition;
    private static final AtomicBoolean menuVisible = new AtomicBoolean(false);
    private static final AtomicBoolean requestShow = new AtomicBoolean(false);

    private static final AtomicBoolean requestHide = new AtomicBoolean(false);
    private static DownloadMenu downloadMenu;
    private static ScrollBar scrollBar;
    private static Boolean hasWarning;
    private static boolean enabled = false;
    private static AnimationTimer animationTimer;
    private static Timer timer;
    private static Timer timerDisplay;
    private static double midRows;
    private static double midCols;
    private static int col_count;
    private static int row_count;
    private static double dx;
    private static double dy;
    private static Size size;

    private static final double extraPixels = 10.0;
    private static Point[][] sourceRows;
    private static Point[][] reverseSourceRows;
    private static Point[][] destinationRows;
    private static WindowInfo targetWindowInfo = new WindowInfo(0, new RECT(), "", 0);
    private static Borders windowBorders;
    private static int foregroundHwnd;
    private static int contentHeight;
    private static int contentWidth;
    private static int contentX;
    private static int contentY;
    private static double newScaling;
    private static Mat alertCaptureMat;
    private static Mat downloadMenuResult;
    private static Mat menuWarningResult;
    private static Mat alertCaptureMatGray;
    private static final Map<String, Render> renderCache = new HashMap<>();
    private static final ScreenshotService screenshotService = NewJNAScreenshotService.getInstance();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

    static {
        EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            if (timer != null) {
                timer.cancel();
                timerDisplay.cancel();
                executorService.shutdown();
                log.info("AR Service shutdown finished.");
            }
        });
    }

    public static void toggle() {
        if (!enabled) {
            enabled = true;
            log.debug("enabling AR Service");

            arrowTemplate = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_download.png")));
            arrowTemplateScaled = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_download.png")));
            alertTemplate = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_alert_small.png")));
            alertTemplateScaled = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_alert_small.png")));
            //greyscale templates
            Imgproc.cvtColor(arrowTemplate, arrowTemplate, Imgproc.COLOR_BGRA2GRAY);
            Imgproc.cvtColor(arrowTemplateScaled, arrowTemplateScaled, Imgproc.COLOR_BGRA2GRAY);
            Imgproc.cvtColor(alertTemplate, alertTemplate, Imgproc.COLOR_BGRA2GRAY);
            Imgproc.cvtColor(alertTemplateScaled, alertTemplateScaled, Imgproc.COLOR_BGRA2GRAY);

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
                    timerDisplay.cancel();
                    executorService.shutdown();
                }
                if (animationTimer != null) {
                    animationTimer.stop();
                }
            });

            //DEBUG
            if (arDebugEnabled) {
                arDebugStage = new Stage();
                arDebugOverlay = new ARDebugOverlay(arDebugStage);
                arDebugScene = new Scene(arDebugOverlay, 640, 480);
                arDebugStage.initModality(Modality.WINDOW_MODAL);
                arDebugStage.initStyle(StageStyle.TRANSPARENT);
                arDebugScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
                arDebugScene.getStylesheets().add(ARService.class.getResource(STYLESHEET).toExternalForm());
                arDebugStage.setScene(arDebugScene);
                arDebugStage.titleProperty().set("AR Debug Overlay");
            }
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
                timer.cancel();
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
                        log.debug("application not running");
                        Thread.sleep(1000);
                        menuVisible.set(false);
                        requestHide.set(true);
                        return;
                    }
                    //hide when game is not on the foreground
                    targetWindowInfo = WindowInfoUtil.getWindowInfo(targetWindowInfo);
                    foregroundHwnd = User32.INSTANCE.GetForegroundWindow();
                    if (foregroundHwnd != targetWindowInfo.hwnd) {

                        log.debug("application not in foreground");
                        menuVisible.set(false);
                        requestHide.set(true);
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
                    windowsScaling = Screen.getScreensForRectangle(new javafx.geometry.Rectangle2D(contentX, contentY, 1, 1)).stream().findFirst().map(screen -> new Scaling(screen.getOutputScaleX(), screen.getOutputScaleY())).orElse(new Scaling(1, 1));

                    newScaling = contentHeight / 1600D;
                    getDownloadMenu().setContentWidth(contentWidth);
                    getDownloadMenu().setContentHeight(contentHeight);
                    getDownloadMenu().setScale(newScaling);
//                                final double newScaling = 1600 / 1600D;
                    if (newScaling != scaling) {
                        log.debug("application scaling changed");
                        log.debug("detected resolution: " + contentWidth + "x" + contentHeight);
                        //scaling
                        scaling = newScaling;
                        Imgproc.resize(arrowTemplate, arrowTemplateScaled, new Size(), scaling, scaling, Imgproc.INTER_AREA);
                        Imgproc.resize(alertTemplate, alertTemplateScaled, new Size(), scaling, scaling, Imgproc.INTER_AREA);
//                        lastDetectedArrowPosition = null;
                    }
                    //test if download image is present
                    final BufferedImage arrowCapture = getArrowCapture();
                    final boolean menuPresent = isDownloadMenu(arrowCapture);
                    boolean pauseThread = false;
                    if (menuPresent) {
                        //show
                        if (!menuVisible.get()) {
                            requestShow.set(true);
                            pauseThread = true;
                        }
                        menuVisible.set(true);
                    } else {
                        //hide
                        if (menuVisible.get()) {
                            requestHide.set(true);
                        }
                        menuVisible.set(false);
                    }
                    if (pauseThread) {
                        Thread.sleep(2000);
                    }
//                        }
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
                    if (menuVisible.get()) {
                        final BufferedImage downloadMenuCapture = getDownloadMenuCapture();
                        final Boolean newHasWarning = menuHasWarning(downloadMenuCapture);
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
                                    log.debug("render from cache. Warning: " + getDownloadMenu().isHasWarning() + ". Scrollbar: " + getDownloadMenu().getScrollBar().getPosition());
                                } else {
                                    arOverlay.getResizableImageView().setImage(null);
                                    log.debug("render required for " + ((hasWarning) ? "warning + " : "no warning + ") + scrollBar.getPosition() + " size:" + scrollBar.getSize());
                                    downloadMenu = getDownloadMenu(hasWarning, scrollBar);
                                    init(getDownloadMenu());
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
                if (frameCounter == 99) {
                    frameCounter = 0;
                } else {
                    frameCounter++;
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
                if (menuVisible.get() && requestShow.get()) {
                    requestShow.set(false);
                    showOverlay();

                } else if (!menuVisible.get() && requestHide.get()) {
                    requestHide.set(false);
                    hideOverlay();
                }
            }

            private static void showOverlay() {
                //94020000 WINDOWED
                //84020000 BORDERLESS
                //B4020000 FULLSCREEN
                if (!arStage.isShowing()) {
//                    log.debug("targetWindowInfo.rect x/y: " + targetWindowInfo.rect.left + "/" + targetWindowInfo.rect.top);
                    positionWindow();
                    arStage.setAlwaysOnTop(true);
                    arStage.show();
//                    log.debug("stage x/y: " + arStage.getX() + "/" + arStage.getY());
                    if (arDebugEnabled) {
                        arDebugStage.setAlwaysOnTop(true);
                        arDebugStage.show();
                    }
                    if (targetWindowInfo.hwnd != 0) {
                        User32.INSTANCE.SetForegroundWindow(targetWindowInfo.hwnd);
                    }
                    log.debug("show");
                }

            }

            private static void hideOverlay() {
                if (arStage.isShowing()) {
                    arOverlay.getResizableImageView().setImage(null);
                    arStage.hide();
                    arStage.setAlwaysOnTop(false);
                    if (arDebugEnabled) {
                        arDebugStage.hide();
                        arDebugStage.setAlwaysOnTop(false);
                    }
                    log.debug("hide");
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

        final WritableRaster dataColorPixel = ((WritableRaster) downloadMenuCapture.getData(new Rectangle((int) (855 * scaling),
                (int) (215 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
        final int colorPixel = ((DataBufferInt) dataColorPixel.getDataBuffer()).getData()[0];

        final int scrollbarX = 1613;
        if (!hasWarning) {//no warning
            //test for scrollbar
            final WritableRaster dataTop = ((WritableRaster) downloadMenuCapture.getData(new Rectangle((int) (scrollbarX * scaling),
                    (int) (300 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final int dataTopPixel = ((DataBufferInt) dataTop.getDataBuffer()).getData()[0];
            if (dataTopPixel == colorPixel) {
                return new ScrollBar(ScrollPosition.UP, 6);
            }
            final WritableRaster dataBottom = ((WritableRaster) downloadMenuCapture.getData(new Rectangle((int) (scrollbarX * scaling),
                    (int) (820 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final int dataBottomPixel = ((DataBufferInt) dataBottom.getDataBuffer()).getData()[0];
            if (dataBottomPixel == colorPixel) {
                return new ScrollBar(ScrollPosition.DOWN, 6);
            }
            return new ScrollBar(ScrollPosition.NONE, 5);
        } else {//warning
            final WritableRaster dataTop = ((WritableRaster) downloadMenuCapture.getData(new Rectangle((int) (scrollbarX * scaling),
                    (int) (365 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final WritableRaster dataTopMiddle = ((WritableRaster) downloadMenuCapture.getData(new Rectangle((int) (scrollbarX * scaling),
                    (int) (387 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final WritableRaster dataMiddle = ((WritableRaster) downloadMenuCapture.getData(new Rectangle((int) (scrollbarX * scaling),
                    (int) (605 * scaling), 1, 1))).createWritableTranslatedChild(0, 0);
            final WritableRaster dataDownMiddle = ((WritableRaster) downloadMenuCapture.getData(new Rectangle((int) (scrollbarX * scaling),
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


    private static void renderMenu(final DownloadMenu downloadMenu) {

        final BufferedImage bufferedImage = new BufferedImage((int) downloadMenu.getMenu().getWidth(), (int) downloadMenu.getMenu().getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics = bufferedImage.createGraphics();
        final AtomicBoolean isFullyRendered = new AtomicBoolean(true);
        for (int index = 1; index <= downloadMenu.menuSize(); index++) {
            final OdysseyMaterial odysseyMaterial = downloadMenu.getDownloadData().get(index);
            if (downloadMenu.isMenuItemVisible(index) && !downloadMenu.isScanned(index)) {
                log.debug("not scanned index:" + index);
                isFullyRendered.set(false);
            }
            if (downloadMenu.isMenuItemVisible(index) && Data.UNKNOWN != odysseyMaterial && odysseyMaterial != null) {
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("Euro Caps", Font.PLAIN, (int) (downloadMenu.getFontSize())));
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                final double menuItemY = downloadMenu.getMenuItemY(index) + downloadMenu.getMenuItemPositionYOffset();

                final int x = (int) (downloadMenu.getMenuItemX(index) + downloadMenu.getMenuTextWriteOffsetX());
                final int y = (int) (menuItemY + downloadMenu.getMenuTextWriteOffsetY());
                final Color color;
                if (MaterialService.isMaterialOnWishlist(odysseyMaterial)) {
                    final javafx.scene.paint.Color preference = javafx.scene.paint.Color.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_WISHLIST_COLOR, javafx.scene.paint.Color.LIME.toString()));
                    color = new Color((float) preference.getRed(),
                            (float) preference.getGreen(),
                            (float) preference.getBlue(),
                            (float) preference.getOpacity());
                } else if (OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(odysseyMaterial)) {
                    final javafx.scene.paint.Color preference = javafx.scene.paint.Color.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_BLUEPRINT_COLOR, javafx.scene.paint.Color.BLUE.toString()));
                    color = new Color((float) preference.getRed(),
                            (float) preference.getGreen(),
                            (float) preference.getBlue(),
                            (float) preference.getOpacity());
                } else {
                    final javafx.scene.paint.Color preference = javafx.scene.paint.Color.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_IRRELEVANT_COLOR, javafx.scene.paint.Color.RED.toString()));
                    color = new Color((float) preference.getRed(),
                            (float) preference.getGreen(),
                            (float) preference.getBlue(),
                            (float) preference.getOpacity());
                }
                if (downloadMenu.isMenuItemLabelVisible(index)) {
                    final String text;
                    final Integer backPackValue = StorageService.getMaterialStorage(odysseyMaterial).getBackPackValue();
                    final String backPackText = backPackValue > 0 ? "(" + backPackValue + ")" : "";
                    if (MaterialService.isMaterialOnWishlist(odysseyMaterial)) {
                        text = "Wishlist - " + StorageService.getMaterialStorage(odysseyMaterial).getTotalValue() + backPackText + "/" + getWishlistCount(odysseyMaterial);
                    } else if (OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(odysseyMaterial)) {
                        text = "Engineer/Blueprint - " + StorageService.getMaterialStorage(odysseyMaterial).getTotalValue() + backPackText;
                    } else {
                        text = "Irrelevant";
                    }
                    final FontMetrics fm = graphics.getFontMetrics();
                    final Rectangle2D rect = fm.getStringBounds(text, graphics);
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(x - fm.getAscent(),
                            y - fm.getAscent(),
                            (int) rect.getWidth() + fm.getAscent() * 2,
                            (int) rect.getHeight());
                    graphics.setColor(color);
                    graphics.drawString(text, x, y);
                }
                graphics.setColor(color);
                graphics.fillRect((int) downloadMenu.getMenuItemX(index), (int) menuItemY, (int) downloadMenu.getMenuItemWidth(index), (int) downloadMenu.getMenuItemHeight(index));
                graphics.drawRect((int) downloadMenu.getMenuItemX(index), (int) menuItemY, (int) downloadMenu.getMenuItemWidth(), (int) downloadMenu.getMenuItemHeight(index));


            }
        }
        graphics.dispose();
        final Mat mat = CvHelper.convertToMat(bufferedImage, null);
        final Mat overlay = Mat.zeros(contentHeight, contentWidth, CvType.CV_32FC4);

        for (int row = 0; row < row_count - 1; row++) {
            for (int col = 0; col < col_count - 1; col++) {
                final Mat submat = mat.submat(
                        (int) Math.max(0, Math.min(reverseSourceRows[row][col].y - extraPixels, reverseSourceRows[row + 1][col].y + extraPixels)),
                        (int) Math.min(contentHeight, Math.max(reverseSourceRows[row][col + 1].y - extraPixels, reverseSourceRows[row + 1][col + 1].y + extraPixels)),
                        (int) Math.max(0, reverseSourceRows[row][col].x - extraPixels),
                        (int) Math.min(contentWidth, reverseSourceRows[row + 1][col + 1].x + extraPixels));
                Imgproc.cvtColor(submat, submat, Imgproc.COLOR_BGRA2GRAY);
                if (Core.countNonZero(submat) == 0) {
                    continue;
                }
                final Mat fromPoints = new Mat(1, 4, CvType.CV_32FC2);
                final Mat toPoints = new Mat(1, 4, CvType.CV_32FC2);
                fromPoints.put(0, 0, new double[]{reverseSourceRows[row][col].x - extraPixels, reverseSourceRows[row][col].y - extraPixels});
                fromPoints.put(0, 1, new double[]{reverseSourceRows[row][col + 1].x + extraPixels, reverseSourceRows[row][col + 1].y - extraPixels});
                fromPoints.put(0, 2, new double[]{reverseSourceRows[row + 1][col].x - extraPixels, reverseSourceRows[row + 1][col].y + extraPixels});
                fromPoints.put(0, 3, new double[]{reverseSourceRows[row + 1][col + 1].x + extraPixels, reverseSourceRows[row + 1][col + 1].y + extraPixels});
                toPoints.put(0, 0, new double[]{0 - extraPixels, 0 - extraPixels});
                toPoints.put(0, 1, new double[]{dx + extraPixels, 0 - extraPixels});
                toPoints.put(0, 2, new double[]{0 - extraPixels, dy + extraPixels});
                toPoints.put(0, 3, new double[]{dx + extraPixels, dy + extraPixels});
                final Mat perspectiveTransform = Imgproc.getPerspectiveTransform(fromPoints, toPoints, Core.DECOMP_LU);
                final Mat dst = new Mat();
                dst.create(size, mat.type());
                Imgproc.warpPerspective(mat, dst, perspectiveTransform, size, Imgproc.INTER_LANCZOS4);
                overlayImage(dst, overlay, new Point((dx * col) + downloadMenu.getMenu().getX(), (dy * row) + downloadMenu.getMenu().getY()));
                perspectiveTransform.release();
                fromPoints.release();
                toPoints.release();
                dst.release();
            }
        }
        final BufferedImage bufferedImage1 = CvHelper.createBufferedImage(overlay);
        mat.release();
        overlay.release();
        final WritableImage overlayImage1 = SwingFXUtils.toFXImage(bufferedImage1, null);
        overlayImage = overlayImage1;
        renderCache.put(String.valueOf(downloadMenu.isHasWarning()) + downloadMenu.getScrollBar().getPosition(), new Render(isFullyRendered.get(), overlayImage1));
    }

    private static Integer getWishlistCount(final OdysseyMaterial odysseyMaterial) {
        return ApplicationState.getInstance().getPreferredCommander().map(commander ->
                ApplicationState.getInstance().getWishlists(commander.getFid()).getSelectedWishlist().getItems().stream()
                        .map(odysseyWishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(odysseyMaterial))
                        .mapToInt(Integer::intValue)
                        .sum()
        ).orElse(0);
    }

    private static boolean menuHasWarning(final BufferedImage downloadMenuCapture) {
        if (downloadMenuCapture == null) {
            return false;
        }
        final BufferedImage alertCapture = downloadMenuCapture.getSubimage(
                (int) (48 * scaling),
                (int) (270 * scaling),
                (int) (140 * scaling),
                (int) (60 * scaling));

        alertCaptureMat = CvHelper.convertToMat(alertCapture, alertCaptureMat);
        final int result_cols = alertCaptureMat.cols() - alertTemplateScaled.cols() + 1;
        final int result_rows = alertCaptureMat.rows() - alertTemplateScaled.rows() + 1;
        if (menuWarningResult == null || menuWarningResult.cols() != result_cols || menuWarningResult.rows() != result_rows) {
            if (menuWarningResult != null) {
                menuWarningResult.release();
            }
            menuWarningResult = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        }
        if (alertCaptureMatGray == null || alertCaptureMat.cols() != alertCaptureMatGray.cols() || alertCaptureMat.rows() != alertCaptureMatGray.rows()) {
            if (alertCaptureMatGray != null) {
                alertCaptureMatGray.release();
            }
            alertCaptureMatGray = new Mat();
        }

        //grayscale capture
        Imgproc.cvtColor(alertCaptureMat, alertCaptureMatGray, Imgproc.COLOR_BGRA2GRAY);

        //matching
        Imgproc.matchTemplate(alertCaptureMatGray, alertTemplateScaled, menuWarningResult, match_method);
        final Core.MinMaxLocResult mmr = Core.minMaxLoc(menuWarningResult);

        if (mmr.maxVal > getMatchingThreshold()) {
            return true;
        }
        return false;
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
                        final BufferedImage warped = getTransformedSectionImage(downloadMenuCapture, new Rectangle(
                                (int) (downloadMenu.getMenuItem(finalIndex).getX() + downloadMenu.getMenuTextReadOffset().getX() - 10),
                                (int) (menuItemY + downloadMenu.getMenuTextReadOffset().getY() - 50),
                                (int) downloadMenu.getMenuTextReadOffset().getWidth() + 20,
                                (int) downloadMenu.getMenuTextReadOffset().getHeight() + 100)
                        );
                        long timeRenderAfter = System.currentTimeMillis();
                        log.debug("Transform menu item time: " + (timeRenderAfter - timeRenderBefore));
                        if (arDebugEnabled) {
                            arDebugOverlay.getResizableImageView(finalIndex).setImage(SwingFXUtils.toFXImage(warped, null));
                        }
                        final int y = (int) (menuItemY + downloadMenu.getMenuTextReadOffset().getY() - downloadMenu.getMenu().getY());
                        final BufferedImage menuItemLabelCaptureOriginal = warped.getSubimage(
                                (int) (downloadMenu.getMenuItem(finalIndex).getX() + downloadMenu.getMenuTextReadOffset().getX()),
                                (int) (downloadMenu.getMenuItem(finalIndex).getY() + downloadMenu.getMenuTextReadOffset().getY() + downloadMenu.getMenuItemPositionYOffset()),
                                (int) downloadMenu.getMenuTextReadOffset().getWidth(),
                                (int) downloadMenu.getMenuTextReadOffset().getHeight()
                        );

                        timeRenderBefore = System.currentTimeMillis();
                        String cleaned = imageToString(finalIndex, menuItemLabelCaptureOriginal);
                        try {
                            OdysseyMaterial.forLocalizedName(cleaned);
                        } catch (final Exception e) {
                            final Mat normal = CvHelper.convertToMat(menuItemLabelCaptureOriginal, null);
                            final Mat inverted = new Mat();
                            Core.bitwise_not(normal, inverted);
                            final BufferedImage menuItemLabelCaptureInverted = CvHelper.mat2Img(inverted);
                            normal.release();
                            inverted.release();
                            cleaned = imageToString(finalIndex, menuItemLabelCaptureInverted);
                            if (arDebugEnabled) {
                                arDebugOverlay.getResizableImageView(finalIndex).setImage(SwingFXUtils.toFXImage(menuItemLabelCaptureInverted, null));
                            }

                        }
                        try {
                            if (cleaned.isBlank()) {
                                downloadMenu.getDownloadData().put(finalIndex, Data.UNKNOWN);
                                downloadMenu.getScanned().put(finalIndex, true);
                            } else {
                                final OdysseyMaterial odysseyMaterial = OdysseyMaterial.forLocalizedName(cleaned);
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

    private static BufferedImage getTransformedSectionImage(final BufferedImage downloadMenuCapture, final Rectangle requestedArea) {
        final Mat src = CvHelper.convertToMat(downloadMenuCapture, null);
        final Mat result = Mat.zeros(src.rows(), src.cols(), CvType.CV_32FC4);
        for (int row = 0; row < row_count - 1; row++) {
            for (int col = 0; col < col_count - 1; col++) {

                final Rectangle section = new Rectangle((int) (dx * col), (int) (dy * row), (int) size.width, (int) size.height);

                if (!section.intersects(requestedArea)) {
                    continue;
                }
                final Mat fromPoints = new Mat(1, 4, CvType.CV_32FC2);
                final Mat toPoints = new Mat(1, 4, CvType.CV_32FC2);
                fromPoints.put(0, 0, new double[]{sourceRows[row][col].x - extraPixels, sourceRows[row][col].y - extraPixels});
                fromPoints.put(0, 1, new double[]{sourceRows[row][col + 1].x + extraPixels, sourceRows[row][col + 1].y - extraPixels});
                fromPoints.put(0, 2, new double[]{sourceRows[row + 1][col].x - extraPixels, sourceRows[row + 1][col].y + extraPixels});
                fromPoints.put(0, 3, new double[]{sourceRows[row + 1][col + 1].x + extraPixels, sourceRows[row + 1][col + 1].y + extraPixels});
                toPoints.put(0, 0, new double[]{0 - extraPixels, 0 - extraPixels});
                toPoints.put(0, 1, new double[]{dx + extraPixels, 0 - extraPixels});
                toPoints.put(0, 2, new double[]{0 - extraPixels, dy + extraPixels});
                toPoints.put(0, 3, new double[]{dx + extraPixels, dy + extraPixels});
                final Mat perspectiveTransform = Imgproc.getPerspectiveTransform(fromPoints, toPoints, Core.DECOMP_LU);
                final Mat dst = new Mat();
                dst.create(size, src.type());

                Imgproc.warpPerspective(src, dst, perspectiveTransform, size, Imgproc.INTER_LANCZOS4);
                overlayImage(dst, result, new Point((dx * col), (dy * row)));
                perspectiveTransform.release();
                fromPoints.release();
                toPoints.release();
                dst.release();
            }
        }
        final BufferedImage bufferedImage = CvHelper.createBufferedImage(result);
        src.release();
        result.release();
        return bufferedImage;
    }


    private static BufferedImage getDownloadMenuCapture() throws AWTException {
        if (targetWindowInfo.hwnd != 0) {
            final int i = User32.INSTANCE.GetForegroundWindow();
            if (i == targetWindowInfo.hwnd) {
                return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getDownloadMenu().getMenu().getAwtRectangle(), downloadMenuCapture);
            }
        }
        return null;
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

    private static DownloadMenu getDownloadMenu() {
        if (downloadMenu == null) {
            downloadMenu = new DownloadMenu(scaling, Boolean.TRUE.equals(hasWarning), scrollBar, contentWidth, contentHeight);
        }
        return downloadMenu;
    }


    private static BufferedImage getArrowCapture() throws AWTException {
        if (targetWindowInfo.hwnd != 0) {
            final int i = User32.INSTANCE.GetForegroundWindow();
            if (i == targetWindowInfo.hwnd) {
                //scale
                return screenshotService.getScreenshot(new java.awt.Point(contentX, contentY), getDownloadMenu().getArrow().getAwtRectangle(), arrowCapture);

            }
        }
        return null;
    }


    private static boolean isDownloadMenu(final BufferedImage arrowCapture) {
        if (arrowCapture == null) {
            return false;
        }
        arrowCaptureMat = CvHelper.convertToMat(arrowCapture, arrowCaptureMat);
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
        Imgproc.matchTemplate(arrowCaptureMatGray, arrowTemplateScaled, downloadMenuResult, match_method);
        final Core.MinMaxLocResult mmr = Core.minMaxLoc(downloadMenuResult);


        if (mmr.maxVal > getMatchingThreshold()) {
            if (frameCounter % 99 == 1) {
                log.debug("dataport downloadmenu detected. Confidence(" + getMatchingThreshold() + "): " + mmr.maxVal);
            }
            return true;
        }
        if (frameCounter % 99 == 1) {
            log.debug("dataport downloadmenu test confidence(" + getMatchingThreshold() + "): " + mmr.maxVal);
        }
//        lastDetectedArrowPosition = null;//TODO REMOVE
        return false;
    }

    private static double getMatchingThreshold() {
        return MATCHING_THRESHOLD;
    }

    private static String imageToString(final int index, final BufferedImage menuItemLabelCapture) throws TesseractException {

        final String ocr = OCRService.imageToString(menuItemLabelCapture);
        if (debugEnabled) {
//            log.debug("menuitem " + index + ": " + ocr);
        }
        String cleaned = ocr.replaceAll("[^a-zA-Z-\s]", "").replaceAll("\s\s", "\s").trim();
        if (cleaned.matches("^.*\s[a-zA-Z]$")) {
            cleaned = cleaned.substring(0, cleaned.length() - 2);
        }
//        if (debugEnabled) {
        log.debug("ocr cleaned " + index + ": " + cleaned);
//        }
        return cleaned;
    }

    private static void init(final DownloadMenu downloadMenu) {
        midRows = (double) downloadMenu.getMenu().getHeight() / 2;
        midCols = (double) downloadMenu.getMenu().getWidth() / 2;
        col_count = (int) (downloadMenu.getMenu().getWidth() / 50);
        row_count = (int) (downloadMenu.getMenu().getHeight() / 50);
        dx = downloadMenu.getMenu().getWidth() / (col_count - 1);
        dy = downloadMenu.getMenu().getHeight() / (row_count - 1);
        size = new Size(dx + extraPixels * 2, dy + extraPixels * 2);
        sourceRows = new Point[row_count][];
        reverseSourceRows = new Point[row_count][];
        for (int row = 0; row < row_count; row++) {
            final Point[] sourceRow = new Point[col_count];
            final Point[] reverseSourceRow = new Point[col_count];
            for (int col = 0; col < col_count; col++) {
                final double x = (int) (dx * col) - extraPixels;
                final double ellipse_offset = 120 * scaling;
                final int a = Math.abs((int) (ellipse_offset * (row * dy - midRows) / midRows));
                final int b = (int) downloadMenu.getMenu().getWidth();
                final double y;
                final double yReverse;
                if (row * dy > midRows) {
                    y = ((row * dy - Math.sqrt(Math.pow(a, 2) * (1 - Math.pow(x - midCols, 2) / Math.pow(b, 2)))) + ellipse_offset * (row * dy - midRows) / midRows) - extraPixels;
                    yReverse = ((row * dy + Math.sqrt(Math.pow(a, 2) * (1 - Math.pow(x - midCols, 2) / Math.pow(b, 2)))) - ellipse_offset * (row * dy - midRows) / midRows) - extraPixels;

                } else {
                    y = ((row * dy + Math.sqrt(Math.pow(a, 2) * (1 - Math.pow(x - midCols, 2) / Math.pow(b, 2)))) + ellipse_offset * (row * dy - midRows) / midRows) - extraPixels;
                    yReverse = ((row * dy - Math.sqrt(Math.pow(a, 2) * (1 - Math.pow(x - midCols, 2) / Math.pow(b, 2)))) - ellipse_offset * (row * dy - midRows) / midRows) - extraPixels;
                }
                sourceRow[col] = new Point(x, y);
                reverseSourceRow[col] = new Point(x, yReverse);
            }
            sourceRows[row] = sourceRow;
            reverseSourceRows[row] = reverseSourceRow;
        }

        destinationRows = new Point[row_count][];
        for (int row = 0; row < row_count; row++) {
            final Point[] destinationRow = new Point[col_count];
            for (int col = 0; col < col_count; col++) {
                destinationRow[col] = new Point((dx * col), (dy * row));
            }
            destinationRows[row] = destinationRow;
        }
    }

    private static void overlayImage(final Mat overlay, final Mat output, final Point location) {
        for (int y = (int) Math.max(location.y, 0); y < output.rows(); ++y) {
            final int overlayY = (int) (y - location.y + extraPixels);
            if (overlayY >= overlay.rows() - extraPixels) {
                break;
            }
            for (int x = (int) Math.max(location.x, 0); x < output.cols(); ++x) {
                final int overlayX = (int) (x - location.x + extraPixels);
                if (overlayX >= overlay.cols() - extraPixels) {
                    break;
                }
                final double[] finalPixelValue = new double[4];
                finalPixelValue[0] = overlay.get(overlayY, overlayX)[0];
                finalPixelValue[1] = overlay.get(overlayY, overlayX)[1];
                finalPixelValue[2] = overlay.get(overlayY, overlayX)[2];
                finalPixelValue[3] = overlay.get(overlayY, overlayX)[3];
                output.put(y, x, finalPixelValue);
            }
        }
    }
}
