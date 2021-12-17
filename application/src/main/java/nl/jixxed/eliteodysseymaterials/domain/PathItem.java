package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
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
    private List<Engineer> alternateEngineers = new ArrayList<>();
    @Setter
    private double distance;

    public String getRecipesString() {
        return this.recipes.entrySet().stream().map(recipe -> LocaleService.getLocalizedStringForCurrentLocale(recipe.getKey().getRecipeName().getLocalizationKey()) + ((recipe.getValue() > 1) ? "(" + recipe.getValue() + ")" : "")).collect(Collectors.joining(", "));
    }

    public Double getAndSetDistanceToClosestEngineer(final StarSystem starSystem) {
        final List<Engineer> potentialEngineers = this.engineers.stream().filter(eng -> this.recipes.keySet().stream().allMatch(moduleRecipe -> moduleRecipe.getEngineers().contains(eng))).toList();
        this.engineer = potentialEngineers.stream().min(Comparator.comparingDouble(value -> value.getDistance(starSystem))).orElseThrow(IllegalArgumentException::new);
        this.alternateEngineers = potentialEngineers.stream().filter(eng -> eng != this.engineer).collect(Collectors.toList());
        this.distance = this.engineer.getDistance(starSystem);
        return this.distance;
    }

    public void setEngineerAndCalculateDistance(final Engineer engineer, final StarSystem starSystem) {
        if (this.engineer != engineer) {
            this.alternateEngineers.remove(engineer);
            this.alternateEngineers.add(this.engineer);
        }
        this.engineer = engineer;
        this.distance = this.engineer.getDistance(starSystem);
    }
}
