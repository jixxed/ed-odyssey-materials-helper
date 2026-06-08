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

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.OCRService;
import nl.jixxed.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
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
            //flip R and B channels to match windows screenshot
            BufferedImage bgrImage = new BufferedImage(importImage.getWidth(), importImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            for (int y = 0; y < importImage.getHeight(); y++) {
                for (int x = 0; x < importImage.getWidth(); x++) {
                    int rgb = importImage.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    int abgr = 0xFF000000 | (b << 16) | (g << 8) | r;
                    bgrImage.setRGB(x, y, abgr);
                }
            }
            importImage = bgrImage;

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

    private static Object[][] downloadPortTests() {
        return new Object[][]{
                {ApplicationLocale.GERMAN, "/ar/dataport_german_red.png", new OdysseyMaterial[]{Data.CATMEDIA, Data.LITERARYFICTION, Data.TRAVELPERMITS, Data.POWERFINANCIALRECORDS, Data.POWEREMPLOYEEDATA}},
                {ApplicationLocale.ENGLISH, "/ar/dataport_english_yellow2560x1440.png", new OdysseyMaterial[]{Data.CHEMICALFORMULAE, Data.CHEMICALPATENTS, Data.GENETICRESEARCH, Data.PURCHASERECORDS, Data.GEOLOGICALDATA}},
                {ApplicationLocale.ENGLISH, "/ar/dataport_english_yellow3840x1600.png", new OdysseyMaterial[]{Data.CHEMICALFORMULAE, Data.CHEMICALPATENTS, Data.GENETICRESEARCH, Data.PURCHASERECORDS, Data.GEOLOGICALDATA}},
                {ApplicationLocale.RUSSIAN, "/ar/dataport_russian_orange.png", new OdysseyMaterial[]{Data.CHEMICALPATENTS}},
                // Add more test cases as needed
        };
    }
    @Tag("manual")
    @ParameterizedTest()
    @MethodSource("downloadPortTests")
    public void testForMaterials(ApplicationLocale locale, String image, OdysseyMaterial[] expected) {
        OpenCV.loadLocally();
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(image)) {
            org.junit.jupiter.api.Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage importImage = ImageIO.read(resourceAsStream);
            var contentWidth = importImage.getWidth();
            var contentHeight = importImage.getHeight();
            var hasWarning = false;
            var scaling = contentHeight / 1600D;
            var downloadMenu1 = new DownloadMenu(null, scaling, Boolean.TRUE.equals(hasWarning), null, contentWidth, contentHeight);
            BufferedImage downloadMenuCapture = new BufferedImage(
                    importImage.getWidth(),
                    importImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );
            downloadMenuCapture.getGraphics().drawImage(importImage, 0, 0, null);
            downloadMenuCapture = downloadMenuCapture.getSubimage((int) downloadMenu1.getMenu().getX(), (int) downloadMenu1.getMenu().getY(), (int) downloadMenu1.getMenu().getWidth(), (int) downloadMenu1.getMenu().getHeight());
            saveImage(downloadMenuCapture, "downloadMenuCapture", 0);
            var contentWidth2 = downloadMenuCapture.getWidth();
            var contentHeight2 = downloadMenuCapture.getHeight();
            var hasWarning2 = false;
            var scaling2 = contentHeight / 1600D;
            var scrollBar = ScrollBarHelper.getScrollBarV2(downloadMenuCapture, hasWarning2, scaling2);
            var downloadMenu = new DownloadMenu(null, scaling2, Boolean.TRUE.equals(hasWarning), scrollBar, contentWidth2, contentHeight2);
            ImageTransformHelper.init(downloadMenu, scaling);
            for (int index = 1; index <= expected.length; index++) {
                final int finalIndex = index;

                try {
                    final double menuItemY = downloadMenu.getMenuItem(finalIndex).getY() + downloadMenu.getMenuItemPositionYOffset();

                    long timeRenderBefore = System.currentTimeMillis();
                    final BufferedImage warped = ImageTransformHelper.transformForSelection(downloadMenuCapture, new java.awt.Rectangle(
                            (int) (downloadMenu.getMenuItem(finalIndex).getX() + downloadMenu.getMenuTextReadOffset().getX() - 10),
                            (int) (menuItemY + downloadMenu.getMenuTextReadOffset().getY() - 50),
                            (int) downloadMenu.getMenuTextReadOffset().getWidth() + 20,
                            (int) downloadMenu.getMenuTextReadOffset().getHeight() + 100)
                    );
                    saveImage(warped, "warped", index);
                    long timeRenderAfter = System.currentTimeMillis();
                    log.debug("Transform menu item time: " + (timeRenderAfter - timeRenderBefore));
//                    final int y = (int) (menuItemY + downloadMenu.getMenuTextReadOffset().getY() - downloadMenu.getMenu().getY());
                    final BufferedImage menuItemLabelCaptureOriginalColor = warped.getSubimage(
                            (int) (downloadMenu.getMenuItem(finalIndex).getX() + downloadMenu.getMenuTextReadOffset().getX()),
                            (int) (downloadMenu.getMenuItem(finalIndex).getY() + downloadMenu.getMenuTextReadOffset().getY() + downloadMenu.getMenuItemPositionYOffset()),
                            (int) downloadMenu.getMenuTextReadOffset().getWidth(),
                            (int) downloadMenu.getMenuTextReadOffset().getHeight()
                    );
                    saveImage(menuItemLabelCaptureOriginalColor, "menuItemLabelCaptureOriginalColor", index);

                    final Mat matColor = CvHelper.convertToMat(menuItemLabelCaptureOriginalColor, null);

//                    final Mat matGray = new Mat(matColor.size(), CvType.CV_8UC4);
//                    Imgproc.cvtColor(matColor, matGray, Imgproc.COLOR_RGB2GRAY);
                    Mat binary = new Mat(matColor.size(), CvType.CV_8UC4);
//                    Imgproc.adaptiveThreshold(
//                            matGray,
//                            binary,
//                            255,
//                            Imgproc.ADAPTIVE_THRESH_MEAN_C,
//                            Imgproc.THRESH_BINARY_INV,
//                            13, // block size (odd number, tune this)
//                            3   // C offset (tune this)
//                    );
//                    Imgproc.threshold(matGray, binary, 0, 255, Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);
                    Map<RGB, Integer> dominantColors = findDominantColors(matColor);
                    RGB dominant = Collections.max(dominantColors.entrySet(), Map.Entry.comparingByValue()).getKey();
                    //remove the dominant color value from each pixel
                    for (int y = 0; y < matColor.rows(); y++) {
                        for (int x = 0; x < matColor.cols(); x++) {
                            double[] bgr = matColor.get(y, x);
                            double b = bgr[0];
                            double g = bgr[1];
                            double r = bgr[2];

                            b = Math.abs(b - dominant.b());
                            g = Math.abs(g - dominant.g());
                            r = Math.abs(r - dominant.r());


                            binary.put(y, x, new double[]{b, g, r, 255.0}); // white

                            // else: leave as is
                        }
                    }

                    final BufferedImage menuItemLabelCaptureOriginalGray = CvHelper.mat2Img(binary);

                    saveImage(menuItemLabelCaptureOriginalGray, "menuItemLabelCaptureOriginalGray", index);
                    binary.release();
                    matColor.release();
//                    matGray.release();
                    timeRenderBefore = System.currentTimeMillis();
                    String cleaned = imageToString(locale, finalIndex, menuItemLabelCaptureOriginalGray);
                    try {
                        OdysseyMaterial.forLocalizedNameSpaceInsensitive(cleaned, locale.getLocale());
                    } catch (final Exception e) {
                        final Mat normal = CvHelper.convertToMat(menuItemLabelCaptureOriginalGray, null);
                        final Mat inverted = new Mat();
                        Core.bitwise_not(normal, inverted);
                        final BufferedImage menuItemLabelCaptureInverted = CvHelper.mat2Img(inverted);
                        normal.release();
                        inverted.release();
                        log.debug("Attempt OCR inverted");
                        cleaned = imageToString(locale, finalIndex, menuItemLabelCaptureInverted);

                    }
                    try {
                        if (cleaned.isBlank()) {
                            downloadMenu.getDownloadData().put(finalIndex, Data.UNKNOWN);
                            downloadMenu.getScanned().put(finalIndex, true);
                        } else {
                            final OdysseyMaterial odysseyMaterial = OdysseyMaterial.forLocalizedNameSpaceInsensitive(cleaned, locale.getLocale());
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
                    org.assertj.core.api.Assertions.assertThat(downloadMenu.getDownloadData().get(finalIndex)).isEqualTo(expected[finalIndex - 1]);
                } catch (final TesseractException e) {
                    log.error("", e);
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    record RGB(int r, int g, int b) {
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


    public static String imageToString(ApplicationLocale locale, final int index, final BufferedImage menuItemLabelCapture) throws TesseractException {
        OCRService.setLocale(locale);
        final String dataCharacterForCurrentLocale = LocaleService.getDataCharacterForARLocale(locale.getLocale());
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