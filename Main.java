import java.io.IOException;
import java.util.Scanner;

public class Main {
    static final int ALL_NUMBERS_IN_TRUE_INPUT = 2;
    public static void main(String[] args) {
        System.out.println("""
                            Программа калькулятор
                Необходимо ввести арифметическое выражение.
                Два числа должны быть целыми: от 1 до 10 каждое.
                Запись чисел должна быть осуществлена в одну строку.
                Оба числа должны быть либо арабскими, либо римскими. 2 + VII - не допустимый формат.
                В выражении допускаются следующие операторы: +, -, *, /.
                При отступлении от этих правил работа программы будет прекращена.
                """);

        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter a binary expression: ");
        String input = sc.nextLine();
        System.out.println(calc(input));
    }

    static String[] stringProcessing(String str) {
        String[] charsFromStr = str.split("[+*/-]");
        for (int i = 0; i < charsFromStr.length; i++)
            charsFromStr[i] = charsFromStr[i].trim();
        return charsFromStr;
    }

    static int isTrueBinaryOperator(String str) {
        char[] operators = {'-', '+', '*', '/'};
        char[] ch = str.trim().toCharArray();
        var counter = 0;
        var index = 0;
        for (char operator : operators) {
            for (var j = 0; j < ch.length; j++) {
                if (ch[j] == operator) {
                    if (++counter == 1)
                        index = j;
                }
            }
        }
        if (counter < 1)
            index = -1;
        return index;
    }

    static boolean isIntNumber(String input) {
        for(char chFromInput : input.toCharArray())
            if (!Character.isDigit(chFromInput))
                return false;
        return true;
    }

    static boolean isRomanNumber(String input) {
        for(String romanDigit : ConversionNumbers.ROMAN_DIGIT_UNITS)
            if (input.equals(romanDigit))
                return true;
        return false;
    }

    static String showRomeResult(int number) {
        int lowRomanNumber = 1;
        int numberTens = number / 10 * 10;
        int numberUnits = number % 10;

        if(number >= lowRomanNumber) {
            if (numberUnits == 0)
                return ConversionNumbers.conversionIntToRoman.get(numberTens);
            else if (numberTens == 0)
                return ConversionNumbers.conversionIntToRoman.get(numberUnits);
            else
                return ConversionNumbers.conversionIntToRoman.get(numberTens) +
                        ConversionNumbers.conversionIntToRoman.get(numberUnits);
        } else {
            try {
                throw new ArithmeticException();
            } catch (ArithmeticException e){
                return "ArithmeticException: in the Roman system there are no numbers less than 1!";
            }
        }
    }

    public static String calc(String input) {
        new ConversionNumbers();
        try {
            if (input.isEmpty()) throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e) {
            return "IllegalArgumentException: String is empty!";
        }
        try {
            if(isTrueBinaryOperator(input) == -1) throw new IOException();
        }
        catch(IOException e) {
            return "IllegalArgumentException: the format of the mathematical operation\n"
                    + "does not satisfy the task - two operands and one operator (+, -, /, *)";
        }

        char mathOperator = input.charAt(isTrueBinaryOperator(input));
        String[] arrStrFromInput = stringProcessing(input);
        try {
            if((!isIntNumber(arrStrFromInput[0]) && !isRomanNumber(arrStrFromInput[0])) ||
                    (!isIntNumber(arrStrFromInput[1]) && !isRomanNumber(arrStrFromInput[1]))) {
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e) {
            return "IllegalArgumentException: among the operands there are non-numbers";
        }
        try {
            if(arrStrFromInput.length != ALL_NUMBERS_IN_TRUE_INPUT) {
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e) {
            return "IllegalArgumentException: the format of the mathematical operation\n"
                    + "does not satisfy the task - two operands and one operator (+, -, /, *)";
        }

        int result;
        int firstNumber = 0;
        int secondNumber = 0;
        try {
            if((isIntNumber(arrStrFromInput[0]) && isRomanNumber(arrStrFromInput[1])) ||
                    (isIntNumber(arrStrFromInput[1]) && isRomanNumber(arrStrFromInput[0])))
                throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e) {
            return "IllegalArgumentException: different number systems are used simultaneously!!!";
        }

        for(var i = 0; i < arrStrFromInput.length; i++) {
            if(i == 0) {
                if (isIntNumber(arrStrFromInput[i]))
                    firstNumber = Integer.parseInt(arrStrFromInput[i]);
                else if (isRomanNumber(arrStrFromInput[i]))
                    firstNumber = ConversionNumbers.conversionRomanToInt.get(arrStrFromInput[i]);
            } else {
                if (isIntNumber(arrStrFromInput[i]))
                    secondNumber = Integer.parseInt(arrStrFromInput[i]);
                else if (isRomanNumber(arrStrFromInput[i]))
                    secondNumber = ConversionNumbers.conversionRomanToInt.get(arrStrFromInput[i]);
            }
        }
        try {
            if((firstNumber < 1 || secondNumber < 1 || firstNumber > 10 || secondNumber > 10)) {
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException e) {
            return "IllegalArgumentException: operands must be from 1 to 10!";
        }

        result = switch (mathOperator) {
            case '+' -> firstNumber + secondNumber;
            case '-' -> firstNumber - secondNumber;
            case '*' -> firstNumber * secondNumber;
            default  -> firstNumber / secondNumber;
        };

        if(isIntNumber(arrStrFromInput[0]))
            return String.valueOf(result);
        else
            return showRomeResult(result);
    }
}