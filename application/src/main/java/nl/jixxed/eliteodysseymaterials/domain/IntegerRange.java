package nl.jixxed.eliteodysseymaterials.domain;

public record IntegerRange(Integer min, Integer max){

    public static IntegerRange sum(IntegerRange rangeA, IntegerRange rangeB){
        return new IntegerRange(rangeA.min() + rangeB.min(), rangeA.max() + rangeB.max());
    }
}
