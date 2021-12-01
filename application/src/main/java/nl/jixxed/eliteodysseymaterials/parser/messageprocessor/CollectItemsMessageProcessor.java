package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;

public class CollectItemsMessageProcessor implements MessageProcessor {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    @Override
    public void process(final JsonNode journalMessage) {
        final Material material = Material.subtypeForName(journalMessage.get("Name").asText());
        if ((APPLICATION_STATE.getSoloMode() && RecipeConstants.isNotRelevantAndNotRequiredEngineeringIngredient(material))
                || (!APPLICATION_STATE.getSoloMode() && !RecipeConstants.isEngineeringOrBlueprintIngredient(material))) {
            NotificationService.showInformation(LocaleService.getLocalizedStringForCurrentLocale("notification.collected.irrelevant.material.title"), LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()));
        }
    }
}
