package nl.jixxed.eliteodysseymaterials.domain;

import javafx.beans.binding.StringBinding;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.ObservableResourceFactory;

public record OdysseyBlueprintListing(BlueprintCategory category, OdysseyBlueprintName name,
                                      Integer amount) implements Comparable<OdysseyBlueprintListing> {

    public StringBinding toStringBinding() {
        return ObservableResourceFactory.getStringBinding(() ->
                LocaleService.getLocalizedStringForCurrentLocale(this.name.getLocalizationKey()) + " (" + this.amount + ")");
    }

    @Override
    public int compareTo(final OdysseyBlueprintListing other) {
        final int categoryCompareTo = this.category.compareTo(other.category);
        if (categoryCompareTo != 0) {
            return categoryCompareTo;
        } else {
            return this.name.compareTo(other.name);
        }
    }
}

