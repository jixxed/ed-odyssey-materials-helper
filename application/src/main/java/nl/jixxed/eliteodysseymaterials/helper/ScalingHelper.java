package nl.jixxed.eliteodysseymaterials.helper;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class ScalingHelper {
    private static final IntegerProperty fontSize = new SimpleIntegerProperty(14);
    private static final EventListener<AfterFontSizeSetEvent> afterFontSizeSetEventEventListener = EventService.addStaticListener(AfterFontSizeSetEvent.class, fontSizeEvent -> fontSize.set(fontSizeEvent.getFontSize()));

    public static DoubleBinding getPixelDoubleBindingFromEm(final Double em) {
        return fontSize.multiply(em);
    }
}
