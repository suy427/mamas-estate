package com.sondahum.mamas.tester

import groovy.json.JsonOutput
import org.junit.AfterClass
import org.junit.BeforeClass;
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

//import static com.sondahum.mamas.common.util.JUtils.jsonToObject
//import static com.sondahum.mamas.common.util.JUtils.objToMap

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


//@AutoConfigureMockMvc
abstract class MockTestRequestHandler /*extends AbstractTestHelper*/{

//    protected Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    MockMvc mvc
//
//    static MultiValueMap<String, String> generateParams(Map parameters) {
//        Map multiValueMap = new LinkedMultiValueMap<String, String>()
//
//        if (parameters){
//            Map<String, List<String>> listValueMap = generateConcatKeyParams(parameters)
//            multiValueMap.putAll(listValueMap)
//        }
//        return multiValueMap
//    }
//
//    static Map<String, List<String>> generateConcatKeyParams(Map parameters){
//        Map<String, List<String>> concatKeyListValueMap = [:]
//        return generateConcatKeyParams(parameters, '', concatKeyListValueMap)
//    }
//
//    static Map<String, List<String>> generateConcatKeyParams(Map parameters, String prevKey, Map<String, List<String>> concatKeyListValueMap){
//        parameters.keySet().each{ String key ->
//            String concatKey = (prevKey) ? prevKey + '.' + key : key
//            def value = parameters[key]
//            if (value instanceof Map)
//                generateConcatKeyParams(value, concatKey, concatKeyListValueMap)
//            else{
//                List<String> valueList = (value instanceof List) ? value : [value]
//                concatKeyListValueMap[concatKey] = valueList.collect{ String.valueOf(it) }
//            }
//        }
//        return concatKeyListValueMap
//    }
//
//
//    /**************************************************
//     *
//     * Values
//     *
//     **************************************************/
//    class RequestValuesHandler {
//        RequestValuesHandler() {
//        }
//
//        RequestValuesHandler(RequestValues... values) {
//            for (int i = 0; i < values.length; i++) {
//                RequestValues valueObject = values[i]
//                if (valueObject instanceof PathValues)
//                    this.pathValues = valueObject
//                else if (valueObject instanceof ParameterValues)
//                    this.paramValues = valueObject
//                else if (valueObject instanceof MultipartValues)
//                    this.multipartValues = valueObject
//                else if (valueObject instanceof BodyValues)
//                    this.bodyValues = valueObject
//                else if (valueObject instanceof HeaderValues)
//                    this.headerValues = valueObject
//                else if (valueObject instanceof ResponseHandler)
//                    this.responseHandler = valueObject
//            }
//        }
//        PathValues pathValues
//        ParameterValues paramValues
//        MultipartValues multipartValues
//        BodyValues bodyValues
//        HeaderValues headerValues
//        ResponseHandler responseHandler
//
//        Object[] getPathArray() { return (this.pathValues?.values ?: []).toArray() }
//
//        MultiValueMap<String, String> getParams() { return generateParams(this.paramValues?.values ?: [:]) }
//
//        String getBodyString() { return JsonOutput.toJson(objToMap(this.bodyValues?.values ?: [:])) }
//
//        List<MockMultipartFile> getMultipartFileList() { return this.multipartValues?.values ?: [] }
//
//        MultiValueMap<String, String> getHeaders() { return generateParams(this.headerValues?.values ?: [:]) }
//
//        Closure getClosureForResponse() { return responseHandler?.closureForResponse }
//    }
//
//    class RequestValues {}
//
//    class PathValues extends RequestValues {
//        List<Object> values
//    }
//
//    class ParameterValues extends RequestValues {
//        Object values
//    }
//
//    class MultipartValues extends RequestValues {
//        List<Object> values
//    }
//
//    class BodyValues extends RequestValues {
//        Object values
//    }
//
//    class HeaderValues extends RequestValues {
//        Object values
//    }
//
//    class ResponseHandler extends RequestValues {
//        Closure closureForResponse
//    }
//
//    ParameterValues parameterValues(Map<String, Object> valueMap) {
//        ParameterValues o = new ParameterValues()
//        o.values = valueMap
//        return o
//    }
//
//    BodyValues bodyValues(Map<String, Object> valueMap) {
//        BodyValues o = new BodyValues()
//        o.values = valueMap
//        return o
//    }
//
//    HeaderValues headerValues(Map<String, Object> valueMap) {
//        HeaderValues o = new HeaderValues()
//        o.values = valueMap
//        return o
//    }
//
//    PathValues pathValues(Object... values) {
//        PathValues o = new PathValues()
//        o.values = Arrays.asList(values)
//        return o
//    }
//
//    MultipartValues multipartValues(Object... values) {
//        MultipartValues o = new MultipartValues()
//        o.values = Arrays.asList(values)
//        return o
//    }
//
//    ResponseHandler responseHandler(Closure closureForResponse) {
//        ResponseHandler o = new ResponseHandler()
//        o.closureForResponse = closureForResponse
//        return o
//    }
//
//
//    /**************************************************
//     *
//     * GET
//     *
//     **************************************************/
//    Object requestGet(String url) {
//        return requestGet(url, null, new RequestValuesHandler())
//    }
//
//    Object requestGet(String url, List<Object> pathParameterList) {
//        return requestGet(url, null, new RequestValuesHandler(pathValues(pathParameterList)))
//    }
//
//    Object requestGet(String url, Map<String, Object> parameterMap) {
//        return requestGet(url, null, new RequestValuesHandler(parameterValues(parameterMap)))
//    }
//
//    Object requestGet(String url, List<Object> pathParameterList, Map<String, Object> parameterMap) {
//        return requestGet(url, null, new RequestValuesHandler(pathValues(pathParameterList), parameterValues(parameterMap)))
//    }
//
//    Object requestGet(String url, RequestValues... values) {
//        return requestGet(url, null, new RequestValuesHandler(values))
//    }
//
//    Object requestGet(String url, ResultHandler resultHandlerForDocument) {
//        return requestGet(url, resultHandlerForDocument, new RequestValuesHandler())
//    }
//
//    Object requestGet(String url, ResultHandler resultHandlerForDocument, List<Object> pathParameterList) {
//        return requestGet(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList)))
//    }
//
//    Object requestGet(String url, ResultHandler resultHandlerForDocument, Map<String, Object> parameterMap) {
//        return requestGet(url, resultHandlerForDocument, new RequestValuesHandler(parameterValues(parameterMap)))
//    }
//
//    Object requestGet(String url, ResultHandler resultHandlerForDocument, List<Object> pathParameterList, Map<String, Object> parameterMap) {
//        return requestGet(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList), parameterValues(parameterMap)))
//    }
//
//    Object requestGet(String url, ResultHandler resultHandlerForDocument, RequestValues... values) {
//        return requestGet(url, resultHandlerForDocument, new RequestValuesHandler(values))
//    }
//
//    Object requestGet(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) {
//        //- Request
//        ResultActions resultActions = mvc
//                .perform(
//                        RestDocumentationRequestBuilders
//                                .get(url, valuesHandler.getPathArray())
//                                .params(valuesHandler.getParams())
//                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//
//        //- Document or Something
//        if (resultHandlerForDocument) {
//            resultActions.andDo(resultHandlerForDocument)
//        }
//        //- Response
//        MvcResult mvcResult = resultActions.andReturn()
//        if (valuesHandler.getClosureForResponse())
//            valuesHandler.getClosureForResponse()(mvcResult.getResponse())
//        String jsonString = mvcResult.getResponse().getContentAsString()
//        def resultObject = jsonToObject(jsonString)
//
//        return resultObject
//    }
//
//
//    /**************************************************
//     *
//     * POST
//     *
//     **************************************************/
//    Object requestPost(String url) {
//        return requestPost(url, null, new RequestValuesHandler())
//    }
//
//    Object requestPost(String url, List<Object> pathParameterList) {
//        return requestPost(url, null, new RequestValuesHandler(pathValues(pathParameterList)))
//    }
//
//    Object requestPost(String url, Map<String, Object> bodyMap) {
//        return requestPost(url, null, new RequestValuesHandler(bodyValues(bodyMap)))
//    }
//
//    Object requestPost(String url, List<Object> pathParameterList, Map<String, Object> bodyMap) {
//        return requestPost(url, null, new RequestValuesHandler(pathValues(pathParameterList), bodyValues(bodyMap)))
//    }
//
//    Object requestPost(String url, RequestValues... values) {
//        return requestPost(url, null, new RequestValuesHandler(values))
//    }
//
//    Object requestPost(String url, ResultHandler resultHandlerForDocument) {
//        return requestPost(url, resultHandlerForDocument, new RequestValuesHandler())
//    }
//
//    Object requestPost(String url, ResultHandler resultHandlerForDocument, List<Object> pathParameterList) {
//        return requestPost(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList)))
//    }
//
//    Object requestPost(String url, ResultHandler resultHandlerForDocument, Map<String, Object> bodyMap) {
//        return requestPost(url, resultHandlerForDocument, new RequestValuesHandler(bodyValues(bodyMap)))
//    }
//
//    Object requestPost(String url, ResultHandler resultHandlerForDocument, List<Object> pathParameterList, Map<String, Object> bodyMap) {
//        return requestPost(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList), bodyValues(bodyMap)))
//    }
//
//    Object requestPost(String url, ResultHandler resultHandlerForDocument, RequestValues... values) {
//        return requestPost(url, resultHandlerForDocument, new RequestValuesHandler(values))
//    }
//
//    Object requestPost(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) {
//        //Request
//        ResultActions resultActions = mvc
//                .perform(
//                        post(url, valuesHandler.getPathArray())
//                                .params(valuesHandler.getParams())
//                                .content(valuesHandler.getBodyString())
//                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//
//                )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//        //- Document or Something
//        if (resultHandlerForDocument) {
//            resultActions.andDo(resultHandlerForDocument)
//        }
//        //Response
//        MvcResult mvcResult = resultActions.andReturn()
//        String jsonString = mvcResult.getResponse().getContentAsString()
//        def resultObject = jsonToObject(jsonString)
//
//        return resultObject
//    }
//
//
//    /**************************************************
//     *
//     * MultiPartFile
//     *
//     **************************************************/
//    /**
//     *  Ex)
//     *          MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
//     *          MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
//     *          MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
//     */
//    Object requestPostWithMultipart(String url, List<MockMultipartFile> multipartFileList) {
//        return requestPostWithMultipart(url, new RequestValuesHandler(new MultipartValues(values: multipartFileList)) as List<MockMultipartFile>)
//    }
//
//    Object requestPostWithMultipart(String url, List<Object> pathParameterList, List<MockMultipartFile> multipartFileList) {
//        return requestPostWithMultipart(url, new RequestValuesHandler(pathValues(pathParameterList), new MultipartValues(values: multipartFileList)) as List<MockMultipartFile>)
//    }
//
//    Object requestPostWithMultipart(String url, RequestValues... values) {
//        return requestPostWithMultipart(url, null, new RequestValuesHandler(values))
//    }
//
//    Object requestPostWithMultipart(String url, ResultHandler resultHandlerForDocument, List<MockMultipartFile> multipartFileList) {
//        return requestPostWithMultipart(url, resultHandlerForDocument, new RequestValuesHandler(new MultipartValues(values: multipartFileList)))
//    }
//
//    Object requestPostWithMultipart(String url, ResultHandler resultHandlerForDocument, List<MockMultipartFile> multipartFileList, List<Object> pathParameterList) {
//        return requestPostWithMultipart(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList), new MultipartValues(values: multipartFileList)))
//    }
//
//    Object requestPostWithMultipart(String url, ResultHandler resultHandlerForDocument, RequestValues... values) {
//        return requestPostWithMultipart(url, resultHandlerForDocument, new RequestValuesHandler(values))
//    }
//
//    Object requestPostWithMultipart(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) {
//        //Request
//        ResultActions resultActions = mvc
//                .perform(
//                        fileUploadWithFiles(url, valuesHandler.getMultipartFileList(), valuesHandler.getPathArray())
//                                .params(valuesHandler.getParams())
//                                .headers(new HttpHeaders(valuesHandler.getParams()))
//                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//        //- Document or Something
//        if (resultHandlerForDocument) {
//            resultActions.andDo(resultHandlerForDocument)
//        }
//        //Response
//        MvcResult mvcResult = resultActions.andReturn()
//        String jsonString = mvcResult.getResponse().getContentAsString()
//        def resultObject = jsonToObject(jsonString)
//
//        return resultObject
//    }
//
//    static MockMultipartHttpServletRequestBuilder fileUploadWithFiles(String url, List<MockMultipartFile> multipartFileList, Object[] pathArray) {
//        MockMultipartHttpServletRequestBuilder mockBuilder = RestDocumentationRequestBuilders.fileUpload(url, pathArray)
//        multipartFileList.each {
//            mockBuilder.file(it)
//        }
//        return mockBuilder
//    }
//
//
//    /**************************************************
//     *
//     * PUT
//     *
//     **************************************************/
//    Object requestPut(String url) {
//        return requestPut(url, null, new RequestValuesHandler())
//    }
//
//    Object requestPut(String url, List<Object> pathParameterList) {
//        return requestPut(url, null, new RequestValuesHandler(pathValues(pathParameterList)))
//    }
//
//    Object requestPut(String url, Map<String, Object> bodyMap) {
//        return requestPut(url, null, new RequestValuesHandler(bodyValues(bodyMap)))
//    }
//
//    Object requestPut(String url, List<Object> pathParameterList, Map<String, Object> bodyMap) {
//        return requestPut(url, null, new RequestValuesHandler(pathValues(pathParameterList), bodyValues(bodyMap)))
//    }
//
//    Object requestPut(String url, RequestValues... values) {
//        return requestPut(url, null, new RequestValuesHandler(values))
//    }
//
//    Object requestPut(String url, ResultHandler resultHandlerForDocument) {
//        return requestPut(url, resultHandlerForDocument, new RequestValuesHandler())
//    }
//
//    Object requestPut(String url, ResultHandler resultHandlerForDocument, List<Object> pathParameterList) {
//        return requestPut(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList)))
//    }
//
//    Object requestPut(String url, ResultHandler resultHandlerForDocument, Map<String, Object> bodyMap) {
//        return requestPut(url, resultHandlerForDocument, new RequestValuesHandler(bodyValues(bodyMap)))
//    }
//
//    Object requestPut(String url, ResultHandler resultHandlerForDocument, List<Object> pathParameterList, Map<String, Object> bodyMap) {
//        return requestPut(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList), bodyValues(bodyMap)))
//    }
//
//    Object requestPut(String url, ResultHandler resultHandlerForDocument, RequestValues... values) {
//        return requestPut(url, resultHandlerForDocument, new RequestValuesHandler(values))
//    }
//
//    Object requestPut(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) {
//        //- Request
//        ResultActions resultActions = mvc
//                .perform(
//                        put(url, valuesHandler.getPathArray())
//                                .params(valuesHandler.getParams())
//                                .content(valuesHandler.getBodyString())
//                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//        //- Document or Something
//        if (resultHandlerForDocument) {
//            resultActions.andDo(resultHandlerForDocument)
//        }
//        //- Response
//        MvcResult mvcResult = resultActions.andReturn()
//        String jsonString = mvcResult.getResponse().getContentAsString()
//
//        return jsonToObject(jsonString)
//    }
//
//
//    /**************************************************
//     *
//     * DELETE
//     *
//     **************************************************/
//    Object requestDelete(String url) {
//        return requestDelete(url, null, new RequestValuesHandler())
//    }
//
//    Object requestDelete(String url, List<Object> pathParameterList) {
//        return requestDelete(url, null, new RequestValuesHandler(pathValues(pathParameterList)))
//    }
//
//    Object requestDelete(String url, Map<String, Object> parameterMap) {
//        return requestDelete(url, null, new RequestValuesHandler(parameterValues(parameterMap)))
//    }
//
//    Object requestDelete(String url, List<Object> pathParameterList, Map<String, Object> parameterMap) {
//        return requestDelete(url, null, new RequestValuesHandler(pathValues(pathParameterList), parameterValues(parameterMap)))
//    }
//
//    Object requestDelete(String url, RequestValues... values) {
//        return requestDelete(url, null, new RequestValuesHandler(values))
//    }
//
//    Object requestDelete(String url, ResultHandler resultHandlerForDocument) {
//        return requestDelete(url, resultHandlerForDocument, new RequestValuesHandler())
//    }
//
//    Object requestDelete(String url, ResultHandler resultHandlerForDocument, List<Object> pathParameterList) {
//        return requestDelete(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList)))
//    }
//
//    Object requestDelete(String url, ResultHandler resultHandlerForDocument, Map<String, Object> parameterMap) {
//        return requestDelete(url, resultHandlerForDocument, new RequestValuesHandler(parameterValues(parameterMap)))
//    }
//
//    Object requestDelete(String url, ResultHandler resultHandlerForDocument, List<Object> pathParameterList, Map<String, Object> parameterMap) {
//        return requestDelete(url, resultHandlerForDocument, new RequestValuesHandler(pathValues(pathParameterList), parameterValues(parameterMap)))
//    }
//
//    Object requestDelete(String url, ResultHandler resultHandlerForDocument, RequestValues... values) {
//        return requestDelete(url, resultHandlerForDocument, new RequestValuesHandler(values))
//    }
//
//    Object requestDelete(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) {
//        //- Reqeust
//        ResultActions resultActions = mvc
//                .perform(
//                        RestDocumentationRequestBuilders.
//                                delete(url, valuesHandler.getPathArray())
//                                .params(valuesHandler.getParams())
//                                .content(valuesHandler.getBodyString())
//                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//        //- Document or Something
//        if (resultHandlerForDocument) {
//            resultActions.andDo(resultHandlerForDocument)
//        }
//        //- Response
//        MvcResult mvcResult = resultActions.andReturn()
//        String jsonString = mvcResult.getResponse().getContentAsString()
//        def resultObject = jsonToObject(jsonString)
//
//        return resultObject
//    }

}
