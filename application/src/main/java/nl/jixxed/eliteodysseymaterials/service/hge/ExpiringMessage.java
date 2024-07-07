package nl.jixxed.eliteodysseymaterials.service.hge;


import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExpiringMessage extends MessageV2{
    private LocalDateTime expiration;
}