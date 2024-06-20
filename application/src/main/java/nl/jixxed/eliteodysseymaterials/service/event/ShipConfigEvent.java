package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShipConfigEvent implements Event {
    private final Type type;
    public enum Type{
        NONE,PIPS,WEIGHT,LIVE
    }
}
