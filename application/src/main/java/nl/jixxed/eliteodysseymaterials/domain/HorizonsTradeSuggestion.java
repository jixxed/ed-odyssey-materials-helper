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
