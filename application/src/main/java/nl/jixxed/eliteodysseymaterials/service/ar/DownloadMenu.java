package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DownloadMenu {
    private boolean hasWarning;
    Map<Integer, Rectangle> menuItems = new HashMap<>();
    private double menuItemHeight;
    private double menuItemWidth;
    private double menuItemSeperation;
    private double menuTextWriteOffsetX;
    private double menuTextWriteOffsetY;
    private Rectangle menuTextReadOffset;
    private Rectangle arrow;
    private Rectangle menu;
    private double scale;
    private int contentHeight;
    private int appHeight;
    private int contentWidth;
    private int appWidth;
    private ScrollBar scrollBar;
    private final Map<Integer, OdysseyMaterial> downloadData = new ConcurrentHashMap<>();
    private final Map<Integer, Boolean> scanned = new ConcurrentHashMap<>();

    public DownloadMenu(final double scale, final double identifierX, final double identifierY, final double identifierW, final double identifierH, final boolean hasWarning, final ScrollBar scrollBar, final int contentHeight, final int contentWidth, final int contentX, final int contentY) {
        this.scale = scale;
        this.scrollBar = scrollBar;
        this.hasWarning = hasWarning;
        this.menuItemHeight = 100 * scale;
        this.menuItemWidth = 1574 * scale;
        this.menuItemSeperation = 4 * scale;
        this.contentHeight = contentHeight;
        this.contentWidth = contentWidth;
//        this.appHeight = appHeight;
//        this.appWidth = appWidth;
        this.menuTextWriteOffsetX = 1220 * scale;
        this.menuTextWriteOffsetY = 25 * scale;
        final double menuItemWidth = 20 * scale;

        this.menuTextReadOffset = new Rectangle(105 * scale, 18 * scale, 600 * scale, 50 * scale);
        this.arrow = new Rectangle(identifierX - 2, identifierY - 2, identifierX + identifierW + 2, identifierY + identifierH + 2);
//        this.menu = new Rectangle(this.arrow.getX() - (35 * scale), this.arrow.getY() - (29 * scale), this.arrow.getX() - (32 * scale) + (1615 * scale), this.arrow.getY() - (25 * scale) + (951 * scale));
        final double menuWidth = 1625 * scale;
        final double menuHeight = 951 * scale;
        this.menu = new Rectangle((double) contentWidth / 2 - menuWidth / 2,
                (double) contentHeight / 2 - menuHeight / 2,
                (double) contentWidth / 2 + menuWidth / 2,
                (double) contentHeight / 2 + menuHeight / 2);
        final double menuItemOffsetX = 25 * scale;
        final double menuItemOffsetY = 275 * scale;
        final double menuItemSpacing = this.menuItemHeight + this.menuItemSeperation;
        this.menuItems.put(1, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY,
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + this.menuItemHeight)
        );
        this.menuItems.put(2, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 1),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + this.menuItemHeight + (menuItemSpacing * 1))
        );
        this.menuItems.put(3, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 2),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + this.menuItemHeight + (menuItemSpacing * 2))
        );
        this.menuItems.put(4, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 3),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + this.menuItemHeight + (menuItemSpacing * 3))
        );
        this.menuItems.put(5, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 4),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + this.menuItemHeight + (menuItemSpacing * 4))
        );
        this.menuItems.put(6, new Rectangle(
                menuItemOffsetX,
                menuItemOffsetY + (menuItemSpacing * 5),
                menuItemOffsetX + menuItemWidth,
                menuItemOffsetY + this.menuItemHeight + (menuItemSpacing * 5))
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

    public double getMenuItemX(final int index) {
        return this.menuItems.get(index).getX();
    }

    public double getMenuItemY(final int index) {
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
//        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.DOWN_MIDDLE == this.scrollBar.getPosition() && (index == 5)) {
//            return this.menuItems.get(index).getY() + (38 * this.scale);
//        }
//        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.UP == this.scrollBar.getPosition() && (index == 5)) {
//            return this.menuItems.get(index).getY() - (38 * this.scale);
//        }
//        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.MIDDLE == this.scrollBar.getPosition() && (index == 1)) {
//            return this.menuItems.get(index).getY() + (67 * this.scale);
//        }
//        if (this.hasWarning && this.scrollBar.getSize() == 6 && ScrollPosition.MIDDLE == this.scrollBar.getPosition() && (index == 6)) {
//            return this.menuItems.get(index).getY() + (67 * this.scale);
//        }

        return this.menuItems.get(index).getY();
    }

    public double getMenuItemWidth(final int index) {
        return this.menuItems.get(index).getWidth();
    }

    public double getMenuItemHeight(final int index) {
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

    public boolean isMenuItemLabelVisible(final int index) {
        if (this.hasWarning && ScrollPosition.DOWN == this.scrollBar.getPosition() && this.scrollBar.getSize() == 5 && index == 1) {
            return false;
        }
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
}
