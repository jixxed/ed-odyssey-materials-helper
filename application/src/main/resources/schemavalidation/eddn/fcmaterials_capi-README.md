# EDDN FCMaterials Schema

## Introduction
This is the documentation for how to take data from an the ED CAPI `/market`
endpoint and properly structure it for sending to EDDN.

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
The data source for this schema is the `/market` ED CAPI endpoint.  You only
want selected parts of the full data returned for this schema.

You **MUST NOT** construct the message by starting with the entirety of the
CAPI data and then removing everything but what you need.  That risks Frontier
adding more data to the endpoint and your `fcmaterials_capi` messages being
rejected as invalid.  Instead, construct the message content by setting just
the data that is necessary.

Your `message` object **MUST**:
1. Have an `"event":"FCMaterials"` member to aid Listeners who pass this
   through a "usually from the Journal" code path.
2. Set a `"MarketID"` key with the value from `"id"` in the CAPI data.
3. Set a `"CarrierID"` key with the value from the `"name"` in the CAPI data.
4. Set the `"Items"` key's contents directly from the `/market` -> `orders`
   -> `onfootmicroresources` CAPI data.
5. Remove any data where the key is `"locName"` from the `"Items"` data.

You **MUST NOT**:
1. Attempt to set a `"CarrierName"` from any source, the `CarrierID` is
   sufficient.

### Example algorithm

1. Make a CAPI `/market` query.
2. Set `event`, `MarketID` and `CarrierID` as outlined above.
3. Set `Items` value to the data in `orders.onfootmicroresources`.
4. Process the contents of `Items`, removing any data with a key of `locName`.

### Augmentations
#### horizons and odyssey flags
Please read [horizons and odyssey flags](../docs/Developers.md#horizons-and-odyssey-flags)
in the Developers' documentation.

You **SHOULD** set these flags from the Journal `FileHeader` data if you are
reasonably sure you have a live game session against which you are performing
CAPI queries.
You **MUST NOT** set them otherwise, as e.g. the player could be active in
the game on another computer, using a different game mode and the CAPI data
will be for that game mode.

#### gameversion and gamebuild
You **MUST** always set these as per [the relevant section](../docs/Developers.md#gameversions-and-gamebuild)
of the Developers' documentation.

## Listeners
The advice above for [Senders](#senders), combined with the actual Schema file
*should* provide all the information you need to process these events.

Do note that the data source for this is the CAPI, and as such the data is not
the same as for the `fcmaterials_journal` schema:

1. There is no good source of `CarrierName` in CAPI `/market` endpoint data, so 
   that is not included.
2. The `sales` and `purchases` values do **not** contain the same form of data.
3. The `sales` member of `Items` will be `[]` if there are no sales orders, but
   when there are orders:
    1. It will be an object/dictionary.
    2. The keys are the commodity ID.
    3. The value of that key is the rest of the data for that sales order.
4. The `purchases` value:
    1. Is always an array, unlike `sales`.
    2. As a consequence does **not** provide the commodity id at all, only
      the name.
