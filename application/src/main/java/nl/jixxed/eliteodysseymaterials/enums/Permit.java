package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
@Getter
public enum Permit {
            FOUR_SEXTANTIS(Allegiance.FEDERATION, PermitType.UNKNOWN, new StarSystem("4 Sextantis", 87.25, 96.84375, -65)),
            ACHENAR(Allegiance.EMPIRE, PermitType.IMPERIAL_NAVY_RANK, new StarSystem("Achenar", 67.5, -119.46875, 24.84375)),
            ALIOTH(Allegiance.ALLIANCE, PermitType.ALLIED, new StarSystem("Alioth", -33.65625, 72.46875, -20.65625)),
            BETA_HYDRI(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Beta Hydri", 15.28125, -15.6875, 10.5625)),
            CD_43_11917(Allegiance.INDEPENDENT, PermitType.GAME_RANK, new StarSystem("CD-43 11917", 18, -10.5625, 78.125)),
            CD_44_1695(Allegiance.FEDERATION, PermitType.UNKNOWN, new StarSystem("CD-44 1695", 67.875, -61.84375, -26.40625)),
            CROM(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Crom", 53.0625, -17.34375, 8.46875)),
            EXBEUR(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Exbeur", -35.15625, 27.84375, -33.65625)),
            FACECE(Allegiance.EMPIRE, PermitType.IMPERIAL_NAVY_RANK, new StarSystem("Facece", 64.28125, -111.4375, 25.1875)),
            HIP_22460(Allegiance.NONE, PermitType.FREE, new StarSystem("HIP 22460", -41.3125, -58.96875, -354.78125)),
            HIP_54530(Allegiance.FEDERATION, PermitType.ALLIED, new StarSystem("HIP 54530", 110.9375, 68.40625, 12.96875)),
            HIP_87621(Allegiance.NONE, PermitType.FREE, new StarSystem("HIP 87621", -220.34375, 112.125, 328.71875)),
            HODACK(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Hodack", 60.28125, 23.53125, 46.40625)),
            HORS(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Hors", 37.1875, -34.71875, 25.84375)),
            HR_4413(Allegiance.NONE, PermitType.UNKNOWN, new StarSystem("HR 4413", 80.96875, -4.40625, 36.0625)),
            ISINOR(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Isinor", 67.125, 68.1875, 54.96875)),
            JOTUN(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Jotun", -11.03125, -79.21875, -92.3125)),
            LTT_198(Allegiance.EMPIRE, PermitType.ALLIED, new StarSystem("LTT 198", 2.53125, -71.625, 9.71875)),
            LUYTEN_347_14(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Luyten 347-14", 2.21875, -7.53125, 16.8125)),
            MBOONI(Allegiance.NONE, PermitType.ALLIED, new StarSystem("MBooni", 10.53125, -49.96875, 7.46875)),
            NASTROND(Allegiance.INDEPENDENT,PermitType.ALLIED, new StarSystem("Nastrond", -75.34375, -40.4375, -56.75)),
            PEREGRINA(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Peregrina", -24.9375, -80.59375, -184.34375)),
            PI_MENSAE(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Pi Mensae", 47.78125, -29.84375, 19.8125)),
            PILOTS_FEDERATION_DISTRICT(Allegiance.INDEPENDENT, PermitType.STARTER,
                new StarSystem("Azoth", 17, -25.28125, 42.84375),
                new StarSystem("Dromi", 25.40625, -31.0625, 41.625),
                new StarSystem("HIP 97950", -91.34375, -32.625, 99.84375),
                new StarSystem("Lia Fail", 21, -26.125, 42.53125),
                new StarSystem("Matet", 25.96875, -37.15625, 38.21875),
                new StarSystem("Orna", 27, -28.5625, 47.34375),
                new StarSystem("Otegine", 25.0625, -32.59375, 47.75),
                new StarSystem("Sharur", 18.3125, -36.5, 48.25),
                new StarSystem("Tarnkappe", 17.3125, -33.5, 45.625),
                new StarSystem("Tyet", 16.25, -34.65625, 47.34375),
                new StarSystem("Wolfsegen", 21.8125, -31.03125, 43.75)
            ),
            PLX_695(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("PLX 695", -43.34375, -4.71875, -62.5)),
            ROSS_128(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Ross 128", 5.53125, 9.4375, 0.125)),
            FOUNDERS_WORLD(Allegiance.INDEPENDENT, PermitType.GAME_RANK, new StarSystem("Shinrarta Dezhra", 55.71875, 17.59375, 27.15625)),
            SIRIUS(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Sirius", 6.25, -1.28125, -5.75)),
            SOL(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Sol", 0, 0, 0)),
            SUMMERLAND(Allegiance.EMPIRE, PermitType.IMPERIAL_NAVY_RANK, new StarSystem("Summerland", 28.9375, -121.09375, 3.53125)),
            TERRA_MATER(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Terra Mater", -49.75, -19.03125, -45)),
            TILIALA(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("Tiliala", 85.75, -14.09375, -3.625)),
            VAN_MAANENS_STAR(Allegiance.INDEPENDENT, PermitType.ALLIED, new StarSystem("van Maanen's Star", -6.3125, -11.6875, -4.125)),
            VEGA(Allegiance.FEDERATION, PermitType.FEDERAL_NAVY_RANK, new StarSystem("Vega", -21.90625, 8.125, 9)),
            UNKNOWN(Allegiance.NONE, PermitType.UNKNOWN, new StarSystem("UNKNOWN", 99999.99, 99999.99, 99999.99));

    private final StarSystem[] systems;
    private final Allegiance allegiance;
    private final PermitType type;

    Permit(Allegiance allegiance, PermitType type, StarSystem  ... systems) {
        this.systems = systems;
        this.allegiance = allegiance;
        this.type = type;
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

