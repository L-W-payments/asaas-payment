package com.miniasaaslw.utils;

public class CpfCnpjUtils {

    public static boolean isValidCnpj(String rawCnpj) {
        if (rawCnpj == null || rawCnpj.length() != 14) return false;

        int[] digits = rawCnpj.chars().map(Character::getNumericValue).toArray();

        return isValidCnpjDigit(digits, 12, 5) && isValidCnpjDigit(digits, 13, 6);
    }

    private static boolean isValidCnpjDigit(int[] digits, int index, int initialWeight) {
        int sum = 0;
        int weight = initialWeight;
        for (int i = 0; i < index; i++) {
            sum += digits[i] * weight;
            weight = weight > 2 ? weight - 1 : 9;
        }

        int remainder = sum % 11;
        int calculatedDigit = remainder < 2 ? 0 : 11 - remainder;

        return digits[index] == calculatedDigit;
    }
}
