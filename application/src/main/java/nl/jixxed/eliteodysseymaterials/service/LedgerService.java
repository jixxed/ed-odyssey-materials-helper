package nl.jixxed.eliteodysseymaterials.service;

import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.util.ArrayList;
import java.util.List;

public class LedgerService {

    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> {
            if (!journalInitEvent.isInitialised()) {
                reset();
            }
        }));
    }
    @Getter
    @Setter
    private static long credits = 0L;
    @Getter
    @Setter
    private static long arx = 0L;
    @Getter
    @Setter
    private static long merc = 0L;

    public static void init() {

    }

    public static void reset() {
        credits = 0L;
        arx = 0L;
        merc = 0L;
    }

    public static void resetArx() {
        arx = 0L;
    }

}
