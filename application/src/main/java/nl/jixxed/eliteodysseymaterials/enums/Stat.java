package nl.jixxed.eliteodysseymaterials.enums;

import java.util.Collections;
import java.util.List;

public interface Stat {

    default String formatValue(final Object value, final Equipment equipment, final Integer level) {
        return formatValue(value, equipment, level, Collections.emptyList());
    }

    String formatValue(Object value, Equipment equipment, Integer level, List<Modification> modifications);

    String getLocalizationKey();
}
