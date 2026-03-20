package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import nl.jixxed.eliteodysseymaterials.schemas.capi.arx.CapiArx;
import nl.jixxed.eliteodysseymaterials.service.LedgerService;

public class CapiArxMessageProcessor implements CapiMessageProcessor<CapiArx> {
    @Override
    public void process(CapiArx capiArx) {
        LedgerService.setArx(capiArx.getBalance().longValue());
    }

    @Override
    public Class<CapiArx> getMessageClass() {
        return CapiArx.class;
    }

    @Override
    public void clear() {
        LedgerService.resetArx();
    }
}
