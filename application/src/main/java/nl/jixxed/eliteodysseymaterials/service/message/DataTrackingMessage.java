package nl.jixxed.eliteodysseymaterials.service.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class DataTrackingMessage {
    private final List<DataTrackingItem> items;
}
