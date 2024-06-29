package nl.jixxed.eliteodysseymaterials.service.event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HorizonsHgeSearchEvent implements Event {
    private final String search;
}
