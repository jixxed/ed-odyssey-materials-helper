package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.journalevents.LeaveBody.LeaveBody;

@Getter
@RequiredArgsConstructor
public class LeaveBodyJournalEvent extends JournalEvent {
    private final LeaveBody leaveBody;
}
