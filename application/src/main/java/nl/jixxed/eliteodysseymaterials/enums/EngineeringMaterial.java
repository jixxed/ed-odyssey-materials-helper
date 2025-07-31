package nl.jixxed.eliteodysseymaterials.enums;
public sealed interface EngineeringMaterial extends HorizonsMaterial permits Manufactured, Encoded, Raw {
    default boolean isHuman(){
        return !getMaterialType().equals(HorizonsMaterialType.GUARDIAN) &&
        !getMaterialType().equals(HorizonsMaterialType.THARGOID);
    }
}
