package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.DataPortType;

import java.util.HashMap;
import java.util.Map;

@Data
public class Terminal {
    private DataPortType type;
    Map<Integer, nl.jixxed.eliteodysseymaterials.enums.Data> datas = new HashMap<>();
}
