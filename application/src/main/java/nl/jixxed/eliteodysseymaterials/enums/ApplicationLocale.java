/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Locale;

@AllArgsConstructor
@Getter
public enum ApplicationLocale {
    ENGLISH(Locale.ENGLISH, "eng", true),
    GERMAN(Locale.GERMAN, "deu", true),
    FRENCH(Locale.FRENCH, "fra", true),
    PORTUGUESE(Locale.forLanguageTag("pt"), "por", true),
    SPANISH(Locale.forLanguageTag("es"), "spa", true),
    RUSSIAN(Locale.forLanguageTag("ru"), "rus", true),
    CHINESE(Locale.CHINESE, "chi", false),
    GEORGIAN(Locale.forLanguageTag("ka"), "kat", false);

    private final Locale locale;
    private final String iso6392B;
    private final boolean ar;

    public String getLocalizationKey() {
        return "application.language." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
