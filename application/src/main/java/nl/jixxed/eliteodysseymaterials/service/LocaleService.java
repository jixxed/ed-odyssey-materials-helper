package nl.jixxed.eliteodysseymaterials.service;

import javafx.beans.binding.ListBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import nl.jixxed.eliteodysseymaterials.BarterConstants;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.SpawnConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LocaleService {
    private static final String RESOURCE_BUNDLE_NAME = "locale";
    private static Locale currentLocale = Locale.ENGLISH;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    private LocaleService() {
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(final Locale locale) {
        currentLocale = locale;
        ObservableResourceFactory.setResources(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, currentLocale));
    }

    public static String getLocalizedStringForCurrentLocale(final String key, final Object... parameters) {
        return getLocalizedString(getCurrentLocale(), key, parameters);
    }

    private static String getLocalizedString(final Locale locale, final String key, final Object... parameters) {
        return MessageFormat.format(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale).getString(key), parameters);
    }

    public static StringBinding getStringBinding(final String key, final Object... parameters) {
        return ObservableResourceFactory.getStringBinding(() -> MessageFormat.format(ObservableResourceFactory.getResources().getString(key), parameters));
    }

    public static ObservableValue<String> getSupplierStringBinding(final String key, final Supplier<Object>... parameterSuppliers) {
        return ObservableResourceFactory.getStringBinding(() -> {
            final Object[] parameters = Arrays.stream(parameterSuppliers).map(Supplier::get).toArray(Object[]::new);
            return MessageFormat.format(ObservableResourceFactory.getResources().getString(key), parameters);
        });
    }

    public static StringBinding getStringBinding(final Material material) {
        return ObservableResourceFactory.getStringBinding(() -> ObservableResourceFactory.getResources().getString(material.getLocalizationKey()) + (APPLICATION_STATE.isFavourite(material) ? " \u2605" : ""));
    }

    public static StringBinding getStringBinding(final Supplier<String> supplier) {
        return ObservableResourceFactory.getStringBinding(supplier);
    }

    public static StringBinding getToolTipStringBinding(final Material material) {
        return ObservableResourceFactory.getStringBinding(() -> {
            if (material.isUnknown()) {
                return ObservableResourceFactory.getResources().getString("material.tooltip.unknown");
            } else {
                final StringBuilder builder = new StringBuilder();
                builder.append(ObservableResourceFactory.getResources().getString(material.getLocalizationKey()));
                addBarterInfoToTooltip(material, builder);
                addRecipesToTooltip(RecipeConstants.findRecipesContaining(material), builder);
                addSpawnLocationsToTooltip(SpawnConstants.getSpawnLocations(material), builder);
                return builder.toString();
            }
        });
    }

    private static void addBarterInfoToTooltip(final Material material, final StringBuilder builder) {
        final Integer barterSellPrice = BarterConstants.getBarterSellPrice(material);
        builder.append("\n\n").append(ObservableResourceFactory.getResources().getString("material.tooltip.barter.sell.price")).append(": $").append(barterSellPrice == -1 ? "?" : NUMBER_FORMAT.format(barterSellPrice));
        if (material instanceof Asset) {
            builder.append("\n").append(ObservableResourceFactory.getResources().getString("material.tooltip.barter.trade")).append(": ").append(BarterConstants.getBarterValues(material));
        }
    }

    private static void addRecipesToTooltip(final Map<RecipeName, Integer> recipesContainingMaterial, final StringBuilder builder) {
        if (!recipesContainingMaterial.isEmpty()) {
            builder.append("\n\n").append(ObservableResourceFactory.getResources().getString("material.tooltip.used.in.recipes")).append(":\n");
            recipesContainingMaterial.entrySet().stream().sorted(Comparator.comparing(entry -> ObservableResourceFactory.getResources().getString(entry.getKey().getLocalizationKey()))).forEach(entry -> builder.append(ObservableResourceFactory.getResources().getString(entry.getKey().getLocalizationKey())).append(" (").append(entry.getValue()).append(")\n"));
        }
    }

    private static void addSpawnLocationsToTooltip(final Map<SpawnLocationType, List<? extends SpawnLocation>> spawnLocations, final StringBuilder builder) {
        if (!spawnLocations.isEmpty()) {
            spawnLocations.forEach((locationType, value) -> {
                final String locations = value.stream().map(spawnLocation -> ObservableResourceFactory.getResources().getString(spawnLocation.getLocalizationKey())).collect(Collectors.joining(", "));
                if (!locations.isBlank()) {
                    builder.append("\n\n").append(ObservableResourceFactory.getResources().getString(locationType.getLocalizationKey())).append(":\n");
                    builder.append(locations);
                }
            });
        }
    }

    public static StringBinding getToolTipStringBinding(final ModuleRecipe recipe) {
        return ObservableResourceFactory.getStringBinding(() -> ObservableResourceFactory.getResources().getString("tab.wishlist.blueprint.tooltip") + "\n" + recipe.getEngineers().stream().map(engineer -> ObservableResourceFactory.getResources().getString(engineer.getLocalizationKey())).collect(Collectors.joining(", ")));
    }

    @SafeVarargs
    public static <T> ListBinding<T> getListBinding(final T... items) {
        return ObservableResourceFactory.getListBinding(items);
    }

}
