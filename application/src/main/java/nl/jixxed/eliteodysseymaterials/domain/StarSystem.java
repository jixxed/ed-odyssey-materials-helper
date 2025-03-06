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
    @Setter
    private Economy primaryEconomy = Economy.UNKNOWN;
    @Setter
    private Economy secondaryEconomy = Economy.UNKNOWN;
    @Setter
    private Government government = Government.UNKNOWN;
    @Setter
    private Security security = Security.UNKNOWN;
    @Setter
    private Allegiance allegiance = Allegiance.NONE;
    @Setter
    private BigInteger population = BigInteger.ZERO;
    @Setter
    private State state = State.NONE;
    private final double x;
    private final double y;
    private final double z;


    public Double getDistance(final double x, final double y, final double z) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
    }

    public Double getDistance(StarSystem starSystem) {
        return getDistance(starSystem.getX(), starSystem.getY(), starSystem.getZ());
    }
}
