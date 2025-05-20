package nl.jixxed.eliteodysseymaterials.enums;

public enum PassengerCabinType {
    ECONOMY, BUSINESS, FIRSTCLASS, LUXURY;


    public Integer getMinSize() {
        return switch (this) {
            case ECONOMY -> 2;
            case BUSINESS -> 3;
            case FIRSTCLASS -> 4;
            case LUXURY -> 5;
        };
    }

    public int getMaxSize() {
        return 6;
    }

    public int getPassengerCount(int moduleSize) {
        return switch (this) {
            case ECONOMY -> {
                if (moduleSize == 2) {
                    yield 2;
                } else if (moduleSize == 3) {
                    yield 4;
                } else if (moduleSize == 4) {
                    yield 8;
                } else if (moduleSize == 5) {
                    yield 16;
                } else if (moduleSize >= 6) {
                    yield 32;
                } else {
                    yield 0;
                }
            }
            case BUSINESS -> {
                if (moduleSize == 3) {
                    yield 3;
                } else if (moduleSize == 4) {
                    yield 6;
                } else if (moduleSize == 5) {
                    yield 10;
                } else if (moduleSize >= 6) {
                    yield 16;
                } else {
                    yield 0;
                }
            }
            case FIRSTCLASS -> {
                if (moduleSize == 4) {
                    yield 3;
                } else if (moduleSize == 5) {
                    yield 6;
                } else if (moduleSize >= 6) {
                    yield 12;
                } else {
                    yield 0;
                }
            }
            case LUXURY -> {
                if (moduleSize == 5) {
                    yield 4;
                } else if (moduleSize >= 6) {
                    yield 8;
                } else {
                    yield 0;
                }
            }
        };
    }
}
