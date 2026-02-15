package nl.jixxed.eliteodysseymaterials.service.ar;

import nu.pattern.OpenCV;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertAll;

@Tag("manual")
class DownloadMenuTest {
    private static Object[][] heightTests() {
        return new Object[][]{
                {0, "/ar/scrollbar/Screenshot_0007.png", false, new double[]{100, 100, 100, 100}},
                {1, "/ar/scrollbar/Screenshot_0009.png", true, new double[]{100, 100, 100, 100, 66}},
                {2, "/ar/scrollbar/Screenshot_0012.png", false, new double[]{29, 100, 100, 100, 100, 100}},
                {3, "/ar/scrollbar/Screenshot_0016.png", true, new double[]{100, 100, 100, 100, 66, 0}},
                {4, "/ar/scrollbar/Screenshot_0026.png", true, new double[]{0, 63, 100, 100, 100, 100, 0}},
                {5, "/ar/scrollbar/Screenshot_0038.png", false, new double[]{0, 100, 100, 100, 100, 100, 32}},
                // Add more test cases as needed
        };
    }

    @ParameterizedTest()
    @MethodSource("heightTests")
    void getMenuItemVisibleHeight(int testIndex, String image, boolean hasWarning, double[] expected) {
        OpenCV.loadLocally();
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(image)) {
            org.junit.jupiter.api.Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage importImage = ImageIO.read(resourceAsStream);
            var scaling = importImage.getHeight() / 1600D;
            var downloadMenu = new DownloadMenu(null, scaling, hasWarning, null, importImage.getWidth(), importImage.getHeight());
            BufferedImage downloadMenuImage = importImage.getSubimage((int) downloadMenu.getMenu().getX(), (int) downloadMenu.getMenu().getY(), (int) downloadMenu.getMenu().getWidth(), (int) downloadMenu.getMenu().getHeight());

            var scrollbar = ScrollBarHelper.getScrollBarV2(downloadMenuImage, hasWarning, scaling);
            downloadMenu = new DownloadMenu(downloadMenuImage, scaling, hasWarning, scrollbar, importImage.getWidth(), importImage.getHeight());
            Graphics2D g2d = downloadMenuImage.createGraphics();
            g2d.setColor(Color.RED);
            var visibleViewPortRect = downloadMenu.getVisibleViewPortRect().getAwtRectangle().getBounds();
            g2d.drawRect(visibleViewPortRect.x, visibleViewPortRect.y, visibleViewPortRect.width, visibleViewPortRect.height);

            g2d.setColor(Color.GREEN);
            for (Rectangle r : downloadMenu.menuItems.values()) {
                var r1 = r.getAwtRectangle();
                g2d.drawRect((int) r1.x, (int) r1.y, (int) r1.width, (int) r1.height);
            }
            saveImage(downloadMenuImage, "isMenuItemLabelVisible", testIndex);
            Assertions.assertThat(downloadMenu.getMenuSize()).isEqualTo(expected.length);
            final var dm = downloadMenu;
            var executables = new java.util.ArrayList<org.junit.jupiter.api.function.Executable>();
            for (int i = 0; i < expected.length; i++) {
                final int idx = i;
                executables.add(() -> Assertions.assertThat(dm.getMenuItemVisibleHeight(idx + 1)).isCloseTo(expected[idx] * 0.675, Assertions.offset(0.5)));
            }
            assertAll(executables);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object[][] menuItemLabelVisibleTests() {
        return new Object[][]{
                {"/ar/scrollbar/Screenshot_0009.png", true, new boolean[]{true, true, true, true, true}},//222
                {"/ar/scrollbar/Screenshot_0012.png", false, new boolean[]{false, true, true, true, true, true}},//222
                {"/ar/scrollbar/Screenshot_0016.png", true, new boolean[]{true, true, true, true, true, false}},//222
                {"/ar/scrollbar/Screenshot_0026.png", true, new boolean[]{false, true, true, true, true, true, false}},//222
                {"/ar/scrollbar/Screenshot_0038.png", false, new boolean[]{false, true, true, true, true, true, false}},//293
                // Add more test cases as needed
        };
    }

    @ParameterizedTest()
    @MethodSource("menuItemLabelVisibleTests")
    void isMenuItemLabelVisible(String image, boolean hasWarning, boolean[] expected) {
        OpenCV.loadLocally();
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(image)) {
            org.junit.jupiter.api.Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage importImage = ImageIO.read(resourceAsStream);
            var scaling = importImage.getHeight() / 1600D;
            var downloadMenu = new DownloadMenu(null, scaling, hasWarning, null, importImage.getWidth(), importImage.getHeight());
            BufferedImage downloadMenuImage = importImage.getSubimage((int) downloadMenu.getMenu().getX(), (int) downloadMenu.getMenu().getY(), (int) downloadMenu.getMenu().getWidth(), (int) downloadMenu.getMenu().getHeight());
            var scrollbar = ScrollBarHelper.getScrollBarV2(downloadMenuImage, hasWarning, scaling);
            downloadMenu = new DownloadMenu(downloadMenuImage, scaling, hasWarning, scrollbar, importImage.getWidth(), importImage.getHeight());

            saveImage(downloadMenuImage, "isMenuItemLabelVisible", 0);
            final var dm = downloadMenu;
            var executables = new java.util.ArrayList<org.junit.jupiter.api.function.Executable>();
            for (int i = 0; i < expected.length; i++) {
                final int idx = i;
                executables.add(() -> org.assertj.core.api.Assertions.assertThat(dm.isMenuItemLabelVisible(idx + 1)).isEqualTo(expected[idx]));
            }
            assertAll(executables);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isMenuItemVisible() {
    }

    private static Object[][] menuItemVisibleForOCRTests() {
        return new Object[][]{
                {"/ar/scrollbar/Screenshot_0009.png", true, new boolean[]{true, true, true, true, true}},//222
                {"/ar/scrollbar/Screenshot_0012.png", false, new boolean[]{false, true, true, true, true, true}},//222
                {"/ar/scrollbar/Screenshot_0016.png", true, new boolean[]{true, true, true, true, true, false}},//222
                {"/ar/scrollbar/Screenshot_0026.png", true, new boolean[]{false, false, true, true, true, true, false}},//222
                {"/ar/scrollbar/Screenshot_0038.png", false, new boolean[]{false, true, true, true, true, true, false}},//293
                // Add more test cases as needed
        };
    }

    @ParameterizedTest()
    @MethodSource("menuItemVisibleForOCRTests")
    void isMenuItemVisibleForOCR(String image, boolean hasWarning, boolean[] expected) {
        OpenCV.loadLocally();
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(image)) {
            org.junit.jupiter.api.Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage importImage = ImageIO.read(resourceAsStream);
            var scaling = importImage.getHeight() / 1600D;
            var downloadMenu = new DownloadMenu(null, scaling, hasWarning, null, importImage.getWidth(), importImage.getHeight());
            BufferedImage downloadMenuImage = importImage.getSubimage((int) downloadMenu.getMenu().getX(), (int) downloadMenu.getMenu().getY(), (int) downloadMenu.getMenu().getWidth(), (int) downloadMenu.getMenu().getHeight());
            var scrollbar = ScrollBarHelper.getScrollBarV2(downloadMenuImage, hasWarning, scaling);
            downloadMenu = new DownloadMenu(downloadMenuImage, scaling, hasWarning, scrollbar, importImage.getWidth(), importImage.getHeight());

            Graphics2D g2d = downloadMenuImage.createGraphics();
            g2d.setColor(Color.RED);
            var visibleViewPortRect = downloadMenu.getVisibleViewPortRect().getAwtRectangle().getBounds();
            g2d.drawRect(visibleViewPortRect.x, visibleViewPortRect.y, visibleViewPortRect.width, visibleViewPortRect.height);

            g2d.setColor(Color.GREEN);
            for (Rectangle r : downloadMenu.menuItems.values()) {
                var r1 = r.getAwtRectangle();
                double x = downloadMenu.getMenuTextReadOffset().getX() + r.getX();
                double y = r.getY() + downloadMenu.getMenuTextReadOffset().getY();
                Rectangle menuItemOCRRect = new Rectangle(x, y,
                        x+downloadMenu.getMenuTextReadOffset().getWidth(), y+downloadMenu.getMenuTextReadOffset().getHeight());
                g2d.drawRect((int) r1.x, (int) r1.y, (int) r1.width, (int) r1.height);
            }


            saveImage(downloadMenuImage, "isMenuItemVisibleForOCR", 0);
            final var dm = downloadMenu;
            var executables = new java.util.ArrayList<org.junit.jupiter.api.function.Executable>();
            for (int i = 0; i < expected.length; i++) {
                final int idx = i;
                executables.add(() -> org.assertj.core.api.Assertions.assertThat(dm.isMenuItemVisibleForOCR(idx + 1)).isEqualTo(expected[idx]));
            }
            assertAll(executables);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveImage(BufferedImage image, String name, int index) throws IOException {
        File output = new File("target/" + name + "." + index + ".png");
        output.mkdirs();
        Files.deleteIfExists(output.toPath());
        ImageIO.write(image, "png", output);
    }
}