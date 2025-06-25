package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModuleDetailsHelperTest {


    @ParameterizedTest(name = "isFirstRow - {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void isFirstRow(int index) {
        assertEquals(index == 0 || index == 1, ModuleDetailsHelper.isFirstRow(index));
    }

    @ParameterizedTest(name = "isLastRow - {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    protected void isLastRow(int index) {
        assertEquals(index == 4 || index == 5, ModuleDetailsHelper.isLastRow(index, 6));
    }

    @ParameterizedTest(name = "isLeftColumn - {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    protected void isLeftColumn(int index) {
        assertEquals(index == 0 || index == 2 || index == 4, ModuleDetailsHelper.isLeftColumn(index));
    }

    @ParameterizedTest(name = "isRightColumn - {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    protected void isRightColumn(int index) {
        assertEquals(index == 1 || index == 3 || index == 5, ModuleDetailsHelper.isRightColumn(index));
    }


    @ParameterizedTest(name = "isFirstRow Odd - {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    public void isFirstRowOdd(int index) {
        assertEquals(index == 0 || index == 1, ModuleDetailsHelper.isFirstRow(index));
    }

    @ParameterizedTest(name = "isLastRow Odd - {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    protected void isLastRowOdd(int index) {
        assertEquals(index == 4, ModuleDetailsHelper.isLastRow(index, 5));
    }

    @ParameterizedTest(name = "isLeftColumn Odd - {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    protected void isLeftColumnOdd(int index) {
        assertEquals(index == 0 || index == 2 || index == 4, ModuleDetailsHelper.isLeftColumn(index));
    }

    @ParameterizedTest(name = "isRightColumn Odd - {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    protected void isRightColumnOdd(int index) {
        assertEquals(index == 1 || index == 3, ModuleDetailsHelper.isRightColumn(index));
    }
}