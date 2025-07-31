package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Data;

@Data
public class GuardianStructureJson {
    private String name;
    private GuardianStructureCoordsJson coords;
    private String body;
    private String layout;
}
