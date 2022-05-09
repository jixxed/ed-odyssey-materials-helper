package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.templates.OdysseyWishlistBlueprintTemplate;
import nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprintTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PathService {

    private static final int MAX_ENGINEER_DISTANCE = 10000;//Ly

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();


    public static List<PathItem<HorizonsBlueprintName>> calculateHorizonsShortestPath(final List<WishlistBlueprintTemplate<HorizonsBlueprintName>> wishlistBlueprints) {
        final LinkedHashSet<HorizonsEngineeringBlueprint> distinctRecipes = wishlistBlueprints.stream()
                .filter(wishlistBlueprint -> wishlistBlueprint.getPrimaryRecipe() instanceof HorizonsEngineeringBlueprint)
                .filter(WishlistBlueprintTemplate::isVisibleBlueprint)
                .sorted(Comparator.comparing((WishlistBlueprintTemplate<HorizonsBlueprintName> e) -> ((HorizonsEngineeringBlueprint) e.getPrimaryRecipe()).getHorizonsBlueprintGrade().getGrade()).reversed())
                .filter(distinctByKey(PathService::getKey))
                .map(WishlistBlueprintTemplate::getPrimaryRecipe)
                .map(HorizonsEngineeringBlueprint.class::cast)
//                .sorted(Comparator.comparing((HorizonsEngineeringBlueprint e) -> e.getHorizonsBlueprintGrade().getGrade()).reversed())
//                .filter(distinctByKey(PathService::getKey))
                .sorted(Comparator.comparing(HorizonsEngineeringBlueprint::hasSingleEngineerPerRegion).thenComparing(horizonsEngineeringBlueprint -> (HorizonsBlueprintName) horizonsEngineeringBlueprint.getBlueprintName()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return calculateShortestPath(wishlistBlueprints, distinctRecipes);
    }

    private static Predicate<WishlistBlueprintTemplate<HorizonsBlueprintName>> distinctByKey(final Function<WishlistBlueprintTemplate<HorizonsBlueprintName>, Object> keyExtractor) {
        final Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private static Function<WishlistBlueprintTemplate<HorizonsBlueprintName>, Object> getKey(final WishlistBlueprintTemplate<HorizonsBlueprintName> blueprint) {
        return (bp) -> {
            if (blueprint.getPrimaryRecipe() instanceof HorizonsExperimentalEffectBlueprint experimentalEffectBlueprint) {
                return ((HorizonsWishlistBlueprint) bp.getWishlistRecipe()).getUuid();
//                return experimentalEffectBlueprint.getBlueprintName().name() + experimentalEffectBlueprint.getHorizonsBlueprintType().name();
            } else if (blueprint.getPrimaryRecipe() instanceof HorizonsModuleBlueprint moduleBlueprint) {
//                return moduleBlueprint.getBlueprintName().name() + moduleBlueprint.getHorizonsBlueprintType().name();
                return ((HorizonsWishlistBlueprint) bp.getWishlistRecipe()).getUuid();
            }
            return blueprint.getPrimaryRecipe().getBlueprintName();
        };
    }

    public static List<PathItem<OdysseyBlueprintName>> calculateOdysseyShortestPath(final List<OdysseyWishlistBlueprintTemplate> wishlistBlueprints) {

        final LinkedHashSet<EngineeringBlueprint<OdysseyBlueprintName>> distinctRecipes = wishlistBlueprints.stream()
                .filter(wishlistBlueprint -> wishlistBlueprint.getPrimaryRecipe() instanceof ModuleBlueprint)
                .filter(OdysseyWishlistBlueprintTemplate::isVisibleBlueprint)
                .map(OdysseyWishlistBlueprintTemplate::getPrimaryRecipe)
                .map(ModuleBlueprint.class::cast)
                .sorted(Comparator.comparing(ModuleBlueprint::hasSingleEngineerPerRegion).thenComparing((ModuleBlueprint moduleBlueprint) -> (OdysseyBlueprintName) moduleBlueprint.getBlueprintName()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return calculateShortestPath(wishlistBlueprints, distinctRecipes);
    }

    private static <T extends BlueprintName<T>> List<PathItem<T>> calculateShortestPath(final List<? extends WishlistBlueprintTemplate<T>> wishlistBlueprints, final LinkedHashSet<? extends EngineeringBlueprint<T>> distinctRecipes) {
        final List<PathItem<T>> sortedPathItems = new ArrayList<>();
        final List<PathItem<T>> pathItems = new ArrayList<>();
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
                        final Map<EngineeringBlueprint<T>, Integer> recipesForEngineer = getRecipesForEngineer(wishlistBlueprints, pathItems, selectedEngineer);
                        final PathItem<T> e = new PathItem(engineers, recipesForEngineer);
                        pathItems.add(e);
                    }
                });
        final int systemsToVisit = pathItems.size();
        for (int i = 0; i < systemsToVisit; i++) {
            final StarSystem finalCurrentStarSystem = currentStarSystem;
            if (pathItems.size() > 1) {
                final PathItem<T> closest = pathItems.stream()
                        .min((pathItem1, pathItem2) -> (int) (pathItem1.getAndSetDistanceToClosestEngineer(finalCurrentStarSystem) - pathItem2.getAndSetDistanceToClosestEngineer(finalCurrentStarSystem)))
                        .orElseThrow(IllegalArgumentException::new);
                sortedPathItems.add(closest);
                currentStarSystem = closest.getEngineer().getStarSystem();
                pathItems.remove(closest);
            } else if (pathItems.size() == 1) {
                final PathItem<T> closest = pathItems.get(0);
                closest.getAndSetDistanceToClosestEngineer(currentStarSystem);
                sortedPathItems.add(closest);
            }
        }
        tryOptimizePath(sortedPathItems);
        final Map<EngineeringBlueprint<T>, Integer> unCraftable = distinctRecipes.stream().filter(recipe -> recipe.getEngineers().stream().noneMatch(engineerPreference::containsKey)).collect(Collectors.groupingBy(
                recipe -> recipe,
                Collectors.summingInt(value -> 1))
        );
        if (unCraftable.size() > 0) {
            final PathItem<T> pathItem = new PathItem(List.of(Engineer.UNKNOWN), unCraftable);
            pathItem.setEngineer(Engineer.UNKNOWN);
            pathItem.setDistance(0.0);
            sortedPathItems.add(pathItem);
        }
        return sortedPathItems;
    }

    private static <T extends BlueprintName<T>> void tryOptimizePath(final List<PathItem<T>> sortedPathItems) {
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

    private static <T extends BlueprintName<T>> void setShortestPath(final List<PathItem<T>> sortedPathItems, final List<Engineer> shortestPath) {
        for (int i = 0; i < sortedPathItems.size(); i++) {
            final Engineer engineer = shortestPath.get(i);
            final StarSystem starSystem = (i == 0) ? LocationService.getCurrentStarSystem() : sortedPathItems.get(i - 1).getEngineer().getStarSystem();
            sortedPathItems.get(i).setEngineerAndCalculateDistance(engineer, starSystem);
        }
    }

    private static <T extends BlueprintName<T>> List<List<Engineer>> getPossiblePathsForItem(final List<PathItem<T>> sortedPathItems) {
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

    private static <T extends BlueprintName<T>> List<List<Engineer>> getPossiblePathsForItem(final List<PathItem<T>> sortedPathItems, final List<Engineer> currentPath, final int index) {
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

    private static <T extends BlueprintName<T>> Map<EngineeringBlueprint<T>, Integer> getRecipesForEngineer(final List<? extends WishlistBlueprintTemplate<T>> wishlistBlueprints, final List<PathItem<T>> pathItems, final Engineer engineer) {
        final List<EngineeringBlueprint<T>> recipes = wishlistBlueprints.stream()
                .filter(WishlistBlueprintTemplate::isVisibleBlueprint)
                .map(WishlistBlueprintTemplate::getPrimaryRecipe)
                .filter(EngineeringBlueprint.class::isInstance)
                .map(blueprint -> (EngineeringBlueprint<T>) blueprint)
                .toList();
        return recipes.stream()
                .filter(recipe -> recipe.getEngineers().contains(engineer))
                .filter(recipe -> pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().containsKey(recipe)))
                .collect(Collectors.groupingBy(
                        recipe -> recipe,
                        Collectors.summingInt(value -> 1))
                );
    }

    private static <T extends BlueprintName<T>> List<Engineer> mostPreferredEngineers(final Map<Engineer, Integer> engineerPreference, final EngineeringBlueprint<T> recipe, final List<Engineer> allowedEngineers) {
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
