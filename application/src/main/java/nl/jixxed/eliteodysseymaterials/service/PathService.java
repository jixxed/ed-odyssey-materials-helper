package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;

import java.util.*;
import java.util.stream.Collectors;

public class PathService {
    public static List<PathItem> calculateShortestPath(final Set<ModuleRecipe> recipes) {
        final List<PathItem> sortedPathItems = new ArrayList<>();
        final List<PathItem> pathItems = new ArrayList<>();
        final Map<Engineer, Integer> engineerPreference = new EnumMap<>(Engineer.class);
        recipes.forEach(recipe -> recipe.getEngineers().forEach(engineer -> engineerPreference.put(engineer, 1 + engineerPreference.getOrDefault(engineer, 0))));
        recipes.forEach(recipe -> {
            if (pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().contains(recipe))) {
                final Engineer engineer = mostPreferredEngineer(engineerPreference, recipe);
                final Set<ModuleRecipe> recipesForEngineer = recipes.stream().filter(recipe1 -> recipe1.getEngineers().contains(engineer)).filter(recipe1 -> pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().contains(recipe1))).collect(Collectors.toSet());
                pathItems.add(new PathItem(engineer, recipesForEngineer));
            }
        });
        Location currentLocation = LocationService.getCurrentLocation();
        final int systemsToVisit = pathItems.size();
        for (int i = 0; i < systemsToVisit; i++) {
            final Location finalCurrentLocation = currentLocation;
            final PathItem closest = pathItems.stream().min((pathItem1, pathItem2) -> (int) (pathItem1.getEngineer().getDistance(finalCurrentLocation) - pathItem2.getEngineer().getDistance(finalCurrentLocation))).orElseThrow(IllegalArgumentException::new);
            sortedPathItems.add(closest);
            currentLocation = closest.getEngineer().getLocation();
            pathItems.remove(closest);
        }
        return sortedPathItems;
    }

    private static Engineer mostPreferredEngineer(final Map<Engineer, Integer> engineerPreference, final ModuleRecipe recipe) {
        return recipe.getEngineers().stream()
                .max(Comparator.comparingInt(engineerPreference::get))
                .orElseThrow(IllegalArgumentException::new);
    }
}
