package com.sondahum.mamas.common.util

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class JUtils {

    static Map<String, Object> objToMap(Object obj) {
        if (!obj) return null
        String jsonString = JsonOutput.toJson(obj)
        Map<String, Object> map = new JsonSlurper().parseText(jsonString) as Map<String, Object>

        return map
    }

    static Object jsonToObject(String jsonString){
        try{
            if (jsonString)
                return new JsonSlurper().parseText(jsonString)
        }catch(Exception e){
            e.printStackTrace()
            return jsonString
        }
        return null
    }

}
