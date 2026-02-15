package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
@RequiredArgsConstructor
@Getter
public enum Permit {
            FOUR_SEXTANTIS(new StarSystem("4 Sextantis", 87.25, 96.84375, -65), Allegiance.FEDERATION),
            ACHENAR(new StarSystem("Achenar", 67.5, -119.46875, 24.84375), Allegiance.EMPIRE),
            ALIOTH(new StarSystem("Alioth", -33.65625, 72.46875, -20.65625), Allegiance.ALLIANCE),
            AZOTH(new StarSystem("Azoth", 17, -25.28125, 42.84375), Allegiance.NONE),
            BETA_HYDRI(new StarSystem("Beta Hydri", 15.28125, -15.6875, 10.5625), Allegiance.FEDERATION),
            CD_43_11917(new StarSystem("CD-43 11917", 18, -10.5625, 78.125), Allegiance.INDEPENDENT),
            CD_44_1695(new StarSystem("CD-44 1695", 67.875, -61.84375, -26.40625), Allegiance.FEDERATION),
            CROM(new StarSystem("Crom", 53.0625, -17.34375, 8.46875), Allegiance.INDEPENDENT),
            DROMI(new StarSystem("Dromi", 25.40625, -31.0625, 41.625), Allegiance.NONE),
            EXBEUR(new StarSystem("Exbeur", -35.15625, 27.84375, -33.65625), Allegiance.FEDERATION),
            FACECE(new StarSystem("Facece", 64.28125, -111.4375, 25.1875), Allegiance.EMPIRE),
            HIP_22460(new StarSystem("HIP 22460", -41.3125, -58.96875, -354.78125), Allegiance.NONE),
            HIP_54530(new StarSystem("HIP 54530", 110.9375, 68.40625, 12.96875), Allegiance.FEDERATION),
            HODACK(new StarSystem("Hodack", 60.28125, 23.53125, 46.40625), Allegiance.INDEPENDENT),
            HORS(new StarSystem("Hors", 37.1875, -34.71875, 25.84375), Allegiance.FEDERATION),
            HR_4413(new StarSystem("HR 4413", 80.96875, -4.40625, 36.0625), Allegiance.NONE),
            ISINOR(new StarSystem("Isinor", 67.125, 68.1875, 54.96875), Allegiance.INDEPENDENT),
            JOTUN(new StarSystem("Jotun", -11.03125, -79.21875, -92.3125), Allegiance.INDEPENDENT),
            LIA_FAIL(new StarSystem("Lia Fail", 21, -26.125, 42.53125), Allegiance.NONE),
            LTT_198(new StarSystem("LTT 198", 2.53125, -71.625, 9.71875), Allegiance.EMPIRE),
            LUYTEN_347_14(new StarSystem("Luyten 347-14", 2.21875, -7.53125, 16.8125), Allegiance.INDEPENDENT),
            MATET(new StarSystem("Matet", 25.96875, -37.15625, 38.21875), Allegiance.NONE),
            NASTROND(new StarSystem("Nastrond", -75.34375, -40.4375, -56.75), Allegiance.INDEPENDENT),
            ORNA(new StarSystem("Orna", 27, -28.5625, 47.34375), Allegiance.NONE),
            OTEGINE(new StarSystem("Otegine", 25.0625, -32.59375, 47.75), Allegiance.NONE),
            PEREGRINA(new StarSystem("Peregrina", -24.9375, -80.59375, -184.34375), Allegiance.INDEPENDENT),
            PHEKDA(new StarSystem("Phekda", -25.0625, 73.3125, -30.28125), Allegiance.NONE),
            PI_MENSAE(new StarSystem("Pi Mensae", 47.78125, -29.84375, 19.8125), Allegiance.INDEPENDENT),
            PLX_695(new StarSystem("PLX 695", -43.34375, -4.71875, -62.5), Allegiance.FEDERATION),
            ROSS_128(new StarSystem("Ross 128", 5.53125, 9.4375, 0.125), Allegiance.FEDERATION),
            SHARUR(new StarSystem("Sharur", 18.3125, -36.5, 48.25), Allegiance.NONE),
            SHINRARTA_DEZHRA(new StarSystem("Shinrarta Dezhra", 55.71875, 17.59375, 27.15625), Allegiance.INDEPENDENT),
            SIRIUS(new StarSystem("Sirius", 6.25, -1.28125, -5.75), Allegiance.INDEPENDENT),
            SOL(new StarSystem("Sol", 0, 0, 0), Allegiance.FEDERATION),
            SUMMERLAND(new StarSystem("Summerland", 28.9375, -121.09375, 3.53125), Allegiance.EMPIRE),
            TARNKAPPE(new StarSystem("Tarnkappe", 17.3125, -33.5, 45.625), Allegiance.NONE),
            TERRA_MATER(new StarSystem("Terra Mater", -49.75, -19.03125, -45), Allegiance.INDEPENDENT),
            TILIALA(new StarSystem("Tiliala", 85.75, -14.09375, -3.625), Allegiance.INDEPENDENT),
            TYET(new StarSystem("Tyet", 16.25, -34.65625, 47.34375), Allegiance.NONE),
            VAN_MAANENS_STAR(new StarSystem("van Maanen's Star", -6.3125, -11.6875, -4.125), Allegiance.INDEPENDENT),
            VEGA(new StarSystem("Vega", -21.90625, 8.125, 9), Allegiance.FEDERATION),
            WOLFSEGEN(new StarSystem("Wolfsegen", 21.8125, -31.03125, 43.75), Allegiance.NONE),
            UNKNOWN(new StarSystem("UNKNOWN", 99999.99, 99999.99, 99999.99), Allegiance.NONE);

    private final StarSystem system;
    private final Allegiance allegiance;

    public static Permit forName(String name) {
        try {
            return Permit.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Permit.UNKNOWN;
        }
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

