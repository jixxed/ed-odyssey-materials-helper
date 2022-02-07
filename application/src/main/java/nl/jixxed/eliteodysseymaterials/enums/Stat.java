package nl.jixxed.eliteodysseymaterials.enums;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

public interface Stat {

    NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();
    NumberFormat NUMBER_FORMAT_0 = NumberFormat.getNumberInstance();

    default String formatValue(final Object value, final Equipment equipment, final Integer level) {
        return formatValue(value, equipment, level, Collections.emptyList());
    }

    String formatValue(Object value, Equipment equipment, Integer level, List<Modification> modifications);

    String getLocalizationKey();

    StatGroup getStatGroup();
}
