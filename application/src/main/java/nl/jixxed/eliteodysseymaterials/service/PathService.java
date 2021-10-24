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
                final List<Engineer> engineers = mostPreferredEngineer(engineerPreference, recipe, tooFarAwayEngineers);
                final Map<ModuleRecipe, Integer> recipesForEngineer = getRecipesForEngineer(wishlistBlueprints, pathItems, engineers.get(0));
                pathItems.add(new PathItem(engineers, recipesForEngineer));
            }
        });
        final int systemsToVisit = pathItems.size();
        for (int i = 0; i < systemsToVisit; i++) {
            final Location finalCurrentLocation = currentLocation;
            if (pathItems.size() > 1) {
                final PathItem closest = pathItems.stream()
                        .min((pathItem1, pathItem2) -> (int) (pathItem1.getAndSetDistanceToClosestEngineer(finalCurrentLocation) - pathItem2.getAndSetDistanceToClosestEngineer(finalCurrentLocation)))
                        .orElseThrow(IllegalArgumentException::new);
                sortedPathItems.add(closest);
                currentLocation = closest.getEngineer().getLocation();
                pathItems.remove(closest);
            } else if (pathItems.size() == 1) {
                final PathItem closest = pathItems.get(0);
                closest.getAndSetDistanceToClosestEngineer(currentLocation);
                sortedPathItems.add(closest);
            }

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

    private static List<Engineer> mostPreferredEngineer(final Map<Engineer, Integer> engineerPreference, final ModuleRecipe recipe, final List<Engineer> tooFarAwayEngineers) {
        final Integer highestPreference = recipe.getEngineers().stream()
                .filter(engineer -> !tooFarAwayEngineers.contains(engineer))
                .max(Comparator.comparingInt(engineerPreference::get))
                .map(engineerPreference::get)
                .orElseThrow(IllegalArgumentException::new);
        return recipe.getEngineers().stream()
                .filter(engineer -> !tooFarAwayEngineers.contains(engineer))
                .filter(engineer -> Objects.equals(engineerPreference.get(engineer), highestPreference))
                .toList();


    }
}
