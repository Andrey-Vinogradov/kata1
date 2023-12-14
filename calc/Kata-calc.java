/* Задание
Создай консольное приложение "Калькулятор" на языке программирования java. Приложение должно читать из консоли введенные пользователем строки, числа, арифметические операции проводимые между ними и выводить в консоль результат их выполнения.
Реализуй класс Main с методом public static String calc(String input). Метод должен принимать строку с арифметическим выражением между двумя числами и возвращать строку с результатом их выполнения. Ты можешь добавлять свои импорты, классы и методы. Добавленные классы не должны иметь модификаторы доступа (public или другие).
Требования:﻿

    -Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b. Данные передаются в одну строку (смотри пример)! Решения, в которых каждое число и арифмитеческая операция передаются с новой строки считаются неверными.
    -Калькулятор умеет работать как с арабскими (1,2,3,4,5...), так и с римскими (I,II,III,IV,V...) числами.
    -Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми.
    -Калькулятор умеет работать только с целыми числами.
    -Калькулятор умеет работать только с арабскими или римскими цифрами одновременно, при вводе пользователем строки вроде 3 + II калькулятор должен выбросить исключение и прекратить свою работу.
    -При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.
    -При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.
    -При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение выбрасывает исключение и завершает свою работу.
    -Результатом операции деления является целое число, остаток отбрасывается.
    -Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль. Результатом работы калькулятора с римскими числами могут быть только положительные числа, если результат работы меньше единицы, выбрасывается исключение
Пример работы программы:

Input:
1 + 2

Output:
3

Input:
VI / III

Output:
II

Input:
I - II

Output:
throws Exception //т.к. в римской системе нет отрицательных чисел

Input:
I + 1

Output:
throws Exception //т.к. используются одновременно разные системы счисления

Input:
1

Output:
throws Exception //т.к. строка не является математической операцией

Input:
1 + 2 + 3

Output:
throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (например, 1 + 2):");

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

    public static String calc(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new Exception("Invalid input: " + input);
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        boolean isArabic = isArabic(operand1) && isArabic(operand2); // можно переделать как я написал
        boolean isRoman = isRoman(operand1) && isRoman(operand2);

        if (!isValidNumber(operand1) || !isValidNumber(operand2)) {
            throw new Exception("Invalid numbers: " + input);
        }

        if (isArabic && isRoman || !isArabic && !isRoman) {
            throw new Exception("Inconsistent numeral systems: " + input);
        }

        int num1 = isArabic ? Integer.parseInt(operand1) : romanToArabic(operand1);
        int num2 = isArabic ? Integer.parseInt(operand2) : romanToArabic(operand2);

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

    private static boolean isArabic(String input) {
        try {
            int num = Integer.parseInt(input);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRoman(String input) {
        String romanPattern = "^[IVXLCDM]+$";
        return input.matches(romanPattern);
    }

    private static boolean isValidNumber(String input) {
        if (isArabic(input)) {
            return true;
        } else if (isRoman(input)) {
            int arabicValue = romanToArabic(input);
            return arabicValue > 0;  // Разрешаем римские числа любого значения
        } else {
            return false;
        }
    }

    private static int romanToArabic(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>(); // надо вынести это, что бы только один раз создавалась коллекция
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    private static String arabicToRoman(int num) { // надо все модификаторы доступа удалить , по условию
        if (num < 1) {
            throw new IllegalArgumentException("Cannot convert non-positive number to Roman numeral");
        }

        StringBuilder romanResult = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"}; // тут так же можно было бы вынести в отдельную HashMap

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                romanResult.append(numerals[i]);
                num -= values[i];
            }
        }

        return romanResult.toString();
    }
}

