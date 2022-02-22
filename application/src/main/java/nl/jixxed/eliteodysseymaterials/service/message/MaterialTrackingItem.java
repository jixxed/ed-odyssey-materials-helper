package nl.jixxed.eliteodysseymaterials.service.message;

import lombok.Builder;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;

@Data
@Builder
public class MaterialTrackingItem {
    private String commander;
    private String timestamp;
    private OdysseyMaterial odysseyMaterial;
    private Integer amount;
    private String system;
    private String primaryEconomy;
    private String secondaryEconomy;
    private String government;
    private String security;
    private String state;
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
