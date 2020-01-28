package com.sondahum.mamas.common.util

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class JUtils {

    Map<String, Object> objToMap(Object obj) {
        return (obj)? new JsonSlurper().parseText(JsonOutput.toJson(obj)) as Map<String, Object> : null
    }

}
