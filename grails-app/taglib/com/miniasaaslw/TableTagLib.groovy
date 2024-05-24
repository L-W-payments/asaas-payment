package com.miniasaaslw

import groovy.json.JsonOutput

class TableTagLib {
    static namespace = "tableTagLib"

    def buildAtlasComplexAttribute = { attrs ->
        out << attrs.attribute + "='" + JsonOutput.prettyPrint(JsonOutput.toJson(attrs.value)) + "'"
    }
}
