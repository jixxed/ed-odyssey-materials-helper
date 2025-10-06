package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.Bank;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;

import java.util.Arrays;

@Slf4j
public class CarrierBankParser {
    public void parse(Bank bank, StoragePool storagePool) {
        if (bank == null) {
            return;
        }

        if (UserPreferencesService.getPreference(PreferenceConstants.CAPI_ENABLE_HORIZONS_MATERIALS, true)) {
            bank.getCommodities().ifPresent(commoditiesBank ->
                    commoditiesBank.forEach((key, bankItems) ->
                            Arrays.stream(bankItems).forEach(bankItem -> {
                                try {
                                    final String name = bankItem.getName();
                                    final Commodity commodity = Commodity.forName(name);
                                    final int amount = Integer.parseInt(bankItem.getQty());
                                    if (commodity.isUnknown()) {
                                        log.warn("Unknown Commodity detected: " + name);
                                    } else {
                                        StorageService.addCommodity(commodity, storagePool, amount);
                                    }
                                } catch (NumberFormatException e) {
                                    log.error("Unable to parse CarrierBank quantity", e);
                                }
                            })
                    )
            );
        }
        if (UserPreferencesService.getPreference(PreferenceConstants.CAPI_ENABLE_ODYSSEY_MATERIALS, true)) {
            bank.getMicroresources().ifPresent(microresourcesBank ->
                    microresourcesBank.forEach((key, bankItems) ->
                            Arrays.stream(bankItems).forEach(bankItem -> {
                                try {
                                    final String name = bankItem.getName();
                                    final OdysseyMaterial material = OdysseyMaterial.subtypeForName(name);
                                    final int amount = Integer.parseInt(bankItem.getQty());
                                    StorageService.addMaterial(material, storagePool, amount);
                                } catch (NumberFormatException e) {
                                    log.error("Unable to parse CarrierBank quantity", e);
                                } catch (IllegalArgumentException e) {
                                    log.warn("Unknown material detected: " + bankItem.getName());
                                }
                            })
                    )
            );
        }
    }
}
