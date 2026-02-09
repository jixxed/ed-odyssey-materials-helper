package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PathService {

    private static final int MAX_ENGINEER_DISTANCE = 10000;//Ly

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private static StarSystem lastHorizonsStarSystem;
    private static List<HorizonsWishlistBlueprint> lastHorizonsWishlistBlueprints;
    private static List<PathItem<HorizonsBlueprintName>> lastHorizonsCalculation;
    private static StarSystem lastOdysseyStarSystem;
    private static List<OdysseyWishlistBlueprint> lastOdysseyWishlistBlueprints;
    private static List<PathItem<OdysseyBlueprintName>> lastOdysseyCalculation;
    private static LocalDateTime lastPinnedCheck = LocalDateTime.MIN;

    public static List<PathItem<HorizonsBlueprintName>> calculateHorizonsShortestPath(final List<HorizonsWishlistBlueprint> wishlistBlueprints) {
        if (lastHorizonsCalculation != null && !PinnedBlueprintService.hasChangedSince(lastPinnedCheck) && LocationService.getCurrentStarSystem().equals(lastHorizonsStarSystem) && sameBlueprints(wishlistBlueprints, lastHorizonsWishlistBlueprints)) {
//            log.info("Shortest path horizons cache hit");
            return lastHorizonsCalculation;
        }
        lastPinnedCheck = LocalDateTime.now();
        var splittedBlueprints = new ArrayList<>(wishlistBlueprints);
        final List<HorizonsExperimentalWishlistBlueprint> collect = wishlistBlueprints.stream()
                .filter(HorizonsModuleWishlistBlueprint.class::isInstance)
                .map(HorizonsModuleWishlistBlueprint.class::cast)
                .filter(bp -> bp.getExperimentalEffect() != null)
                .map(bp -> {
                    final HorizonsExperimentalWishlistBlueprint blueprint = new HorizonsExperimentalWishlistBlueprint(bp.getExperimentalEffect());
                    blueprint.setRecipeName(bp.getRecipeName());
                    blueprint.setQuantity(bp.getQuantity());
                    blueprint.setVisible(bp.isVisible());
                    blueprint.setUuid("-1");
                    return blueprint;
                })
                .toList();
        splittedBlueprints.addAll(collect);
        final LinkedHashSet<HorizonsEngineeringBlueprint> distinctRecipes = splittedBlueprints.stream()
                .map(WishlistBlueprint::getBlueprint)
                .filter(HorizonsEngineeringBlueprint.class::isInstance)
                .map(HorizonsEngineeringBlueprint.class::cast)
                .filter(distinctByKey2(PathService::getKey2))
                .sorted(Comparator.comparing(HorizonsEngineeringBlueprint::hasSingleEngineerPerRegion)
                        .thenComparing(horizonsEngineeringBlueprint -> (HorizonsBlueprintName) horizonsEngineeringBlueprint.getBlueprintName())
                        .thenComparing(horizonsEngineeringBlueprint -> horizonsEngineeringBlueprint.getHorizonsBlueprintGrade().getGrade()).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        lastHorizonsStarSystem = LocationService.getCurrentStarSystem();
        lastHorizonsWishlistBlueprints = wishlistBlueprints;
        lastHorizonsCalculation = calculateShortestPath(splittedBlueprints, distinctRecipes, false);
        return lastHorizonsCalculation;
    }

    private static boolean sameBlueprints(List<HorizonsWishlistBlueprint> wishlistBlueprints, List<HorizonsWishlistBlueprint> lastHorizonsWishlistBlueprints) {
        return lastHorizonsWishlistBlueprints.size() == wishlistBlueprints.size()
                && lastHorizonsWishlistBlueprints.stream()
                .filter(element -> !wishlistBlueprints.contains(element))
                .toList()
                .isEmpty();
    }

    @SuppressWarnings("java:S1640")
    private static <T extends BlueprintName<T>> List<PathItem<T>> calculateShortestPath(final List<? extends WishlistBlueprint<T>> wishlistBlueprints, final LinkedHashSet<? extends EngineeringBlueprint<T>> distinctRecipes, final boolean isOdyssey) {
        final List<PathItem<T>> sortedPathItems = new ArrayList<>();
        final List<PathItem<T>> pathItems = new ArrayList<>();
        final Map<Engineer, Integer> engineerPreference = new HashMap<>();
        StarSystem currentStarSystem = LocationService.getCurrentStarSystem();
        final StarSystem finalCurrentStarSystem1 = currentStarSystem;
        final Set<Engineer> allowedEngineers = Arrays.stream(Engineer.values())
                .filter(engineer -> engineer.getDistance(finalCurrentStarSystem1) < MAX_ENGINEER_DISTANCE)
                .filter(engineer -> (isOdyssey) ? APPLICATION_STATE.isEngineerUnlocked(engineer) : APPLICATION_STATE.isEngineerUnlockedExact(engineer))
                .collect(Collectors.toSet());
        allowedEngineers.add(Engineer.REMOTE_WORKSHOP);
        distinctRecipes.forEach(recipe -> {
                    final Engineer closestEngineer = recipe.getEngineers().stream().min(Comparator.comparing(engineer -> engineer.getDistance(finalCurrentStarSystem1))).orElseThrow(IllegalArgumentException::new);
                    recipe.getEngineers().stream()
                            .filter(allowedEngineers::contains)
                            .forEach(engineer -> {
                                int value = 1;
                                if (engineer.getDistance(finalCurrentStarSystem1).equals(0D)) {
                                    value = 100;
                                } else if (closestEngineer == engineer) {
                                    value = 2;
                                }
                                engineerPreference.put(engineer, value + engineerPreference.getOrDefault(engineer, 0));
                            });
                }
        );
        Stream<EngineeringBlueprint<?>> stream = isOdyssey
                ? getOdysseyStream(distinctRecipes, allowedEngineers, engineerPreference)
                : getHorizonsStream(distinctRecipes, allowedEngineers, engineerPreference);
        stream.filter(recipe -> recipe.getEngineers().stream().anyMatch(engineerPreference::containsKey))
                .forEachOrdered(recipe -> {
                    if (recipe.getEngineers().stream().anyMatch(engineer -> engineer.getDistance(finalCurrentStarSystem1).equals(0D)) || pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().containsKey(recipe))) {
                        final List<Engineer> engineers = mostPreferredEngineers(engineerPreference, recipe, allowedEngineers);
                        //prefer a previously selected engineer, otherwise first in the list
                        final Engineer selectedEngineer = engineers.stream()
                                .filter(engineer -> pathItems.stream().anyMatch(pathItem -> pathItem.getEngineers().contains(engineer)) || engineer.getDistance(finalCurrentStarSystem1).equals(0D))
                                .min(Comparator.comparing(engineer -> engineer.getDistance(finalCurrentStarSystem1)))
                                .orElse(engineers.getFirst());
                        recipe.getEngineers().stream()
                                .filter(engineer -> engineer != selectedEngineer)
                                .forEach(engineer -> {
                                    if (engineerPreference.containsKey(engineer)) {
                                        engineerPreference.put(engineer, engineerPreference.get(engineer) - 1);
                                    }
                                });
                        final List<? extends WishlistBlueprint<T>> blueprintsForEngineer = getBlueprintsForEngineer(wishlistBlueprints, pathItems, selectedEngineer, allowedEngineers, isOdyssey);
                        if (!blueprintsForEngineer.isEmpty()) {
                            final PathItem<T> e = new PathItem<>(engineers, blueprintsForEngineer.stream().map(WishlistBlueprint::getBlueprint).toList());
                            pathItems.add(e);
                        }
                    }
                });


        final int systemsToVisit = pathItems.size();
        for (int i = 0; i < systemsToVisit; i++) {
            final StarSystem finalCurrentStarSystem = currentStarSystem;
            if (pathItems.size() > 1) {
                final PathItem<T> closest = pathItems.stream()
                        .min(Comparator.comparingDouble(pathItem -> pathItem.getAndSetDistanceToClosestEngineer(finalCurrentStarSystem)))
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
        final List<? extends WishlistBlueprint<T>> unCraftable = wishlistBlueprints.stream()
                .filter(WishlistBlueprint::isVisible)
                .filter(bp -> bp.getBlueprint() instanceof EngineeringBlueprint<T>)
                .filter(bp -> ((EngineeringBlueprint<T>) bp.getBlueprint()).getEngineers().stream().noneMatch(engineerPreference::containsKey))
                .toList();
        if (!unCraftable.isEmpty()) {
            final PathItem<T> pathItem = new PathItem<>(List.of(Engineer.UNKNOWN), unCraftable.stream().map(WishlistBlueprint::getBlueprint).toList());
            pathItem.setEngineer(Engineer.UNKNOWN);
            pathItem.setDistance(0.0);
            sortedPathItems.add(pathItem);
        }
        return sortedPathItems;
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlueprintName<T>> Stream<EngineeringBlueprint<?>> getHorizonsStream(LinkedHashSet<? extends EngineeringBlueprint<T>> distinctRecipes, Set<Engineer> allowedEngineers, Map<Engineer, Integer> engineerPreference) {
        return distinctRecipes.stream()
                .map(HorizonsEngineeringBlueprint.class::cast)
                .sorted(Comparator
                        .comparing(HorizonsEngineeringBlueprint::hasSingleEngineerPerRegion)
                        .thenComparing(bp -> bp.getEngineers().stream().filter(allowedEngineers::contains).max(Comparator.comparingInt(engineerPreference::get)).map(engineerPreference::get).orElse(0))
                        .thenComparing(horizonsEngineeringBlueprint -> (HorizonsBlueprintName) horizonsEngineeringBlueprint.getBlueprintName())
                        .thenComparing(horizonsEngineeringBlueprint -> horizonsEngineeringBlueprint.getHorizonsBlueprintGrade().getGrade()).reversed()).map(EngineeringBlueprint.class::cast);
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlueprintName<T>> Stream<EngineeringBlueprint<?>> getOdysseyStream(LinkedHashSet<? extends EngineeringBlueprint<T>> distinctRecipes, Set<Engineer> allowedEngineers, Map<Engineer, Integer> engineerPreference) {
        return distinctRecipes.stream()
                .map(ModuleBlueprint.class::cast)
                .sorted(Comparator
                        .comparing(ModuleBlueprint::hasSingleEngineerPerRegion)
                        .thenComparing(bp -> bp.getEngineers().stream().filter(allowedEngineers::contains).max(Comparator.comparingInt(engineerPreference::get)).map(engineerPreference::get).orElse(0))
                        .thenComparing((ModuleBlueprint moduleBlueprint) -> (OdysseyBlueprintName) moduleBlueprint.getBlueprintName())).map(EngineeringBlueprint.class::cast);
    }


    private static Predicate<HorizonsEngineeringBlueprint> distinctByKey2(final Function<HorizonsEngineeringBlueprint, Object> keyExtractor) {
        final Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private static Function<HorizonsEngineeringBlueprint, Object> getKey2(final HorizonsEngineeringBlueprint blueprint) {
        return bp -> blueprint.getBlueprintName().toString() + blueprint.getHorizonsBlueprintType().toString();
    }

    public static List<PathItem<OdysseyBlueprintName>> calculateOdysseyShortestPath(final List<OdysseyWishlistBlueprint> wishlistBlueprints) {
        if (LocationService.getCurrentStarSystem().equals(lastOdysseyStarSystem) && wishlistBlueprints.equals(lastOdysseyWishlistBlueprints) && lastOdysseyCalculation != null) {
//            log.info("Shortest path horizons cache hit");
            return lastOdysseyCalculation;
        }
        final LinkedHashSet<ModuleBlueprint> distinctRecipes = wishlistBlueprints.stream()
                .map(WishlistBlueprint::getBlueprint)
                .filter(ModuleBlueprint.class::isInstance)
                .map(ModuleBlueprint.class::cast)
                .sorted(Comparator.comparing(ModuleBlueprint::hasSingleEngineerPerRegion)
                        .thenComparing(moduleBlueprint -> (OdysseyBlueprintName) moduleBlueprint.getBlueprintName()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        lastOdysseyStarSystem = LocationService.getCurrentStarSystem();
        lastOdysseyWishlistBlueprints = wishlistBlueprints;
        lastOdysseyCalculation = calculateShortestPath(wishlistBlueprints, distinctRecipes, true);
        return lastOdysseyCalculation;
    }

    private static <T extends BlueprintName<T>> void optimizePathItems(final List<PathItem<T>> pathItems) {
        for (int index = 0; index < pathItems.size(); index++) {
            final PathItem<T> toMove = pathItems.get(index);
            if (toMove.getEngineer().getDistance(LocationService.getCurrentStarSystem()).equals(0D)) {
                continue;
            }
            final List<? extends Blueprint<T>> blueprints = toMove.getRecipes().entrySet().stream().flatMap(entry -> createList(entry.getKey(), entry.getValue()).stream()).toList();
            final int finalIndex = index;
            final AtomicInteger count = new AtomicInteger(0);
            for (final Blueprint<T> blueprint : blueprints) {
                for (int index2 = 0; index2 < pathItems.size(); index2++) {
                    //skip self
                    if (finalIndex == index2) {
                        continue;
                    }
                    final PathItem<T> toMoveTo = pathItems.get(index2);
                    if (blueprint instanceof ModuleBlueprint mbp) {
                        if (mbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                            count.incrementAndGet();
                            break;
                        }
                    }
                    if (blueprint instanceof HorizonsBlueprint hbp) {
                        if (hbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                            count.incrementAndGet();
                            break;
                        }
                    }
                    if (blueprint instanceof HorizonsExperimentalEffectBlueprint hbp) {
                        if (hbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                            count.incrementAndGet();
                            break;
                        }
                    }

                }

            }
            if (count.get() == blueprints.size()) {//all bp's can be moved
                for (int i = 0; i < blueprints.size(); i++) {
                    final Blueprint<T> bp = blueprints.get(i);
                    for (int index2 = 0; index2 < pathItems.size(); index2++) {
                        //skip self
                        if (finalIndex == index2) {
                            continue;
                        }
                        final PathItem<T> toMoveTo = pathItems.get(index2);
                        if (bp instanceof ModuleBlueprint mbp) {
                            if (mbp.getEngineers().stream().anyMatch(eng -> toMoveTo.getEngineer().equals(eng))) {
                                toMoveTo.addBlueprint(bp);
                                break;
                            }
                        }
                        if (bp instanceof HorizonsBlueprint hbp) {
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

    private static <T extends BlueprintName<T>> List<? extends Blueprint<T>> createList(Blueprint<T> key, Integer value) {
        final List<Blueprint<T>> list = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            list.add(key);
        }
        return list;
    }

    private static <T extends BlueprintName<T>> void tryOptimizePath(final List<PathItem<T>> sortedPathItems) {
        if (sortedPathItems.size() > 1) {
            final List<List<Engineer>> paths = getPossiblePathsForItem(sortedPathItems).stream()
                    .filter(engineers -> !engineers.contains(Engineer.REMOTE_WORKSHOP) || engineers.getFirst().equals(Engineer.REMOTE_WORKSHOP))
                    .collect(Collectors.toList());
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

    private static <T extends BlueprintName<T>> List<? extends WishlistBlueprint<T>> getBlueprintsForEngineer(final List<? extends WishlistBlueprint<T>> wishlistBlueprints, final List<PathItem<T>> pathItems, final Engineer engineer, final Set<Engineer> allowedEngineers, boolean isOdyssey) {

        return wishlistBlueprints.stream()
                .filter(WishlistBlueprint::isVisible)
                .filter(bp -> bp instanceof HorizonsModuleWishlistBlueprint || bp instanceof HorizonsExperimentalWishlistBlueprint || bp instanceof OdysseyWishlistBlueprint)
                .filter(bp -> getEngineers(bp.getBlueprint(), isOdyssey).contains(engineer))
                .filter(bp -> pathItems.stream().noneMatch(pathItem -> pathItem.getRecipes().containsKey(bp.getBlueprint())))
                .filter(bp -> engineer.equals(Engineer.REMOTE_WORKSHOP) || !(getEngineers((EngineeringBlueprint<?>) bp.getBlueprint(), isOdyssey).contains(Engineer.REMOTE_WORKSHOP)))
                .filter(bp -> (wishlistBlueprints.stream().filter(wb -> wb.getBlueprint() != null).filter(WishlistBlueprint::isVisible).noneMatch(
                                wbp -> (wbp instanceof HorizonsModuleWishlistBlueprint hmwbp)
                                        && hmwbp.getBlueprint().getBlueprintName().equals(bp.getBlueprint().getBlueprintName())
                                        && !hmwbp.getPercentageToComplete().keySet().stream().allMatch(grade -> ((HorizonsModuleBlueprint) HorizonsBlueprintConstants.getRecipe(hmwbp.getRecipeName(), hmwbp.getBlueprintType(), grade)).getEngineers().contains(engineer)))
                                ||
                                wishlistBlueprints.stream().filter(WishlistBlueprint::isVisible).anyMatch(wbp -> (wbp.getBlueprint() instanceof HorizonsModuleBlueprint hwbp)
                                        && hwbp.getBlueprintName().equals(bp.getBlueprint().getBlueprintName())
                                        && (hwbp.getEngineers().stream().noneMatch(allowedEngineers::contains)
                                        || hwbp.getEngineers().contains(Engineer.REMOTE_WORKSHOP))
                                )
                        )
                )
                .collect(Collectors.toList());

    }

    public static <E extends BlueprintName<E>> List<Engineer> getEngineers(Blueprint<E> blueprint, boolean isOdyssey) {
        List<Engineer> engineers = new ArrayList<>();
        if (blueprint instanceof EngineeringBlueprint<E> engineeringBlueprint) {
            engineers.addAll(engineeringBlueprint.getEngineers());
        }
        if (blueprint instanceof HorizonsModuleBlueprint moduleBlueprint && canCraftInRemoteWorkshop(moduleBlueprint, isOdyssey)) {
            engineers.add(Engineer.REMOTE_WORKSHOP);
        }
        return engineers;
    }

    private static boolean canCraftInRemoteWorkshop(HorizonsModuleBlueprint moduleBlueprint, boolean isOdyssey) {
        return moduleBlueprint.getEngineers().stream().anyMatch(engineer -> {
            boolean isUnlocked = !isOdyssey && APPLICATION_STATE.isEngineerUnlockedExact(engineer);
            boolean canCraft = moduleBlueprint.getHorizonsBlueprintGrade().getGrade() <= APPLICATION_STATE.getEngineerRank(engineer);
            return isUnlocked && canCraft && PinnedBlueprintService.isPinned(engineer, moduleBlueprint);
        });
    }

    private static <T extends BlueprintName<T>> List<Engineer> mostPreferredEngineers(final Map<Engineer, Integer> engineerPreference, final EngineeringBlueprint<T> recipe, final Set<Engineer> allowedEngineers) {
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
