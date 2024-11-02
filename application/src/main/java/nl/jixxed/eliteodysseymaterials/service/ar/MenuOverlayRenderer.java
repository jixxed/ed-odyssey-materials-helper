package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

@Slf4j
public class MenuOverlayRenderer {

    public static BufferedImage renderMenu(final DownloadMenu downloadMenu) {
//        final int contentWidth = downloadMenu.getContentWidth();
//        final int contentHeight = downloadMenu.getContentHeight();
        final BufferedImage bufferedImage = new BufferedImage((int) downloadMenu.getMenu().getWidth(), (int) downloadMenu.getMenu().getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics = bufferedImage.createGraphics();
//        final AtomicBoolean isFullyRendered = new AtomicBoolean(true);

//        graphics.setColor(Color.WHITE);
//        graphics.drawRect((int) downloadMenu.getTerminalType().getX(), (int) downloadMenu.getTerminalType().getY(), (int) downloadMenu.getTerminalType().getWidth(), (int) downloadMenu.getTerminalType().getHeight());

        for (int index = 1; index <= downloadMenu.menuSize(); index++) {
            final OdysseyMaterial odysseyMaterial = downloadMenu.getDownloadData().get(index);
//            if (downloadMenu.isMenuItemVisible(index) && !downloadMenu.isScanned(index)) {
//                log.debug("not scanned index:" + index);
//                isFullyRendered.set(false);
//            }
//            graphics.setColor(Color.YELLOW);
//            graphics.drawRect((int) (downloadMenu.getMenuItemX(index) + downloadMenu.getMenuTextReadOffset().getX()), (int) (downloadMenu.getMenuItemY(index) + downloadMenu.getMenuTextReadOffset().getY()), (int) downloadMenu.getMenuTextReadOffset().getWidth(), (int) downloadMenu.getMenuTextReadOffset().getHeight());
            if (downloadMenu.isMenuItemVisible(index) && Data.UNKNOWN != odysseyMaterial && odysseyMaterial != null) {
                graphics.setColor(Color.WHITE);
                if (Locale.forLanguageTag("ru").equals(LocaleService.getCurrentLocale())) {
                    graphics.setFont(new Font("Eurostile-Roman", Font.PLAIN, (int) (downloadMenu.getFontSize())));
                } else {
//                    graphics.setFont(new Font("Eurostile-Roman", Font.BOLD, (int) (downloadMenu.getFontSize())));
                    graphics.setFont(new Font("Euro Caps", Font.PLAIN, (int) (downloadMenu.getFontSize())));
                }
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                final double menuItemY = downloadMenu.getMenuItemY(index) + downloadMenu.getMenuItemPositionYOffset();

                final int x = (int) (downloadMenu.getMenuItemX(index) + downloadMenu.getMenuTextWriteOffsetX());
                final int y = (int) (menuItemY + downloadMenu.getMenuTextWriteOffsetY());
                final Color color;
                if (WishlistService.isMaterialOnWishlist(odysseyMaterial)) {
                    final javafx.scene.paint.Color preference = javafx.scene.paint.Color.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_WISHLIST_COLOR, javafx.scene.paint.Color.LIME.toString()));
                    color = new Color((float) preference.getRed(),
                            (float) preference.getGreen(),
                            (float) preference.getBlue(),
                            (float) preference.getOpacity());
                } else if (OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(odysseyMaterial)) {
                    final javafx.scene.paint.Color preference = javafx.scene.paint.Color.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_BLUEPRINT_COLOR, javafx.scene.paint.Color.BLUE.toString()));
                    color = new Color((float) preference.getRed(),
                            (float) preference.getGreen(),
                            (float) preference.getBlue(),
                            (float) preference.getOpacity());
                } else if (odysseyMaterial.isPowerplay()){
                    final javafx.scene.paint.Color preference = javafx.scene.paint.Color.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_POWERPLAY_COLOR, javafx.scene.paint.Color.PURPLE.toString()));
                    color = new Color((float) preference.getRed(),
                            (float) preference.getGreen(),
                            (float) preference.getBlue(),
                            (float) preference.getOpacity());
                }else {
                    final javafx.scene.paint.Color preference = javafx.scene.paint.Color.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_IRRELEVANT_COLOR, javafx.scene.paint.Color.RED.toString()));
                    color = new Color((float) preference.getRed(),
                            (float) preference.getGreen(),
                            (float) preference.getBlue(),
                            (float) preference.getOpacity());
                }
                if (downloadMenu.isMenuItemLabelVisible(index)) {
                    final String text;
                    final Integer backPackValue = StorageService.getMaterialStorage(odysseyMaterial).getBackPackValue();
                    final String backPackText = backPackValue > 0 ? "(" + backPackValue + ")" : "";
                    if (WishlistService.isMaterialOnWishlist(odysseyMaterial)) {
                        text = LocaleService.getLocalizedStringForCurrentLocale("ar.overlay.wishlist") + " - " + StorageService.getMaterialStorage(odysseyMaterial).getTotalValue() + backPackText + "/" + WishlistService.getAllWishlistsCount(odysseyMaterial);
                    } else if (OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(odysseyMaterial)) {
                        text = LocaleService.getLocalizedStringForCurrentLocale("ar.overlay.blueprint") + " - " + StorageService.getMaterialStorage(odysseyMaterial).getTotalValue() + backPackText;
                    } else if (odysseyMaterial.isPowerplay()){
                        text = LocaleService.getLocalizedStringForCurrentLocale("ar.overlay.powerplay");
                    } else {
                        text = LocaleService.getLocalizedStringForCurrentLocale("ar.overlay.irrelevant");
                    }
                    final FontMetrics fm = graphics.getFontMetrics();
                    final Rectangle2D rect = fm.getStringBounds(text, graphics);
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(x - fm.getAscent(),
                            y,
                            (int) rect.getWidth() + fm.getAscent() * 2,
                            (int) fm.getHeight() + (int) (0.1 * fm.getHeight()));
                    graphics.setColor(color);
                    graphics.drawString(text, x, y + fm.getHeight() - fm.getDescent() + (int) (0.05 * fm.getHeight()));
                }
                graphics.setColor(color);
                graphics.fillRect((int) downloadMenu.getMenuItemX(index), (int) menuItemY, (int) downloadMenu.getMenuItemWidth(index), (int) downloadMenu.getMenuItemHeight(index));
                graphics.drawRect((int) downloadMenu.getMenuItemX(index), (int) menuItemY, (int) downloadMenu.getMenuItemWidth(), (int) downloadMenu.getMenuItemHeight(index));


            }
        }
        graphics.dispose();
        return ImageTransformHelper.transform(bufferedImage, downloadMenu);
//        return bufferedImage1;
//        final WritableImage overlayImage1 = SwingFXUtils.toFXImage(bufferedImage1, null);
//        overlayImage = overlayImage1;
//        renderCache.put(String.valueOf(downloadMenu.isHasWarning()) + downloadMenu.getScrollBar().getPosition(), new Render(isFullyRendered.get(), overlayImage1));
    }


    public static BufferedImage renderMenu(final BartenderMenu bartenderMenu) {
        final BufferedImage bufferedImage = new BufferedImage((int) bartenderMenu.getContentWidth(), (int) bartenderMenu.getContentHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics = bufferedImage.createGraphics();

        final javafx.scene.paint.Color preference = javafx.scene.paint.Color.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_BARTENDER_COLOR, javafx.scene.paint.Color.WHITE.toString()));
        final Color color = new Color((float) preference.getRed(),
                (float) preference.getGreen(),
                (float) preference.getBlue(),
                (float) preference.getOpacity());
        graphics.setColor(color);

        if (Locale.forLanguageTag("ru").equals(LocaleService.getCurrentLocale())) {
            graphics.setFont(new Font("Eurostile-Roman", Font.PLAIN, (int) (bartenderMenu.getHeaderFontSize())));
        } else {
            graphics.setFont(new Font("Euro Caps", Font.PLAIN, (int) (bartenderMenu.getHeaderFontSize())));
        }
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        final Rectangle rectangle = Boolean.TRUE.equals((bartenderMenu.getSubMenu())) ? bartenderMenu.getSubMenuEntry() : bartenderMenu.getMenuEntry();

//        final String text = bartenderMenu.getSubMenu().toString();
        final FontMetrics fmHeader = graphics.getFontMetrics();
        graphics.drawString("FC", bartenderMenu.getFleetCarrierHeaderPosition().x+ ((135 - fmHeader.stringWidth("FC"))/2), bartenderMenu.getFleetCarrierHeaderPosition().y + fmHeader.getHeight() - fmHeader.getDescent() + (int) (0.05 * fmHeader.getHeight()));
        graphics.drawString("WL", bartenderMenu.getWishlistHeaderPosition().x+ ((135 - fmHeader.stringWidth("WL"))/2), bartenderMenu.getWishlistHeaderPosition().y + fmHeader.getHeight() - fmHeader.getDescent() + (int) (0.05 * fmHeader.getHeight()));
        graphics.drawString("Move the mousecursor over the cocktail for a rescan", (int)bartenderMenu.getMenu().getX()+10, (int)bartenderMenu.getMenu().getY()+ 10 + fmHeader.getHeight() - fmHeader.getDescent() + (int) (0.05 * fmHeader.getHeight()));

        if (Locale.forLanguageTag("ru").equals(LocaleService.getCurrentLocale())) {
            graphics.setFont(new Font("Eurostile-Roman", Font.PLAIN, (int) (bartenderMenu.getFontSize())));
        } else {
            graphics.setFont(new Font("Euro Caps", Font.PLAIN, (int) (bartenderMenu.getFontSize())));
        }

        final FontMetrics fm = graphics.getFontMetrics();
        bartenderMenu.visibleAssets.forEach(asset -> {
            if (!asset.isUnknown()) {
//                final Rectangle rectangle = bartenderMenu.getMenuItem(asset);
//                graphics.drawRect((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
                final String fcValue = StorageService.getMaterialStorage(asset).getFleetCarrierValue().toString();
                final String wlValue = WishlistService.getAllWishlistsCount(asset).toString();
                graphics.drawString(fcValue, bartenderMenu.getFleetCarrierTextPosition(asset).x+ ((135 - fm.stringWidth(fcValue))/2), bartenderMenu.getFleetCarrierTextPosition(asset).y + fm.getHeight() - fm.getDescent() + (int) (0.05 * fm.getHeight()));
                graphics.drawString(wlValue, bartenderMenu.getWishlistTextPosition(asset).x + ((135 - fm.stringWidth(wlValue))/2), bartenderMenu.getWishlistTextPosition(asset).y + fm.getHeight() - fm.getDescent() + (int) (0.05 * fm.getHeight()));
            }
        });
        //debugging
//         graphics.setColor(Color.RED);
//        graphics.drawRect( (int)bartenderMenu.getMenu().getX() + bartenderMenu.getSubMenuDetectionLeftPixel().x -1,  (int)bartenderMenu.getMenu().getY() + bartenderMenu.getSubMenuDetectionLeftPixel().y -1, 3,3);
//        graphics.drawRect( (int)bartenderMenu.getMenu().getX() + bartenderMenu.getSubMenuDetectionRightPixel().x -1, (int)bartenderMenu.getMenu().getY() +  bartenderMenu.getSubMenuDetectionRightPixel().y -1, 3,3);
//        graphics.drawRect( (int)bartenderMenu.getMenu().getX() + bartenderMenu.getSellMenuDetectionLeftPixel().x -1,  (int)bartenderMenu.getMenu().getY() + bartenderMenu.getSellMenuDetectionLeftPixel().y -1, 3,3);
//        graphics.drawRect( (int)bartenderMenu.getMenu().getX() + bartenderMenu.getSellMenuDetectionRightPixel().x -1, (int)bartenderMenu.getMenu().getY() +  bartenderMenu.getSellMenuDetectionRightPixel().y -1, 3,3);
//        graphics.drawRect((int) bartenderMenu.getMenu().getX(), (int) bartenderMenu.getMenu().getY(), (int) bartenderMenu.getMenu().getWidth() - 1, (int) bartenderMenu.getMenu().getHeight() - 1);
        graphics.dispose();
        return bufferedImage;
    }

}
