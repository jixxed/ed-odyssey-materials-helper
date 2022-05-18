package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CapiOAuthCallbackEvent implements Event {
    private String code;
    private String state;
}
