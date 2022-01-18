package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.SystemEconomy;
import nl.jixxed.eliteodysseymaterials.enums.SystemGovernment;
import nl.jixxed.eliteodysseymaterials.enums.SystemSecurity;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class StarSystem {
    private final String name;
    private SystemEconomy primaryEconomy = SystemEconomy.UNKNOWN;
    private SystemEconomy secondaryEconomy = SystemEconomy.UNKNOWN;
    private SystemGovernment government = SystemGovernment.UNKNOWN;
    private SystemSecurity security = SystemSecurity.UNKNOWN;
    private String state = "";
    private final double x;
    private final double y;
    private final double z;
}
