# EDDN FCMaterials Schema

## Introduction
This is the documentation for how to take data from an ED `FCMaterials.json`
file and properly structure it for sending to EDDN.

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
The data source for this schema is the file `FCMaterials.json`.  That it has
been freshly written is signalled by the ED Journal event `FCMaterials`.
**NB: This schema is not, currently, for sending CAPI `/market`-sourced data
about these materials.**

So, monitor the Journal as normal, and when you see a `FCMaterials` event open
the `FCMaterials.json` file for reading, read it, and close it again.  Use the
data you got from reading this file, not merely the Journal event.

Your `message` should primarily be the contents of this file, with the addition
of any augmentations, as noted below.

### Augmentations
#### horizons and odyssey flags
Please read [horizons and odyssey flags](../docs/Developers.md#horizons-and-odyssey-flags)
in the Developers' documentation.

#### gameversion and gamebuild
You **MUST** always set these as per [the relevant section](../docs/Developers.md#gameversions-and-gamebuild)
of the Developers' documentation.

## Listeners
The advice above for [Senders](#senders), combined with the actual Schema file
*should* provide all the information you need to process these events.
