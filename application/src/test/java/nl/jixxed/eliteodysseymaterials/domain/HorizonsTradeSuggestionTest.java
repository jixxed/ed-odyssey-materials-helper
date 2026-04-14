/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.Raw;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HorizonsTradeSuggestionTest {

    @Test
    void amountRequiredToTradeDowntrade() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.VANADIUM, Raw.CARBON,20,1,2);// 1 to 3
        Assertions.assertThat(horizonsTradeSuggestion.amountRequiredToTrade()).isEqualTo(1.0);
    }
    @Test
    void amountRequiredToTradeUptrade() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.CARBON, Raw.VANADIUM,20,1,2);// 6 to 1
        Assertions.assertThat(horizonsTradeSuggestion.amountRequiredToTrade()).isEqualTo(12.0);
    }
    @Test
    void amountRequiredToTradeCrossDowntrade() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.CHROMIUM, Raw.CARBON,20,1,2);// 2 to 1
        Assertions.assertThat(horizonsTradeSuggestion.amountRequiredToTrade()).isEqualTo(4.0);
    }
    @Test
    void amountRequiredToTradeCrossDowntrade2() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.MOLYBDENUM, Raw.CARBON,20,1,2);// 2 to 3
        Assertions.assertThat(horizonsTradeSuggestion.amountRequiredToTrade()).isEqualTo(2.0);
    }
    @Test
    void amountRequiredToTradeCrossDowntrade3() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.MOLYBDENUM, Raw.CARBON,20,1,1);// 2 to 3
        Assertions.assertThat(horizonsTradeSuggestion.amountRequiredToTrade()).isEqualTo(2.0);
    }
    @Test
    void amountRequiredToTradeCrossUptrade() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.CHROMIUM, Raw.VANADIUM,20,1,2);// 6 to 1
        Assertions.assertThat(horizonsTradeSuggestion.amountRequiredToTrade()).isEqualTo(12.0);
    }

    @Test
    void receivedOnTradeDowntrade() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.VANADIUM, Raw.CARBON,20,1,2);// 1 to 3
        Assertions.assertThat(horizonsTradeSuggestion.receivedOnTrade()).isEqualTo(3);
    }
    @Test
    void receivedOnTradeUptrade() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.CARBON, Raw.VANADIUM,20,1,2);// 6 to 1
        Assertions.assertThat(horizonsTradeSuggestion.receivedOnTrade()).isEqualTo(2);
    }
    @Test
    void receivedOnTradeCrossDowntrade() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.CHROMIUM, Raw.CARBON,20,1,2);// 2 to 1
        Assertions.assertThat(horizonsTradeSuggestion.receivedOnTrade()).isEqualTo(2);
    }
    @Test
    void receivedOnTradeCrossDowntrade2() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.MOLYBDENUM, Raw.CARBON,20,1,2);// 2 to 3
        Assertions.assertThat(horizonsTradeSuggestion.receivedOnTrade()).isEqualTo(3);
    }
    @Test
    void receivedOnTradeCrossUptrade() {
        final HorizonsTradeSuggestion horizonsTradeSuggestion = new HorizonsTradeSuggestion(Raw.CHROMIUM, Raw.VANADIUM,20,1,2);// 6 to 1
        Assertions.assertThat(horizonsTradeSuggestion.receivedOnTrade()).isEqualTo(2);
    }
}