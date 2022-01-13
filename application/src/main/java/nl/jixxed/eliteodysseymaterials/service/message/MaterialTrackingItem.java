package nl.jixxed.eliteodysseymaterials.service.message;

import lombok.Builder;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.Material;

@Data
@Builder
public class MaterialTrackingItem {
    private String commander;
    private String timestamp;
    private Material material;
    private Integer amount;
    private String system;
    private Double x;
    private Double y;
    private Double z;
    private Double latitude;
    private Double longitude;
    private String body;
    private String settlement;
    private String session;
    private String version;
}
