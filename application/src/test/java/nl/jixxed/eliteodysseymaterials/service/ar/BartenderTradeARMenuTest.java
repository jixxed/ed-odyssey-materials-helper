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
import nl.jixxed.eliteodysseymaterials.service.ARService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

//tests fail due to colors
@Slf4j
public class BartenderTradeARMenuTest {
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
            BartenderTradeMenu bartenderTradeMenu = new BartenderTradeMenu();
            bartenderTradeMenu.setContentWidth(image.getWidth());
            bartenderTradeMenu.setContentHeight(image.getHeight());
            // get the cocktail from the image
            final Rectangle cocktail = bartenderTradeMenu.getCocktail();
            BufferedImage subimage = image.getSubimage((int) cocktail.getX(), (int) cocktail.getY(), (int) cocktail.getWidth(), (int) cocktail.getHeight());

            BufferedImage convertedImg = new BufferedImage(subimage.getWidth(), subimage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            convertedImg.getGraphics().drawImage(subimage, 0, 0, null);
            BartenderTradeARMenu bartenderTradeARMenu = new BartenderTradeARMenu();
            bartenderTradeARMenu.clear();
            bartenderTradeARMenu.update(0,0, 2560, 1440);
            boolean isBartenderMenu = bartenderTradeARMenu.isBartenderMenu(convertedImg, null);
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
            BartenderTradeMenu bartenderTradeMenu = new BartenderTradeMenu();
            bartenderTradeMenu.setContentWidth(image.getWidth());
            bartenderTradeMenu.setContentHeight(image.getHeight());
            // get the cocktail from the image
            final Rectangle cocktail = bartenderTradeMenu.getCocktail();
            BufferedImage subimage = image.getSubimage((int) cocktail.getX(), (int) cocktail.getY(), (int) cocktail.getWidth(), (int) cocktail.getHeight());

            BufferedImage convertedImg = new BufferedImage(subimage.getWidth(), subimage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            convertedImg.getGraphics().drawImage(subimage, 0, 0, null);
            BartenderTradeARMenu bartenderTradeARMenu = new BartenderTradeARMenu();
            bartenderTradeARMenu.clear();
            bartenderTradeARMenu.update(0,0, 2560, 1440);
            boolean isBartenderMenu = bartenderTradeARMenu.isBartenderMenu(convertedImg, null);
            Assertions.assertFalse(isBartenderMenu, "Expected the image to be recognized as a bartender menu.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
