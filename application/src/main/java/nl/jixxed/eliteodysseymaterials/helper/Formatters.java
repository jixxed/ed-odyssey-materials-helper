package nl.jixxed.eliteodysseymaterials.helper;

import java.text.NumberFormat;

public class Formatters {


    public static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_1 = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_0 = NumberFormat.getNumberInstance();
    static {
        NUMBER_FORMAT_0.setMaximumFractionDigits(0);
        NUMBER_FORMAT_1.setMaximumFractionDigits(1);
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
        NUMBER_FORMAT_2.setMinimumFractionDigits(2);
        NUMBER_FORMAT_2.setGroupingUsed(false);
    }
}
