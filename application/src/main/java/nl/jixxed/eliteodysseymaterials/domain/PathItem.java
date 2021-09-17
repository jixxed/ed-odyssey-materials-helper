package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@ToString
public class PathItem {
    private final Engineer engineer;
    private final Set<ModuleRecipe> recipes;

    public String getRecipesString() {
        return this.recipes.stream().map(recipe -> LocaleService.getLocalizedStringForCurrentLocale(recipe.getRecipeName().getLocalizationKey())).collect(Collectors.joining(","));
    }
}
