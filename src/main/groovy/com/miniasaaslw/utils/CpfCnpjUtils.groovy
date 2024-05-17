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

    public static String parseCpf(String cpf){
        return cpf.replaceAll("[.-]", "")
    }
}
