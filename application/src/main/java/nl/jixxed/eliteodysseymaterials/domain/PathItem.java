package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@ToString
public class PathItem {
    private final Engineer engineer;
    private final Map<ModuleRecipe, Integer> recipes;
    @Setter
    private double distance;

    public String getRecipesString() {
        return this.recipes.entrySet().stream().map(recipe -> LocaleService.getLocalizedStringForCurrentLocale(recipe.getKey().getRecipeName().getLocalizationKey()) + ((recipe.getValue() > 1) ? "(" + recipe.getValue() + ")" : "")).collect(Collectors.joining(", "));
    }
}
