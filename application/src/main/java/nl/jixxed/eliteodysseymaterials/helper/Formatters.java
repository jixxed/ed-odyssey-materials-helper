package nl.jixxed.eliteodysseymaterials.helper;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class Formatters {


    public static final NumberFormat NUMBER_FORMAT_3 = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_2_NO_GROUP = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_2_DUAL_DECIMAL = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_1 = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_1_CEIL = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_0 = NumberFormat.getNumberInstance();
    static {
        NUMBER_FORMAT_0.setMaximumFractionDigits(0);
        NUMBER_FORMAT_1_CEIL.setMaximumFractionDigits(1);
        NUMBER_FORMAT_1.setMaximumFractionDigits(1);
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
        NUMBER_FORMAT_1.setRoundingMode(RoundingMode.HALF_UP);
        NUMBER_FORMAT_2.setRoundingMode(RoundingMode.HALF_UP);
        NUMBER_FORMAT_2_DUAL_DECIMAL.setMaximumFractionDigits(2);
        NUMBER_FORMAT_2_DUAL_DECIMAL.setMinimumFractionDigits(2);
        NUMBER_FORMAT_2_DUAL_DECIMAL.setGroupingUsed(false);
        NUMBER_FORMAT_1_CEIL.setRoundingMode(RoundingMode.UP);
        NUMBER_FORMAT_3.setMaximumFractionDigits(3);
        NUMBER_FORMAT_3.setMinimumFractionDigits(3);
        NUMBER_FORMAT_3.setGroupingUsed(false);

        NUMBER_FORMAT_2_NO_GROUP.setMaximumFractionDigits(2);
        NUMBER_FORMAT_2_NO_GROUP.setMinimumFractionDigits(2);
        NUMBER_FORMAT_2_NO_GROUP.setGroupingUsed(false);
    }
}
