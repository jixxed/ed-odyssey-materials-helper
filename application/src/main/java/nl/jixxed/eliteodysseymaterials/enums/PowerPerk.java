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

public enum PowerPerk {
    RANK_DECAL,
    REDUCED_REBUY_BY_OPPOSING_POWER,
    REDUCED_REBUY_IN_OWN_TERRITORY,
    REDUCED_WEAPON_MODULE_COST_IN_OWN_TERRITORY,
    REDUCED_REARM_PRICES_IN_OWN_TERRITORY,
    REDUCED_BOUNTY_VALUE_IN_OWN_TERRITORY,
    REDUCED_REFUEL_PRICES_IN_OWN_TERRITORY,
    REDUCED_REPAIR_PRICES_IN_OWN_TERRITORY,
    INCREASE_FOOD_AND_MEDICINE_PROFIT,
    INCREASE_MINOR_FACTION_REPUTATION,
    INCREASE_SALVAGE_PROFIT,
    INCREASE_BOUNTY_PAYOUT,
    INCREASE_SEARCH_AND_RESCUE_PAYOUT,
    INCREASE_MINING_COMMODITY_PAYOUT,
    INCREASE_IMPERIAL_SLAVES_COMMODITY_PAYOUT,
    INCREASE_TRADE_BOND_SALES_AWARD,
    INCREASE_TRADE_BOND_RARE_GOODS_AWARD,
    INCREASE_ORGANICS_DATA_SALES,
    INCREASE_TECHNOLOGY_COMMODITY_PROFIT,
    INCREASE_BLACK_MARKET_PROFITS,
    INCREASE_EXPLORATION_DATA_SALES;

    public String getLocalizationKey() {
        return "power.perk." + this.name().toLowerCase();
    }
    public String getLocalizationTitleKey() {
        return "power.perk.title." + this.name().toLowerCase();
    }
}