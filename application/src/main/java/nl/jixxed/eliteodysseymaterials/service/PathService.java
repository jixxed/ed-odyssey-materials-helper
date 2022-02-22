package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PathService {

    private static final int MAX_ENGINEER_DISTANCE = 10000;//Ly

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public static List<PathItem> calculateShortestPath(final List<WishlistBlueprint> wishlistBlueprints) {

        final LinkedHashSet<ModuleBlueprint> distinctRecipes = wishlistBlueprints.stream()
                .filter(wishlistBlueprint -> wishlistBlueprint.getRecipe() instanceof ModuleBlueprint)
                .filter(WishlistBlueprint::isVisibleBlueprint)
                .map(WishlistBlueprint::getRecipe)
                .map(ModuleBlueprint.class::cast)
                .sorted(Comparator.comparing(ModuleBlueprint::hasSingleEngineerPerRegion).thenComparing(Blueprint::getBlueprintName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        final List<PathItem> sortedPathItems = new ArrayList<>();
        final List<PathItem> pathItems = new ArrayList<>();
        final Map<Engineer, Integer> engineerPreference = new EnumMap<>(Engineer.class);
        StarSystem currentStarSystem = LocationService.getCurrentStarSystem();
        final StarSystem finalCurrentStarSystem1 = currentStarSystem;
        final List<Engineer> allowedEngineers = Arrays.stream(Engineer.values())
                .filter(engineer -> engineer.getDistance(finalCurrentStarSystem1) < MAX_ENGINEER_DISTANCE)
                .filter(APPLICATION_STATE::isEngineerUnlocked)
                .toList();
        distinctRecipes.forEach(recipe -> recipe.getEngineers().stream()
                .filter(allowedEngineers::contains)
                .forEach(engineer -> engineerPreference.put(engineer, 1 + engineerPreference.getOrDefault(engineer, 0))));

        distinctRecipes.stream()
                .filter(recipe -> recipe.getEngineers().stream().anyMatch(engineerPreference::containsKey))
                .forEach(recipe -> {
                    if (pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().containsKey(recipe))) {
                        final List<Engineer> engineers = mostPreferredEngineers(engineerPreference, recipe, allowedEngineers);
                        //prefer a previously selected engineer, otherwise first in the list
                        final Engineer selectedEngineer = engineers.stream()
                                .filter(engineer -> pathItems.stream().anyMatch(pathItem -> pathItem.getEngineers().contains(engineer)))
                                .findFirst()
                                .orElse(engineers.get(0));
                        recipe.getEngineers().stream()
                                .filter(engineer -> engineer != selectedEngineer)
                                .forEach(engineer -> {
                                    if (engineerPreference.containsKey(engineer)) {
                                        engineerPreference.put(engineer, engineerPreference.get(engineer) - 1);
                                    }
                                });
                        final Map<ModuleBlueprint, Integer> recipesForEngineer = getRecipesForEngineer(wishlistBlueprints, pathItems, selectedEngineer);
                        pathItems.add(new PathItem(engineers, recipesForEngineer));
                    }
                });
        final int systemsToVisit = pathItems.size();
        for (int i = 0; i < systemsToVisit; i++) {
            final StarSystem finalCurrentStarSystem = currentStarSystem;
            if (pathItems.size() > 1) {
                final PathItem closest = pathItems.stream()
                        .min((pathItem1, pathItem2) -> (int) (pathItem1.getAndSetDistanceToClosestEngineer(finalCurrentStarSystem) - pathItem2.getAndSetDistanceToClosestEngineer(finalCurrentStarSystem)))
                        .orElseThrow(IllegalArgumentException::new);
                sortedPathItems.add(closest);
                currentStarSystem = closest.getEngineer().getStarSystem();
                pathItems.remove(closest);
            } else if (pathItems.size() == 1) {
                final PathItem closest = pathItems.get(0);
                closest.getAndSetDistanceToClosestEngineer(currentStarSystem);
                sortedPathItems.add(closest);
            }
        }
        tryOptimizePath(sortedPathItems);
        final Map<ModuleBlueprint, Integer> unCraftable = distinctRecipes.stream().filter(recipe -> recipe.getEngineers().stream().noneMatch(engineerPreference::containsKey)).collect(Collectors.groupingBy(
                recipe -> recipe,
                Collectors.summingInt(value -> 1))
        );
        if (unCraftable.size() > 0) {
            final PathItem pathItem = new PathItem(List.of(Engineer.UNKNOWN), unCraftable);
            pathItem.setEngineer(Engineer.UNKNOWN);
            pathItem.setDistance(0.0);
            sortedPathItems.add(pathItem);
        }
        return sortedPathItems;
    }

    private static void tryOptimizePath(final List<PathItem> sortedPathItems) {
        if (sortedPathItems.size() > 1) {
            final List<List<Engineer>> paths = getPossiblePathsForItem(sortedPathItems);
            List<Engineer> shortestPath = new ArrayList<>();
            double shortestLength = Double.MAX_VALUE;
            for (final List<Engineer> engineers : paths) {
                double total = 0D;
                for (final Engineer engineer : engineers) {
                    final StarSystem starSystem = (engineers.indexOf(engineer) == 0) ? LocationService.getCurrentStarSystem() : engineers.get(engineers.indexOf(engineer) - 1).getStarSystem();
                    total += engineer.getDistance(starSystem);
                }
                if (total < shortestLength) {
                    shortestLength = total;
                    shortestPath = engineers;
                }
            }
            setShortestPath(sortedPathItems, shortestPath);
        }
    }

    private static void setShortestPath(final List<PathItem> sortedPathItems, final List<Engineer> shortestPath) {
        for (int i = 0; i < sortedPathItems.size(); i++) {
            final Engineer engineer = shortestPath.get(i);
            final StarSystem starSystem = (i == 0) ? LocationService.getCurrentStarSystem() : sortedPathItems.get(i - 1).getEngineer().getStarSystem();
            sortedPathItems.get(i).setEngineerAndCalculateDistance(engineer, starSystem);
        }
    }

    private static List<List<Engineer>> getPossiblePathsForItem(final List<PathItem> sortedPathItems) {
        final List<Engineer> engineers = new ArrayList<>(sortedPathItems.get(0).getAlternateEngineers());
        engineers.add(sortedPathItems.get(0).getEngineer());
        final List<List<Engineer>> paths = new ArrayList<>();
        engineers.forEach(engineer -> {
            final List<Engineer> path = new ArrayList<>();
            path.add(engineer);
            if (sortedPathItems.size() > 1) {
                paths.addAll(getPossiblePathsForItem(sortedPathItems, path, 0));
            } else {
                paths.add(path);
            }
        });
        return paths;
    }

    private static List<List<Engineer>> getPossiblePathsForItem(final List<PathItem> sortedPathItems, final List<Engineer> currentPath, final int index) {
        final List<Engineer> engineers = new ArrayList<>(sortedPathItems.get(index + 1).getAlternateEngineers());
        engineers.add(sortedPathItems.get(index + 1).getEngineer());
        final List<List<Engineer>> paths = new ArrayList<>();
        for (final Engineer engineer : engineers) {
            final List<Engineer> path = new ArrayList<>(currentPath);
            path.add(engineer);
            if (path.size() == sortedPathItems.size()) {
                paths.add(path);
            } else {
                paths.addAll(getPossiblePathsForItem(sortedPathItems, path, index + 1));
            }
        }
        return paths;
    }

    private static Map<ModuleBlueprint, Integer> getRecipesForEngineer(final List<WishlistBlueprint> wishlistBlueprints, final List<PathItem> pathItems, final Engineer engineer) {
        final List<ModuleBlueprint> recipes = wishlistBlueprints.stream()
                .filter(WishlistBlueprint::isVisibleBlueprint)
                .map(WishlistBlueprint::getRecipe)
                .filter(ModuleBlueprint.class::isInstance)
                .map(ModuleBlueprint.class::cast)
                .toList();
        return recipes.stream()
                .filter(recipe -> recipe.getEngineers().contains(engineer))
                .filter(recipe -> pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().containsKey(recipe)))
                .collect(Collectors.groupingBy(
                        recipe -> recipe,
                        Collectors.summingInt(value -> 1))
                );
    }

    private static List<Engineer> mostPreferredEngineers(final Map<Engineer, Integer> engineerPreference, final ModuleBlueprint recipe, final List<Engineer> allowedEngineers) {
        final Integer highestPreference = recipe.getEngineers().stream()
                .filter(allowedEngineers::contains)
                .map(engineerPreference::get)
                .max(Comparator.comparingInt(Integer::intValue))
                .orElseThrow(IllegalArgumentException::new);
        return recipe.getEngineers().stream()
                .filter(allowedEngineers::contains)
                .filter(engineer -> Objects.equals(engineerPreference.get(engineer), highestPreference))
                .toList();


    }
}
