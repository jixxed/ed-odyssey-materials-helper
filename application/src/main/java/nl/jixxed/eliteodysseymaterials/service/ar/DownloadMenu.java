package nl.jixxed.eliteodysseymaterials.service.ar;

import javafx.scene.paint.Color;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.DataPortType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class DownloadMenu {
    public static final int VIEWPORT_HEIGHT = 553;
    private boolean hasWarning;
    Map<Integer, Rectangle> menuItems = new HashMap<>();
    private Rectangle menu;
    private double scale;
    private int contentHeight;
    private int contentWidth;
    private DataPortType type;
    private String dataPortName;
    private ScrollBarV2 scrollBar;
    private final Map<Integer, OdysseyMaterial> downloadData = new ConcurrentHashMap<>();
    private final Map<Integer, Boolean> scanned = new ConcurrentHashMap<>();
    private int menuSize;
    private BufferedImage downloadMenuCapture;

    public DownloadMenu(final BufferedImage downloadMenuCapture, final double scale, final boolean hasWarning, final ScrollBarV2 scrollBar, final int contentWidth, final int contentHeight) {
        this.scale = scale;
        this.scrollBar = scrollBar;
        this.hasWarning = hasWarning;
        this.contentHeight = contentHeight;
        this.contentWidth = contentWidth;
        this.menuSize = 0;
        this.downloadMenuCapture = downloadMenuCapture;
        setupMenuSize();
        setupMenu();
        setupMenuItems();
    }


    public int detectMenuSize(BufferedImage downloadMenuCapture) {
        try {
            final double menuItemOffsetX = 25 * this.scale;
            final double menuItemOffsetY = 275 * this.scale;
            final double menuItemHeight = 100 * this.scale;
            final double menuItemSeperation = 4 * this.scale;
            final double menuItemSpacing = menuItemHeight + menuItemSeperation;
            final double menuItemWidth = 20 * this.scale;
            final Point pixelOffset = new Point((int) ((5 * this.scale) + menuItemWidth), (int) (10 * this.scale));

            byte[] colorAvailable = convertColor(PreferencesService.getPreference(PreferenceConstants.AVAILABLE_DATAPORT_COLOR, "0x452801FF"));
            byte[] colorAvailableHighlight = convertColor(PreferencesService.getPreference(PreferenceConstants.AVAILABLE_HIGHLIGHT_DATAPORT_COLOR, "0xFF9500FF"));
            byte[] colorDownloaded = convertColor(PreferencesService.getPreference(PreferenceConstants.DOWNLOADED_DATAPORT_COLOR, "0x3D3D3DFF"));
            byte[] colorDownloadedHighlight = convertColor(PreferencesService.getPreference(PreferenceConstants.DOWNLOADED_HIGHLIGHT_DATAPORT_COLOR, "0xD6D6D6FF"));

            int matches = 0;
            for (int i = 0; i < 5; i++) {
                final WritableRaster dataColorPixelTest = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (menuItemOffsetX + pixelOffset.x),
                        (int) (menuItemOffsetY + (menuItemSpacing * i) + pixelOffset.y), 1, 1))).createWritableTranslatedChild(0, 0);
                byte[] colorToTest = DataBufferHelper.getData(dataColorPixelTest.getDataBuffer());
                log.debug("colorToTest {}: {}", i + 1, toHex(colorToTest));
                if (matchesOneOfColors(colorToTest, colorAvailable, colorAvailableHighlight, colorDownloaded, colorDownloadedHighlight)) {
                    matches++;
                }
            }
            return matches;
        } catch (Exception e) {
            log.error("Error detecting menu size", e);
            return 0;
        }
    }

    private byte[] convertColor(String webColor) {
        Color color = Color.web(webColor);
        return new byte[]{
                (byte) (color.getOpacity() * 255),
                (byte) (color.getBlue() * 255),
                (byte) (color.getGreen() * 255),
                (byte) (color.getRed() * 255)
        };
    }

    private String toHex(byte[] color) {//ARGB color
        return String.format("#%02X%02X%02X%02X", color[1], color[2], color[3], color[0]);//RGBA to ARGB
    }

    private boolean matchesOneOfColors(byte[] colorToTest, byte[] colorToMatch1, byte[] colorToMatch2, byte[] colorToMatch3, byte[] colorToMatch4) {
        return matchesColor(colorToTest, colorToMatch1) || matchesColor(colorToTest, colorToMatch2) || matchesColor(colorToTest, colorToMatch3) || matchesColor(colorToTest, colorToMatch4);
    }

    private boolean matchesColor(byte[] colorToTest, byte[] colorToMatch) {
        int range = 8;
        return colorToTest[0] >= colorToMatch[0] - range && colorToTest[0] <= colorToMatch[0] + range
                && colorToTest[1] >= colorToMatch[1] - range && colorToTest[1] <= colorToMatch[1] + range
                && colorToTest[2] >= colorToMatch[2] - range && colorToTest[2] <= colorToMatch[2] + range
                && colorToTest[3] >= colorToMatch[3] - range && colorToTest[3] <= colorToMatch[3] + range;
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
        double offset = (this.hasWarning) ? 71 : 0;
        final double menuItemOffsetX = 25 * this.scale;
        final double menuItemOffsetY = (275 + offset) * this.scale;
        final double menuItemHeight = 100 * this.scale;
        final double menuItemWidth = 20 * this.scale;
        final double menuItemSeperation = 4 * this.scale;
        final double menuItemSpacing = menuItemHeight + menuItemSeperation;
        final double menuItemPositionYOffset = getMenuItemPositionYOffset();
        this.menuItems.clear();
        for (int i = 1; i < menuSize + 1; i++) {
            double x = menuItemOffsetX;
            double y = menuItemOffsetY + (menuItemSpacing * (i - 1)) - menuItemPositionYOffset;
            this.menuItems.put(i, new Rectangle(
                    x,
                    y,
                    x + menuItemWidth,
                    y + menuItemHeight)
            );
        }
    }

    public double getMenuItemPositionYOffset() {
        if (this.scrollBar == null)
            return 0;
        if (!scrollBar.isActive()) {
            return 0;
        }
        double offset = (this.hasWarning) ? 71 : 0;
        double viewportHeight = (VIEWPORT_HEIGHT - offset) * this.scale;
        double menuHeight = menuSize * ((100 + 4) * this.scale);
        return (menuHeight - viewportHeight) * this.scrollBar.getPosition() / 100.0;
    }

    public Rectangle getVisibleViewPortRect() {
        final double menuItemOffsetX = 25 * this.scale;
        final double menuItemOffsetY = 275 * this.scale;
        final double menuWidth = 1575 * this.scale;
        double offset = (this.hasWarning) ? 71 : 0;
        double viewportHeight = (VIEWPORT_HEIGHT - offset) * this.scale;
        return new Rectangle(menuItemOffsetX, menuItemOffsetY + (offset * this.scale), menuItemOffsetX + menuWidth, menuItemOffsetY + viewportHeight + (offset * this.scale));
    }

    public Rectangle getMenuItem(final int index) {
        return this.menuItems.get(index);
    }

    double getMenuItemX(final int index) {
        return this.menuItems.get(index).getX();
    }

    double getMenuItemY(final int index) {
        return getMenuItem(index).getY() - getMenuItemPositionYOffset();

    }

    double getMenuItemWidth(final int index) {
        return this.menuItems.get(index).getWidth();
    }

    double getMenuItemWidth() {
        return 1574 * this.scale;
    }

    /**
     * visible height of menu item in viewport
     *
     * @param index
     * @return
     */
    double getMenuItemVisibleHeight(final int index) {
        Rectangle visibleViewPortRect = getVisibleViewPortRect();
        Rectangle rectangle = this.menuItems.get(index);
        double height = visibleViewPortRect.getAwtRectangle().createIntersection(rectangle.getAwtRectangle()).getHeight();
        return Math.max(0, height);
    }

    /**
     * Whether there is enough space to show the label of the menu item
     *
     * @param index
     * @return
     */
    boolean isMenuItemLabelVisible(final int index) {
        //label for inventory
        var requiredHeight = getFontSize() * 2;
        return getMenuItemVisibleHeight(index) - requiredHeight > 0;
    }

    /**
     * Whether there is enough space to show the menu item at all
     *
     * @param index
     * @return
     */
    public boolean isMenuItemVisible(final int index) {
        //menu entry
        return getMenuItemVisibleHeight(index) > 0;
    }

    /**
     * Whether the text for ocr is visible
     *
     * @param index
     * @return
     */
    public boolean isMenuItemVisibleForOCR(final int index) {
        //menu entry text for ocr
        Rectangle menuItem = this.menuItems.get(index);
        double x = menuItem.getX() + getMenuTextReadOffset().getX();
        double y = menuItem.getY() + getMenuTextReadOffset().getY();
        Rectangle menuItemOCRRect = new Rectangle(x, y,
                x + getMenuTextReadOffset().getWidth(), y + getMenuTextReadOffset().getHeight());
        Rectangle visibleViewPortRect = getVisibleViewPortRect();
        return visibleViewPortRect.getAwtRectangle().contains(menuItemOCRRect.getAwtRectangle());
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
            setupMenuSize();
            setupMenu();
            setupMenuItems();
        } else {
            this.scale = scale;
        }
    }

    public void setContentHeight(final int contentHeight) {
        if (this.contentHeight != contentHeight) {
            this.contentHeight = contentHeight;
            setupMenuSize();
            setupMenu();
            setupMenuItems();
        }
    }

    public void setContentWidth(final int contentWidth) {
        if (this.contentWidth != contentWidth) {
            this.contentWidth = contentWidth;
            setupMenuSize();
            setupMenu();
            setupMenuItems();
        }
    }

    public void updateMenuState(BufferedImage downloadMenuCapture, ScrollBarV2 scrollBar, boolean hasWarning) {
        boolean changed = false;
        if (!Objects.equals(this.downloadMenuCapture, downloadMenuCapture)) {
            this.downloadMenuCapture = downloadMenuCapture;
            changed = true;
        }
        if (!Objects.equals(this.scrollBar, scrollBar)) {
            this.scrollBar = scrollBar;
            changed = true;
        }
        if (this.hasWarning != hasWarning) {
            this.hasWarning = hasWarning;
            changed = true;
        }
        if (changed) {
            setupMenuSize();
            setupMenu();
            setupMenuItems();
        }
    }

    public void setDownloadMenuCapture(BufferedImage downloadMenuCapture) {
        if (!Objects.equals(this.downloadMenuCapture, downloadMenuCapture)) {
            this.downloadMenuCapture = downloadMenuCapture;
            setupMenuSize();
            setupMenu();
            setupMenuItems();
        } else {
            this.downloadMenuCapture = downloadMenuCapture;
        }
    }

    public void setScrollBar(ScrollBarV2 scrollBar) {
        if (!Objects.equals(this.scrollBar, scrollBar)) {
            this.scrollBar = scrollBar;
            setupMenuSize();
            setupMenu();
            setupMenuItems();
        } else {
            this.scrollBar = scrollBar;
        }
    }

    public void setHasWarning(boolean hasWarning) {
        if (this.hasWarning != hasWarning) {
            this.hasWarning = hasWarning;
            setupMenuSize();
            setupMenu();
            setupMenuItems();
        } else {
            this.hasWarning = hasWarning;
        }
    }

    private void setupMenuSize() {
        if (scrollBar != null && scrollBar.isActive()) {
            menuSize = scrollBar.getSize();
        } else if (downloadMenuCapture != null) {
            menuSize = detectMenuSize(downloadMenuCapture);
        }
        log.debug("Menu size detected/set to: " + menuSize);
    }

//    private void detectMenuColors(BufferedImage downloadMenuCapture) {
//        try {
//            final WritableRaster dataColorPixel = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (855 * this.scale),
//                    (int) (215 * this.scale), 1, 1))).createWritableTranslatedChild(0, 0);
//            byte[] color = DataBufferHelper.getData(dataColorPixel.getDataBuffer());
//            final WritableRaster dataColorPixel2 = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (37 * this.scale),
//                    (int) (896 * this.scale), 1, 1))).createWritableTranslatedChild(0, 0);
//            byte[] color2 = DataBufferHelper.getData(dataColorPixel2.getDataBuffer());
//            final WritableRaster dataColorPixel3 = ((WritableRaster) downloadMenuCapture.getData(new java.awt.Rectangle((int) (137 * this.scale),
//                    (int) (896 * this.scale), 1, 1))).createWritableTranslatedChild(0, 0);
//            byte[] color3 = DataBufferHelper.getData(dataColorPixel3.getDataBuffer());
//            log.debug("Highlight color: {}", toHex(color));
//            log.debug("Default color: {}", toHex(color2));
//            log.debug("Downloaded color: {}", toHex(color3));
//            PreferencesService.setPreference(PreferenceConstants.ENABLE_AUTO_DETECT_AR, Boolean.FALSE);
//            PreferencesService.setPreference(PreferenceConstants.HIGHLIGHT_DATAPORT_COLOR, javafx.scene.paint.Color.web(toHex(color)).toString());
//            PreferencesService.setPreference(PreferenceConstants.DEFAULT_DATAPORT_COLOR, javafx.scene.paint.Color.web(toHex(color2)).toString());
//            PreferencesService.setPreference(PreferenceConstants.DOWNLOADED_DATAPORT_COLOR, javafx.scene.paint.Color.web(toHex(color3)).toString());
//            EventService.publish(new DataportColorDetectedEvent());
//
//        } catch (Exception e) {
//            log.error("Error detecting colors", e);
//        }
//    }

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

    public double getMenuItemDefaultHeight() {
        return 100 * this.scale;
    }
}
