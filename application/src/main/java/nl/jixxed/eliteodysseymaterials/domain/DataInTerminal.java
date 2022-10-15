package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.DataPortType;

import java.util.HashSet;
import java.util.Set;

@Data
public class DataInTerminal {
    private nl.jixxed.eliteodysseymaterials.enums.Data data;
    private DataPortType type;
    private Set<Integer> ids = new HashSet<>();
}
