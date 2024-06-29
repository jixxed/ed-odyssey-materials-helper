package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.SystemAllegiance;
import nl.jixxed.eliteodysseymaterials.enums.SystemEconomy;
import nl.jixxed.eliteodysseymaterials.enums.SystemGovernment;
import nl.jixxed.eliteodysseymaterials.enums.SystemSecurity;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class StarSystem {
    public static final StarSystem SOL = new StarSystem("Sol", 0, 0, 0);
    public static final StarSystem COLONIA = new StarSystem("Colonia", -9530.5, -910.28125, 19808.125);
    private final String name;
    private SystemEconomy primaryEconomy = SystemEconomy.UNKNOWN;
    private SystemEconomy secondaryEconomy = SystemEconomy.UNKNOWN;
    private SystemGovernment government = SystemGovernment.UNKNOWN;
    private SystemSecurity security = SystemSecurity.UNKNOWN;
    @Setter
    private SystemAllegiance allegiance = SystemAllegiance.UNKNOWN;
    private String state = "";
    private final double x;
    private final double y;
    private final double z;
}
