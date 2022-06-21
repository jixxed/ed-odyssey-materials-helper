package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;

public class BuyMicroResourcesMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
//        final Iterator<JsonNode> microResources = journalMessage.get("MicroResources").elements();
//        microResources.forEachRemaining(microResource -> {
//            final OdysseyMaterial odysseyMaterial = OdysseyMaterial.subtypeForName(microResource.get("Name").asText());
//            final Storage materialStorage = StorageService.getMaterialStorage(odysseyMaterial);
//            materialStorage.setValue(materialStorage.getValue(StoragePool.SHIPLOCKER));
//        });
    }
}
