package com.miniasaaslw.utils

class CpfCnpjUtils {

    public static Boolean validateCpf(String cpf){
        int[] numbers = new int[11]

        if(cpf == null || cpf.isEmpty()){
            return false
        }

        for(int i = 0; i < 11; i++){
            numbers[i] = Integer.parseInt(cpf.substring(i, i+1))
        }

        if(!validateCpfLength(cpf)){
            return false
        }

        if(!validateEqualNumbers(cpf)){
            return false
        }

        if(!validateCpfFirstDigit(numbers)){
            return false
        }

        if(!validateCpfSecondDigit(numbers)){
            return false
        }


        return true
    }

    private static Boolean validateCpfLength(String cpf){
        return cpf.length() == 11
    }

    private static Boolean validateEqualNumbers(String cpf){
        return !cpf.matches("(\\d)\\1{10}")
    }

    private static Boolean validateCpfFirstDigit(int[] numbers) {
        int firstDigit
        int sum = 0

        for(int i = 0, j = 10; i < 9; i++, j--){
            sum+= numbers[i] * j
        }

        if(sum % 11 < 2){
            firstDigit = 0
        } else {
            firstDigit = 11 - (sum % 11)
        }

        return firstDigit == numbers[9]
    }

    private static Boolean validateCpfSecondDigit(int[] numbers){
        int secondDigit
        int sum = 0

        for(int i = 0, j = 11; i < 10; i++, j--){
            sum+= numbers[i] * j
        }

        if(sum % 11 < 2){
            secondDigit = 0
        } else {
            secondDigit = 11 - (sum % 11)
        }

        return secondDigit == numbers[10]
    }

    public static boolean validateCnpj(String rawCnpj) {
        if (rawCnpj == null || rawCnpj.length() != 14) return false;

        int[] digits = rawCnpj.chars().map(Character::getNumericValue).toArray();

        return validateCnpjDigit(digits, 12, 5) && validateCnpjDigit(digits, 13, 6);
    }

    private static boolean validateCnpjDigit(int[] digits, int index, int initialWeight) {
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
