package nl.jixxed.eliteodysseymaterials.service.hge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FixedSizeCircularReferenceTest {

    @Test
    void add() {
        FixedSizeCircularReference<Integer> fixedSizeCircularReference = new FixedSizeCircularReference<>(5);
        fixedSizeCircularReference.add(1);
        fixedSizeCircularReference.add(2);
        fixedSizeCircularReference.add(3);
        fixedSizeCircularReference.add(4);
        fixedSizeCircularReference.add(5);
        fixedSizeCircularReference.add(6);

        //assert that list contains 2,3,4,5,6
        Assertions.assertEquals(List.of(2,3,4,5,6), fixedSizeCircularReference.asList());
    }
    @Test
    void addDouble() {
        FixedSizeCircularReference<Integer> fixedSizeCircularReference = new FixedSizeCircularReference<>(5);
        fixedSizeCircularReference.add(1);
        fixedSizeCircularReference.add(2);
        fixedSizeCircularReference.add(3);
        fixedSizeCircularReference.add(4);
        fixedSizeCircularReference.add(5);
        fixedSizeCircularReference.add(2);

        //assert that list contains 2,3,4,5,6
        Assertions.assertEquals(List.of(1,3,4,5,2), fixedSizeCircularReference.asList());
    }

    @Test
    void addDoubleSmaller() {
        FixedSizeCircularReference<Integer> fixedSizeCircularReference = new FixedSizeCircularReference<>(5);
        fixedSizeCircularReference.add(1);
        fixedSizeCircularReference.add(2);
        fixedSizeCircularReference.add(3);
        fixedSizeCircularReference.add(4);
        fixedSizeCircularReference.add(2);

        //assert that list contains 2,3,4,5,6
        Assertions.assertEquals(List.of(1,3,4,2), fixedSizeCircularReference.asList());
    }
}