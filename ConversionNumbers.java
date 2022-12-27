import java.util.HashMap;
class ConversionNumbers {
    int MAX_NUMBER = 100;
    int STEP = 10;
    public static final String[] ROMAN_DIGIT_UNITS = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    public static final String[] ROMAN_DIGIT_TENS = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};

    public static HashMap<String, Integer> conversionRomanToInt = new HashMap<>();
    public static HashMap<Integer, String> conversionIntToRoman = new HashMap<>();

    ConversionNumbers() {

        for (var i = 0; i < ROMAN_DIGIT_UNITS.length; i++) {
            conversionRomanToInt.put(ROMAN_DIGIT_UNITS[i], i + 1);
        }
        for (var i = 0; i < ROMAN_DIGIT_UNITS.length - 1; i++) {
            conversionIntToRoman.put(i + 1, ROMAN_DIGIT_UNITS[i]);
        }
        for (var i = 10; i <= MAX_NUMBER; i += STEP) {
            conversionIntToRoman.put(i, ROMAN_DIGIT_TENS[(i - STEP) / STEP]);
        }
    }
}
