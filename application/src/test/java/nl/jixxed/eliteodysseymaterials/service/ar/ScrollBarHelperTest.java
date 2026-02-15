package nl.jixxed.eliteodysseymaterials.service.ar;

import nu.pattern.OpenCV;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.assertj.core.data.Offset.offset;
@Tag("manual")
class ScrollBarHelperTest {
    private static Object[][] scrollBarHeightTests() {
        return new Object[][]{
                {"/ar/scrollbar/Screenshot_0007.png", true, 0},//222
                {"/ar/scrollbar/Screenshot_0026.png", true, 328},//222
                {"/ar/scrollbar/Screenshot_0038.png",false, 431},//293
                // Add more test cases as needed
        };
    }

    @ParameterizedTest()
    @MethodSource("scrollBarHeightTests")
    void getScrollBarHeight(String image, boolean hasWarning, int expected) {
        OpenCV.loadLocally();
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(image)) {
            org.junit.jupiter.api.Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage importImage = ImageIO.read(resourceAsStream);
            var scaling = importImage.getHeight() / 1600D;
            var downloadMenu = new DownloadMenu(null, scaling, Boolean.TRUE.equals(hasWarning), null, importImage.getWidth(), importImage.getHeight());
            BufferedImage downloadMenuImage = importImage.getSubimage((int)downloadMenu.getMenu().getX(), (int)downloadMenu.getMenu().getY(), (int)downloadMenu.getMenu().getWidth(), (int)downloadMenu.getMenu().getHeight());
            saveImage(downloadMenuImage,"downloadmenu", expected);
            int scrollBarHeight = ScrollBarHelper.getScrollBarHeight(downloadMenuImage,hasWarning, scaling);

            Assertions.assertThat(scrollBarHeight).isEqualTo(expected);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Object[][] listSizeTests() {
        return new Object[][]{
                {"/ar/scrollbar/Screenshot_0009.png", true, 5},//222
                {"/ar/scrollbar/Screenshot_0012.png", false, 6},//222
                {"/ar/scrollbar/Screenshot_0016.png", true, 6},//222
                {"/ar/scrollbar/Screenshot_0026.png", true, 7},//222
                {"/ar/scrollbar/Screenshot_0038.png",false, 7},//293
                // Add more test cases as needed
        };
    }
    @ParameterizedTest()
    @MethodSource("listSizeTests")
    void getListSize(String image, boolean hasWarning, int expected) {
        OpenCV.loadLocally();
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(image)) {
            org.junit.jupiter.api.Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage importImage = ImageIO.read(resourceAsStream);
            var scaling = importImage.getHeight() / 1600D;
            var downloadMenu = new DownloadMenu(null, scaling, hasWarning, null, importImage.getWidth(), importImage.getHeight());
            BufferedImage downloadMenuImage = importImage.getSubimage((int)downloadMenu.getMenu().getX(), (int)downloadMenu.getMenu().getY(), (int)downloadMenu.getMenu().getWidth(), (int)downloadMenu.getMenu().getHeight());
            saveImage(downloadMenuImage,"downloadmenu", expected);
            int listSize = ScrollBarHelper.getListSize(downloadMenuImage,hasWarning, scaling);

            Assertions.assertThat(listSize).isEqualTo(expected);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Object[][] progressTests() {
        return new Object[][]{
                {"/ar/scrollbar/Screenshot_0009.png", true, 0},//222
                {"/ar/scrollbar/Screenshot_0012.png", false, 100},//222
                {"/ar/scrollbar/Screenshot_0016.png", true, 0},//222
                {"/ar/scrollbar/Screenshot_0026.png", true, 57},//222
                {"/ar/scrollbar/Screenshot_0038.png",false, 59},//293
                // Add more test cases as needed
        };
    }
    @ParameterizedTest()
    @MethodSource("progressTests")
    void getProgress(String image, boolean hasWarning, double expected) {
        OpenCV.loadLocally();
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(image)) {
            org.junit.jupiter.api.Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage importImage = ImageIO.read(resourceAsStream);
            var scaling = importImage.getHeight() / 1600D;
            var downloadMenu = new DownloadMenu(null, scaling, hasWarning, null, importImage.getWidth(), importImage.getHeight());
            BufferedImage downloadMenuImage = importImage.getSubimage((int)downloadMenu.getMenu().getX(), (int)downloadMenu.getMenu().getY(), (int)downloadMenu.getMenu().getWidth(), (int)downloadMenu.getMenu().getHeight());
            saveImage(downloadMenuImage,"downloadmenu", (int)expected);
            double progress = ScrollBarHelper.getProgress(downloadMenuImage, hasWarning, scaling);

            Assertions.assertThat(progress).isCloseTo(expected, offset(0.5));
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