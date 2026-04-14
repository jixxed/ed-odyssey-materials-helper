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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Trailblazer;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrailblazerService {

    public static List<Trailblazer> getAllTrailblazers() {

        return List.of(
                new Trailblazer(new StarSystem("Bletii", 99.34375, 6.12500, -166.71875), "Trailblazer Wish", 714D, 0D),
                new Trailblazer(new StarSystem("34 Omicron Cephei", -183.15625, 24.65625, -84.25000), "Trailblazer Star", 635D, 0D),
                new Trailblazer(new StarSystem("Orgen", 165.46875, -4.84375, 79.31250), "Trailblazer Song", 7D, 0D),
                new Trailblazer(new StarSystem("Klikis", 38.28125, 74.68750, 163.71875), "Trailblazer Promise", 951D, 0D),
                new Trailblazer(new StarSystem("Ngobe", -7.84375, -124.09375, -164.09375), "Trailblazer Faith", 418D, 0D),
                new Trailblazer(new StarSystem("HIP 90578", -81.75000, 7.78125, 161.06250), "Trailblazer Dream", 18D, 0D),
                new Trailblazer(new StarSystem("Minerva", -22.06250, -64.93750, -15.12500), "Trailblazer Echo" + LocaleService.getLocalizedStringForCurrentLocale("tab.colonisation.trailblazers.limited"), 1156D, 0D)
        );
    }
}
