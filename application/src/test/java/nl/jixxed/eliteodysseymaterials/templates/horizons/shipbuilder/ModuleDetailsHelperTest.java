/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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