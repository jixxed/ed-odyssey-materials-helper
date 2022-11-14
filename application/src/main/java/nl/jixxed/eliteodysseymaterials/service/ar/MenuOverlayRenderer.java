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
                } else {
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
                        text = LocaleService.getLocalizedStringForCurrentLocale("ar.overlay.wishlist") + " - " + StorageService.getMaterialStorage(odysseyMaterial).getTotalValue() + backPackText + "/" + WishlistService.getWishlistCount(odysseyMaterial);
                    } else if (OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(odysseyMaterial)) {
                        text = LocaleService.getLocalizedStringForCurrentLocale("ar.overlay.blueprint") + " - " + StorageService.getMaterialStorage(odysseyMaterial).getTotalValue() + backPackText;
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



}
