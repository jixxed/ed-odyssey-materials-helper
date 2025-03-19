package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.Power;

import java.util.Optional;

@AllArgsConstructor
@Data
public class PowerplayEvent implements Event {
    private final Power power;
    private Long merits;
    private Long rank;

    public PowerplayEvent(Long merits, Power power) {
        this.merits = merits;
        this.power = power;
    }

    public PowerplayEvent(Power power, Long rank) {
        this.power = power;
        this.rank = rank;
    }

    public Optional<Long> getMerits() {
        return Optional.ofNullable(merits);
    }

    public Optional<Long> getRank() {
        return Optional.ofNullable(rank);
    }
}
