package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.ConstructionProgress;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ColonisationConstructionDepotEvent implements Event {
    private final BigInteger marketID;
    private final BigDecimal constructionProgress;
    private final Boolean constructionComplete;
    private final Boolean constructionFailed;
    private final Map<Commodity, ConstructionProgress> resourcesRequired;
}
