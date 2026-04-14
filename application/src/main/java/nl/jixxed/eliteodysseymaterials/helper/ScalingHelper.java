/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
