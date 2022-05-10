package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

import java.util.List;

@AllArgsConstructor
public enum Engineer {
    //Odyssey
    DOMINO_GREEN(Expansion.ODYSSEY, Settlement.THE_JACKRABBIT, Specialisation.STRATEGIC, new StarSystem("Orishis", -31, 93.96875, -3.5625), List.of(EngineerPrerequisite.ODYSSEY_A1)),
    KIT_FOWLER(Expansion.ODYSSEY, Settlement.THE_LAST_CALL, Specialisation.FORCE, new StarSystem("Capoya", -60.65625, 82.4375, -45.0625), List.of(EngineerPrerequisite.ODYSSEY_A2, EngineerPrerequisite.ODYSSEY_A3)),
    YARDEN_BOND(Expansion.ODYSSEY, Settlement.SALAMANDER_BANK, Specialisation.DYNAMIC, new StarSystem("Bayan", -19.96875, 117.625, -90.46875), List.of(EngineerPrerequisite.ODYSSEY_A4, EngineerPrerequisite.ODYSSEY_A5)),
    HERO_FERRARI(Expansion.ODYSSEY, Settlement.NEVERMORE_TERRACE, Specialisation.DYNAMIC, new StarSystem("Siris", 131.0625, -73.59375, -11.25), List.of(EngineerPrerequisite.ODYSSEY_B1)),
    WELLINGTON_BECK(Expansion.ODYSSEY, Settlement.BECK_FACILITY, Specialisation.STRATEGIC, new StarSystem("Jolapa", 100.1875, -41.34375, -78), List.of(EngineerPrerequisite.ODYSSEY_B2, EngineerPrerequisite.ODYSSEY_B3)),
    UMA_LASZLO(Expansion.ODYSSEY, Settlement.LASZLOS_RESOLVE, Specialisation.FORCE, new StarSystem("Xuane", 93.875, -9.25, -32.53125), List.of(EngineerPrerequisite.ODYSSEY_B4, EngineerPrerequisite.ODYSSEY_B5)),
    JUDE_NAVARRO(Expansion.ODYSSEY, Settlement.MARSHALLS_DRIFT, Specialisation.FORCE, new StarSystem("Aurai", 0.9375, -47.8125, 46.28125), List.of(EngineerPrerequisite.ODYSSEY_C1)),
    TERRA_VELASQUEZ(Expansion.ODYSSEY, Settlement.RASCALS_CHOICE, Specialisation.DYNAMIC, new StarSystem("Shou Xing", -16.28125, -44.53125, 94.375), List.of(EngineerPrerequisite.ODYSSEY_C2, EngineerPrerequisite.ODYSSEY_C3)),
    ODEN_GEIGER(Expansion.ODYSSEY, Settlement.ANKHS_PROMISE, Specialisation.STRATEGIC, new StarSystem("Candiaei", -113.5, -4.9375, 66.84375), List.of(EngineerPrerequisite.ODYSSEY_C4, EngineerPrerequisite.ODYSSEY_C5)),
    BALTANOS(Expansion.ODYSSEY, Settlement.THE_DIVINE_APPARATUS, Specialisation.DYNAMIC, new StarSystem("Deriso", -9520.3125, -909.5, 19808.75), List.of(EngineerPrerequisite.ODYSSEY_D1_1)),
    ROSA_DAYETTE(Expansion.ODYSSEY, Settlement.ROSAS_SHOP, Specialisation.STRATEGIC, new StarSystem("Kojeara", -9513.09375, -908.84375, 19814.28125), List.of(EngineerPrerequisite.ODYSSEY_D1_2)),
    ELEANOR_BRESA(Expansion.ODYSSEY, Settlement.BRESA_MODIFICATIONS, Specialisation.FORCE, new StarSystem("Desy", -9534.21875, -912.21875, 19792.375), List.of(EngineerPrerequisite.ODYSSEY_D1_3)),
    YI_SHEN(Expansion.ODYSSEY, Settlement.EIDOLON_HOLD, Specialisation.STRATEGIC, new StarSystem("Einheriar", -9557.8125, -880.1875, 19801.5625), List.of(EngineerPrerequisite.ODYSSEY_D2, EngineerPrerequisite.ODYSSEY_D3)),
    //Horizons
    ELVIRA_MARTUUK(Expansion.HORIZONS, Settlement.LONG_SIGHT_BASE, Specialisation.UNKNOWN, new StarSystem("Khun", -171.59375, 19.96875, -56.96875), List.of(EngineerPrerequisite.HORIZONS_A_LEARN, EngineerPrerequisite.HORIZONS_A_INVITE, EngineerPrerequisite.HORIZONS_A_UNLOCK)),
    MEL_BRANDON(Expansion.HORIZONS, Settlement.THE_BRIG, Specialisation.UNKNOWN, new StarSystem("Luchtaine", -9523.3125, -914.46875, 19825.90625), List.of(EngineerPrerequisite.HORIZONS_A1_LEARN, EngineerPrerequisite.HORIZONS_A1_INVITE, EngineerPrerequisite.HORIZONS_A1_UNLOCK)),
    ZACARIAH_NEMO(Expansion.HORIZONS, Settlement.NEMO_CYBER_PARTY_BASE, Specialisation.UNKNOWN, new StarSystem("Yoru", 97.875, -86.90625, 64.125), List.of(EngineerPrerequisite.HORIZONS_A2_LEARN, EngineerPrerequisite.HORIZONS_A2_INVITE, EngineerPrerequisite.HORIZONS_A2_UNLOCK)),
    MARCO_QWENT(Expansion.HORIZONS, Settlement.QWENT_RESEARCH_BASE, Specialisation.UNKNOWN, new StarSystem("Sirius", 6.25, -1.28125, -5.75), List.of(EngineerPrerequisite.HORIZONS_A3_LEARN, EngineerPrerequisite.HORIZONS_A3_INVITE, EngineerPrerequisite.HORIZONS_A3_UNLOCK)),
    PROFESSOR_PALIN(Expansion.HORIZONS, Settlement.ABEL_LABORATORY, Specialisation.UNKNOWN, new StarSystem("Arque", 66.5, 38.0625, 61.125), List.of(EngineerPrerequisite.HORIZONS_A3A_LEARN, EngineerPrerequisite.HORIZONS_A3A_INVITE, EngineerPrerequisite.HORIZONS_A3A_UNLOCK)),
    LORI_JAMESON(Expansion.HORIZONS, Settlement.JAMESON_BASE, Specialisation.UNKNOWN, new StarSystem("Shinrarta Dezhra", 55.71875, 17.59375, 27.15625), List.of(EngineerPrerequisite.HORIZONS_A3B_LEARN, EngineerPrerequisite.HORIZONS_A3B_INVITE, EngineerPrerequisite.HORIZONS_A3B_UNLOCK)),
    CHLOE_SEDESI(Expansion.HORIZONS, Settlement.CINDER_DOCK, Specialisation.UNKNOWN, new StarSystem("Shenve", 351.96875, -373.46875, -711.09375), List.of(EngineerPrerequisite.HORIZONS_A3C_LEARN, EngineerPrerequisite.HORIZONS_A3C_INVITE, EngineerPrerequisite.HORIZONS_A3C_UNLOCK)),
    THE_DWELLER(Expansion.HORIZONS, Settlement.BLACK_HIDE, Specialisation.UNKNOWN, new StarSystem("Wyrd", -11.625, 31.53125, -3.9375), List.of(EngineerPrerequisite.HORIZONS_B_LEARN, EngineerPrerequisite.HORIZONS_B_INVITE, EngineerPrerequisite.HORIZONS_B_UNLOCK)),
    MARSHA_HICKS(Expansion.HORIZONS, Settlement.THE_WATCHTOWER, Specialisation.UNKNOWN, new StarSystem("Tir", -9532.9375, -923.4375, 19799.125), List.of(EngineerPrerequisite.HORIZONS_B1_LEARN, EngineerPrerequisite.HORIZONS_B1_INVITE, EngineerPrerequisite.HORIZONS_B1_UNLOCK)),
    LEI_CHEUNG(Expansion.HORIZONS, Settlement.TRADERS_REST, Specialisation.UNKNOWN, new StarSystem("Laksak", -21.53125, -6.3125, 116.03125), List.of(EngineerPrerequisite.HORIZONS_B2_LEARN, EngineerPrerequisite.HORIZONS_B2_INVITE, EngineerPrerequisite.HORIZONS_B2_UNLOCK)),
    RAM_TAH(Expansion.HORIZONS, Settlement.PHOENIX_BASE, Specialisation.UNKNOWN, new StarSystem("Meene", 118.78125, -56.4375, -97.1875), List.of(EngineerPrerequisite.HORIZONS_B2A_LEARN, EngineerPrerequisite.HORIZONS_B2A_INVITE, EngineerPrerequisite.HORIZONS_B2A_UNLOCK)),
    FELICITY_FARSEER(Expansion.HORIZONS, Settlement.FARSEER_INC, Specialisation.UNKNOWN, new StarSystem("Deciat", 122.625, -0.8125, -47.28125), List.of(EngineerPrerequisite.HORIZONS_C_LEARN, EngineerPrerequisite.HORIZONS_C_INVITE, EngineerPrerequisite.HORIZONS_C_UNLOCK)),
    JURI_ISHMAAK(Expansion.HORIZONS, Settlement.PATERS_MEMORIAL, Specialisation.UNKNOWN, new StarSystem("Giryak", 14.6875, 27.65625, 108.65625), List.of(EngineerPrerequisite.HORIZONS_C1_LEARN, EngineerPrerequisite.HORIZONS_C1_INVITE, EngineerPrerequisite.HORIZONS_C1_UNLOCK)),
    COLONEL_BRIS_DEKKER(Expansion.HORIZONS, Settlement.DEKKERS_YARD, Specialisation.UNKNOWN, new StarSystem("Sol", 0, 0, 0), List.of(EngineerPrerequisite.HORIZONS_C1A_LEARN, EngineerPrerequisite.HORIZONS_C1A_INVITE, EngineerPrerequisite.HORIZONS_C1A_UNLOCK)),
    THE_SARGE(Expansion.HORIZONS, Settlement.THE_BEACH, Specialisation.UNKNOWN, new StarSystem("Beta-3 Tucani", 32.25, -55.1875, 23.875), List.of(EngineerPrerequisite.HORIZONS_C1B_LEARN, EngineerPrerequisite.HORIZONS_C1B_INVITE, EngineerPrerequisite.HORIZONS_C1B_UNLOCK)),
    TOD_THE_BLASTER_MCQUINN(Expansion.HORIZONS, Settlement.TROPHY_CAMP, Specialisation.UNKNOWN, new StarSystem("Wolf 397", 40, 79.21875, -10.40625), List.of(EngineerPrerequisite.HORIZONS_D_LEARN, EngineerPrerequisite.HORIZONS_D_INVITE, EngineerPrerequisite.HORIZONS_D_UNLOCK)),
    PETRA_OLMANOVA(Expansion.HORIZONS, Settlement.SANCTUARY, Specialisation.UNKNOWN, new StarSystem("Asura", -9550.28125, -916.65625, 19816.1875), List.of(EngineerPrerequisite.HORIZONS_D1_LEARN, EngineerPrerequisite.HORIZONS_D1_INVITE, EngineerPrerequisite.HORIZONS_D1_UNLOCK)),
    SELENE_JEAN(Expansion.HORIZONS, Settlement.PROSPECTORS_REST, Specialisation.UNKNOWN, new StarSystem("Kuk", -21.28125, 69.09375, -16.3125), List.of(EngineerPrerequisite.HORIZONS_D2_LEARN, EngineerPrerequisite.HORIZONS_D2_INVITE, EngineerPrerequisite.HORIZONS_D2_UNLOCK)),
    DIDI_VATERMANN(Expansion.HORIZONS, Settlement.VATERMANN_LLC, Specialisation.UNKNOWN, new StarSystem("Leesti", 72.75, 48.75, 68.25), List.of(EngineerPrerequisite.HORIZONS_D2A_LEARN, EngineerPrerequisite.HORIZONS_D2A_INVITE, EngineerPrerequisite.HORIZONS_D2A_UNLOCK)),
    BILL_TURNER(Expansion.HORIZONS, Settlement.TURNER_METALLICS_INC, Specialisation.UNKNOWN, new StarSystem("Alioth", -33.65625, 72.46875, -20.65625), List.of(EngineerPrerequisite.HORIZONS_D2B_LEARN, EngineerPrerequisite.HORIZONS_D2B_INVITE, EngineerPrerequisite.HORIZONS_D2B_UNLOCK)),
    LIZ_RYDER(Expansion.HORIZONS, Settlement.DEMOLITION_UNLIMITED, Specialisation.UNKNOWN, new StarSystem("Eurybia", 51.40625, -54.40625, -30.5), List.of(EngineerPrerequisite.HORIZONS_E_LEARN, EngineerPrerequisite.HORIZONS_E_INVITE, EngineerPrerequisite.HORIZONS_E_UNLOCK)),
    ETIENNE_DORN(Expansion.HORIZONS, Settlement.KRAKENS_RETREAT, Specialisation.UNKNOWN, new StarSystem("Los", -9509.34375, -886.3125, 19820.125), List.of(EngineerPrerequisite.HORIZONS_E1_LEARN, EngineerPrerequisite.HORIZONS_E1_INVITE, EngineerPrerequisite.HORIZONS_E1_UNLOCK)),
    HERA_TANI(Expansion.HORIZONS, Settlement.THE_JETS_HOLE, Specialisation.UNKNOWN, new StarSystem("Kuwemaki", 134.65625, -226.90625, -7.8125), List.of(EngineerPrerequisite.HORIZONS_E2_LEARN, EngineerPrerequisite.HORIZONS_E2_INVITE, EngineerPrerequisite.HORIZONS_E2_UNLOCK)),
    BROO_TARQUIN(Expansion.HORIZONS, Settlement.BROOS_LEGACY, Specialisation.UNKNOWN, new StarSystem("Muang", 17.03125, -172.78125, -3.46875), List.of(EngineerPrerequisite.HORIZONS_E2A_LEARN, EngineerPrerequisite.HORIZONS_E2A_INVITE, EngineerPrerequisite.HORIZONS_E2A_UNLOCK)),
    TIANA_FORTUNE(Expansion.HORIZONS, Settlement.FORTUNES_LOSS, Specialisation.UNKNOWN, new StarSystem("Achenar", 67.5, -119.46875, 24.84375), List.of(EngineerPrerequisite.HORIZONS_E2B_LEARN, EngineerPrerequisite.HORIZONS_E2B_INVITE, EngineerPrerequisite.HORIZONS_E2B_UNLOCK)),
    REMOTE_WORKSHOP(Expansion.HORIZONS, Settlement.UNKNOWN, Specialisation.UNKNOWN, new StarSystem("UNKNOWN", 0, 0, 0), List.of()),

    UNKNOWN(Expansion.ODYSSEY, Settlement.UNKNOWN, Specialisation.UNKNOWN, new StarSystem("UNKNOWN", 0, 0, 0), List.of());

    private final Expansion expansion;
    @Getter
    private final Settlement settlement;
    @Getter
    private final Specialisation specialisation;
    @Getter
    private final StarSystem starSystem;
    @Getter
    private final List<EngineerPrerequisite> prerequisites;


    public Double getDistance(final double x, final double y, final double z) {
        if (this == REMOTE_WORKSHOP) {
            return 0D;
        } else {
            return Math.sqrt(Math.pow(this.getStarSystem().getX() - x, 2) + Math.pow(this.getStarSystem().getY() - y, 2) + Math.pow(this.getStarSystem().getZ() - z, 2));
        }
    }

    public Double getDistance(final StarSystem starSystem) {
        return getDistance(starSystem.getX(), starSystem.getY(), starSystem.getZ());
    }

    public String getLocalizationKey() {
        return "engineer.name." + this.name().toLowerCase();
    }

    public boolean isOdyssey() {
        return Expansion.ODYSSEY.equals(this.expansion);
    }

    public boolean isHorizons() {
        return Expansion.HORIZONS.equals(this.expansion) && this != REMOTE_WORKSHOP;
    }
}
























