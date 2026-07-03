# EDDN ScanOrganic Schema

## Introduction
Here we document how to take data from an ED `ScanOrganic` Journal Event and
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
The primary data source for this schema is the ED Journal event `ScanOrganic`.

### ScanType
The 'Analyse' scan type only triggers when you have the scan tool out for a
long enough duration following your third scan. It's possible to put away the
tool before this completes and the event will then trigger the next time the
tool is used, which could be in another system entirely. For this reason,
it may report incorrect data and is thus excluded from submission.

### Variant
Variant should be reported if present. It was not included in older journal
versions and therefore is not required to facilitate older journal submissions.

### Use of status.json
You are encouraged to augment your submission with latitude and longitude values
from status.json.

### Augmentations
#### horizons and odyssey flags
Please read [horizons and odyssey flags](../docs/Developers.md#horizons-and-odyssey-flags)
in the Developers' documentation.

#### gameversion and gamebuild
You **MUST** always set these as per [the relevant section](../docs/Developers.md#gameversions-and-gamebuild)
of the Developers' documentation.

#### StarSystem
You MUST add a StarSystem key/value pair representing the name of the system
this event occurred in. Source this from either Location, FSDJump or
CarrierJump as appropriate.

**You MUST apply a location cross-check, as per
[Other data augmentations](../docs/Developers.md#other-data-augmentations).**

#### StarPos
You MUST add a `StarPos` array containing the system co-ordinates from the
last `FSDJump`, `CarrierJump`, or `Location` event.

**You MUST apply a location cross-check, as per
[Other data augmentations](../docs/Developers.md#other-data-augmentations).**

#### BodyID and BodyName
BodyID is already present in the form of the 'Body' key. This should be
renamed to BodyID to mirror many other events.

If proper synchronicity can be achieved, `BodyName` should be reported,
be it from Status.json or from some Journal events. Please cross-check it
as possible.

#### Latitude / Longitude
As live `Status.json` data is not always available, this augmentation is
optional. While latitude and longitude are not reported with the event data,
this event necessitates being on a planet surface. Pulling the current coordinates
from the `Status.json` should be sufficient to populate the data. Please ensure
the data is properly synced with the journal event.