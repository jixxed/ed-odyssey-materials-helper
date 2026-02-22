package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@Getter
public enum Permit {
    FOUR_SEXTANTIS(Allegiance.FEDERATION, PermitType.UNKNOWN, new StarSystem("4 Sextantis", 87.25, 96.84375, -65.0)),//unobtainable
    ACHENAR(Allegiance.EMPIRE, PermitType.IMPERIAL_NAVY_RANK, new StarSystem("Achenar", 67.5, -119.46875, 24.84375)),//imperial
    ALIOTH(Allegiance.ALLIANCE, PermitType.ALLIED, new Location(new StarSystem("Alcor", -36.0, 71.78125, -15.125), null, null, "Meredith's Dream", null, null), new StarSystem("Alioth", -33.65625, 72.46875, -20.65625)),
    BETA_HYDRI(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Beta Hydri", 15.28125, -15.6875, 10.5625)),//federal
    CD_43_11917(Allegiance.INDEPENDENT, PermitType.GAME_RANK, new StarSystem("CD-43 11917", 18.0, -10.5625, 78.125)),//CQC
    CD_44_1695(Allegiance.FEDERATION, PermitType.UNKNOWN, new StarSystem("CD-44 1695", 67.875, -61.84375, -26.40625)),
    CROM(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("69 G. Carinae", 50.78125, -14.28125, 3.15625), null, null, "The Silver Cove", null, null), new StarSystem("Crom", 53.0625, -17.34375, 8.46875)),
    EXBEUR(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Exbeur", -35.15625, 27.84375, -33.65625)),//federal
    FACECE(Allegiance.EMPIRE, PermitType.IMPERIAL_NAVY_RANK, new StarSystem("Facece", 64.28125, -111.4375, 25.1875)),//imperial
    HIP_22460(Allegiance.NONE, PermitType.FREE, new StarSystem("HIP 22460", -41.3125, -58.96875, -354.78125)),
    HIP_54530(Allegiance.FEDERATION, PermitType.ALLIED, new Location(new StarSystem("Ts'ao Tach", 112.25, 66.65625, 20.6875), null, null, "Grasp of Unity", null, null), new StarSystem("HIP 54530", 110.9375, 68.40625, 12.96875)),
    HIP_87621(Allegiance.NONE, PermitType.FREE, new StarSystem("HIP 87621", -220.34375, 112.125, 328.71875)),
    HODACK(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("Pemoeri", 68.71875, 32.75, 41.03125), null, null, "Varuna's Pledge", null, null), new StarSystem("Hodack", 60.28125, 23.53125, 46.40625)),
    HORS(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Hors", 37.1875, -34.71875, 25.84375)),//federal
    HR_4413(Allegiance.NONE, PermitType.UNKNOWN, new StarSystem("HR 4413", 80.96875, -4.40625, 36.0625)),
    ISINOR(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("HR 4803", 79.09375, 64.84375, 45.5), null, null, "Chariot of the Faithful", null, null), new StarSystem("Isinor", 67.125, 68.1875, 54.96875)),
    JOTUN(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("38 Arietis", -27.5625, -78.125, -84.78125), null, null, "The Indefatigable", null, null), new StarSystem("Jotun", -11.03125, -79.21875, -92.3125)),
    LTT_198(Allegiance.EMPIRE, PermitType.ALLIED, new Location(new StarSystem("Tavgi", 8.34375, -72.84375, 12.84375), null, null, "Orchestral Falcon", null, null), new StarSystem("LTT 198", 2.53125, -71.625, 9.71875)),
    LUYTEN_347_14(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("Narenses", -1.15625, -11.03125, 21.875), null, null, "Warden's Oath", null, null), new StarSystem("Luyten 347-14", 2.21875, -7.53125, 16.8125)),
    MBOONI(Allegiance.NONE, PermitType.ALLIED, new Location(new StarSystem("LHS 1163", 2.375, -46.1875, 0.6875), null, null, "Hunziker Terminal", null, null), new StarSystem("MBooni", 10.53125, -49.96875, 7.46875)),
    NASTROND(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("Wonorne Nu", -69.46875, -47.0, -50.03125), null, null, "The People's Vigil", null, null), new StarSystem("Nastrond", -75.34375, -40.4375, -56.75)),
    PEREGRINA(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("HIP 18609", -15.96875, -85.65625, -173.90625), null, null, "Peregrina Gateway", null, null), new StarSystem("Peregrina", -24.9375, -80.59375, -184.34375)),
    PI_MENSAE(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("LP 91-140", 38.71875, -35.53125, 9.84375), null, null, "Shephard of the Mighty", null, null), new StarSystem("Pi Mensae", 47.78125, -29.84375, 19.8125)),
    PILOTS_FEDERATION_DISTRICT(Allegiance.INDEPENDENT, PermitType.STARTER,
            new StarSystem("Azoth", 17.0, -25.28125, 42.84375),
            new StarSystem("Dromi", 25.40625, -31.0625, 41.625),
            new StarSystem("Lia Fail", 21.0, -26.125, 42.53125),
            new StarSystem("Matet", 25.96875, -37.15625, 38.21875),
            new StarSystem("Orna", 27.0, -28.5625, 47.34375),
            new StarSystem("Otegine", 25.0625, -32.59375, 47.75),
            new StarSystem("Sharur", 18.3125, -36.5, 48.25),
            new StarSystem("Tarnkappe", 17.3125, -33.5, 45.625),
            new StarSystem("Tyet", 16.25, -34.65625, 47.34375),
            new StarSystem("Wolfsegen", 21.8125, -31.03125, 43.75),
            new StarSystem("Arapahoma", -98.03125, -34.875, 99.53125),
            new StarSystem("HIP 97950", -91.34375, -32.625, 99.84375),
            new StarSystem("Kongoloca", -97.65625, -29.21875, 88.90625),
            new StarSystem("Ngunabozho", -97.875, -33.0625, 95.1875),
            new StarSystem("Col 285 Sector FG-D a42-0", -92.3125, -34.84375, 96.46875)
    ),
    PLX_695(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("PLX 695", -43.34375, -4.71875, -62.5)),//federal
    ROSS_128(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Ross 128", 5.53125, 9.4375, 0.125)),//federal
    FOUNDERS_WORLD(Allegiance.INDEPENDENT, PermitType.GAME_RANK, new StarSystem("Shinrarta Dezhra", 55.71875, 17.59375, 27.15625)),//elite
    SIRIUS(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("Luyten's Star", 6.5625, 2.34375, -10.25), null, null, "Spirit of Laelaps", null, null), new StarSystem("Sirius", 6.25, -1.28125, -5.75)),
    SOL(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Sol", 0.0, 0.0, 0.0)),//federal
    SUMMERLAND(Allegiance.EMPIRE, PermitType.IMPERIAL_NAVY_RANK, new StarSystem("Summerland", 28.9375, -121.09375, 3.53125)),//imperial
    TERRA_MATER(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("Hecate", -56.0, -25.125, -44.28125), null, null, "Daunting Hammer", null, null), new StarSystem("Terra Mater", -49.75, -19.03125, -45.0)),
    TILIALA(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("Akandinigua", 85.65625, -18.15625, -4.5625), null, null, "Tiliala's Lament", null, null), new StarSystem("Tiliala", 85.75, -14.09375, -3.625)),
    VAN_MAANENS_STAR(Allegiance.INDEPENDENT, PermitType.ALLIED, new Location(new StarSystem("Epsilon Eridani", 1.9375, -7.75, -6.84375), null, null, "Shadows Endurant", null, null), new StarSystem("van Maanen's Star", -6.3125, -11.6875, -4.125)),
    VEGA(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Vega", -21.90625, 8.125, 9.0)),//federal
    UNKNOWN(Allegiance.NONE, PermitType.UNKNOWN, new StarSystem("UNKNOWN", 99999.99, 99999.99, 99999.99));

    private final StarSystem[] systems;
    private final Allegiance allegiance;
    private final PermitType type;
    private Location permitLocation;

    Permit(Allegiance allegiance, PermitType type, StarSystem... systems) {
        this.systems = systems;
        this.allegiance = allegiance;
        this.type = type;

    }
    Permit(Allegiance allegiance, PermitType type, Location permitLocation, StarSystem... systems) {
        this(allegiance, type, systems);
        this.permitLocation = permitLocation;

    }

    public static Permit forName(String name) {
        try {
            return Permit.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Permit.UNKNOWN;
        }
    }

    public String getLocalizationKey() {
        return "permit.name." + this.name().toLowerCase();
    }

    public String getDescriptionLocalizationKey() {
        return "permit.unlock.description." + this.name().toLowerCase();
    }

    public boolean isUnknown() {
        return Permit.UNKNOWN.equals(this) || type.equals(PermitType.UNKNOWN);
    }
}
//old list
//hip48762permit
//ic4673
//peregrinapermit
//ltt198permit
//ross128permit
//summerlandpermit
//ngc2286
//ngc3603
//lave2
//regor
//plx695permit
//praei1
//praei2
//praei3
//praei4
//praei5
//praei6
//wolf262permit
//achenarpermit
//scheek
//alliancelandingpermit
//triton
//securitylandingpermit
//foundersworldpermit
//aiphsty
//outbreaklandingpermit
//dynasty1
//dynasty3
//dynasty4
//dynasty5
//hip54530permit
//horspermit
//siriuspermit
//solpermit
//lhs2894permit
//vegapermit
//cqcworldpermit
//hip39425permit
//lhs2921permit
//hodackpermit
//alphahydripermit
//vanmaanenspermit
//tilialapermit
//federationlandingpermit
//betahydripermit
//hyponia
//kambapermit
//bellicapermit
//hip51073permit
//none
//tge1
//tge2
//tge3
//ross354permit
//bovomit
//lft509permit
//polarispermit
//sidgoir
//terramaterpermit
//empirepermit
//witchsreachpermit
//facecepermit
//luyten34714permit
//aliothpermit
//alliancepermit
//hr4413permit
//phekdapermit
//hip10332permit
//lhs3091permit
//outbreakpermit
//froadik
//jotunpermit
//eventpermit
//isinorpermit
//mingfupermit
//hip104941permit
//themoon
//crompermit
//4sextantispermit
//empirelandingpermit
//securitypermit
//cd441695permit
//nastrondpermit
//bleia1
//bleia2
//bleia3
//bleia4
//bleia5
//dryman
//exbeurpermit
//pimensaepermit
//federationpermit
//hip22182permit

