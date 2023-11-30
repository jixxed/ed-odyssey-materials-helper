package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.CodexEntry.CodexEntry;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.util.ArrayList;
import java.util.List;

public class CodexEntryMessageProcessor implements MessageProcessor<CodexEntry> {
    private boolean isInitialised = Boolean.FALSE;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public CodexEntryMessageProcessor() {
        eventListeners.add(EventService.addListener(this, JournalInitEvent.class, journalInitEvent ->
                this.isInitialised = journalInitEvent.isInitialised()
        ));
    }

    @Override
    public void process(final CodexEntry event) {
        if(isInitialised) {
            EDDNService.codexEntry(event);
        }
    }

    @Override
    public Class<CodexEntry> getMessageClass() {
        return CodexEntry.class;
    }
}
