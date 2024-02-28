# EDDN BlackMarket Schema

## Introduction

**This schema is deprecated.  The same data has been made available via the
`prohibited` array in the [commodity schema](./commodity-v3.0.json) since 
September 2017.**

What follows below is only for historical curiosity.

---

Here we document how to take data from an ED `MarketSell` Journal Event and
properly structure it for sending to EDDN.

Please consult [EDDN Schemas README](./README-EDDN-schemas.md) for general
documentation for a schema such as this.

## Senders
The primary data source for this schema is the ED Journal event `MarketSell`.

### Key Renames
Some key names in this Schema are different from how they appear in the source
Journal data.  Look for keys where the object contains a `renamed` key - the
value is what the name would have been in the source Journal data.

### Elisions
You MUST remove the following key/value pairs from the data:

  - `TotalSale`
  - `AvgPricePaid`
  - `StolenGoods`
  - `BlackMarket` - Because we're using this schema, so this is un-necessary. 

### Augmentations
#### horizons and odyssey flags
Please read [horizons and odyssey flags](../docs/Developers.md#horizons-and-odyssey-flags)
in the Developers' documentation.

#### systemName
The star system name for where this market is.   Use the `StarSystem` value
from the prior `Docked` or `Location` event.

#### stationName
From the `StationName` value on the prior `Docked` or `Location` event.
