package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.CodexEntry.CodexEntry;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class CodexEntryMessageProcessor implements MessageProcessor<CodexEntry> {
    @Override
    public void process(final CodexEntry event) {
        EDDNService.codexEntry(event);
    }

    @Override
    public Class<CodexEntry> getMessageClass() {
        return CodexEntry.class;
    }
}
