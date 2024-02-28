# EDDN Outfitting Schema

## Introduction
Here we document how to take data from an ED `Outfitting` Journal
Event and properly structure it for sending to EDDN.

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
The primary data source for this schema is the ED Journal event
`Outfitting`.

You MAY also source this data from the CAPI `/shipyard` endpoint.
Please read
[the guidance on checking for CAPI lag](../docs/Developers.md#detecting-capi-data-lag)
before utilising CAPI data for EDDN messages.

You only need the `name` key's value for each member of the `modules` array.

### Key Renames
Some key names in this Schema are different from how they appear in the source
Journal data.  Look for keys where the object contains a `renamed` key - the
value is what the name would have been in the source Journal data.

### The modules/Items list
The source data, Journal or CAPI, contains more than just the names of the
available items.  This Schema is only concerned with the names, so the list
you build will have only strings as its members, not including other information
such as id, category, cost/BuyPrice, sku or stock.

### Elisions
Remove items whose availability depends on the Cmdr's status rather than on the
station. Namely:

- Items that aren't weapons/utilities (`Hpt_*`), standard/internal
  modules (`Int_*`) or armour (`*_Armour_*`) (i.e. bobbleheads, decals,
  paintjobs and shipkits).  This is enforced by the schema.
- Items that have a non-null `"sku"` property, unless
  it's `"ELITE_HORIZONS_V_PLANETARY_LANDINGS"` (i.e. PowerPlay and tech
  broker items).
- The `"Int_PlanetApproachSuite"` module (for historical reasons).

### Augmentations
#### horizons and odyssey flags
Please read [horizons and odyssey flags](../docs/Developers.md#horizons-and-odyssey-flags)
in the Developers' documentation.

#### gameversion and gamebuild
You **MUST** always set these as per [the relevant section](../docs/Developers.md#gameversions-and-gamebuild)
of the Developers' documentation.

