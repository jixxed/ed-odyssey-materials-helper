package nl.jixxed.eliteodysseymaterials.service.hge;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PUBLIC)
public class ExpiringMessage {
    private Message message;
    private boolean owned;
    private LocalDateTime expiration;
}