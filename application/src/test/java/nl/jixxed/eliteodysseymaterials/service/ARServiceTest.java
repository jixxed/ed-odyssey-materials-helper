package nl.jixxed.eliteodysseymaterials.service;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.ar.*;
import nl.jixxed.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//tests fail due to colors
@Slf4j
public class ARServiceTest {
    @Tag("manual")
    @Test
    public void isBartenderMenu() {
        // Load the test image resource
//        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/ar/3840x1600.png")) {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/ar/2560x1440.png")) {
            Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage image = ImageIO.read(resourceAsStream);
            ARService.debug = true;
            // Call the method and assert the result
            BartenderMenu bartenderMenu = new BartenderMenu();
            bartenderMenu.setContentWidth(image.getWidth());
            bartenderMenu.setContentHeight(image.getHeight());
            // get the cocktail from the image
            final Rectangle cocktail = bartenderMenu.getCocktail();
            BufferedImage subimage = image.getSubimage((int) cocktail.getX(), (int) cocktail.getY(), (int) cocktail.getWidth(), (int) cocktail.getHeight());

            BufferedImage convertedImg = new BufferedImage(subimage.getWidth(), subimage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            convertedImg.getGraphics().drawImage(subimage, 0, 0, null);
            ARService.updateScaling(bartenderMenu);
            boolean isBartenderMenu = ARService.isBartenderMenu(convertedImg);
            Assertions.assertTrue(isBartenderMenu, "Expected the image to be recognized as a bartender menu.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Tag("manual")
    @Test
    public void isNotBartenderMenu() {
        // Load the test image resource
//        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/ar/3840x1600.png")) {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/ar/2560x1440_false.png")) {
            Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
            BufferedImage image = ImageIO.read(resourceAsStream);
            ARService.debug = true;
            // Call the method and assert the result
            BartenderMenu bartenderMenu = new BartenderMenu();
            bartenderMenu.setContentWidth(image.getWidth());
            bartenderMenu.setContentHeight(image.getHeight());
            // get the cocktail from the image
            final Rectangle cocktail = bartenderMenu.getCocktail();
            BufferedImage subimage = image.getSubimage((int) cocktail.getX(), (int) cocktail.getY(), (int) cocktail.getWidth(), (int) cocktail.getHeight());

            BufferedImage convertedImg = new BufferedImage(subimage.getWidth(), subimage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            convertedImg.getGraphics().drawImage(subimage, 0, 0, null);
            ARService.updateScaling(bartenderMenu);
            boolean isBartenderMenu = ARService.isBartenderMenu(convertedImg);
            Assertions.assertFalse(isBartenderMenu, "Expected the image to be recognized as a bartender menu.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            Assertions.assertNotNull(resourceAsStream, "Test image resource not found.");
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

    private static void saveImage(BufferedImage image, String name, int index) throws IOException {
        File output = new File("target/" + name + "." + index + ".png");
        output.mkdirs();
        Files.deleteIfExists(output.toPath());
        ImageIO.write(image, "png", output);
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
