package nl.jixxed.eliteodysseymaterials.domain;

import java.util.List;

public class LevelValue {
    private final List<Object> levelValues;

    public LevelValue(final Object levelOneValue, final Object levelTwoValue, final Object levelThreeValue, final Object levelFourValue, final Object levelFiveValue) {
        this.levelValues = List.of(levelOneValue, levelTwoValue, levelThreeValue, levelFourValue, levelFiveValue);
    }

    public Object getValueForLevel(final Integer level) {
        return this.levelValues.get(level - 1);
    }
}
