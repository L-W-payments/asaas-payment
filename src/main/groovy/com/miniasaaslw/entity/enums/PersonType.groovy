package com.miniasaaslw.entity.enums

enum PersonType {

    NATURAL,
    LEGAL

    public static List<PersonType> getPersonTypeList() {
        return [PersonType.NATURAL, PersonType.LEGAL]
    }

    public Boolean isNatural() {
        return this == NATURAL
    }

    public Boolean isLegal() {
        return this == LEGAL
    }
}