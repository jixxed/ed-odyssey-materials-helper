/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

public enum Mounting {
    FIXED,
    GIMBALLED,
    TURRETED,
    NA;

    public String getShortName(){
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
    public String getLocalizationKey() {
        return "ships.mounting." + this.name().toLowerCase();
    }
    public boolean isHigher(Mounting mounting) {
        return this.ordinal() > mounting.ordinal();
    }
    public boolean isLower(Mounting mounting) {
        return this.ordinal() < mounting.ordinal();
    }

    public EdAwesomeIcon getIcon() {
        return switch (this){
            case FIXED, NA -> EdAwesomeIcon.SHIPS_FIXED;
            case GIMBALLED -> EdAwesomeIcon.SHIPS_GIMBALLED;
            case TURRETED -> EdAwesomeIcon.SHIPS_TURRETED;
        };
    }
}
