package nl.jixxed.eliteodysseymaterials.domain;

import javafx.beans.binding.StringBinding;
import javafx.util.Pair;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.ObservableResourceFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public record BlueprintListing(BlueprintCategory category, HorizonsBlueprintName name, HorizonsBlueprintType type,
                               List<Pair<HorizonsBlueprintGrade, Integer>> gradeGroups,
                               Integer amount) implements Comparable<BlueprintListing> {

    public StringBinding toStringBinding() {
        return ObservableResourceFactory.getStringBinding(() ->
//                LocaleService.getLocalizedStringForCurrentLocale(this.category.getLocalizationKey()) +
//                " - " +
                        LocaleService.getLocalizedStringForCurrentLocale(this.name.getLocalizationKey()) +
                                (!this.category.equals(BlueprintCategory.SYNTHESIS) ? " - " + LocaleService.getLocalizedStringForCurrentLocale(this.type.getLocalizationKey()) : "") +
                                ((!this.gradeGroups.get(0).getKey().equals(HorizonsBlueprintGrade.NONE)) ? " - " : "") +
                                (this.gradeGroups.stream()
                                        .filter(pair -> pair.getKey().getGrade() > 0)
                                        .sorted(Comparator.comparingInt(value -> value.getKey().getGrade()))
                                        .map(pair -> this.category.equals(BlueprintCategory.SYNTHESIS) ? LocaleService.getLocalizedStringForCurrentLocale("blueprint.synthesis.grade" + pair.getKey().getGrade()) : String.valueOf(pair.getKey().getGrade()))
                                        .collect(Collectors.joining(", "))) +
                                " (" + this.amount + ")"
        );
    }

    @Override
    public int compareTo(final BlueprintListing other) {
        final int categoryCompareTo = this.category.compareTo(other.category);
        if (categoryCompareTo != 0) {
            return categoryCompareTo;
        } else {
            final int nameCompareTo = this.name.compareTo(other.name);
            if (nameCompareTo != 0) {
                return nameCompareTo;
            } else {

                final int typeCompareTo = this.type.compareTo(other.type);
                if (typeCompareTo != 0) {
                    return typeCompareTo;
                } else {
                    final String grades = this.gradeGroups.stream().map(Pair::getKey).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining());
                    final String otherGrades = other.gradeGroups.stream().map(Pair::getKey).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining());
                    return grades.compareTo(otherGrades);
                }
            }
        }
    }
}

