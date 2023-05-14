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