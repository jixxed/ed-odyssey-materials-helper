/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.cg;

import java.util.Map;

/**
 * Holds per-band optimistic and pessimistic growth deltas.
 * Each band uses its own growth rate from the lookback window.
 */
public class PerBandDeltas {

    private final Map<String, Long> optimisticDeltas;
    private final Map<String, Long> pessimisticDeltas;

    public PerBandDeltas(Map<String, Long> optimisticDeltas, Map<String, Long> pessimisticDeltas) {
        this.optimisticDeltas = optimisticDeltas;
        this.pessimisticDeltas = pessimisticDeltas;
    }

    public Map<String, Long> optimisticDeltas() {
        return optimisticDeltas;
    }

    public Map<String, Long> pessimisticDeltas() {
        return pessimisticDeltas;
    }

    /**
     * Gets the optimistic delta for a specific band.
     */
    public long optimisticFor(String band) {
        return optimisticDeltas.getOrDefault(band, 0L);
    }

    /**
     * Gets the pessimistic delta for a specific band.
     */
    public long pessimisticFor(String band) {
        return pessimisticDeltas.getOrDefault(band, 0L);
    }

}
