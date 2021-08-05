package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Asset implements Material {
    AEROGEL(AssetType.CHEMICAL),
    CHEMICALCATALYST(AssetType.CHEMICAL),
    CHEMICALSUPERBASE(AssetType.CHEMICAL),
    EPINEPHRINE(AssetType.CHEMICAL),
    EPOXYADHESIVE(AssetType.CHEMICAL),
    GRAPHENE(AssetType.CHEMICAL),
    OXYGENICBACTERIA(AssetType.CHEMICAL),
    PHNEUTRALISER(AssetType.CHEMICAL),
    RDX(AssetType.CHEMICAL),
    VISCOELASTICPOLYMER(AssetType.CHEMICAL),
    CIRCUITBOARD(AssetType.CIRCUIT),
    CIRCUITSWITCH(AssetType.CIRCUIT),
    ELECTRICALFUSE(AssetType.CIRCUIT),
    ELECTRICALWIRING(AssetType.CIRCUIT),
    ELECTROMAGNET(AssetType.CIRCUIT),
    IONBATTERY(AssetType.CIRCUIT),
    METALCOIL(AssetType.CIRCUIT),
    MICROELECTRODE(AssetType.CIRCUIT),
    MICROSUPERCAPACITOR(AssetType.CIRCUIT),
    MICROTRANSFORMER(AssetType.CIRCUIT),
    MOTOR(AssetType.CIRCUIT),
    OPTICALFIBRE(AssetType.CIRCUIT),
    CARBONFIBREPLATING(AssetType.TECH),
    ENCRYPTEDMEMORYCHIP(AssetType.TECH),
    MICROHYDRAULICS(AssetType.TECH),
    MICROTHRUSTERS(AssetType.TECH),
    MEMORYCHIP(AssetType.TECH),
    OPTICALLENS(AssetType.TECH),
    SCRAMBLER(AssetType.TECH),
    TITANIUMPLATING(AssetType.TECH),
    TRANSMITTER(AssetType.TECH),
    TUNGSTENCARBIDE(AssetType.TECH),
    WEAPONCOMPONENT(AssetType.TECH),
    UNKNOWN(AssetType.TECH);//add it to tech if it is unknown

    private final AssetType type;

    public static Asset forName(final String name) {
        try {
            return Asset.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Asset.UNKNOWN;
        }
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.ASSET;
    }

    @Override
    public String getLocalizationKey() {
        return "material.asset." + this.toString().toLowerCase();
    }


    @Override
    public boolean isUnknown() {
        return this == Asset.UNKNOWN;
    }


    @Override
    public boolean isIllegal() {
        return false;
    }

}
