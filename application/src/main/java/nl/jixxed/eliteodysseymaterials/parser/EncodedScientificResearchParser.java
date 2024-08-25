package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Encoded;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ScientificResearch.ScientificResearch;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.math.BigInteger;

@Slf4j
public class EncodedScientificResearchParser implements HorizonsParser<ScientificResearch> {

    @Override
    public void parse(final ScientificResearch event) {
        final BigInteger count = event.getCount();
        final String materialName =event.getName();

        final Encoded material = Encoded.forName(materialName);
        if (Encoded.UNKNOWN.equals(material)) {
            log.warn("Unknown Scientific Research Encoded data detected: " + event);
        } else {
            StorageService.removeMaterial(material, count.intValue());
        }
    }
}
