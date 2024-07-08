package nl.jixxed.eliteodysseymaterials.service.hge;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
public class ExpiringMessage extends MessageV2{
    private LocalDateTime expiration;
}