package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModuleDetailsHelper {

    public static boolean isFirstRow(int index) {
        return index <= 1;
    }

    public static boolean isLastRow(int index, int size) {
        return (index / 2) == ((size + 1) / 2 - 1);
    }

    public static boolean isLeftColumn(int index) {
        return index % 2 == 0;
    }

    public static boolean isRightColumn(int index) {
        return index % 2 == 1;
    }
}
