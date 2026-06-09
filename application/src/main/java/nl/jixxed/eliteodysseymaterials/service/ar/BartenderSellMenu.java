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

import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;

import java.awt.*;
import java.util.List;

public class BartenderSellMenu implements BartenderMenu {

    public static final int VIEWPORT_HEIGHT = 1064;
    @Getter
    private double scale;
    @Getter
    private double contentHeight;
    @Getter
    private double contentWidth;
    @Setter
    private ScrollBarV2 scrollBarV2;

    @Getter
    private Rectangle menu;

    public BartenderSellMenu() {
        this.scale = 1;
        this.contentHeight = 1600;
        this.contentWidth = 3840;//2844.445;
//        this.subMenu = BartenderMenuType.NONE;
        setupMenu();
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

    public Point getSellMenuDetectionLeftPixel() {
        return new Point((int) (142D * this.scale), (int) (135D * this.scale));

    }

    public Point getSellMenuDetectionRightPixel() {
        return new Point((int) (2688D * this.scale), (int) (171D * this.scale));

    }

    public Point getSellMenuDetectionRightPixel2() {
        return new Point((int) (2750D * this.scale), (int) (171D * this.scale));

    }

    public double getFontSize() {
        return 30 * this.scale;
    }

    double getHeaderFontSize() {
        return 30 * this.scale;
    }

    public Rectangle getScrollbar() {
//        2445/400
//        2460/1470
        //585,64 - 109, 109
        //497,77778
        return new Rectangle(
                this.menu.getX() + 1948 * this.scale,
                0,
                this.menu.getX() + 1963 * this.scale,
                this.menu.getY() + 1470 * this.scale
        );
    }

    private double getCategoryHeaderHeight() {
        return 72;
    }

    private double getCategoryEmptyTextHeight() {
        return 50;
    }

    private double getCategoryEntryHeight() {
        return 79;
    }
    private double getItemHeight() {
        return 73;
    }

    public double getItemX(OdysseyMaterial material, int index) {
//        return 587 * this.scale;
        return getMenu().getX() + 88 * this.scale;
    }

    public double getItemY(OdysseyMaterial material, int index, List<OdysseyMaterial> goods, List<OdysseyMaterial> assets, List<OdysseyMaterial> datas) {
//        List<OdysseyMaterial> goods = StorageService.getRawShipLocker().getItems()
//                .map(list -> list.stream()
//                        .map(item -> OdysseyMaterial.subtypeForName(item.getName()))
//                        .filter(mat -> !mat.isIllegal() || LocationService.getStationGovernment().equals(Government.ANARCHY))
//                        .toList()
//                ).orElse(Collections.emptyList());
//        List<OdysseyMaterial> assets = StorageService.getRawShipLocker().getComponents()
//                .map(list -> list.stream()
//                        .map(item -> OdysseyMaterial.subtypeForName(item.getName()))
//                        .filter(mat -> !mat.isIllegal() || LocationService.getStationGovernment().equals(Government.ANARCHY))
//                        .toList()
//                ).orElse(Collections.emptyList());
//        List<OdysseyMaterial> datas = StorageService.getRawShipLocker().getData()
//                .map(list -> list.stream()
//                        .map(item -> OdysseyMaterial.subtypeForName(item.getName()))
//                        .filter(mat -> !mat.isIllegal() || LocationService.getStationGovernment().equals(Government.ANARCHY))
//                        .toList()
//                ).orElse(Collections.emptyList());
        double y = 0;
        if (material instanceof Asset) {
            y += getCategoryHeaderHeight() * this.scale;
            y += index * Math.ceil(getCategoryEntryHeight() * this.scale);
        }
        if (material instanceof Good) {
            y += getCategoryHeaderHeight() * 2 * this.scale;
            y += assets.isEmpty()
                    ? Math.ceil(getCategoryEmptyTextHeight() * this.scale)
                    : (double)assets.size() * Math.ceil(getCategoryEntryHeight() * this.scale);
            y += index * Math.ceil(getCategoryEntryHeight() * this.scale);
        }
        if (material instanceof Data) {
            y += getCategoryHeaderHeight() * 3 * this.scale;
            y += assets.isEmpty()
                    ? Math.ceil(getCategoryEmptyTextHeight() * this.scale)
                    : (double)assets.size() * Math.ceil(getCategoryEntryHeight() * this.scale);
            y += goods.isEmpty()
                    ? Math.ceil(getCategoryEmptyTextHeight() * this.scale)
                    : (double)goods.size() * Math.ceil(getCategoryEntryHeight() * this.scale);
            y += index * Math.ceil(getCategoryEntryHeight() * this.scale);
        }
        return y - getMenuItemPositionYOffset(goods, assets, datas) + getVisibleViewPortRect().getY();
    }

    public double getItemWidth(OdysseyMaterial material, int index) {
        return 1461 * this.scale;
    }

    public double getItemHeight(OdysseyMaterial material, int index) {
        return getItemHeight() * this.scale;
    }
    /**
     * visible height of menu item in viewport
     *
     * @param index
     * @return
     */
    double getMenuItemVisibleHeight(OdysseyMaterial material, int index, List<OdysseyMaterial> goods, List<OdysseyMaterial> assets, List<OdysseyMaterial> datas) {
        Rectangle visibleViewPortRect = getVisibleViewPortRect();
        Rectangle rectangle = new Rectangle(
                getItemX(material, index),
                getItemY(material, index, goods, assets, datas),
                getItemX(material, index) + getItemWidth(material, index),
                getItemY(material, index, goods, assets, datas) + getItemHeight(material, index)
        );
        double height = visibleViewPortRect.getAwtRectangle().createIntersection(rectangle.getAwtRectangle()).getHeight();
        return Math.max(0, height);
    }


    public double getMenuItemPositionYOffset(List<OdysseyMaterial> goods, List<OdysseyMaterial> assets, List<OdysseyMaterial> datas) {
        if (this.scrollBarV2 == null)
            return 0;
        if (!scrollBarV2.isActive()) {
            return 0;
        }
        double menuSize = getCategoryHeaderHeight() * 3D;
        menuSize += (goods.isEmpty() ? getCategoryEmptyTextHeight() : (double)goods.size() * getCategoryEntryHeight()) * this.scale;
        menuSize += (assets.isEmpty() ? getCategoryEmptyTextHeight() : (double)assets.size() * getCategoryEntryHeight()) * this.scale;
        menuSize += (datas.isEmpty() ? getCategoryEmptyTextHeight() : (double)datas.size() * getCategoryEntryHeight()) * this.scale;
        double viewportHeight = VIEWPORT_HEIGHT * this.scale;
        double menuHeight = menuSize;
        return (menuHeight - viewportHeight) * this.scrollBarV2.getPosition() / 100.0;
    }

    public Rectangle getVisibleViewPortRect() {
        final double menuItemOffsetX = getMenu().getX() + 87 * this.scale;
        final double menuItemOffsetY = 400 * this.scale;
        final double menuWidth = (1866) * this.scale;
        double viewportHeight = VIEWPORT_HEIGHT * this.scale;
        return new Rectangle(menuItemOffsetX, menuItemOffsetY, menuItemOffsetX + menuWidth, menuItemOffsetY + viewportHeight);
    }

    public boolean isMenuItemVisible(OdysseyMaterial material, int index, List<OdysseyMaterial> goods, List<OdysseyMaterial> assets, List<OdysseyMaterial> datas) {
        //menu entry
        return getMenuItemVisibleHeight(material, index, goods, assets, datas) > 0;
    }

//942 1020 1170 1248
//cat title height 150 - 78 = 72
//cat empty text height 122 - 72 = 50
//cat entry height = 78

    //127/249 = 122
}
