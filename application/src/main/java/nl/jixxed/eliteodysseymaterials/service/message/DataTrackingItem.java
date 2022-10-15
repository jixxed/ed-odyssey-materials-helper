package nl.jixxed.eliteodysseymaterials.service.message;

import lombok.Builder;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.DataPortType;

@Data
@Builder
public class DataTrackingItem {
    private String commander;
    private String timestamp;
    private nl.jixxed.eliteodysseymaterials.enums.Data data;
    private String dataPortName;
    private DataPortType type;
    private Integer amount;
    private String session;
    private String version;
}
