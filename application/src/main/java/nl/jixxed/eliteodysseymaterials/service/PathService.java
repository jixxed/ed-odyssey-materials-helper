package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.templates.generic.WishlistBlueprintTemplate;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist.OdysseyWishlistBlueprintTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
                .sorted(Comparator.comparing(HorizonsEngineeringBlueprint::hasSingleEngineerPerRegion).thenComparing(horizonsEngineeringBlueprint -> (HorizonsBlueprintName) horizonsEngineeringBlueprint.getBlueprintName())
                        .thenComparing(horizonsEngineeringBlueprint -> horizonsEngineeringBlueprint.getHorizonsBlueprintGrade().getGrade()).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return calculateShortestPath(wishlistBlueprints, distinctRecipes, false);
    }

    private static Predicate<WishlistBlueprintTemplate<HorizonsBlueprintName>> distinctByKey(final Function<WishlistBlueprintTemplate<HorizonsBlueprintName>, Object> keyExtractor) {
        final Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private static Function<WishlistBlueprintTemplate<HorizonsBlueprintName>, Object> getKey(final WishlistBlueprintTemplate<HorizonsBlueprintName> blueprint) {
        return bp -> {
            if (blueprint.getPrimaryRecipe() instanceof HorizonsExperimentalEffectBlueprint || blueprint.getPrimaryRecipe() instanceof HorizonsModuleBlueprint) {
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
        return calculateShortestPath(wishlistBlueprints, distinctRecipes, true);
    }

    private static <T extends BlueprintName<T>> List<PathItem<T>> calculateShortestPath(final List<? extends WishlistBlueprintTemplate<T>> wishlistBlueprints, final LinkedHashSet<? extends EngineeringBlueprint<T>> distinctRecipes, final boolean isOdyssey) {
        final List<PathItem<T>> sortedPathItems = new ArrayList<>();
        final List<PathItem<T>> pathItems = new ArrayList<>();
        final Map<Engineer, Integer> engineerPreference = new EnumMap<>(Engineer.class);
        StarSystem currentStarSystem = LocationService.getCurrentStarSystem();
        final StarSystem finalCurrentStarSystem1 = currentStarSystem;
        final List<Engineer> allowedEngineers = Arrays.stream(Engineer.values())
                .filter(engineer -> engineer.getDistance(finalCurrentStarSystem1) < MAX_ENGINEER_DISTANCE)
                .filter(engineer -> (isOdyssey) ? APPLICATION_STATE.isEngineerUnlocked(engineer) : APPLICATION_STATE.isEngineerUnlockedExact(engineer))
                .collect(Collectors.toList());
        allowedEngineers.add(Engineer.REMOTE_WORKSHOP);
        distinctRecipes.forEach(recipe -> recipe.getEngineers().stream()
                .filter(allowedEngineers::contains)
                .forEach(engineer -> engineerPreference.put(engineer, 1 + engineerPreference.getOrDefault(engineer, 0))));


        distinctRecipes.stream()
                .filter(recipe -> recipe.getEngineers().stream().anyMatch(engineerPreference::containsKey))
                .forEachOrdered(recipe -> {
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
                        final List<? extends WishlistBlueprintTemplate<T>> blueprintsForEngineer = getBlueprintsForEngineer(wishlistBlueprints, pathItems, selectedEngineer, allowedEngineers);
                        if (!blueprintsForEngineer.isEmpty()) {
                            final PathItem<T> e = new PathItem<>(engineers, blueprintsForEngineer);
                            pathItems.add(e);
                        }
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
        optimizePathItems(sortedPathItems);
        tryOptimizePath(sortedPathItems);
        final List<? extends WishlistBlueprintTemplate<T>> unCraftable = wishlistBlueprints.stream()
                .filter(WishlistBlueprintTemplate::isVisibleBlueprint)
                .filter(bp -> bp.getPrimaryRecipe() instanceof EngineeringBlueprint<T>)
                .filter(bp -> ((EngineeringBlueprint<T>) bp.getPrimaryRecipe()).getEngineers().stream().noneMatch(engineerPreference::containsKey))
                .toList();
        if (!unCraftable.isEmpty()) {
            final PathItem<T> pathItem = new PathItem<>(List.of(Engineer.UNKNOWN), unCraftable);
            pathItem.setEngineer(Engineer.UNKNOWN);
            pathItem.setDistance(0.0);
            sortedPathItems.add(pathItem);
        }
        return sortedPathItems;
    }

    private static <T extends BlueprintName<T>> void optimizePathItems(final List<PathItem<T>> pathItems) {
        for (int index = 0; index < pathItems.size(); index++) {
            final PathItem<T> toMove = pathItems.get(index);
            final List<? extends WishlistBlueprintTemplate<T>> blueprints = toMove.getBlueprints();
            final int finalIndex = index;
            final AtomicInteger count = new AtomicInteger(0);
            for (final WishlistBlueprintTemplate<T> blueprint : blueprints) {
                for (int index2 = 0; index2 < pathItems.size(); index2++) {
                    //skip self
                    if (finalIndex == index2) {
                        continue;
                    }
                    final PathItem<T> toMoveTo = pathItems.get(index2);
//                blueprints.values().stream()
//                        .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
//                        .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
//                        .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(this.engineer))
                    if (blueprint.getPrimaryRecipe() instanceof ModuleBlueprint mbp) {
                        if (mbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                            count.incrementAndGet();
                            break;
                        }
                    }
                    if (blueprint.getPrimaryRecipe() instanceof HorizonsBlueprint hbp) {
                        if (hbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                            count.incrementAndGet();
                            break;
                        }
                    }
                    if (blueprint.getPrimaryRecipe() instanceof HorizonsExperimentalEffectBlueprint hbp) {
                        if (hbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                            count.incrementAndGet();
                            break;
                        }
                    }

                }

            }
            if (count.get() == blueprints.size()) {//all bp's can be moved
                for (int i = 0; i < blueprints.size(); i++) {
                    final WishlistBlueprintTemplate<T> bp = blueprints.get(i);
                    for (int index2 = 0; index2 < pathItems.size(); index2++) {
                        //skip self
                        if (finalIndex == index2) {
                            continue;
                        }
                        final PathItem<T> toMoveTo = pathItems.get(index2);
                        if (bp.getPrimaryRecipe() instanceof ModuleBlueprint mbp) {
                            if (mbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                                toMoveTo.addBlueprint(bp);
                                break;
                            }
                        }
                        if (bp.getPrimaryRecipe() instanceof HorizonsBlueprint hbp) {
                            if (hbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                                toMoveTo.addBlueprint(bp);
                                break;
                            }
                        }

                    }

                }
                pathItems.remove(toMove);
            }
        }
    }

    private static <T extends BlueprintName<T>> void tryOptimizePath(final List<PathItem<T>> sortedPathItems) {
        if (sortedPathItems.size() > 1) {
            final List<List<Engineer>> paths = getPossiblePathsForItem(sortedPathItems).stream().filter(engineers -> !engineers.contains(Engineer.REMOTE_WORKSHOP) || engineers.get(0).equals(Engineer.REMOTE_WORKSHOP)).collect(Collectors.toList());
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

    private static <T extends BlueprintName<T>> List<? extends WishlistBlueprintTemplate<T>> getBlueprintsForEngineer(final List<? extends WishlistBlueprintTemplate<T>> wishlistBlueprints, final List<PathItem<T>> pathItems, final Engineer engineer, final List<Engineer> allowedEngineers) {
        return wishlistBlueprints.stream()
                .filter(WishlistBlueprintTemplate::isVisibleBlueprint)
                .filter(bp -> bp.getPrimaryRecipe() instanceof EngineeringBlueprint<T>)
                .filter(bp -> ((EngineeringBlueprint<T>) bp.getPrimaryRecipe()).getEngineers().contains(engineer))
                .filter(bp -> pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().containsKey(bp.getPrimaryRecipe())))
                .filter(bp -> engineer.equals(Engineer.REMOTE_WORKSHOP) || !((EngineeringBlueprint<T>) bp.getPrimaryRecipe()).getEngineers().contains(Engineer.REMOTE_WORKSHOP))
                .filter(bp -> bp.getPrimaryRecipe() instanceof ModuleBlueprint || (bp.getPrimaryRecipe() instanceof HorizonsModuleBlueprint) ||
                        (bp.getPrimaryRecipe() instanceof HorizonsExperimentalEffectBlueprint &&
                                (wishlistBlueprints.stream().filter(WishlistBlueprintTemplate::isVisibleBlueprint).noneMatch(
                                        wbp -> (wbp.getPrimaryRecipe() instanceof HorizonsModuleBlueprint)
                                                && wbp.getPrimaryRecipe().getBlueprintName().equals(bp.getPrimaryRecipe().getBlueprintName())
                                                && !wbp.getRecipe().stream().allMatch(bp1 -> ((HorizonsModuleBlueprint) bp1).getEngineers().contains(engineer)))
                                        ||
                                        wishlistBlueprints.stream().filter(WishlistBlueprintTemplate::isVisibleBlueprint).anyMatch(wbp -> (wbp.getPrimaryRecipe() instanceof HorizonsModuleBlueprint hwbp)
                                                && hwbp.getBlueprintName().equals(bp.getPrimaryRecipe().getBlueprintName())
                                                && (hwbp.getEngineers().stream().noneMatch(allowedEngineers::contains)
                                                || hwbp.getEngineers().contains(Engineer.REMOTE_WORKSHOP))
                                        )
                                )
                        )
                )
                .collect(Collectors.toList());

    }

    private static <T extends BlueprintName<T>> List<Engineer> mostPreferredEngineers(final Map<Engineer, Integer> engineerPreference, final EngineeringBlueprint<T> recipe, final List<Engineer> allowedEngineers) {
        if (recipe instanceof HorizonsEngineeringBlueprint bp && bp.getEngineers().contains(Engineer.REMOTE_WORKSHOP)) {
            return List.of(Engineer.REMOTE_WORKSHOP);
        }
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
