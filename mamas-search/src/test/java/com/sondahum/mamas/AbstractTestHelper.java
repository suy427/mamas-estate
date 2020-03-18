package com.sondahum.mamas;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Closure;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MamasEstateApplicationStarter.class)
public abstract class AbstractTestHelper {

    protected MockMvc mockMvc;
    protected final ObjectMapper mapper = new ObjectMapper();

    private class RequestValues {}
    private class PathValues extends RequestValues { List<Object> values = new ArrayList<>();}
    private class ParameterValues extends RequestValues {Map<String, Object> values = new LinkedHashMap<>();}
    private class MultipartValues extends RequestValues {List<Object> values = new ArrayList<>();}
    private class BodyValues extends RequestValues {Object values = null;}
    private class HeaderValues extends RequestValues {Object values = null;}
    private class ResponseHandler extends RequestValues {Closure closureForResponse = null;}

    protected ParameterValues parameterValues(Map<String, Object> valueMap){
        ParameterValues o = new ParameterValues();
        o.values = valueMap;
        return o;
    }

    protected BodyValues bodyValues(Map<String, Object> valueMap){
        BodyValues o = new BodyValues();
        o.values = valueMap;
        return o;
    }

    protected HeaderValues headerValues(Map<String, Object> valueMap){
        HeaderValues o = new HeaderValues();
        o.values = valueMap;
        return o;
    }

    protected PathValues pathValues(Object... values){
        PathValues o = new PathValues();
        o.values = Arrays.asList(values);
        return o;
    }

    protected MultipartValues multipartValues(Object... values){
        MultipartValues o = new MultipartValues();
        o.values = Arrays.asList(values);
        return o;
    }

    protected ResponseHandler responseHandler(Closure closureForResponse){
        ResponseHandler o = new ResponseHandler();
        o.closureForResponse = closureForResponse;
        return o;
    }


    class RequestValuesHandler {

        PathValues pathValues;
        ParameterValues paramValues;
        MultipartValues multipartValues;
        BodyValues bodyValues;
        HeaderValues headerValues;
        ResponseHandler responseHandler;


        RequestValuesHandler() {}

        RequestValuesHandler(RequestValues... values) {
            for (RequestValues valueObject : values) {
                if (valueObject instanceof PathValues)
                    this.pathValues = (PathValues) valueObject;
                else if (valueObject instanceof ParameterValues)
                    this.paramValues = (ParameterValues) valueObject;
                else if (valueObject instanceof MultipartValues)
                    this.multipartValues = (MultipartValues) valueObject;
                else if (valueObject instanceof BodyValues)
                    this.bodyValues = (BodyValues) valueObject;
                else if (valueObject instanceof HeaderValues)
                    this.headerValues = (HeaderValues) valueObject;
                else if (valueObject instanceof ResponseHandler)
                    this.responseHandler = (ResponseHandler) valueObject;
            }
        }

        List<Object> getPathArray() {
            return this.pathValues.values;
        }

        MultiValueMap<String, String> getParams() {
            return generateParams(this.paramValues.values);
        }

        String getBodyString() throws Exception { return mapper.writeValueAsString(this.bodyValues.values); }

        List<MockMultipartFile> getMultipartFileList() {
            return this.multipartValues.values;
        }

        MultiValueMap<String, String> getHeaders() {
            return generateParams(this.headerValues.values);
        }

        Closure getClosureForResponse() {
            return responseHandler.closureForResponse;
        }

        MultiValueMap<String, String> generateParams(MultiValueMap<String, Object> parameters) {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

            if (!parameters.isEmpty()){
                Map<String, List<String>> listValueMap = generateConcatKeyParams(parameters);

                multiValueMap.putAll(listValueMap);
            }
            return multiValueMap;
        }

        Map<String, List<String>> generateConcatKeyParams(MultiValueMap<String, Object> parameters) {
            Map<String, List<String>> concatKeyListValueMap = new LinkedHashMap<>();
            return generateConcatKeyParams(parameters, "", concatKeyListValueMap);
        }

        Map<String, List<String>> generateConcatKeyParams (
                MultiValueMap<String, Object> parameters, String prevKey, Map<String, List<String>> concatKeyListValueMap)
        {
            for (String key : parameters.keySet()) {
                String concatKey = prevKey.equals("") ? prevKey+"."+key : key;
                Object value = parameters.get(key);

                if (value instanceof Map) {
                    generateConcatKeyParams((MultiValueMap<String, Object>) value, concatKey, concatKeyListValueMap);
                } else {
                    List<String> valueList = (value instanceof List) ? (List<String>)value : new ArrayList<>(Arrays.asList(value.toString()));
                    concatKeyListValueMap.put(concatKey, valueList.stream().map(String::valueOf).collect(Collectors.toList()));
                }
            }
            return concatKeyListValueMap;
        }
    }
}
