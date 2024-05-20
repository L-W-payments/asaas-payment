package com.miniasaaslw.utils

class StateUtils {

    private static final List<String> statesFromBrasil = [
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT",
        "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO",
        "RR", "SC", "SP", "SE", "TO"
    ]

    public static boolean isStateValid(String state) {
        if (state == null) return false

        if (state.length() != 2) return false

        if (!statesFromBrasil.contains(state.toUpperCase())) return false

        return true
    }
}
