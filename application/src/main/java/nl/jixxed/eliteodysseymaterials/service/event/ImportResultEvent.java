package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;

@Getter
@AllArgsConstructor
public class ImportResultEvent implements Event {
    private final ImportResult result;
}
