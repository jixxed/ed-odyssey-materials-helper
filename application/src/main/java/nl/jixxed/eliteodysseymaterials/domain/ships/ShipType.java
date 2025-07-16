package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ShipType {
    SIDE_WINDER(128049249, "SideWinder", ShipSize.S, false, 0),
    EAGLE(128049255, "Eagle", ShipSize.S, false, 0),
    HAULER(128049261, "Hauler", ShipSize.S, false, 0),
    ADDER(128049267, "Adder", ShipSize.S, false, 1),
    VIPER_MK_III(128049273, "Viper", ShipSize.S, false, 0),
    COBRA_MK_III(128049279, "CobraMkIII", ShipSize.S, false, 1),
    TYPE_6(128049285, "Type6", ShipSize.M, false, 0),
    DOLPHIN(128049291, "Dolphin", ShipSize.S, false, 0),
    TYPE_7(128049297, "Type7", ShipSize.L, false, 0),
    ASP(128049303, "Asp", ShipSize.M, false, 1),
    VULTURE(128049309, "Vulture", ShipSize.S, false, 1),
    EMPIRE_TRADER(128049315, "Empire_Trader", ShipSize.L, false, 1),
    FEDERATION_DROPSHIP(128049321, "Federation_Dropship", ShipSize.M, false, 1),
    ORCA(128049327, "Orca", ShipSize.L, false, 1),
    TYPE_8(0, "Type8", ShipSize.M, false, 0),
    TYPE_9(128049333, "Type9", ShipSize.L, true, 3),
    PYTHON(128049339, "Python", ShipSize.M, false, 1),
    PYTHON_NX(190658840, "Python_nx", ShipSize.M, false, 1),
    BELUGA_LINER(128049345, "BelugaLiner", ShipSize.L, true, 3),
    FER_DE_LANCE(128049351, "FerDeLance", ShipSize.M, false, 1),
    ANACONDA(128049363, "Anaconda", ShipSize.L, true, 3),
    FEDERATION_CORVETTE(128049369, "Federation_Corvette", ShipSize.L, true, 3),
    CUTTER(128049375, "Cutter", ShipSize.L, true, 3),
    DIAMOND_BACK(128671217, "DiamondBack", ShipSize.S, false, 0),
    EMPIRE_COURIER(128671223, "Empire_Courier", ShipSize.S, false, 0),
    DIAMOND_BACK_XL(128671831, "DiamondBackXL", ShipSize.S, false, 0),
    EMPIRE_EAGLE(128672138, "Empire_Eagle", ShipSize.S, false, 0),
    FEDERATION_DROPSHIP_MK_II(128672145, "Federation_Dropship_MkII", ShipSize.M, false, 1),
    FEDERATION_GUNSHIP(128672152, "Federation_Gunship", ShipSize.M, true, 1),
    VIPER_MK_IV(128672255, "Viper_MkIV", ShipSize.S, false, 0),
    COBRA_MK_IV(128672262, "CobraMkIV", ShipSize.S, false, 1),
    INDEPENDANT_TRADER(128672269, "Independant_Trader", ShipSize.M, true, 1),
    ASP_SCOUT(128672276, "Asp_Scout", ShipSize.M, false, 1),
    TYPE_9_MILITARY(128785619, "Type9_Military", ShipSize.L, true, 3),
    KRAIT_MK_II(128816567, "Krait_MkII", ShipSize.M, true, 2),
    TYPE_X(128816574, "TypeX", ShipSize.M, false, 1),
    TYPE_X_2(128816581, "TypeX_2", ShipSize.M, true, 3),
    TYPE_X_3(128816588, "TypeX_3", ShipSize.M, false, 1),
    KRAIT_LIGHT(128839281, "Krait_Light", ShipSize.M, false, 1),
    MANDALAY(0, "Mandalay", ShipSize.M, false, 1),
    MAMBA(128915979, "Mamba", ShipSize.M, false, 1),
    COBRA_MK_V(128947462, "CobraMkV", ShipSize.S, false, 2),
    CORSAIR(0, "Corsair", ShipSize.M, false, 1),
    PANTHER_CLIPPER_MK_II(0, "PantherMkII", ShipSize.L, true, 3);

    final long internalID;
    final String internalName;
    final ShipSize shipSize;
    final boolean fighterBay;
    final int multiCrewSeats;//seats excluding pilot seat


    public String getLocalizationKey() {
        return "ships.name." + this.name().toLowerCase();
    }

    public static ShipType forInternalName(String internalName) {
        return Arrays.stream(ShipType.values())
                .filter(shipType -> shipType.internalName.equalsIgnoreCase(internalName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
