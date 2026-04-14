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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BlueprintCategory {

    ENGINEER_UNLOCKS(1), WEAPON_MODULES(5), WEAPON_GRADES(4), SUIT_MODULES(3), SUIT_GRADES(2),//odyssey
    HARDPOINT(2), UTILITY_MOUNT(3), CORE_INTERNAL(4), OPTIONAL_INTERNAL(5), OPTIONAL_MILITARY(6), EXPERIMENTAL_EFFECTS(7), SYNTHESIS(8), TECHBROKER(9), CARGO_HATCH(10);//horizons
    private final int order;

    public String getLocalizationKey() {
        return "blueprint.category.name." + this.name().toLowerCase();
    }
}
