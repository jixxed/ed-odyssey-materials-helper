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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@RequiredArgsConstructor
public enum ArmourType {
    LIGHTWEIGHT_ALLOY(ArmourMainType.LIGHTWEIGHT_ALLOY, ArmourSubType.NORMAL),
    REINFORCED_ALLOY(ArmourMainType.REINFORCED_ALLOY, ArmourSubType.NORMAL),
    MILITARY_GRADE_COMPOSITE(ArmourMainType.MILITARY_GRADE_COMPOSITE, ArmourSubType.NORMAL),
    MIRRORED_SURFACE_COMPOSITE(ArmourMainType.MIRRORED_SURFACE_COMPOSITE, ArmourSubType.NORMAL),
    REACTIVE_SURFACE_COMPOSITE(ArmourMainType.REACTIVE_SURFACE_COMPOSITE, ArmourSubType.NORMAL),
    LIGHTWEIGHT_ALLOY_ABLATIVE(ArmourMainType.LIGHTWEIGHT_ALLOY, ArmourSubType.ABLATIVE),
    REINFORCED_ALLOY_ABLATIVE(ArmourMainType.REINFORCED_ALLOY, ArmourSubType.ABLATIVE),
    MILITARY_GRADE_COMPOSITE_ABLATIVE(ArmourMainType.MILITARY_GRADE_COMPOSITE, ArmourSubType.ABLATIVE),
    MIRRORED_SURFACE_COMPOSITE_ABLATIVE(ArmourMainType.MIRRORED_SURFACE_COMPOSITE, ArmourSubType.ABLATIVE),
    REACTIVE_SURFACE_COMPOSITE_ABLATIVE(ArmourMainType.REACTIVE_SURFACE_COMPOSITE, ArmourSubType.ABLATIVE);

    @Getter
    private final ArmourMainType MainType;
    @Getter
    private final ArmourSubType subType;

    public String getLocalizationKey() {
        return "ships.module.name.armour." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
