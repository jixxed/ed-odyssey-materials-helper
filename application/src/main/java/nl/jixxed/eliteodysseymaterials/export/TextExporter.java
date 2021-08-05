package nl.jixxed.eliteodysseymaterials.export;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Comparator;
import java.util.Map;

public class TextExporter {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public static String createTextWishlist(final Map<Material, Integer> wishlistNeededMaterials) {
        final StringBuilder textBuilder = new StringBuilder();
        final Integer maxNameLength = wishlistNeededMaterials.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);

        final String materialColumnWidth = "%-" + (maxNameLength + 5) + "s";
        textBuilder.append(String.format(materialColumnWidth, "Material"));
        textBuilder.append(String.format("%12s", "Available"));
        textBuilder.append(String.format("%12s", "Required"));
        textBuilder.append(String.format("%12s", "Need"));
        textBuilder.append("\n\n");
        wishlistNeededMaterials.entrySet().stream()
                .sorted(Comparator.comparing(item -> item.getKey().getStorageType()))
                .forEach(item -> {
                    textBuilder.append(String.format(materialColumnWidth, LocaleService.getLocalizedStringForCurrentLocale(item.getKey().getLocalizationKey())));
                    final Integer total = switch (item.getKey().getStorageType()) {
                        case GOOD -> APPLICATION_STATE.getGoods().get(item.getKey()).getTotalValue();
                        case DATA -> APPLICATION_STATE.getData().get(item.getKey()).getTotalValue();
                        case ASSET -> APPLICATION_STATE.getAssets().get(item.getKey()).getTotalValue();
                        case OTHER -> 0;
                    };
                    textBuilder.append(String.format("%12s", total));
                    textBuilder.append(String.format("%12s", item.getValue()));
                    textBuilder.append(String.format("%12s", Math.max(0, item.getValue() - total)));
                    textBuilder.append("\n");
                });
        return textBuilder.toString();
    }
}
