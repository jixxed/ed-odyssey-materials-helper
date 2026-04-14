/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service;

import com.sun.jna.NativeLibrary;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.service.ar.OcrConstants;
import nl.jixxed.eliteodysseymaterials.service.event.ARLocaleChangeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;
import nl.jixxed.tess4j.ITesseract;
import nl.jixxed.tess4j.Tesseract1;
import nl.jixxed.tess4j.TesseractException;
import nl.jixxed.tess4j.util.LoadLibs;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class OCRService {
    private static final ITesseract instance;  // JNA Interface Mapping
    private static final String TESS_DATA_PATH = "tess.data.path";
    private static final String LEPT_DATA_PATH = "lept.data.path";
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {

        System.setProperty(TESS_DATA_PATH, OcrConstants.TESS4J_DIR);
        System.setProperty(LEPT_DATA_PATH, OcrConstants.TESS4J_DIR);
        instance = new Tesseract1();
        instance.setDatapath(Path.of(OcrConstants.TESS4J_DIR, "tessdata").toString());
        setLocale(ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.AR_LOCALE, "ENGLISH")));
        EVENT_LISTENERS.add(EventService.addStaticListener(ARLocaleChangeEvent.class, arLocaleChangeEvent -> {
                    setLocale(arLocaleChangeEvent.getLocale());
                }
        ));
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
                    try {
                        NativeLibrary.getInstance(LoadLibs.getTesseractLibName()).close();
                        NativeLibrary.getInstance(LoadLibs.getLeptonicaLibName()).close();
                    } catch (final Exception e) {
                        log.error("Failed to free resources", e);
                    }
                }
        ));
    }

    public static void setLocale(ApplicationLocale appLocale) {
        instance.setLanguage(appLocale.getIso6392B());
        setCharWhitelist(LocaleService.getDataCharacterForARLocale(appLocale.getLocale()));
    }

    static void setCharWhitelist(final String characters) {
        instance.setVariable("tessedit_char_whitelist", characters);
    }

    static synchronized String imageToString(final BufferedImage image) throws TesseractException {
        try{
            return instance.doOCR(image);
        }catch (Error error){
            log.error("Error in imageToString", error);
        }
        return "";
    }
}
