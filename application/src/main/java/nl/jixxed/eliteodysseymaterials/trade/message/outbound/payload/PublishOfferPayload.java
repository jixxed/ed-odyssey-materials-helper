package nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Offer;


@Data
@SuperBuilder
public class PublishOfferPayload extends Offer implements Payload {

}
