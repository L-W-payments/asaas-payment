package com.miniasaaslw.utils.entity.enums

enum PersonType {
    NATURAL,
    LEGAL

    public static List<PersonType> getPersonTypeList() {
        return [PersonType.NATURAL, PersonType.LEGAL]
    }
}