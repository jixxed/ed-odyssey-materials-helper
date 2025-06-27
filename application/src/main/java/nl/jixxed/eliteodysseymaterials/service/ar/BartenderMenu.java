package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.BartenderMenuType;

import java.awt.*;
import java.util.ArrayList;

public class BartenderMenu {
    @Getter
    java.util.List<Asset> visibleAssets = new ArrayList<>();
    @Getter
    private double scale;
    @Getter
    private double contentHeight;
    @Getter
    private double contentWidth;
    @Getter
    private Rectangle menu;
    @Setter
    @Getter
    private BartenderMenuType subMenu;

    public BartenderMenu() {
        this.scale = 1;
        this.contentHeight = 1600;
        this.contentWidth = 3840;//2844.445;
        this.subMenu = BartenderMenuType.NONE;
        setupMenu();
    }

    public double getFontSize() {
        return 50 * this.scale;
    }

    double getHeaderFontSize() {
        return 30 * this.scale;
    }

    private void setupScale() {
        if ((this.contentWidth / this.contentHeight > 1920D / 1080D)) {
            //UW
            this.scale = this.contentHeight / 1600D;
//            this.scale = this.contentWidth / (1600D / 9D * 16D);
        } else if ((this.contentWidth / this.contentHeight < 1920D / 1080D)) {
            //4:3
//            this.scale = this.contentHeight / 1600D;
            this.scale = this.contentWidth / (1600D / 9D * 16D);
        } else {
            this.scale = this.contentHeight / 1600D;
        }
    }

    private void setupMenu() {
        if ((this.contentWidth / this.contentHeight > 1920D / 1080D)) {
            //UW
            final double menuWidth = this.contentHeight / 9D * 16D;
            this.menu = new Rectangle(this.contentWidth / 2.0 - menuWidth / 2,
                    0,
                    this.contentWidth / 2.0 + menuWidth / 2,
                    this.contentHeight);
        } else if ((this.contentWidth / this.contentHeight < 1920D / 1080D)) {
            //4:3
            final double menuHeight = this.contentWidth / 16D * 9D;
            this.menu = new Rectangle(0,
                    this.contentHeight / 2.0 - menuHeight / 2,
                    this.contentWidth,
                    this.contentHeight / 2.0 + menuHeight / 2);
        } else {
            this.menu = new Rectangle(0,
                    0,
                    this.contentWidth,
                    this.contentHeight);

        }
//        final double menuWidth = 2844.445 * this.scale;
//        final double menuHeight = 1600 * this.scale;
//        this.menu = new Rectangle(this.contentWidth / 2.0 - menuWidth / 2,
//                this.contentHeight / 2.0 - menuHeight / 2,
//                this.contentWidth / 2.0 + menuWidth / 2,
//                this.contentHeight / 2.0 + menuHeight / 2);
    }

    public Rectangle getCocktail() {
        //585,64 - 109, 109
        //497,77778
        return new Rectangle(
                this.menu.getX() + 82 * this.scale,
                this.menu.getY() + 63 * this.scale,
                this.menu.getX() + (82 + 109) * this.scale + 8,
                this.menu.getY() + (63 + 109) * this.scale + 8
        );
    }

//    public void setScale(final double scale) {
//        if (this.scale != scale) {
//            this.scale = scale;
//            setupMenu();
//        } else {
//            this.scale = scale;
//        }
//    }

    public void setContentHeight(final int contentHeight) {
        if (this.contentHeight != contentHeight) {
            this.contentHeight = contentHeight;
            setupScale();
            setupMenu();
        } else {
            this.contentHeight = contentHeight;
        }
    }

    public void setContentWidth(final int contentWidth) {
        if (this.contentWidth != contentWidth) {
            this.contentWidth = contentWidth;
            setupScale();
            setupMenu();
        } else {
            this.contentWidth = contentWidth;
        }

    }

    Rectangle getSubMenuEntry() {
        //740
        //154
        final int x = (int) (this.menu.getX() + 88 * this.scale);
        final int y = (int) (this.menu.getY() + 377 * this.scale);
        final int x2 = (int) (this.menu.getX() + 1272 * this.scale);
        final int y2 = (int) (this.menu.getY() + 448 * this.scale);

        return new Rectangle(x, y, x2, y2);
    }

    public Rectangle getSubMenuEntryText(final int index) {
        //740
        //154
        final int offsetY = index * 78;
        final int x = (int) (230 * this.scale);
        final int y = (int) ((377 + 16 + offsetY) * this.scale);
        final int x2 = (int) (772 * this.scale);
        final int y2 = (int) ((377 + 70 - 16 + offsetY) * this.scale);

        return new Rectangle(x, y, x2, y2);
    }

    Rectangle getMenuEntry() {
        final int x = (int) (this.menu.getX() + 357 * this.scale);
        final int y = (int) (this.menu.getY() + 377 * this.scale);
        final int x2 = (int) (this.menu.getX() + 1541 * this.scale);
        final int y2 = (int) (this.menu.getY() + 448 * this.scale);

        return new Rectangle(x, y, x2, y2);
    }

    public Rectangle getMenuEntryText(final int index) {
        final int offsetY = index * 78;
        final int x = (int) (500 * this.scale);
        final int y = (int) ((377 + 16 + offsetY) * this.scale);
        final int x2 = (int) (1031 * this.scale);
        final int y2 = (int) ((377 + 70 - 16 + offsetY) * this.scale);

        return new Rectangle(x, y, x2, y2);
    }

    Point getSubMenuDetectionLeftPixel() {
//        return new Point((int) (142D * this.scale), (int) (204D * this.scale));
        return new Point((int) (370D * this.scale), (int) (204D * this.scale));
    }

    Point getSubMenuDetectionRightPixel() {
        return new Point((int) (2040D * this.scale), (int) (204D * this.scale));
    }

    Point getSellMenuDetectionLeftPixel() {
        return new Point((int) (142D * this.scale), (int) (135D * this.scale));

    }

    Point getSellMenuDetectionRightPixel() {
        return new Point((int) (2688D * this.scale), (int) (171D * this.scale));

    }

    Point getSellMenuDetectionRightPixel2() {
        return new Point((int) (2750D * this.scale), (int) (171D * this.scale));

    }

    Rectangle getMenuItem(final Asset asset) {
        final int offsetX = (this.subMenu.equals(BartenderMenuType.SUBMENU)) ? 0 : 445;
        final int offsetY = this.visibleAssets.indexOf(asset) * 78;
        final int x = (int) (this.menu.getX() + (1035 + offsetX) * this.scale);//88start
        final int y = (int) (this.menu.getY() + (377 + offsetY) * this.scale);
        final int x2 = (int) (this.menu.getX() + (1272 + offsetX) * this.scale);
        final int y2 = (int) (this.menu.getY() + (448 + offsetY) * this.scale);

        return new Rectangle(x, y, x2, y2);
    }

    Point getWishlistTextPosition(final Asset asset) {
        final int offsetX = (this.subMenu.equals(BartenderMenuType.SUBMENU)) ? 0 : 445;
        final int offsetY = this.visibleAssets.indexOf(asset) * 78;
        final int x = (int) (this.menu.getX() + (1035 + 70 + offsetX) * this.scale);//88start
        final int y = (int) (this.menu.getY() + (377 + 5 + offsetY) * this.scale);

        return new Point(x, y);
    }

    Point getFleetCarrierTextPosition(final Asset asset) {
        final int offsetX = (this.subMenu.equals(BartenderMenuType.SUBMENU)) ? 0 : 445;
        final int offsetY = this.visibleAssets.indexOf(asset) * 78;
        final int x = (int) (this.menu.getX() + (1035 - 145 + offsetX) * this.scale);//88start
        final int y = (int) (this.menu.getY() + (377 + 5 + offsetY) * this.scale);

        return new Point(x, y);
    }

    Point getWishlistHeaderPosition() {
        final int offsetX = (this.subMenu.equals(BartenderMenuType.SUBMENU)) ? 0 : 445;
        final int offsetY = -78;
        final int x = (int) (this.menu.getX() + (1035 + 70 + offsetX) * this.scale);//88start
        final int y = (int) (this.menu.getY() + (377 + 35 + offsetY) * this.scale);

        return new Point(x, y);
    }

    Point getFleetCarrierHeaderPosition() {
        final int offsetX = (this.subMenu.equals(BartenderMenuType.SUBMENU)) ? 0 : 445;
        final int offsetY = -78;
        final int x = (int) (this.menu.getX() + (1035 - 145 + offsetX) * this.scale);//88start
        final int y = (int) (this.menu.getY() + (377 + 35 + offsetY) * this.scale);

        return new Point(x, y);
    }

    Point getLine11LeftPixel() {
        final int offsetX = -5;
        final int offsetY = 35;
        final int x = (int) ((357 + offsetX) * this.scale);
        final int y = (int) ((1157 + offsetY) * this.scale);
        return new Point(x, y);
    }

    Point getLine11RightPixel() {
        final int offsetX = 5;
        final int offsetY = 35;
        final int x = (int) ((357 + offsetX) * this.scale);
        final int y = (int) ((1157 + offsetY) * this.scale);
        return new Point(x, y);
    }

    Point getLine12LeftPixel() {
        final int offsetX = -5;
        final int offsetY = 35;
        final int x = (int) ((357 + offsetX) * this.scale);
        final int y = (int) ((1235 + offsetY) * this.scale);
        return new Point(x, y);
    }

    Point getLine12RightPixel() {
        final int offsetX = 5;
        final int offsetY = 35;
        final int x = (int) ((357 + offsetX) * this.scale);
        final int y = (int) ((1235 + offsetY) * this.scale);
        return new Point(x, y);
    }
}
