/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class HorizonsTechBrokerBlueprint extends HorizonsBlueprint {
    @Getter
    private final List<HorizonsBrokerType> horizonsBrokerTypes;

    public HorizonsTechBrokerBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final List<HorizonsBrokerType> horizonsBrokerTypes) {
        super(horizonsBlueprintName, horizonsBlueprintType, materials, modifiers, engineers);
        this.horizonsBrokerTypes = horizonsBrokerTypes;
    }

    public HorizonsTechBrokerBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final List<HorizonsBrokerType> horizonsBrokerTypes, final GameVersion gameVersion) {
        super(horizonsBlueprintName, horizonsBlueprintType, HorizonsBlueprintGrade.NONE, materials, modifiers, engineers, gameVersion);
        this.horizonsBrokerTypes = horizonsBrokerTypes;
    }
}
