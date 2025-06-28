package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.service.ar.BartenderMenu;
import nl.jixxed.eliteodysseymaterials.service.ar.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ARServiceTest {
    @Disabled
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

    @Disabled
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
}
