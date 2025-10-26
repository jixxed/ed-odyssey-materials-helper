package nl.jixxed.eliteodysseymaterials.templates.components;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class PipetteOverlay {

    public interface ColorConsumer {
        void accept(Color color);
    }

    private static final int MAG_SIZE = 150;   // size of magnifier box
    private static final int ZOOM = 10;        // magnification factor
    private static final int SAMPLE_RADIUS = 5; // half size of area to zoom

    public static void start(ColorConsumer consumer) {
        Platform.runLater(() -> {
            try {
                Robot robot = new Robot();

                // --- Capture all screens and create overlays ---
                List<ScreenOverlay> overlays = new ArrayList<>();
                List<ScreenInfo> screens = new ArrayList<>();
                int index = 0;
                for (Screen screen : Screen.getScreens()) {
                    javafx.geometry.Rectangle2D bounds = screen.getBounds();
                    Rectangle screenRect = new Rectangle(
                            (int) bounds.getMinX(),
                            (int) bounds.getMinY(),
                            (int) bounds.getWidth(),
                            (int) bounds.getHeight());
                    BufferedImage screenshot = robot.createScreenCapture(screenRect);
                    Image fxScreenshot = SwingFXUtils.toFXImage(screenshot, new WritableImage(screenshot.getWidth(), screenshot.getHeight()));
                    screens.add(new ScreenInfo(index++, screenRect, screenshot, fxScreenshot, bounds));
                    
                    // Create overlay for this screen
                    Stage overlay = new Stage(StageStyle.UNDECORATED);
                    overlay.setAlwaysOnTop(true);
                    overlay.setWidth(bounds.getWidth());
                    overlay.setHeight(bounds.getHeight());
                    overlay.setX(bounds.getMinX());
                    overlay.setY(bounds.getMinY());
                    
                    Canvas canvas = new Canvas(bounds.getWidth(), bounds.getHeight());
                    GraphicsContext gc = canvas.getGraphicsContext2D();
//                    overlay.setOpacity(0.5);to debug multiple screens
                    // Draw the screenshot as the background
                    gc.drawImage(fxScreenshot, 0, 0);
                    
                    Pane root = new Pane(canvas);
                    Scene scene = new Scene(root, Color.BLACK);
                    overlay.setScene(scene);
                    
                    overlays.add(new ScreenOverlay(overlay, canvas, gc, fxScreenshot, bounds));
                }
                overlays.forEach(overlay -> {
                    overlay.stage.show();
                });
                // --- Animation Timer for magnifier ---
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        Point mouse = MouseInfo.getPointerInfo().getLocation();
                        int globalX = mouse.x;
                        int globalY = mouse.y;

                        // Find which screen the mouse is on
                        ScreenInfo currentScreen = findScreenForPoint(screens, globalX, globalY);
                        if (currentScreen == null) {
                            // Mouse outside all screens - redraw screenshots without magnifier
                            for (ScreenOverlay overlay : overlays) {
                                overlay.gc.clearRect(0, 0, overlay.canvas.getWidth(), overlay.canvas.getHeight());
                                overlay.gc.drawImage(overlay.screenshot, 0, 0);
                            }
                            return;
                        }

                        // Convert global coordinates to screen-relative coordinates
                        int x = globalX - (int) currentScreen.bounds.getMinX();
                        int y = globalY - (int) currentScreen.bounds.getMinY();
                        
                        // Clamp coordinates to screen bounds
                        x = Math.max(0, Math.min(currentScreen.screenshot.getWidth() - 1, x));
                        y = Math.max(0, Math.min(currentScreen.screenshot.getHeight() - 1, y));

                        // Update all overlays
                        for (ScreenOverlay overlay : overlays) {
                            overlay.gc.clearRect(0, 0, overlay.canvas.getWidth(), overlay.canvas.getHeight());
                            overlay.gc.drawImage(overlay.screenshot, 0, 0);
                            
                            // Only draw magnifier on the current screen
                            if (overlay.bounds.equals(currentScreen.bounds)) {
                                // Capture small region around cursor
                                int sampleX = Math.max(0, Math.min(currentScreen.screenshot.getWidth() - SAMPLE_RADIUS * 2 - 1, x - SAMPLE_RADIUS));
                                int sampleY = Math.max(0, Math.min(currentScreen.screenshot.getHeight() - SAMPLE_RADIUS * 2 - 1, y - SAMPLE_RADIUS));
                                BufferedImage region = currentScreen.screenshot.getSubimage(sampleX, sampleY, SAMPLE_RADIUS * 2 + 1, SAMPLE_RADIUS * 2 + 1);
                                Image fxZoom = SwingFXUtils.toFXImage(region, new WritableImage(SAMPLE_RADIUS * 4 + 1, SAMPLE_RADIUS * 4 + 1));

                                // Compute magnifier position in global coordinates (avoid edges)
                                double globalMagX = globalX + 20;
                                double globalMagY = globalY + 20;
                                if (globalMagX + MAG_SIZE > currentScreen.bounds.getMaxX())
                                    globalMagX = globalX - MAG_SIZE - 20;
                                if (globalMagY + MAG_SIZE > currentScreen.bounds.getMaxY())
                                    globalMagY = globalY - MAG_SIZE - 20;

                                // Convert global coordinates to local canvas coordinates for this screen
                                double localMagX = globalMagX - overlay.bounds.getMinX();
                                double localMagY = globalMagY - overlay.bounds.getMinY();

                                // Draw magnifier box background
                                overlay.gc.setStroke(Color.WHITE);
                                overlay.gc.setLineWidth(2);
                                overlay.gc.setFill(Color.color(0, 0, 0, 0.6));
                                overlay.gc.fillRect(localMagX - 2, localMagY - 2, MAG_SIZE + 4, MAG_SIZE + 4);
                                overlay.gc.strokeRect(localMagX - 2, localMagY - 2, MAG_SIZE + 4, MAG_SIZE + 4);

                                // Draw zoomed pixels
                                overlay.gc.drawImage(fxZoom, 0, 0, region.getWidth(), region.getHeight(),
                                        localMagX, localMagY, MAG_SIZE, MAG_SIZE);

                                // Draw crosshair
                                overlay.gc.setStroke(Color.WHITE);
                                double mid = MAG_SIZE / 2.0;
                                overlay.gc.strokeLine(localMagX + mid, localMagY, localMagX + mid, localMagY + MAG_SIZE);
                                overlay.gc.strokeLine(localMagX, localMagY + mid, localMagX + MAG_SIZE, localMagY + mid);

                                // Sample color from single pixel under cursor
                                java.awt.Color awtColor = currentScreen.screenshot.getRGB(x, y) == 0 ? new java.awt.Color(0,0,0) : new java.awt.Color(currentScreen.screenshot.getRGB(x, y));
                                Color fxColor = Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());

                                // Color preview bar at bottom of magnifier
                                overlay.gc.setFill(fxColor);
                                overlay.gc.fillRect(localMagX, localMagY + MAG_SIZE - 20, MAG_SIZE, 20);
                                overlay.gc.setFill(Color.WHITE);
                                overlay.gc.fillText(String.format("#%02X%02X%02X", awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue()), localMagX + 5, localMagY + MAG_SIZE - 5);
                            }
                        }
                    }
                };
                timer.start();

                // Add event handlers to all overlays
                for (ScreenOverlay screenOverlay : overlays) {
                    Scene scene = screenOverlay.stage.getScene();
                    
                    scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            timer.stop();
                            pickColor(robot, e, consumer);
                            for (ScreenOverlay o : overlays) {
                                o.stage.close();
                            }
                        } else if (e.getButton() == MouseButton.SECONDARY) {
                            timer.stop();
                            for (ScreenOverlay o : overlays) {
                                o.stage.close();
                            }
                        }
                    });

                    scene.setOnKeyPressed(e -> {
                        if (e.getCode() == KeyCode.ESCAPE) {
                            timer.stop();
                            for (ScreenOverlay o : overlays) {
                                o.stage.close();
                            }
                        }
                    });
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static ScreenInfo findScreenForPoint(List<ScreenInfo> screens, int globalX, int globalY) {
        for (ScreenInfo screenInfo : screens) {
            if (globalX >= screenInfo.bounds.getMinX() && globalX < screenInfo.bounds.getMaxX() &&
                globalY >= screenInfo.bounds.getMinY() && globalY < screenInfo.bounds.getMaxY()) {
                return screenInfo;
            }
        }
        return screens.isEmpty() ? null : screens.get(0); // Fallback to first screen
    }
@AllArgsConstructor
    private static class ScreenInfo {
        final int index;
        final Rectangle rect;
        final BufferedImage screenshot;
        final Image fxScreenshot;
        final javafx.geometry.Rectangle2D bounds;
    }

    private static class ScreenOverlay {
        final Stage stage;
        final Canvas canvas;
        final GraphicsContext gc;
        final Image screenshot;
        final javafx.geometry.Rectangle2D bounds;

        ScreenOverlay(Stage stage, Canvas canvas, GraphicsContext gc, Image screenshot, javafx.geometry.Rectangle2D bounds) {
            this.stage = stage;
            this.canvas = canvas;
            this.gc = gc;
            this.screenshot = screenshot;
            this.bounds = bounds;
        }
    }

    private static void pickColor(Robot robot, MouseEvent e, ColorConsumer consumer) {
        java.awt.Color awtColor = robot.getPixelColor((int) e.getScreenX(), (int) e.getScreenY());
        Color fxColor = Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
        consumer.accept(fxColor);
    }
}