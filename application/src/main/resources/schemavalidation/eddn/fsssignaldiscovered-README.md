# EDDN FSSSignalDiscovered Schema

## Introduction
Here we document how to take data from an ED `FSSSignalDiscovered` Journal 
Event and properly structure it for sending to EDDN.

Please consult [EDDN Schemas README](./README-EDDN-schemas.md) for general
documentation for a schema such as this.

## Senders
The only data source for this schema is the ED Journal event 
`FSSSignalDiscovered`.

### Batching
You MUST coalesce contiguous runs of `FSSSignalDiscovered` events into a
single `signals` array in the message. Minimum size of `signals` is 1 item.

Do not make a request for every single event other than where they occur
singly (such as when a player utilises the FSS to zoom into USS individually,
if there is a different following event).

Suggested algorithm for batching:

1. You will need to track the current location from `Location`, `FSDJump` and
  `CarrierJump` events.  This is in order to add the top-level augmentation
  of `StarSystem` (system name) and `StarPos`.  You will need to record:
    1. `SystemAddress` - for cross-checking.
    2. `StarSystem` - name of the star system.
    3. `StarPos` - the galactic co-ordinates of the system.
2. If the event is `FSSSignalDiscovered`, store it to the temporal list.
3. If the event is any other, then:
    1. check if it is `Location`, `FSDJump` or `CarrierJump` - if so you should
      use this new location in the message for the augmentations.
    2. If it is not one of those events then you should use the tracked
      location from the prior such event for the augmentations.
   
    Now construct the full `fsssignaldiscovered` schema message using the
    tracked location and the stored list of events.  *You **MUST** check that
    the `SystemAddress` for each `FSSSignalDiscovered` event matches the
    tracked location.*  If there is a mis-match then drop that event.
4. Use the `timestamp` of the first signal in the batch as the top-level
   `timestamp` in the `message` object.

Point 3i/ii above are because in the current (3.8.0.406) Horizons client the
`FSSSignalDiscovered` events arrive after `Location`/`FSDJump`/`CarrierJump`,
but in the current (4.0.0.1302) Odyssey client they arrive before such events.

Thus, in Horizons you use the last-tracked location, but in Odyssey you use
the "just arrived" location.

Manually FSS-scanned USS type signals will come in one by one, possibly with
other events between them (such as `Music` due to zooming in/out in FSS).
There is no need to attempt batching these together if separated by other
events, even though you'll be using the `timestamp` of the first on the
message, despite the actual time-line being dependent on how quickly the
player scans them.

This batching is more concerned with not causing an EDDN message per event
upon entry into a system.

### Elisions
Remove the `event` key/pair from each member of the `signals` array.  Including
this would be redundant as by definition we're sending `FSSSignalDiscovered`
events on this schema.

You MUST remove the following key/value pairs from the data:

  - `TimeRemaining` key/value pair (will be present on USS).  This has a slight
    PII nature and is also very ephemeral.

You MUST drop the whole `FSSSignalDiscovered` event if `USSType` key
has `$USS_Type_MissionTarget;` value.  Only the Cmdr with the mission has any
use of these.  There's not even a statistical use.

Because of the location cross-check the `SystemAddress` is in the top-level
`message` object, and thus you **MUST** remove such from each signal in the
array.

Do **NOT** remove the `timestamp` from each signal in the array.  Whilst these
should be identical for a "just logged in or arrived in system" set of signals,
this is not true of manually FSS scanned USS signals.

### Augmentations
#### horizons flag
You SHOULD add this key/value pair, using the value from the `LoadGame` event.

#### odyssey flag
You SHOULD add this key/value pair, using the value from the `LoadGame` event.

#### gameversion and gamebuild
You **MUST** always set these as per [the relevant section](../docs/Developers.md#gameversions-and-gamebuild)
of the Developers' documentation.

#### StarSystem
You **MUST** add a `StarSystem` string containing the system name from the last
tracked location.  You **MUST** cross-check each `FSSSignalDiscovered`
->`SystemAddress` value to ensure it matches.  If it does not, you **MUST**
drop the event.

#### StarPos
You **MUST** add a `StarPos` array containing the system co-ordinates from the
tracked location.  You **MUST** cross-check each `FSSSignalDiscovered`
->`SystemAddress` value to ensure it matches.  If it does not, you **MUST**
drop the event.

## Receivers
### Augmentations are 'SHOULD', not 'MUST'
Receivers should remember that  `horizons` and  `odyssey` augmentations
are optional key/value pairs.  You **SHOULD NOT** rely on them being present
in any given event.

### Duplicate messages from 'busy' systems
When a system is particularly full of signals, such as when many Fleet Carriers
are present, it has been observed that the game repeats the identical
sequence of `FSSSignalDiscovered` events.  So you might receive what looks like
a duplicate message, other than the timestamp (if the timestamp is the same
then the EDDN Relay should drop the duplicate).

## Examples
This is a few example of messages that passes current `FSSSignalDiscovered` schema.
1. A message without `horizons` or `odyssey` augmentations.
```json
{
   "$schemaRef":"https://eddn.edcd.io/schemas/fsssignaldiscovered/1",
   "header":{
      "gatewayTimestamp":"2021-11-06T22:48:43.483147Z",
      "softwareName":"a software",
      "softwareVersion":"a version",
      "uploaderID":"an uploader"
   },
   "message":{
      "timestamp":"2021-11-06T22:48:42Z",
      "event":"FSSSignalDiscovered",
      "SystemAddress":1774711381,
      "signals":[
         {
            "timestamp":"2021-11-06T22:48:42Z",
            "SignalName":"EXPLORER-CLASS X2X-74M",
            "IsStation":true
         }
      ],
      "StarSystem":"HR 1185",
      "StarPos": [
          -64.66, -148.94, -330.41
      ]
   }
}
```

2. A message with `horizons`, `odyssey`, `systemName`, `StarPos` fields which says it sent from Odyssey.
```json
{
   "$schemaRef":"https://eddn.edcd.io/schemas/fsssignaldiscovered/1",
   "header":{
      "gatewayTimestamp":"2021-11-06T22:48:43.483147Z",
      "softwareName":"a software",
      "softwareVersion":"a version",
      "uploaderID":"an uploader"
   },
   "message":{
      "timestamp":"2021-11-06T22:48:42Z",
      "event":"FSSSignalDiscovered",
      "SystemAddress":1350507186531,
      "signals":[
         {
            "timestamp":"2021-11-06T22:48:42Z",
            "event":"FSSSignalDiscovered",
            "SignalName":"EXPLORER-CLASS X2X-74M",
            "IsStation":true
         },
         {
            "timestamp":"2021-11-06T22:48:42Z",
            "event":"FSSSignalDiscovered", 
            "SignalName":"$USS_NonHumanSignalSource;", 
            "USSType":"$USS_Type_NonHuman;",
            "SpawningState":"$FactionState_None;", 
            "SpawningFaction":"$faction_none;",
            "ThreatLevel":5
            }
      ],
      "StarPos": [
          8.1875,
          124.21875,
          -38.5
      ],
      "StarSystem": "HIP 56186",
      "horizons": true,
      "odyssey": true
   }
}
```
