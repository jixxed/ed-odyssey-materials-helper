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

import java.awt.*;
import java.awt.image.BufferedImage;

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

                // --- Take full screen screenshot before overlay ---
                Rectangle screenRect = new Rectangle(
                        (int) Screen.getPrimary().getBounds().getMinX(),
                        (int) Screen.getPrimary().getBounds().getMinY(),
                        (int) Screen.getPrimary().getBounds().getWidth(),
                        (int) Screen.getPrimary().getBounds().getHeight());
                BufferedImage screenshot = robot.createScreenCapture(screenRect);
                Image fxScreenshot = SwingFXUtils.toFXImage(screenshot, new WritableImage(screenshot.getWidth(), screenshot.getHeight()));

                // --- Overlay Stage ---
                Stage overlay = new Stage(StageStyle.UNDECORATED);
                overlay.setAlwaysOnTop(true);
//                overlay.setFullScreen(true);
                overlay.setWidth(Screen.getPrimary().getBounds().getWidth());
                overlay.setHeight(Screen.getPrimary().getBounds().getHeight());
                overlay.setX(Screen.getPrimary().getBounds().getMinX());
                overlay.setY(Screen.getPrimary().getBounds().getMinY());
                Canvas canvas = new Canvas(screenRect.width, screenRect.height);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                // Draw the screenshot as the background (non-transparent)
                gc.drawImage(fxScreenshot, 0, 0);

                Pane root = new Pane(canvas);
                Scene scene = new Scene(root, Color.BLACK);
                overlay.setScene(scene);
                overlay.show();

                // --- Animation Timer for magnifier ---
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        Point mouse = MouseInfo.getPointerInfo().getLocation();
                        int x = mouse.x;
                        int y = mouse.y;

                        // Redraw screenshot background
                        gc.drawImage(fxScreenshot, 0, 0);

                        // Capture small region around cursor
                        int sampleX = Math.max(0, Math.min(screenRect.width - SAMPLE_RADIUS * 2 - 1, x - SAMPLE_RADIUS));
                        int sampleY = Math.max(0, Math.min(screenRect.height - SAMPLE_RADIUS * 2 - 1, y - SAMPLE_RADIUS));
                        BufferedImage region = screenshot.getSubimage(sampleX, sampleY, SAMPLE_RADIUS * 2 + 1, SAMPLE_RADIUS * 2 + 1);
                        Image fxZoom = SwingFXUtils.toFXImage(region, new WritableImage(SAMPLE_RADIUS * 4 + 1, SAMPLE_RADIUS * 4 + 1));

                        // Compute magnifier position (avoid edges)
                        double magX = x + 20;
                        double magY = y + 20;
                        if (magX + MAG_SIZE > screenRect.width)
                            magX = x - MAG_SIZE - 20;
                        if (magY + MAG_SIZE > screenRect.height)
                            magY = y - MAG_SIZE - 20;

                        // Draw magnifier box background
                        gc.setStroke(Color.WHITE);
                        gc.setLineWidth(2);
                        gc.setFill(Color.color(0, 0, 0, 0.6));
                        gc.fillRect(magX - 2, magY - 2, MAG_SIZE + 4, MAG_SIZE + 4);
                        gc.strokeRect(magX - 2, magY - 2, MAG_SIZE + 4, MAG_SIZE + 4);

                        // Draw zoomed pixels
                        gc.drawImage(fxZoom, 0, 0, region.getWidth(), region.getHeight(),
                                magX, magY, MAG_SIZE, MAG_SIZE);

                        // Draw crosshair
                        gc.setStroke(Color.WHITE);
                        double mid = MAG_SIZE / 2.0;
                        gc.strokeLine(magX + mid, magY, magX + mid, magY + MAG_SIZE);
                        gc.strokeLine(magX, magY + mid, magX + MAG_SIZE, magY + mid);

                        // Sample color from single pixel under cursor
                        java.awt.Color awtColor = screenshot.getRGB(x, y) == 0 ? new java.awt.Color(0,0,0) : new java.awt.Color(screenshot.getRGB(x, y));
                        Color fxColor = Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());

                        // Color preview bar at bottom of magnifier
                        gc.setFill(fxColor);
                        gc.fillRect(magX, magY + MAG_SIZE - 20, MAG_SIZE, 20);
                        gc.setFill(Color.WHITE);
                        gc.fillText(String.format("#%02X%02X%02X", awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue()), magX + 5, magY + MAG_SIZE - 5);
                    }
                };
                timer.start();

                // Mouse interaction
                scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        timer.stop();
                        pickColor(robot, e, consumer);
                        overlay.close();
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        timer.stop();
                        overlay.close();
                    }
                });

                // Cancel with ESC
                scene.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ESCAPE) {
                        timer.stop();
                        overlay.close();
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void pickColor(Robot robot, MouseEvent e, ColorConsumer consumer) {
        java.awt.Color awtColor = robot.getPixelColor((int) e.getScreenX(), (int) e.getScreenY());
        Color fxColor = Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
        consumer.accept(fxColor);
    }
}