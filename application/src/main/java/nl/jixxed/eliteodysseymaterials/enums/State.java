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

import java.util.Arrays;

public enum State {
    UNKNOWN,
    NONE,
    BOOM,
    BUST,
    CIVILUNREST,
    CIVILWAR,
    ELECTION,
    EXPANSION,
    FAMINE,
    INVESTMENT,
    LOCKDOWN,
    OUTBREAK,
    RETREAT,
    WAR,
    CIVILLIBERTY,
    PIRATEATTACK,
    BLIGHT,
    DROUGHT,
    INFRASTRUCTUREFAILURE,
    NATURALDISASTER,
    PUBLICHOLIDAY,
    TERRORISM,
    COLDWAR,
    COLONISATION,
    HISTORICEVENT,
    REVOLUTION,
    TECHNOLOGICALLEAP,
    TRADEWAR;


    public static State forName(final String name) {
        return Arrays.stream(State.values()).filter(factionState -> factionState.name().equalsIgnoreCase(name)).findFirst().orElse(State.UNKNOWN);
    }


    public String getLocalizationKey() {
        return "state." + this.name().toLowerCase();
    }
}
