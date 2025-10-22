package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScrollBarV2 {
    private boolean active;
    private double position;
    private int size;
}
