package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@AllArgsConstructor
@Getter
public enum Asset implements OdysseyMaterial {
    AEROGEL(AssetType.CHEMICAL, 9, 5),
    CHEMICALCATALYST(AssetType.CHEMICAL, 7, 4),
    CHEMICALSUPERBASE(AssetType.CHEMICAL, 9, 5),
    EPINEPHRINE(AssetType.CHEMICAL, 5, 3),
    EPOXYADHESIVE(AssetType.CHEMICAL, 5, 3),
    GRAPHENE(AssetType.CHEMICAL, 23, 12),
    OXYGENICBACTERIA(AssetType.CHEMICAL, 5, 3),
    PHNEUTRALISER(AssetType.CHEMICAL, 5, 3),
    RDX(AssetType.CHEMICAL, 7, 4),
    VISCOELASTICPOLYMER(AssetType.CHEMICAL, 11, 6),
    CIRCUITBOARD(AssetType.CIRCUIT, 9, 5),
    CIRCUITSWITCH(AssetType.CIRCUIT, 4, 2),
    ELECTRICALFUSE(AssetType.CIRCUIT, 5, 3),
    ELECTRICALWIRING(AssetType.CIRCUIT, 9, 5),
    ELECTROMAGNET(AssetType.CIRCUIT, 9, 5),
    IONBATTERY(AssetType.CIRCUIT, 9, 5),
    METALCOIL(AssetType.CIRCUIT, 9, 5),
    MICROELECTRODE(AssetType.CIRCUIT, 16, 8),
    MICROSUPERCAPACITOR(AssetType.CIRCUIT, 5, 3),
    MICROTRANSFORMER(AssetType.CIRCUIT, 7, 4),
    MOTOR(AssetType.CIRCUIT, 5, 3),
    OPTICALFIBRE(AssetType.CIRCUIT, 11, 6),
    CARBONFIBREPLATING(AssetType.TECH, 11, 6),
    ENCRYPTEDMEMORYCHIP(AssetType.TECH, 4, 2),
    MICROHYDRAULICS(AssetType.TECH, 7, 4),
    MICROTHRUSTERS(AssetType.TECH, 5, 4),
    MEMORYCHIP(AssetType.TECH, 4, 2),
    OPTICALLENS(AssetType.TECH, 9, 5),
    SCRAMBLER(AssetType.TECH, 5, 3),
    TITANIUMPLATING(AssetType.TECH, 11, 6),
    TRANSMITTER(AssetType.TECH, 5, 3),
    TUNGSTENCARBIDE(AssetType.TECH, 11, 6),
    WEAPONCOMPONENT(AssetType.TECH, 18, 9),
    UNKNOWN(AssetType.TECH, 0, 0);//add it to tech if it is unknown

    private final AssetType type;
    private final int buyValue;
    private final int sellValue;

    public static Asset forName(final String name) {
        try {
            return Asset.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Asset.UNKNOWN;
        }
    }

    @Override
    public OdysseyStorageType getStorageType() {
        return OdysseyStorageType.ASSET;
    }

    @Override
    public String getLocalizationKey() {
        return "material.asset." + this.name().toLowerCase();
    }


    @Override
    public boolean isUnknown() {
        return this == Asset.UNKNOWN;
    }


    @Override
    public boolean isIllegal() {
        return false;
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
