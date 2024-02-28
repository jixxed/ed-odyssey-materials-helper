# EDDN Schemas Documentation

EDDN message Schemas are [JSON](https://www.json.org/json-en.html) files
conforming to 'draft 04' of the [JSON Schema](https://json-schema.org/)
specification.

## Canonical location of Schema files

For the EDDN Live service you should always be checking
[the live version of the schemas, and their READMEs](https://github.com/EDCD/EDDN/tree/live/schemas).
Any other version of the Schemas is not guaranteed to be synchronized with
those actually running on the Live service.

## Documentation of Schema files

The Schema files themselves are considered to be the canonical definition of
the required, and allowed, contents of the relevant EDDN message.  There
**MUST** be an accompanying README file, e.g. for `commodity-v3.0.json` there
is also a `commodity-README.md` file in the project root `schemas/` directory.

For more general documentation that all developers wanting to either Upload
messages or Listen to the stream of messages from the Relay, please consult
[the Developer documentation](../docs/Developers.md).

### Mandatory Schema file contents

It is best to base any new Schema file on
[the provided template](./TEMPLATES/journalevent-v1.0.json).  As per its
contents all Schemas specify a top-level JSON Object with the data:

1. `$schemaRef` - Which Schema (including version) this message is for.
2. `$id` - The canonical URL for this schema once it is in live service.
   1. Remember to have the version as in `journal/1` not `journal-v1.0`.
   2. Do **NOT** end this with a `#` empty fragment.  This is
  [documented](https://json-schema.org/draft/2020-12/json-schema-core.html#section-8.2.1)
  as unnecessary.
   3. Where there are two separate schemas for the same kind of data, but one
     is for Journal-sourced, and the other for CAPI-sourced, you should have
     the "filename" of the schema end with `_<source>`, e.g.
     `fcmaterials_journal/1` and `fcmaterials_capi/1`.
3. `header` - Object containing mandatory information about the upload;
    1. `uploaderID` - a unique ID for the player uploading this data.  
       Don't worry about privacy, the EDDN service will hash this with a key
       that is regularly changed so no-one knows who an uploader is in-game.
    2. `softwareName` - an identifier for the software performing the upload.
    3. `softwareVersion` - The version of that software being used.

   Listeners MAY make decisions about whether to utilise the data in any
   message based on the combination of `softwareName` and `softwareVersion`.

   **DO not** add `gatewaytimestamp` yourself. The EDDN Gateway will add
   this and will overwrite any that you provide, so don't bother.
4. `message` - Object containing the data for this message. Consult the
   relevant README file within this documentation, e.g.
   [codexentry-README.md](./codexentry-README.md).

Whilst currently (2022-11-23) the `gameversion` and `gamebuild` fields in the
message `header` aren't yet mandatory in the sense of what's in the schema
definitions, all Senders are **strongly encouraged** to send them.

Indeed, the per-schema documentation, for every schema now states:

#### gameversion and gamebuild
You **MUST** always set these as per [the relevant section](../docs/Developers.md#gameversions-and-gamebuild)
of the Developers' documentation.

### General EDDN message outline

Each `message` object must have, at bare minimum:

1. `timestamp` - string date and time in ISO8601 format. 
    1. Whilst this technically allows for any timezone to be cited you SHOULD
      provide this in UTC, aka 'Zulu Time' as in the example above.
      You MUST ensure that you are doing this properly. 
      Do not claim 'Z' whilst actually using a local time that is offset from
      UTC.
    2. Historically we had never been explicit about if Senders should include
      sub-second resolution in the timestamps, or if Listeners should be
      prepared to accept such.  As of 2022-06-24 we are explicitly stating that
      Senders **MAY** include sub-second resolution, and Listeners **MUST**
      be prepared to accept such.

   If you are only utilising Journal-sourced data then simply using the
   value from there should be sufficient as the PC game client is meant to
   always be correctly citing UTC for this.  Indeed it has been observed,
   in the Odyssey 4.0.0.1002 client, that with the Windows clock behind UTC
   by 21 seconds both the in-game UI clock *and* the Journal event
   timestamps are still properly UTC to the nearest second.

   Listeners MAY make decisions on accepting data based on this time stamp,
   i.e. "too old".
2. Where the data is sourced from a Journal event please do preserve the
   `event` key and value.  Yes, where we use an event-specific Schema this
   might seem redundant, but it might aid an EDDN listener in streamlining
   their code, and it does no harm.

   Any new Schema based on Journal data **MUST** make `event` a required
   property of the `message` dictionary.
3. At least one other key/value pair representing the data. In general there
   will be much more than this. Consult the
   [Schemas and their documentation](./).

4. Please consult the advice pertaining to
   [horizons and odyssey flags](../docs/Developers.md#horizons-and-odyssey-flags) and include them
   whenever possible.

Where a key has to be renamed this will be specified in the Schema through a
`renamed` property on the object in question.
