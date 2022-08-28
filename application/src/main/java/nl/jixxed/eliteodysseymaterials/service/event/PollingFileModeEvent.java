package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PollingFileModeEvent implements Event {
    private final boolean pollingEnabled;
}
