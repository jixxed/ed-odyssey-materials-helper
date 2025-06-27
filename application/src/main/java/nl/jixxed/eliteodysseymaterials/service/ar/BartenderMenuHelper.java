package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.BartenderMenuType;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public class BartenderMenuHelper {


    public static BartenderMenuType getMenuType(final BufferedImage bartenderMenuCapture, final BartenderMenu bartenderMenu) {
        if (bartenderMenuCapture == null) {
            return BartenderMenuType.NONE;
        }
        final Point leftPixel = bartenderMenu.getSubMenuDetectionLeftPixel();
        final Point rightPixel = bartenderMenu.getSubMenuDetectionRightPixel();
        final int leftRGB = bartenderMenuCapture.getRGB(leftPixel.x, leftPixel.y);
        final int rightRGB = bartenderMenuCapture.getRGB(rightPixel.x, rightPixel.y);
        if (leftRGB == rightRGB) {
            return BartenderMenuType.SUBMENU;
        } else if (isMainMenuCircuits(bartenderMenuCapture, bartenderMenu)) {//test 12
            return BartenderMenuType.MAIN_CIRCUITS;
        } else if (isMainMenuTech(bartenderMenuCapture, bartenderMenu)) {//test 11
            return BartenderMenuType.MAIN_TECH;
        } else {
            return BartenderMenuType.MAIN_CHEMICALS;
        }
    }

    private static boolean isMainMenuTech(final BufferedImage bartenderMenuCapture, final BartenderMenu bartenderMenu) {
        final Point leftPixel = bartenderMenu.getLine11LeftPixel();
        final Point rightPixel = bartenderMenu.getLine11RightPixel();
        return bartenderMenuCapture.getRGB(leftPixel.x, leftPixel.y) != bartenderMenuCapture.getRGB(rightPixel.x, rightPixel.y);
    }

    private static boolean isMainMenuCircuits(final BufferedImage bartenderMenuCapture, final BartenderMenu bartenderMenu) {
        final Point leftPixel = bartenderMenu.getLine12LeftPixel();
        final Point rightPixel = bartenderMenu.getLine12RightPixel();
        return bartenderMenuCapture.getRGB(leftPixel.x, leftPixel.y) != bartenderMenuCapture.getRGB(rightPixel.x, rightPixel.y);
    }

    public static boolean isTradeMenu(final BufferedImage bartenderMenuCapture, final BartenderMenu bartenderMenu) {
        return bartenderMenuCapture != null && !isSellMenu(bartenderMenuCapture, bartenderMenu);
    }

    private static boolean isSellMenu(final BufferedImage bartenderMenuCapture, final BartenderMenu bartenderMenu) {
        if (bartenderMenuCapture == null) {
            return false;
        }
        final Point leftPixel = bartenderMenu.getSellMenuDetectionLeftPixel();//cocktail
        // 2 pixel check in case of a mouse over
        final Point rightPixel = bartenderMenu.getSellMenuDetectionRightPixel();//balance triangle bottom left
        final Point rightPixel2 = bartenderMenu.getSellMenuDetectionRightPixel2();//balance triangle bottom right
        final int left = bartenderMenuCapture.getRGB(leftPixel.x, leftPixel.y);
        final int right = bartenderMenuCapture.getRGB(rightPixel.x, rightPixel.y);
        final int right2 = bartenderMenuCapture.getRGB(rightPixel2.x, rightPixel2.y);
        return left == right || left == right2;
    }

}
