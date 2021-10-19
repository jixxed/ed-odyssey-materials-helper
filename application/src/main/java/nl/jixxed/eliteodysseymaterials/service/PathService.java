package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint;

import java.util.*;
import java.util.stream.Collectors;

public class PathService {

    private static final int MAX_ENGINEER_DISTANCE = 5000;//Ly

    public static List<PathItem> calculateShortestPath(final List<WishlistBlueprint> wishlistBlueprints) {

        final Set<ModuleRecipe> distinctRecipes = (Set<ModuleRecipe>) (Set<?>) wishlistBlueprints.stream()
                .filter(wishlistBlueprint -> wishlistBlueprint.getRecipe() instanceof ModuleRecipe)
                .filter(WishlistBlueprint::isVisibleBlueprint)
                .map(wishlistBlueprint -> wishlistBlueprint.getRecipe())
                .collect(Collectors.toSet());
        final List<PathItem> sortedPathItems = new ArrayList<>();
        final List<PathItem> pathItems = new ArrayList<>();
        final Map<Engineer, Integer> engineerPreference = new EnumMap<>(Engineer.class);
        Location currentLocation = LocationService.getCurrentLocation();
        final Location finalCurrentLocation1 = currentLocation;
        final List<Engineer> tooFarAwayEngineers = Arrays.stream(Engineer.values()).filter(engineer -> engineer.getDistance(finalCurrentLocation1) > MAX_ENGINEER_DISTANCE).toList();
        distinctRecipes.forEach(recipe -> recipe.getEngineers().stream()
                .filter(engineer -> !tooFarAwayEngineers.contains(engineer))
                .forEach(engineer -> engineerPreference.put(engineer, 1 + engineerPreference.getOrDefault(engineer, 0))));
        distinctRecipes.stream().filter(recipe -> recipe.getEngineers().stream().anyMatch(engineerPreference::containsKey)).forEach(recipe -> {
            if (pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().containsKey(recipe))) {
                final Engineer engineer = mostPreferredEngineer(engineerPreference, recipe, tooFarAwayEngineers);
                final Map<ModuleRecipe, Integer> recipesForEngineer = getRecipesForEngineer(wishlistBlueprints, pathItems, engineer);
                pathItems.add(new PathItem(engineer, recipesForEngineer));
            }
        });
        final int systemsToVisit = pathItems.size();
        for (int i = 0; i < systemsToVisit; i++) {
            final Location finalCurrentLocation = currentLocation;
            final PathItem closest = pathItems.stream().min((pathItem1, pathItem2) -> (int) (pathItem1.getEngineer().getDistance(finalCurrentLocation) - pathItem2.getEngineer().getDistance(finalCurrentLocation))).orElseThrow(IllegalArgumentException::new);
            closest.setDistance(closest.getEngineer().getDistance(currentLocation));
            sortedPathItems.add(closest);
            currentLocation = closest.getEngineer().getLocation();
            pathItems.remove(closest);
        }
        return sortedPathItems;
    }

    private static Map<ModuleRecipe, Integer> getRecipesForEngineer(final List<WishlistBlueprint> wishlistBlueprints, final List<PathItem> pathItems, final Engineer engineer) {
        final List<ModuleRecipe> recipes = (List<ModuleRecipe>) (List<?>) wishlistBlueprints.stream()
                .filter(wishlistBlueprint -> wishlistBlueprint.getRecipe() instanceof ModuleRecipe)
                .filter(WishlistBlueprint::isVisibleBlueprint)
                .map(WishlistBlueprint::getRecipe)
                .collect(Collectors.toList());
        return recipes.stream()
                .filter(recipe1 -> recipe1.getEngineers().contains(engineer))
                .filter(recipe1 -> pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().containsKey(recipe1)))
                .collect(Collectors.groupingBy(
                        recipe -> recipe,
                        Collectors.summingInt(value -> 1))
                );
    }

    private static Engineer mostPreferredEngineer(final Map<Engineer, Integer> engineerPreference, final ModuleRecipe recipe, final List<Engineer> tooFarAwayEngineers) {
        return recipe.getEngineers().stream()
                .filter(engineer -> !tooFarAwayEngineers.contains(engineer))
                .max(Comparator.comparingInt(engineerPreference::get))
                .orElseThrow(IllegalArgumentException::new);
    }
}
