/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service;

import com.sun.jna.NativeLibrary;
import com.sun.jna.platform.win32.WinUser;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.ar.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;
import nl.jixxed.eliteodysseymaterials.templates.overlay.ar.AROverlay;
import nu.pattern.OpenCV;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ARService {

    private static final String STYLESHEET = "/css/ar.css";
    private static final String ELITE_DANGEROUS_CLIENT_WINDOW_NAME = "Elite - Dangerous (CLIENT)";
//    private static final String ELITE_DANGEROUS_CLIENT_WINDOW_NAME = "Elite Simulator";
    private static final AtomicBoolean REQUEST_SHOW = new AtomicBoolean(false);
    private static final AtomicBoolean REQUEST_HIDE = new AtomicBoolean(false);
    private static final AtomicBoolean FORCE_VISIBLE = new AtomicBoolean(false);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    public static boolean debug = false;
    private static AROverlay arOverlay;
    private static Stage arStage;
    private static final List<ARMenu> arMenus;
    private static boolean enabled = false;
    private static AnimationTimer windowVisibilityAndPositionTimer;
    private static Timer ocrAndRenderTimer;
    private static Timer gameStateTimer;
    private static WindowInfo targetWindowInfo = new WindowInfo(0, new RECT(), "", 0);
    private static int foregroundHwnd;
    private static int contentHeight;
    private static int contentWidth;
    private static int contentX;
    private static int contentY;

    static {
        OpenCV.loadLocally();
        arMenus = List.of(new DataportDownloadARMenu(), new BartenderTradeARMenu(), new BartenderSellARMenu());

        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            if (ocrAndRenderTimer != null) {
                ocrAndRenderTimer.cancel();
            }
            if (gameStateTimer != null) {
                gameStateTimer.cancel();
            }
            if (windowVisibilityAndPositionTimer != null) {
                windowVisibilityAndPositionTimer.stop();
            }
            executorService.shutdownNow();
            scheduledExecutorService.shutdownNow();
            log.info("AR Service shutdown finished.");

            NativeLibrary.getInstance("user32").close();
            NativeLibrary.getInstance("gdi32").close();
        }));
    }

    public static void forceShow() {
        FORCE_VISIBLE.set(true);
        if (enabled && !arStage.isShowing()) {
            showOverlay();
        }
    }

    public static void forceHide() {
        FORCE_VISIBLE.set(false);
        if (enabled && arStage.isShowing()) {
            hideOverlay();
        }
    }

    private static void showOverlay() {
        if (!arStage.isShowing()) {
            positionWindow();
            arStage.setAlwaysOnTop(true);
            arStage.show();
            final int handle = WindowInfoUtil.findWindow(windowTitle -> windowTitle.contains(arStage.getTitle()));
            long wl = User32.INSTANCE.GetWindowLongA(handle, WinUser.GWL_EXSTYLE);
            wl = wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT;
            User32.INSTANCE.SetWindowLongA(handle, WinUser.GWL_EXSTYLE, wl);
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

    public static void toggle() {
        if (!enabled) {
            enabled = true;
//            DataportDownloadARMenu.scaling = 1;
//            BartenderTradeARMenu.scaling = 1;
            log.debug("enabling AR Service");

            arStage = new Stage();
            for (int res : new int[]{16, 32, 48, 64, 128, 256, 512}) {
                arStage.getIcons().add(new Image(FXApplication.class.getResourceAsStream("/images/application/appicon" + res + ".png")));
            }
            arOverlay = new AROverlay();
            Scene arScene = new Scene(arOverlay, 640, 480);
            arStage.initModality(Modality.WINDOW_MODAL);
            arStage.initStyle(StageStyle.TRANSPARENT);
            arScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            arScene.getStylesheets().add(ARService.class.getResource(STYLESHEET).toExternalForm());
            arStage.setScene(arScene);
            arStage.titleProperty().set("AR Overlay");
            scheduleNext(50L);

            arStage.setOnCloseRequest(event -> {
                log.debug("arStage setOnCloseRequest");
                if (enabled) {
                    event.consume();
                }
            });

            setupAnimationTimer();
            if (PreferencesService.getPreference(PreferenceConstants.AR_FORCE_VISIBLE, false)) {
                FORCE_VISIBLE.set(true);
                if (!arStage.isShowing()) {
                    showOverlay();
                }
            }
        } else {
            enabled = false;
            FORCE_VISIBLE.set(false);
            if (arStage != null) {
                arStage.close();
            }
            log.debug("disabling AR Service");
            if (windowVisibilityAndPositionTimer != null) {
                windowVisibilityAndPositionTimer.stop();
                windowVisibilityAndPositionTimer = null;

            }
            if (ocrAndRenderTimer != null) {
                ocrAndRenderTimer.cancel();
                ocrAndRenderTimer = null;
            }
            if (gameStateTimer != null) {
                gameStateTimer.cancel();
                gameStateTimer = null;
            }
        }
    }

    private static void scheduleNext(long delayMs) {
        scheduledExecutorService.schedule(() -> {
            long delay = doWork();
            scheduleNext(delay);


        }, delayMs, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    private static long doWork() {
        try {
            //determine the window handle if it is unknown
            if (targetWindowInfo.hwnd == 0 || !WindowInfoUtil.isWindow(targetWindowInfo.hwnd)) {
                targetWindowInfo.hwnd = WindowInfoUtil.findWindow(windowTitle -> windowTitle.contains(ELITE_DANGEROUS_CLIENT_WINDOW_NAME));
            }
            if (targetWindowInfo.hwnd == 0) {
                //application not running
                arMenus.forEach(ARMenu::clear);
                arOverlay.getResizableImageView().setImage(null);
                REQUEST_HIDE.set(true);
                return 1000L;
            }
            //hide when game is not on the foreground
            targetWindowInfo = WindowInfoUtil.getWindowInfo(targetWindowInfo);
            foregroundHwnd = User32.INSTANCE.GetForegroundWindow();
            if (foregroundHwnd != targetWindowInfo.hwnd) {
                arMenus.forEach(ARMenu::clear);
                arOverlay.getResizableImageView().setImage(null);
                REQUEST_HIDE.set(true);
                return 1000L;
            }
            //determine scaling used for further processing
            final RECT rect = targetWindowInfo.rect;
            contentWidth = rect.right - rect.left;
            contentHeight = rect.bottom - rect.top;
            contentX = targetWindowInfo.rect.left;
            contentY = targetWindowInfo.rect.top;
            arMenus.forEach(arMenu -> arMenu.update(contentX, contentY, contentWidth, contentHeight));

            boolean anyVisible = false;
            for (ARMenu arMenu : arMenus) {
                if (arMenu.isEnabled()) {
                    final boolean visible = arMenu.isMenuVisible(targetWindowInfo);
                    if (!arMenu.getVisible().get()) {
                        REQUEST_SHOW.set(true);
                    }
                    arMenu.getVisible().set(visible);
                    if (visible) {
                        anyVisible = true;
                        break;
                    }else{
                        arMenu.clear();
                    }
                }
            }
            if (!anyVisible) {
                //hide
                if (arMenus.stream().anyMatch(arMenu -> arMenu.getVisible().get())) {
                    REQUEST_HIDE.set(true);
                }
//                arMenus.forEach(ARMenu::clear);
            }
            if (arMenus.stream().anyMatch(arMenu -> arMenu.getVisible().get())) {
                arMenus.forEach(arMenu -> {
                    if (arMenu.getVisible().get()) {
                        try {
                            arMenu.analyzeAndRender(targetWindowInfo, (image) -> arOverlay.getResizableImageView().setImage(image));
                        } catch (final Exception e) {
                            log.error("error in analyze and render", e);
                        }
                    }
                });
            } else {
                arMenus.forEach(ARMenu::clear);
                arOverlay.getResizableImageView().setImage(null);
            }
        } catch (final Exception e) {
            log.error("error", e);
        }
        return 50L;
    }

    private static void setupAnimationTimer() {
        windowVisibilityAndPositionTimer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                if (arStage.isShowing()) {
                    positionWindow();
                }
                if (FORCE_VISIBLE.get() && enabled) {
                    if (!arStage.isShowing()) {
                        showOverlay();
                    }
                    REQUEST_SHOW.set(false);
                    REQUEST_HIDE.set(false);
                } else if ((arMenus.stream().anyMatch(arMenu -> arMenu.getVisible().get())) && REQUEST_SHOW.get()) {
                    REQUEST_SHOW.set(false);
                    showOverlay();

                } else if (arMenus.stream().noneMatch(arMenu -> arMenu.getVisible().get()) && REQUEST_HIDE.get()) {
                    REQUEST_HIDE.set(false);
                    hideOverlay();
                }
            }
        };
        windowVisibilityAndPositionTimer.start();
    }

}
