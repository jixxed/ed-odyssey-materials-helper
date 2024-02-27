# EDDN Commodity Schema

## Introduction
Here we document how to take data from an ED `Market` Journal Event and
properly structure it for sending to EDDN.

Please consult [EDDN Schemas README](./README-EDDN-schemas.md) for general
documentation for a schema such as this.

If you find any discrepancies between what this document says and what is
defined in the relevant Schema file, then you should, in the first instance,
assume that it is the Schema file that is correct.
**PLEASE open
[an issue on GitHub](https://github.com/EDCD/EDDN/issues/new/choose)
to report any such anomalies you find so that we can check and resolve the
discrepancy.**

## Senders
The primary data source for this schema is the ED Journal event `Market`, 
and the additional file, `Market.json`, that it signals the writing of.

So, look for the `Market` event, and when it occurs open and read the 
`Market.json` file which contains the actual data.  Treat *that* data as 
the event.

It *is* also possible to construct this data from a CAPI `/market` query.  
See [Using CAPI data](#using-capi-data) below.

### Statusflags
`statusFlags` is an optional augmentation that can be used to identify
`Producer`, `Consumer`, and `Rare` goods from the output of `Market.json`.  
So, as per the schema, do include it if available.

### Key Renames
Some key names in this Schema are different from how they appear in source
Journal data.  Look for keys where the object contains a `renamed` key - the
value is what the name would have been in the source Journal data.  The names
used are as found in the CAPI source data.

### Elisions
You MUST remove the following key/value pairs from the data:

- `StationType` key/value.
- `Producer` key/value pair in Items.
- `Rare` key/value pair in Items.
- `id` key/value pair in Items.

In the list of commodites:

- Skip commodities with `"categoryname": "NonMarketable"` (i.e.
  Limpets - not purchasable in station market) or a *non-empty*`"legality":` 
  string (not normally traded at this station market).

If the data is sourced from the journal folder:
- Remove the `$` prefix and `_name;` suffix from the `Name` field.
- As the Journal Market.json doesn't contain `economies` or `prohibited` data, 
  leave these entirely out of the message.  You **MUST NOT** send empty lists.

#### Item Category
Remove not only the `Category_Localised` key:values, but also the
`Category` key:value pair from each Item.

### Augmentations
#### horizons and odyssey flags
Please read [horizons and odyssey flags](../docs/Developers.md#horizons-and-odyssey-flags)
in the Developers' documentation.

#### gameversion and gamebuild
You **MUST** always set these as per [the relevant section](../docs/Developers.md#gameversions-and-gamebuild)
of the Developers' documentation.

### Using CAPI data
It is *not* recommended to use CAPI data as the source as it's fraught with 
additional issues.  EDMarketConnector does so in order to facilitate 
obtaining data without the player needing to open the commodities screen.

Please read
[the guidance on checking for CAPI lag](../docs/Developers.md#detecting-capi-data-lag)
before utilising CAPI data for EDDN messages.

Note that CAPI `/market` data will sometimes have the `statusFlasg` per 
item, which are defined as optional in this schema (because they're not in 
the Market.json data).  You SHOULD include this data in your message if 
using CAPI as the source.

Now you will need to construct the necessary additional fields:

#### CAPI horizons flag
If your application can be certain that the game client is still running, 
and logged into the game (not just run to the main menu), then you can 
simply use the value from the `LoadGame` journal event.

Otherwise, you MUST check if any of the economies from the `/market` 
data have a `name` of `Colony`, if so, set this flag true.

Additionally, you should retrieve the CAPI `/shipyard` endpoint and check if 
any of the listed ships or modules have a `sku` value of 
`ELITE_HORIZONS_V_PLANETARY_LANDINGS`.  If so, set this flag true.

#### CAPI odyssey flag
Unfortunately there is no method to be *certain* of this from CAPI data, so 
you will have to trust in the system/station name check and use the value 
from the Journal `LoadGame` event.
