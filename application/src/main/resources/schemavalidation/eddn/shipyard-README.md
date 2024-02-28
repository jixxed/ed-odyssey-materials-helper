# EDDN Shipyard Schema

## Introduction
Here we document how to take data from an ED `Shipyard` Journal
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
`Shipyard`.

You MAY also source this data from the CAPI `/shipyard` endpoint.
Please read
[the guidance on checking for CAPI lag](../docs/Developers.md#detecting-capi-data-lag)
before utilising CAPI data for EDDN messages.

The `ships` array is built from *only* the `name` values of either the Journal
`PriceList` array in the `Shipyard.json` file, or from the `ships` array of
CAPI `/shipyard` data.

When using CAPI data *include* ships listed in the `"unavailable_list"` 
property (i.e. available at this station, but not to this Cmdr).

### Key Renames
Some key names in this Schema are different from how they appear in the source
Journal data.  Look for keys where the object contains a `renamed` key - the
value is what the name would have been in the source Journal data.

### Augmentations
#### horizons and odyssey flags
Please read [horizons and odyssey flags](../docs/Developers.md#horizons-and-odyssey-flags)
in the Developers' documentation.

#### gameversion and gamebuild
You **MUST** always set these as per [the relevant section](../docs/Developers.md#gameversions-and-gamebuild)
of the Developers' documentation.

