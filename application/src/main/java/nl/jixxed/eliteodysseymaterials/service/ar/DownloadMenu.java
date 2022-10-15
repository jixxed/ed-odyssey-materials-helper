package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.DataPortType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DownloadMenu {
    private boolean hasWarning;
    Map<Integer, Rectangle> menuItems = new HashMap<>();
    private Rectangle menu;
    private double scale;
    private int contentHeight;
    private int contentWidth;
    private DataPortType type;
    private String dataPortName;
    private ScrollBar scrollBar;
    private final Map<Integer, OdysseyMaterial> downloadData = new ConcurrentHashMap<>();
    private final Map<Integer, Boolean> scanned = new ConcurrentHashMap<>();

    public DownloadMenu(final double scale, final boolean hasWarning, final ScrollBar scrollBar, final int contentWidth, final int contentHeight) {
        this.scale = scale;
        this.scrollBar = scrollBar;
        this.hasWarning = hasWarning;
        this.contentHeight = contentHeight;
        this.contentWidth = contentWidth;
        setupMenu();
        setupMenuItems();
    }

    private void setupMenu() {
        final double menuWidth = 1625 * this.scale;
        final double menuHeight = 951 * this.scale;
        this.menu = new Rectangle(this.contentWidth / 2.0 - menuWidth / 2,
                this.contentHeight / 2.0 - menuHeight / 2,
                this.contentWidth / 2.0 + menuWidth / 2,
                this.contentHeight / 2.0 + menuHeight / 2);
    }

    private void setupMenuItems() {
        final double menuItemOffsetX = 25 * this.scale;
        final double menuItemOffsetY = 275 * this.scale;
        final double menuItemHeight = 100 * this.scale;
        final double menuItemWidth = 20 * this.scale;
        final double menuItemSeperation = 4 * this.scale;
        final double menuItemSpacing = menuItemHeight + menuItemSeperation;
        this.menuItems.put(1, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY,
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + menuItemHeight)
        );
        this.menuItems.put(2, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 1),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + menuItemHeight + (menuItemSpacing * 1))
        );
        this.menuItems.put(3, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 2),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + menuItemHeight + (menuItemSpacing * 2))
        );
        this.menuItems.put(4, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 3),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + menuItemHeight + (menuItemSpacing * 3))
        );
        this.menuItems.put(5, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 4),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + menuItemHeight + (menuItemSpacing * 4))
        );
        this.menuItems.put(6, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 5),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + menuItemHeight + (menuItemSpacing * 5))
        );
    }

    public double getMenuItemPositionYOffset() {
        double offset = (this.hasWarning) ? 71 : 0;
        offset -= switch (this.scrollBar.getPosition()) {
            case DOWN -> {
                if (this.scrollBar.getSize() == 6 && this.hasWarning) {
                    yield 139;
                }
                if (this.scrollBar.getSize() == 6) {
                    yield 68;
                } else {
                    yield 35;
                }
            }
            case DOWN_MIDDLE -> 104;//only with 6 and warning
            case MIDDLE -> 70;//only with 6 and warning
            case UP_MIDDLE -> 40;//only with 6 and warning
            case UP -> 0;
            case NONE -> 0;
        };
        return offset * this.scale;
    }

    public Rectangle getMenuItem(final int index) {
        return this.menuItems.get(index);
    }

    double getMenuItemX(final int index) {
        return this.menuItems.get(index).getX();
    }

    double getMenuItemY(final int index) {
        if (this.hasWarning && ScrollPosition.DOWN == this.scrollBar.getPosition() && this.scrollBar.getSize() == 5 && index == 1) {
            return this.menuItems.get(index).getY() + (36 * this.scale);
        }
        if (!this.hasWarning && ScrollPosition.DOWN == this.scrollBar.getPosition() && this.scrollBar.getSize() == 6 && index == 1) {
            return this.menuItems.get(index).getY() + (67 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.DOWN == this.scrollBar.getPosition() && (index == 2)) {
            return this.menuItems.get(index).getY() + (35 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.UP_MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
            return this.menuItems.get(index).getY() + (41 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
            return this.menuItems.get(index).getY() + (70 * this.scale);
        }

        return this.menuItems.get(index).getY();
    }

    double getMenuItemWidth(final int index) {
        return this.menuItems.get(index).getWidth();
    }

    double getMenuItemWidth() {
        return 1574 * this.scale;
    }

    double getMenuItemHeight(final int index) {
        if (this.hasWarning && ScrollPosition.DOWN == this.scrollBar.getPosition() && this.scrollBar.getSize() == 5 && index == 1) {
            return this.menuItems.get(index).getHeight() - (36 * this.scale);
        }
        if (this.hasWarning && ScrollPosition.UP == this.scrollBar.getPosition() && this.scrollBar.getSize() == 5 && index == 5) {
            return this.menuItems.get(index).getHeight() - (34 * this.scale);
        }
        if (!this.hasWarning && ScrollPosition.UP == this.scrollBar.getPosition() && this.scrollBar.getSize() == 6 && index == 6) {
            return this.menuItems.get(index).getHeight() - (67 * this.scale);
        }
        if (!this.hasWarning && ScrollPosition.DOWN == this.scrollBar.getPosition() && this.scrollBar.getSize() == 6 && index == 1) {
            return this.menuItems.get(index).getHeight() - (67 * this.scale);
        }
        if (this.hasWarning && ScrollPosition.UP == this.scrollBar.getPosition() && this.scrollBar.getSize() == 6 && index == 6) {
            return this.menuItems.get(index).getHeight() - (67 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.DOWN == this.scrollBar.getPosition() && (index == 2)) {
            return this.menuItems.get(index).getHeight() - (35 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.DOWN_MIDDLE == this.scrollBar.getPosition() && (index == 6)) {
            return this.menuItems.get(index).getHeight() - (35 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.UP_MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
            return this.menuItems.get(index).getHeight() - (41 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.UP == this.scrollBar.getPosition() && (index == 5)) {
            return this.menuItems.get(index).getHeight() - (35 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
            return this.menuItems.get(index).getHeight() - (70 * this.scale);
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.MIDDLE == this.scrollBar.getPosition() && (index == 6)) {
            return this.menuItems.get(index).getHeight() - (70 * this.scale);
        }
        return this.menuItems.get(index).getHeight();
    }

    boolean isMenuItemLabelVisible(final int index) {
        //only case for 5
//        if (this.hasWarning && ScrollPosition.DOWN == this.scrollBar.getPosition() && this.scrollBar.getSize() == 5 && index == 1) {
//            return false;
//        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.DOWN == this.scrollBar.getPosition() && (index == 2)) {
            return false;
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.UP_MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
            return false;
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
            return false;
        }
        return true;
    }

    public boolean isMenuItemVisible(final int index) {
//        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.MIDDLE == this.scrollBar.getPosition() && (index == 1 || index == 6)) {
//            return false;
//        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.DOWN == this.scrollBar.getPosition() && (index == 1)) {
            return false;
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.DOWN_MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
            return false;
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.UP_MIDDLE == this.scrollBar.getPosition() && (index == 6)) {
            return false;
        }
        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.UP == this.scrollBar.getPosition() && (index == 6)) {
            return false;
        }
        return true;
    }

    public boolean isMenuItemVisibleForOCR(final int index) {
        if (ScrollPosition.MIDDLE == this.scrollBar.getPosition() && (index == 1 || index == 6)) {
            return false;
        }
        if (ScrollPosition.DOWN_MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
            return false;
        }
        if (ScrollPosition.UP_MIDDLE == this.scrollBar.getPosition() && (index == 1 || index == 6)) {
            return false;
        }
        if (ScrollPosition.UP == this.scrollBar.getPosition() && this.scrollBar.getSize() == 6 && (index == 6)) {
            return false;
        }
        if (ScrollPosition.DOWN == this.scrollBar.getPosition() && this.hasWarning && (index <= this.scrollBar.getSize() - 4)) {
            return false;
        }
        if (ScrollPosition.DOWN == this.scrollBar.getPosition() && this.scrollBar.getSize() == 6 && (index == 1)) {
            return false;
        }
        return true;
    }

    public int menuSize() {
        return this.scrollBar.getSize();
    }

    public boolean isScanned(final Integer index) {
        return this.scanned.get(index) != null;
    }


    public double getFontSize() {
        return 24 * this.scale;
    }

    public Rectangle getMenuTextReadOffset() {
        return new Rectangle(105 * this.scale, 18 * this.scale, 700 * this.scale, 55 * this.scale);
    }

    double getMenuTextWriteOffsetX() {
        return 1170 * this.scale;
    }

    double getMenuTextWriteOffsetY() {
        return 1;
    }

    public void setScale(final double scale) {
        if (this.scale != scale) {
            this.scale = scale;
            setupMenu();
            setupMenuItems();
        } else {
            this.scale = scale;
        }
    }

    public void setContentHeight(final int contentHeight) {
        if (this.contentHeight != contentHeight) {
            this.contentHeight = contentHeight;
            setupMenu();
            setupMenuItems();
        } else {
            this.contentHeight = contentHeight;
        }
    }

    public void setContentWidth(final int contentWidth) {
        if (this.contentWidth != contentWidth) {
            this.contentWidth = contentWidth;
            setupMenu();
            setupMenuItems();
        } else {
            this.contentWidth = contentWidth;
        }

    }

    public Rectangle getArrow() {
        return new Rectangle(
                this.menu.getX() + 37 * this.scale,
                this.menu.getY() + 28 * this.scale,
                this.menu.getX() + (37 + 60) * this.scale + 3,
                this.menu.getY() + (28 + 65) * this.scale + 3
        );
    }


    public Rectangle getTerminalType() {
        return new Rectangle(1125 * this.scale,
                75 * this.scale,
                1600 * this.scale,
                105 * this.scale
        );
    }
}
