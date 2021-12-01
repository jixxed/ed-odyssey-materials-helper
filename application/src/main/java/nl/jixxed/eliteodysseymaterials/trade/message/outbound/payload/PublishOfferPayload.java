package nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Offer;


@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PublishOfferPayload extends Offer implements Payload {

}
