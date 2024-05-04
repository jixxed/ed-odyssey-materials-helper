package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ShipType {
    SIDE_WINDER(128049249,"SideWinder", ShipSize.S),
    EAGLE(128049255,"Eagle", ShipSize.S),
    HAULER(128049261,"Hauler", ShipSize.S),
    ADDER(128049267,"Adder", ShipSize.S),
    VIPER_MK_III(128049273,"Viper", ShipSize.S),
    COBRA_MK_III(128049279,"CobraMkIII", ShipSize.S),
    TYPE_6(128049285,"Type6", ShipSize.M),
    DOLPHIN(128049291,"Dolphin", ShipSize.S),
    TYPE_7(128049297,"Type7", ShipSize.L),
    ASP(128049303,"Asp", ShipSize.M),
    VULTURE(128049309,"Vulture", ShipSize.S),
    EMPIRE_TRADER(128049315,"Empire_Trader", ShipSize.L),
    FEDERATION_DROPSHIP(128049321,"Federation_Dropship", ShipSize.M),
    ORCA(128049327,"Orca", ShipSize.L),
    TYPE_9(128049333,"Type9", ShipSize.L),
    PYTHON(128049339,"Python", ShipSize.M),
    PYTHON_NX(190658840,"Python_nx", ShipSize.M),
    BELUGA_LINER(128049345,"BelugaLiner", ShipSize.L),
    FER_DE_LANCE(128049351,"FerDeLance", ShipSize.M),
    ANACONDA(128049363,"Anaconda", ShipSize.L),
    FEDERATION_CORVETTE(128049369,"Federation_Corvette", ShipSize.L),
    CUTTER(128049375,"Cutter", ShipSize.L),
    DIAMOND_BACK(128671217,"DiamondBack", ShipSize.S),
    EMPIRE_COURIER(128671223,"Empire_Courier", ShipSize.S),
    DIAMOND_BACK_XL(128671831,"DiamondBackXL", ShipSize.S),
    EMPIRE_EAGLE(128672138,"Empire_Eagle", ShipSize.S),
    FEDERATION_DROPSHIP_MK_II(128672145,"Federation_Dropship_MkII", ShipSize.M),
    FEDERATION_GUNSHIP(128672152,"Federation_Gunship", ShipSize.M),
    VIPER_MK_IV(128672255,"Viper_MkIV", ShipSize.S),
    COBRA_MK_IV(128672262,"CobraMkIV", ShipSize.S),
    INDEPENDANT_TRADER(128672269,"Independant_Trader", ShipSize.M),
    ASP_SCOUT(128672276,"Asp_Scout", ShipSize.M),
    TYPE_9_MILITARY(128785619,"Type9_Military", ShipSize.L),
    KRAIT_MK_II(128816567,"Krait_MkII", ShipSize.M),
    TYPE_X(128816574,"TypeX", ShipSize.M),
    TYPE_X_2(128816581,"TypeX_2", ShipSize.M),
    TYPE_X_3(128816588,"TypeX_3", ShipSize.M),
    KRAIT_LIGHT(128839281,"Krait_Light", ShipSize.M),
    MAMBA(128915979,"Mamba", ShipSize.M);

    final long internalID;
    final String internalName;
    final ShipSize shipSize;


    public String getLocalizationKey() {
        return "ships.name." + this.name().toLowerCase();
    }

    public static ShipType forInternalName(String internalName){
        return Arrays.stream(ShipType.values())
                .filter(shipType->shipType.internalName.equalsIgnoreCase(internalName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
