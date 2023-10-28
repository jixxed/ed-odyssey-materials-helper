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
