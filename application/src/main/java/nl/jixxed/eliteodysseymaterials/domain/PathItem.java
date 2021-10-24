package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@ToString
public class PathItem {
    private final List<Engineer> engineers;
    private final Map<ModuleRecipe, Integer> recipes;
    @Setter
    private Engineer engineer;
    @Setter
    private double distance;

    public String getRecipesString() {
        return this.recipes.entrySet().stream().map(recipe -> LocaleService.getLocalizedStringForCurrentLocale(recipe.getKey().getRecipeName().getLocalizationKey()) + ((recipe.getValue() > 1) ? "(" + recipe.getValue() + ")" : "")).collect(Collectors.joining(", "));
    }

    public Double getAndSetDistanceToClosestEngineer(final Location location) {
        this.engineer = this.engineers.stream().min(Comparator.comparingDouble(value -> value.getDistance(location))).orElseThrow(IllegalArgumentException::new);
        this.distance = this.engineer.getDistance(location);
        return this.distance;
    }
}
