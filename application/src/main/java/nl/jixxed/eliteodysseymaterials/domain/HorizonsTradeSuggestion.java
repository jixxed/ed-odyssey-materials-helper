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
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;

@Getter
public class HorizonsTradeSuggestion {
    private final HorizonsMaterial horizonsMaterialFrom;
    private final HorizonsMaterial horizonsMaterialTo;
    private final int owned;
    private final int reserved;
    private final int need;
    private TradeFactor tradefactor;


    public HorizonsTradeSuggestion(final HorizonsMaterial horizonsMaterialFrom, final HorizonsMaterial horizonsMaterialTo, final int owned, final int reserved, final int need) {
        this.horizonsMaterialFrom = horizonsMaterialFrom;
        this.horizonsMaterialTo = horizonsMaterialTo;
        this.owned = owned;
        this.reserved = reserved;
        this.need = need;
        calculateTradeFactor();
    }

    public double getPercentageUsedOnTrade() {
        return amountRequiredToTrade() / (this.owned - this.reserved);
    }

    public boolean canCompleteTrade() {
        return amountRequiredToTrade() <= this.owned - this.reserved;
    }

    public double amountRequiredToTrade() {
        return Math.round(Math.ceil(this.need * ((double) this.tradefactor.getOffer() / this.tradefactor.getReceive())) / this.tradefactor.getOffer()) * this.tradefactor.getOffer();
    }

    public int receivedOnTrade() {
        return (int) (amountRequiredToTrade() / this.tradefactor.getOffer() * this.tradefactor.getReceive());
    }

    private void calculateTradeFactor() {
        if (this.horizonsMaterialFrom.getMaterialType() == this.horizonsMaterialTo.getMaterialType()) {//up-down trade
            final int diff = this.horizonsMaterialFrom.getRarity().getLevel() - this.horizonsMaterialTo.getRarity().getLevel();
            if (diff == 0) {
                this.tradefactor = new TradeFactor(1, 1);
            } else if (diff > 0) {
                this.tradefactor = new TradeFactor(1, (int) Math.pow(3, diff));
            } else /*if (diff < 0)*/ {
                this.tradefactor = new TradeFactor((int) Math.pow(6, Math.abs(diff)), 1);
            }
        } else if (this.horizonsMaterialFrom.getClass() == this.horizonsMaterialTo.getClass()) {//crosstrade
            final int diff = this.horizonsMaterialFrom.getRarity().getLevel() - this.horizonsMaterialTo.getRarity().getLevel();
            if (diff == 0) {
                this.tradefactor = new TradeFactor(6, 1);
            } else if (diff > 0) {
                this.tradefactor = new TradeFactor(2, (int) Math.pow(3, diff - 1.0));
            } else /*if (diff < 0)*/ {
                this.tradefactor = new TradeFactor((int) Math.pow(6, Math.abs(diff) + 1.0), 1);
            }
        }
    }
}
