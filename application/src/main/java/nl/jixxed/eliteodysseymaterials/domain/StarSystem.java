package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.math.BigInteger;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class StarSystem {
    public static final StarSystem SOL = new StarSystem("Sol", 0, 0, 0);
    public static final StarSystem COLONIA = new StarSystem("Colonia", -9530.5, -910.28125, 19808.125);
    private final String name;
    private Economy primaryEconomy = Economy.UNKNOWN;
    private Economy secondaryEconomy = Economy.UNKNOWN;
    private Government government = Government.UNKNOWN;
    private Security security = Security.UNKNOWN;
    @Setter
    private Allegiance allegiance = Allegiance.NONE;
    private BigInteger population = BigInteger.ZERO;
    private State state = State.NONE;
    private final double x;
    private final double y;
    private final double z;
}
