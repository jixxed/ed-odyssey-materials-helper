package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
@RequiredArgsConstructor
@Data
public class BrokerTraderJournalEvent {
    private final LocalDateTime timestamp;
    private final String event;
    private final BigInteger marketId;
    private final String type;
} 