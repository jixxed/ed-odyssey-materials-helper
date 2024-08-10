package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Raw;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ScientificResearch.ScientificResearch;

import java.math.BigInteger;
import java.util.Map;

@Slf4j
public class RawScientificResearchParser implements HorizonsParser<ScientificResearch> {

    @Override
    public void parse(final ScientificResearch event, final Map<HorizonsMaterial, Integer> storage) {
        final BigInteger count = event.getCount();
        final String materialName =event.getName();

        final Raw material = Raw.forName(materialName);
        if (Raw.UNKNOWN.equals(material)) {
            log.warn("Unknown Scientific Research Raw data detected: " + event);
        } else {
            storage.put(material, storage.get(material) - count.intValue());
        }
    }
}
