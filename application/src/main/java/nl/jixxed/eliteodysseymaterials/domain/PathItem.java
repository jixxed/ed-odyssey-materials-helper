package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class PathItem<E extends BlueprintName<E>> {
    private final List<Engineer> engineers;
    private final List<WishlistBlueprintTemplate<E>> blueprints;
    private Map<Blueprint<E>, Integer> recipes;
    private Engineer engineer;
    private List<Engineer> alternateEngineers = new ArrayList<>();
    private double distance;

    public PathItem(final List<Engineer> engineers, final List<? extends WishlistBlueprintTemplate<E>> blueprints) {
        this.engineers = engineers;
        this.blueprints = (List<WishlistBlueprintTemplate<E>>) blueprints;
        this.recipes = blueprints.stream().map(WishlistBlueprintTemplate::getPrimaryRecipe).collect(Collectors.groupingBy(
                recipe -> recipe,
                Collectors.summingInt(value -> 1))
        );
    }
    public void addBlueprint(final WishlistBlueprintTemplate<E> blueprint){
        this.blueprints.add(blueprint);
        this.recipes = this.blueprints.stream().map(WishlistBlueprintTemplate::getPrimaryRecipe).collect(Collectors.groupingBy(
                recipe -> recipe,
                Collectors.summingInt(value -> 1))
        );
    }
    public String getRecipesString() {
        return this.recipes.entrySet().stream().map(recipe -> {
            if (recipe.getKey() instanceof ModuleBlueprint moduleBlueprint) {
                return LocaleService.getLocalizedStringForCurrentLocale(moduleBlueprint.getBlueprintName().getLocalizationKey()) + ((recipe.getValue() > 1) ? "(" + recipe.getValue() + ")" : "");
            }
            if (recipe.getKey() instanceof HorizonsModuleBlueprint horizonsModuleBlueprint) {
                return LocaleService.getLocalizedStringForCurrentLocale(horizonsModuleBlueprint.getHorizonsBlueprintName().getLocalizationKey()) + ((recipe.getValue() > 1) ? "(" + recipe.getValue() + ")" : "");
            }
            return "";
        }).collect(Collectors.joining(", "));
    }

    public Double getAndSetDistanceToClosestEngineer(final StarSystem starSystem) {
        final List<Engineer> potentialEngineers = this.getEngineers().stream().filter(eng -> this.recipes.keySet().stream().allMatch(moduleRecipe -> {
            if (moduleRecipe instanceof ModuleBlueprint moduleBlueprint) {
                return moduleBlueprint.getEngineers().contains(eng);
            }
            if (moduleRecipe instanceof HorizonsModuleBlueprint horizonsModuleBlueprint) {
                return horizonsModuleBlueprint.getEngineers().contains(eng);
            }
            if (moduleRecipe instanceof HorizonsExperimentalEffectBlueprint experimentalEffectBlueprint) {
                return experimentalEffectBlueprint.getEngineers().contains(eng);
            }
            return false;
        })).toList();
        this.setEngineer(potentialEngineers.stream().min(Comparator.comparingDouble(value -> value.getDistance(starSystem))).orElseThrow(IllegalArgumentException::new));
        this.setAlternateEngineers(potentialEngineers.stream().filter(eng -> eng != this.getEngineer()).collect(Collectors.toList()));
        this.setDistance(this.getEngineer().getDistance(starSystem));
        return this.getDistance();
    }

    public void setEngineerAndCalculateDistance(final Engineer engineer, final StarSystem starSystem) {
        if (this.getEngineer() != engineer) {
            this.getAlternateEngineers().remove(engineer);
            this.getAlternateEngineers().add(this.getEngineer());
        }
        this.setEngineer(engineer);
        this.setDistance(this.getEngineer().getDistance(starSystem));
    }

    public double getDistance() {
        if(engineers.contains(Engineer.REMOTE_WORKSHOP))
            return -0.000000001;
        return distance;
    }
}
