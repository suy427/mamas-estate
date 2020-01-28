package com.sondahum.mamas

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.junit.Test

class GeneralTest {

    @Test
    void test() {
        SearchOption test = new SearchOption(
                query: 'adfa',
                startDate: new Date(),
                endDate: new Date()
        )

        String jstring = JsonOutput.toJson(test)
        def ga = new JsonSlurper().parseText(jstring)

        //TODO 이게 안된다 Baeldung에는 된다고 되있었는디...ㅎㅎ
        def ha = new JsonSlurper().parseText(jstring) as SearchOption
        def haha = ga as SearchOption

        String ss = ha.query

        println jstring
        println ga

        println ss
        println ha
        println haha
    }

    class SearchOption {
        String query
        Date startDate
        Date endDate
    }

}