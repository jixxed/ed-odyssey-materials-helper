package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.BarterConstants;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ReceiveText.ReceiveText;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PointToOdysseyResourceEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ReceiveTextMessageProcessor implements MessageProcessor<ReceiveText> {

    private static final String FROM = "$CHAT_System;";

    @Override
    public void process(final ReceiveText event) {
        final String from = event.getFrom();
        final String message = event.getMessage();
        final String message_localised = event.getMessage_Localised().orElse("");
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            if (FROM.equals(from)
                    && message.contains("name=" + commander.getName())
                    && message.contains("targetedAction=$HumanoidEmote_point_Action_Targeted")
                    && message.contains("target=$MicroResource_of")) {
                final Pattern pattern = Pattern.compile("^.*\\((.*)\\)$");
                final Matcher matcher = pattern.matcher(message_localised);
                if (matcher.find()) {
                    try {
                        final OdysseyMaterial odysseyMaterial = OdysseyMaterial.forLocalizedName(matcher.group(1));
                        EventService.publish(new PointToOdysseyResourceEvent(odysseyMaterial));
                        final String text;
                        final Integer backPackValue = StorageService.getMaterialStorage(odysseyMaterial).getBackPackValue();
                        final String backPackText = backPackValue > 0 ? "(" + backPackValue + ")" : "";
                        if (WishlistService.isMaterialOnWishlist(odysseyMaterial)) {
                            text = LocaleService.getLocalizedStringForCurrentLocale(odysseyMaterial.getLocalizationKey()) + " - Wishlist - " + StorageService.getMaterialStorage(odysseyMaterial).getTotalValue() + backPackText + "/" + WishlistService.getAllWishlistsCount(odysseyMaterial);
                            NotificationService.showInformation(NotificationType.WISHLIST_POINT, "Material information", text);
                        } else if (OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(odysseyMaterial)) {
                            text = LocaleService.getLocalizedStringForCurrentLocale(odysseyMaterial.getLocalizationKey()) + " - Engineer/Blueprint - " + StorageService.getMaterialStorage(odysseyMaterial).getTotalValue() + backPackText;
                            NotificationService.showInformation(NotificationType.RELEVANT_POINT, "Material information", text);
                        } else {
                            final Integer barterSellPrice = BarterConstants.getBarterSellPrice(odysseyMaterial);
                            text = LocaleService.getLocalizedStringForCurrentLocale(odysseyMaterial.getLocalizationKey()) + " - Irrelevant (" + LocaleService.getStringBinding("material.tooltip.barter.sell.price", barterSellPrice == -1 ? "?" : NUMBER_FORMAT.format(barterSellPrice))).build() + ")";
                            NotificationService.showInformation(NotificationType.IRRELEVANT_POINT, "Material information", text);
                        }
                    } catch (final IllegalArgumentException ex) {
                        log.warn("Resource not an OdysseyMaterial");
                    }
                }
            }
        });
    }
    @Override
    public Class<ReceiveText> getMessageClass() {
        return ReceiveText.class;
    }

}
