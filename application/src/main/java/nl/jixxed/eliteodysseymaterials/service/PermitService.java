package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Allegiance;
import nl.jixxed.eliteodysseymaterials.enums.Permit;

import java.util.List;

public class PermitService {
   private static List<Permit> permitSystems = List.of(
            new Permit(new StarSystem("4 Sextantis", 87.25, 96.84375, -65), Allegiance.FEDERATION),
            new Permit(new StarSystem("Achenar", 67.5, -119.46875, 24.84375), Allegiance.EMPIRE),
            new Permit(new StarSystem("Alioth", -33.65625, 72.46875, -20.65625), Allegiance.ALLIANCE),
            new Permit(new StarSystem("Azoth", 17, -25.28125, 42.84375), Allegiance.NONE),
            new Permit(new StarSystem("Beta Hydri", 15.28125, -15.6875, 10.5625), Allegiance.FEDERATION),
            new Permit(new StarSystem("CD-43 11917", 18, -10.5625, 78.125), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("CD-44 1695", 67.875, -61.84375, -26.40625), Allegiance.FEDERATION),
            new Permit(new StarSystem("Crom", 53.0625, -17.34375, 8.46875), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Dromi", 25.40625, -31.0625, 41.625), Allegiance.NONE),
            new Permit(new StarSystem("Exbeur", -35.15625, 27.84375, -33.65625), Allegiance.FEDERATION),
            new Permit(new StarSystem("Facece", 64.28125, -111.4375, 25.1875), Allegiance.EMPIRE),
            new Permit(new StarSystem("HIP 22460", -41.3125, -58.96875, -354.78125), Allegiance.NONE),
            new Permit(new StarSystem("HIP 54530", 110.9375, 68.40625, 12.96875), Allegiance.FEDERATION),
            new Permit(new StarSystem("Hodack", 60.28125, 23.53125, 46.40625), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Hors", 37.1875, -34.71875, 25.84375), Allegiance.FEDERATION),
            new Permit(new StarSystem("HR 4413", 80.96875, -4.40625, 36.0625), Allegiance.NONE),
            new Permit(new StarSystem("Isinor", 67.125, 68.1875, 54.96875), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Jotun", -11.03125, -79.21875, -92.3125), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Lia Fail", 21, -26.125, 42.53125), Allegiance.NONE),
            new Permit(new StarSystem("LTT 198", 2.53125, -71.625, 9.71875), Allegiance.EMPIRE),
            new Permit(new StarSystem("Luyten 347-14", 2.21875, -7.53125, 16.8125), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Matet", 25.96875, -37.15625, 38.21875), Allegiance.NONE),
            new Permit(new StarSystem("Nastrond", -75.34375, -40.4375, -56.75), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Orna", 27, -28.5625, 47.34375), Allegiance.NONE),
            new Permit(new StarSystem("Otegine", 25.0625, -32.59375, 47.75), Allegiance.NONE),
            new Permit(new StarSystem("Peregrina", -24.9375, -80.59375, -184.34375), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Phekda", -25.0625, 73.3125, -30.28125), Allegiance.NONE),
            new Permit(new StarSystem("Pi Mensae", 47.78125, -29.84375, 19.8125), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("PLX 695", -43.34375, -4.71875, -62.5), Allegiance.FEDERATION),
            new Permit(new StarSystem("Ross 128", 5.53125, 9.4375, 0.125), Allegiance.FEDERATION),
            new Permit(new StarSystem("Sharur", 18.3125, -36.5, 48.25), Allegiance.NONE),
            new Permit(new StarSystem("Shinrarta Dezhra", 55.71875, 17.59375, 27.15625), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Sirius", 6.25, -1.28125, -5.75), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Sol", 0, 0, 0), Allegiance.FEDERATION),
            new Permit(new StarSystem("Summerland", 28.9375, -121.09375, 3.53125), Allegiance.EMPIRE),
            new Permit(new StarSystem("Tarnkappe", 17.3125, -33.5, 45.625), Allegiance.NONE),
            new Permit(new StarSystem("Terra Mater", -49.75, -19.03125, -45), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Tiliala", 85.75, -14.09375, -3.625), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Tyet", 16.25, -34.65625, 47.34375), Allegiance.NONE),
            new Permit(new StarSystem("van Maanen's Star", -6.3125, -11.6875, -4.125), Allegiance.INDEPENDENT),
            new Permit(new StarSystem("Vega", -21.90625, 8.125, 9), Allegiance.FEDERATION),
            new Permit(new StarSystem("Wolfsegen", 21.8125, -31.03125, 43.75), Allegiance.NONE)
    );

   public static boolean isPermitSystem(final StarSystem starSystem) {
       return permitSystems.stream().anyMatch(permit -> permit.system().equals(starSystem));
   }
}
