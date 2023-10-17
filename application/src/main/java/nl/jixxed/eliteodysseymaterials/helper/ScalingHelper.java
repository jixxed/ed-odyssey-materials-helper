package nl.jixxed.eliteodysseymaterials.helper;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1068")
public class ScalingHelper {
    private static IntegerProperty fontSize;
    private static EventListener<AfterFontSizeSetEvent> afterFontSizeSetEventEventListener;

    public static void init() {
        fontSize = new SimpleIntegerProperty(FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE,"NORMAL")).getSize());
        afterFontSizeSetEventEventListener = EventService.addStaticListener(AfterFontSizeSetEvent.class, fontSizeEvent -> fontSize.set(fontSizeEvent.getFontSize()));
    }
    public static DoubleBinding getPixelDoubleBindingFromEm(final Double em) {
        return fontSize.multiply(em);
    }
    public static Double getPixelDoubleFromEm(final Double em) {
        return fontSize.multiply(em).doubleValue();
    }
    public static Double scalePixel(final Double pixel) {
        return fontSize.doubleValue() / 14D * pixel;
    }
}
