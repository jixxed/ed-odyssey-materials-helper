package nl.jixxed.eliteodysseymaterials.helper;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class MoneyFormatter {

    public static String formatMoney(BigInteger value) {
        return formatMoney(value, 2);
    }

    /**
     * Converts a BigInteger money value to a compact format with K, M, B, T, etc. suffixes
     *
     * @param value     The money value to format
     * @param precision Number of decimal places to show (default: 2)
     * @return Formatted string with appropriate suffix
     */
    public static String formatMoney(BigInteger value, int precision) {
        if (value == null) {
            return "0";
        }

        // Handle zero and negative values
        if (value.equals(BigInteger.ZERO)) {
            return "0";
        }

        String prefix = "";
        if (value.signum() < 0) {
            prefix = "-";
            value = value.abs();
        }

        String suffix = "";
        BigDecimal decimalValue = new BigDecimal(value);

        // Define thresholds and corresponding suffixes
        // Using BigDecimal for precise threshold comparisons
        BigDecimal thousand = new BigDecimal("1000");
        BigDecimal million = new BigDecimal("1000000");
        BigDecimal billion = new BigDecimal("1000000000");
        BigDecimal trillion = new BigDecimal("1000000000000");
        BigDecimal quadrillion = new BigDecimal("1000000000000000");
        BigDecimal quintillion = new BigDecimal("1000000000000000000");

        if (value.compareTo(BigInteger.ONE) < 0) {
            // For values less than 1 (if using BigDecimal input)
            return prefix + "0";
        } else if (decimalValue.compareTo(thousand) < 0) {
            // Less than 1,000
            return prefix + Formatters.NUMBER_FORMAT_2.format(value);
        } else if (decimalValue.compareTo(million) < 0) {
            // Thousands: 1,000 - 999,999
            suffix = getSuffix(Suffix.K);
            decimalValue = decimalValue.divide(thousand, precision + 2, RoundingMode.HALF_UP);
        } else if (decimalValue.compareTo(billion) < 0) {
            // Millions: 1,000,000 - 999,999,999
            suffix = getSuffix(Suffix.M);
            decimalValue = decimalValue.divide(million, precision + 2, RoundingMode.HALF_UP);
        } else if (decimalValue.compareTo(trillion) < 0) {
            // Billions: 1,000,000,000 - 999,999,999,999
            suffix = getSuffix(Suffix.B);
            decimalValue = decimalValue.divide(billion, precision + 2, RoundingMode.HALF_UP);
        } else if (decimalValue.compareTo(quadrillion) < 0) {
            // Trillions: 1,000,000,000,000 - 999,999,999,999,999
            suffix = getSuffix(Suffix.T);
            decimalValue = decimalValue.divide(trillion, precision + 2, RoundingMode.HALF_UP);
        } else if (decimalValue.compareTo(quintillion) < 0) {
            // Quadrillions: 1,000,000,000,000,000 - 999,999,999,999,999,999
            suffix = getSuffix(Suffix.Q);
            decimalValue = decimalValue.divide(quadrillion, precision + 2, RoundingMode.HALF_UP);
        } else {
            // Quintillions and beyond
            suffix = getSuffix(Suffix.QN);
            decimalValue = decimalValue.divide(quintillion, precision + 2, RoundingMode.HALF_UP);
        }

        // Format with the specified precision
        decimalValue = decimalValue.setScale(precision, RoundingMode.HALF_UP);

        // Remove trailing zeros and unnecessary decimal point
        String result = Formatters.NUMBER_FORMAT_2.format(decimalValue.stripTrailingZeros());

        return prefix + result + suffix;
    }

    private static String getSuffix(Suffix suffix) {
        return LocaleService.getLocalizedStringForCurrentLocale(suffix.getLocalizationKey());
    }

    private enum Suffix {
        K, M, B, T, Q, QN;

        public String getLocalizationKey() {
            return "number.suffix." + this.name().toLowerCase();
        }
    }
}
