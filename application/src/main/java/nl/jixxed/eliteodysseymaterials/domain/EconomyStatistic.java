package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EconomyStatistic {
    private String economy;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer amount;
}
