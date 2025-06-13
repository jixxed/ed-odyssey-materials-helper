package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Trailblazer;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrailblazerService {

    public static List<Trailblazer> getAllTrailblazers() {

        return List.of(
                new Trailblazer(new StarSystem("Bletii", 99.34375, 6.12500, -166.71875), "Trailblazer Wish", 714D, 0D),
                new Trailblazer(new StarSystem("34 Omicron Cephei", -183.15625, 24.65625, -84.25000), "Trailblazer Star", 635D, 0D),
                new Trailblazer(new StarSystem("Orgen", 165.46875, -4.84375, 79.31250), "Trailblazer Song", 7D, 0D),
                new Trailblazer(new StarSystem("Klikis", 38.28125, 74.68750, 163.71875), "Trailblazer Promise", 951D, 0D),
                new Trailblazer(new StarSystem("Ngobe", -7.84375, -124.09375, -164.09375), "Trailblazer Faith", 418D, 0D),
                new Trailblazer(new StarSystem("HIP 90578", -81.75000, 7.78125, 161.06250), "Trailblazer Dream", 18D, 0D),
                new Trailblazer(new StarSystem("Minerva", -22.06250, -64.93750, -15.12500), "Trailblazer Echo (Limited)", 1156D, 0D)
        );
    }
}
