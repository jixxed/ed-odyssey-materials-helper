package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Operation;

/**
 * Event indicating a backpack change message was received in the journal
 */
@AllArgsConstructor
@Builder
@Getter
public class BackpackChangeEvent implements Event {
    private final String timestamp;
    private final OdysseyMaterial odysseyMaterial;
    private final Integer amount;
    private final Operation operation;
    private final String commander;
    private final String system;
    private final String primaryEconomy;
    private final String secondaryEconomy;
    private final String government;
    private final String security;
    private final String state;
    private final Double x;
    private final Double y;
    private final Double z;
    private final Double latitude;
    private final Double longitude;
    private final String body;
    private final String settlement;
}
