/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.mapping;

import lombok.Data;

import java.math.BigInteger;
import java.util.Optional;

@Data
public class MaterialMapping {
    private String name;
    private BigInteger ownerID;
    private Long count;
    private Optional<String> name_Localised;
    private Optional<BigInteger> missionID;

    public static MaterialMapping map(final nl.jixxed.eliteodysseymaterials.schemas.journal.Backpack.Component material) {
        return map(material.getName(), material.getOwnerID(), material.getCount().longValue(), material.getName_Localised(), material.getMissionID());
    }
    public static MaterialMapping map(final nl.jixxed.eliteodysseymaterials.schemas.journal.Backpack.Item material) {
        return map(material.getName(), material.getOwnerID(), material.getCount().longValue(), material.getName_Localised(), material.getMissionID());
    }
    public static MaterialMapping map(final nl.jixxed.eliteodysseymaterials.schemas.journal.Backpack.Data material) {
        return map(material.getName(), material.getOwnerID(), material.getCount().longValue(), material.getName_Localised(), material.getMissionID());
    }

    private static MaterialMapping map(final String name, final BigInteger ownerID, final Long count, final Optional<String> name_Localised, final Optional<BigInteger> missionID) {
        final MaterialMapping mapping = new MaterialMapping();
        mapping.name = name;
        mapping.ownerID = ownerID;
        mapping.count = count;
        mapping.name_Localised = name_Localised;
        mapping.missionID = missionID;
        return mapping;
    }
}
