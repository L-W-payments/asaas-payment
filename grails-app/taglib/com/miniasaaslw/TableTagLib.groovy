package com.miniasaaslw

import groovy.json.JsonOutput

class TableTagLib {
    static namespace = "tableTagLib"

    def buildAtlasTable = { attrs ->
        out << attrs.attribute + "='" + JsonOutput.prettyPrint(JsonOutput.toJson(attrs.value)) + "'"
    }
}
