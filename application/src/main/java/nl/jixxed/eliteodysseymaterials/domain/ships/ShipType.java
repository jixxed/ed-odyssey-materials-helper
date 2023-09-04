package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ShipType {
    SIDE_WINDER(128049249,"SideWinder"),
    EAGLE(128049255,"Eagle"),
    HAULER(128049261,"Hauler"),
    ADDER(128049267,"Adder"),
    VIPER_MK_III(128049273,"Viper"),
    COBRA_MK_III(128049279,"CobraMkIII"),
    TYPE_6(128049285,"Type6"),
    DOLPHIN(128049291,"Dolphin"),
    TYPE_7(128049297,"Type7"),
    ASP(128049303,"Asp"),
    VULTURE(128049309,"Vulture"),
    EMPIRE_TRADER(128049315,"Empire_Trader"),
    FEDERATION_DROPSHIP(128049321,"Federation_Dropship"),
    ORCA(128049327,"Orca"),
    TYPE_9(128049333,"Type9"),
    PYTHON(128049339,"Python"),
    BELUGA_LINER(128049345,"BelugaLiner"),
    FER_DE_LANCE(128049351,"FerDeLance"),
    ANACONDA(128049363,"Anaconda"),
    FEDERATION_CORVETTE(128049369,"Federation_Corvette"),
    CUTTER(128049375,"Cutter"),
    DIAMOND_BACK(128671217,"DiamondBack"),
    EMPIRE_COURIER(128671223,"Empire_Courier"),
    DIAMOND_BACK_XL(128671831,"DiamondBackXL"),
    EMPIRE_EAGLE(128672138,"Empire_Eagle"),
    FEDERATION_DROPSHIP_MK_II(128672145,"Federation_Dropship_MkII"),
    FEDERATION_GUNSHIP(128672152,"Federation_Gunship"),
    VIPER_MK_IV(128672255,"Viper_MkIV"),
    COBRA_MK_IV(128672262,"CobraMkIV"),
    INDEPENDANT_TRADER(128672269,"Independant_Trader"),
    ASP_SCOUT(128672276,"Asp_Scout"),
    TYPE_9_MILITARY(128785619,"Type9_Military"),
    KRAIT_MK_II(128816567,"Krait_MkII"),
    TYPE_X(128816574,"TypeX"),
    TYPE_X_2(128816581,"TypeX_2"),
    TYPE_X_3(128816588,"TypeX_3"),
    KRAIT_LIGHT(128839281,"Krait_Light"),
    MAMBA(128915979,"Mamba");

    final long internalID;
    final String internalName;


    public String getLocalizationKey() {
        return "ships.name." + this.name().toLowerCase();
    }
}
