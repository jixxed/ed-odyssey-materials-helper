package nl.jixxed.eliteodysseymaterials.service.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class MaterialTrackingMessage {
    private final List<MaterialTrackingItem> items;
}
