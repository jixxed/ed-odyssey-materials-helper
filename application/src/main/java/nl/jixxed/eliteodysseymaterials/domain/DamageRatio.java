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
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Comparator;
import java.util.stream.Stream;

@Getter
public class DamageRatio {
    private Double kineticRatio;
    private Double thermalRatio;
    private Double antiXenoRatio;
    private Double absoluteRatio;
    private Double explosiveRatio;
    private Double causticRatio;

    public DamageRatio(Double kineticRatio, Double thermalRatio, Double antiXenoRatio, Double absoluteRatio, Double explosiveRatio, Double causticRatio) {
        this.kineticRatio = kineticRatio != null ? kineticRatio : 0.0;
        this.thermalRatio = thermalRatio != null ? thermalRatio : 0.0;
        this.antiXenoRatio = antiXenoRatio != null ? antiXenoRatio : 0.0;
        this.absoluteRatio = absoluteRatio != null ? absoluteRatio : 0.0;
        this.explosiveRatio = explosiveRatio != null ? explosiveRatio : 0.0;
        this.causticRatio = causticRatio != null ? causticRatio : 0.0;
    }

    public void transfer(HorizonsModifier from, HorizonsModifier to, Double amount) {
        var fromRatio = getRatio(from);
        var toRatio = getRatio(to);
        //cap increment at what can be decreased
        var increment = amount > fromRatio ? fromRatio : amount;
        //don't allow ratio to go below 0
        setRatio(from, Math.max(0D, fromRatio - amount));
        //safeguard - don't go over 1(100%)
        setRatio(to, Math.min(1D, toRatio + increment));
    }

    public void transferPercentage(HorizonsModifier from, HorizonsModifier to, Double percentage) {
        var fromRatio = getRatio(from);
        var toRatio = getRatio(to);
        //cap increment at what can be decreased
        var amount = fromRatio * percentage;
        //don't allow ratio to go below 0
        setRatio(from, Math.max(0D, fromRatio - amount));
        //safeguard - don't go over 1(100%)
        setRatio(to, Math.min(1D, toRatio + amount));
    }

    private Double getRatio(HorizonsModifier ratio) {
        return switch (ratio) {
            case KINETIC_DAMAGE_RATIO -> kineticRatio;
            case THERMAL_DAMAGE_RATIO -> thermalRatio;
            case ANTI_XENO_DAMAGE_RATIO -> antiXenoRatio;
            case ABSOLUTE_DAMAGE_RATIO -> absoluteRatio;
            case EXPLOSIVE_DAMAGE_RATIO -> explosiveRatio;
            case CAUSTIC_DAMAGE_RATIO -> causticRatio;
            default -> throw new IllegalArgumentException("Unsupported ratio modifier");
        };
    }

    private void setRatio(HorizonsModifier ratio, Double value) {
        switch (ratio) {
            case KINETIC_DAMAGE_RATIO -> kineticRatio = value;
            case THERMAL_DAMAGE_RATIO -> thermalRatio = value;
            case ANTI_XENO_DAMAGE_RATIO -> antiXenoRatio = value;
            case ABSOLUTE_DAMAGE_RATIO -> absoluteRatio = value;
            case EXPLOSIVE_DAMAGE_RATIO -> explosiveRatio = value;
            case CAUSTIC_DAMAGE_RATIO -> causticRatio = value;
            default -> throw new IllegalArgumentException("Unsupported ratio modifier");
        }
    }

    public String getDamageType() {
       return Stream.of(HorizonsModifier.KINETIC_DAMAGE_RATIO, HorizonsModifier.THERMAL_DAMAGE_RATIO, HorizonsModifier.ANTI_XENO_DAMAGE_RATIO,
                        HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, HorizonsModifier.CAUSTIC_DAMAGE_RATIO)
                .max(Comparator.comparing(this::getRatio))
                .map(HorizonsModifier::getJournalName)
               .orElseThrow(() -> new IllegalArgumentException("Invalid damage ratios"));
    }
}
