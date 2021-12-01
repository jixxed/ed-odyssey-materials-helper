package nl.jixxed.eliteodysseymaterials.service;

import javafx.beans.binding.ListBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.constants.BarterConstants;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.constants.SpawnConstants;
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
        return ObservableResourceFactory.getStringBinding(() -> {
            final String[] localizedParams = Arrays.stream(parameters).map(LocaleService::localizeParameter).toArray(String[]::new);
            return MessageFormat.format(ObservableResourceFactory.getResources().getString(key), (Object[]) localizedParams);
        });
    }

//    public static StringBinding getStringBindingLocalizedParameters(final String key, final String... parameters) {
//        return ObservableResourceFactory.getStringBinding(() -> {
//            final String[] localizedParams = Arrays.stream(parameters).map(parameter -> ObservableResourceFactory.getResources().getString(parameter)).toArray(String[]::new);
//            return MessageFormat.format(ObservableResourceFactory.getResources().getString(key), (Object[]) localizedParams);
//        });
//    }

    public static ObservableValue<String> getSupplierStringBinding(final String key, final Supplier<Object>... parameterSuppliers) {
        return ObservableResourceFactory.getStringBinding(() -> {
            final Object[] parameters = Arrays.stream(parameterSuppliers).map(Supplier::get).toArray(Object[]::new);
            return MessageFormat.format(ObservableResourceFactory.getResources().getString(key), parameters);
        });
    }

    public static StringBinding getStringBinding(final Material material) {
        return ObservableResourceFactory.getStringBinding(() -> ObservableResourceFactory.getResources().getString(material.getLocalizationKey()) + (material.isIllegal() ? "   \u20E0 " : "") + (APPLICATION_STATE.isFavourite(material) ? " \u2605" : ""));
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
                if (material.isIllegal()) {
                    builder.append("\n\n").append(ObservableResourceFactory.getResources().getString("material.tooltip.illegal"));
                }
                addBarterInfoToTooltip(material, builder);
                if (material instanceof Data data) {
                    addTransferTimeToTooltip(data, builder);
                }
                addRecipesToTooltip(RecipeConstants.findRecipesContaining(material), builder);
                addSpawnLocationsToTooltip(SpawnConstants.getSpawnLocations(material), builder);
                return builder.toString();
            }
        });
    }

    private static void addTransferTimeToTooltip(final Data data, final StringBuilder builder) {
        builder.append("\n\n")
                .append(LocaleService.getLocalizedStringForCurrentLocale((data.isUpload()) ? "material.tooltip.data.upload" : "material.tooltip.data.download", data.getTransferTime()));
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

    public static <T> ListBinding<T> getListBinding(final Supplier<T[]> supplier) {
        return ObservableResourceFactory.getListBinding(supplier);
    }

    private static String localizeParameter(final Object parameter) {
        if (parameter instanceof LocalizationKey localizationKey) {
            return ObservableResourceFactory.getResources().getString(localizationKey.getKey());
        } else if (parameter instanceof Material material) {
            return ObservableResourceFactory.getResources().getString(material.getLocalizationKey());
        }
        return parameter.toString();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class LocalizationKey {
        private final String key;

        public static LocalizationKey of(final String key) {
            return new LocalizationKey(key);
        }
    }
}
