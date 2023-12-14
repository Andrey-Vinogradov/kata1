import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {

    static Map<Character, Integer> romanToArabicMap = new HashMap<>(
            Map.of('I', 1,
                   'V', 5,
                   'X', 10,
                   'L', 50,
                   'C', 100,
                   'D', 500,
                   'M', 1000)
    );


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (например, 1 + 2 или II * V):");

        try {
            String input = scanner.nextLine();
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    static String calc(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new Exception("Invalid input: " + input);
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        int num1 = 0;
        int num2 = 0;
        boolean isArabic = false;

        if (isArabic(operand1) && isArabic(operand2)) {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
            isArabic = true;
        } else if (isRoman(operand1) && isRoman(operand2)) {
            num1 = romanToArabic(operand1);
            num2 = romanToArabic(operand2);
        } else {
            throw new Exception("Inconsistent numeral systems: " + input);
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Division by zero: " + input);
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Invalid operator: " + input);
        }

        // Возвращаем результат в соответствующем формате
        return isArabic ? String.valueOf(result) : arabicToRoman(result);
    }

    static boolean isArabic(String input) {
        try {
            int num = Integer.parseInt(input);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isRoman(String input) {
        String romanPattern = "^[IVXLCDM]+$";
        if (input.matches(romanPattern)) {
            int arabicValue = romanToArabic(input);
            return arabicValue > 0;  // Разрешаем римские числа любого значения
        }
        return false;
    }


    static int romanToArabic(String roman) {

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanToArabicMap.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    static String arabicToRoman(int num) {
        if (num < 1) {
            throw new IllegalArgumentException("Cannot convert non-positive number to Roman numeral");
        }

        StringBuilder romanResult = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"}; 
	// тут так же помжно было бы вынести в отдельную HashMap но это как дополнение

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                romanResult.append(numerals[i]);
                num -= values[i];
            }
        }

        return romanResult.toString();
    }
}
