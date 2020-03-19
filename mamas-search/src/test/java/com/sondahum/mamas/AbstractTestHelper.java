package com.sondahum.mamas;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MamasEstateApplicationStarter.class)
public abstract class AbstractTestHelper {

    protected MockMvc mockMvc;
    protected final ObjectMapper mapper = new ObjectMapper();

    private class RequestValues {}
    private class PathValues extends RequestValues { List<Object> pathValueList = new ArrayList<>();}
    private class ParameterValues extends RequestValues {MultiValueMap<String, Object> parameterValueMap = new LinkedMultiValueMap<>();} // 얘는 MultiValueMap이 될 아이다.
    private class HeaderValues extends RequestValues {MultiValueMap<String, Object> headerValueMap = new LinkedMultiValueMap<>();}
    private class MultipartValues extends RequestValues {List<Object> multiPartValueList = new ArrayList<>();}
    private class BodyValues extends RequestValues {Object values = null;}
    private class ResponseHandler extends RequestValues {Consumer<MockHttpServletResponse> responseConsumer = null;}

    protected ParameterValues parameterValues(Map<String, Object> parameterValueMap){
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.parameterValueMap = convertToMultiValueMap(parameterValueMap);
        return parameterValues;
    }

    private MultiValueMap<String, Object> convertToMultiValueMap(Map<String, Object> map) {
        MultiValueMap<String, String> ret = new LinkedMultiValueMap<>();
        List<Object> li1 = new ArrayList<>();

        map.forEach( (key, value) -> {
            if (!(value instanceof String)) {

            } else {
                ret.add(key, (String)value);
            }
                });

        return null;
    }

    protected BodyValues bodyValues(Map<String, Object> valueMap){
        BodyValues o = new BodyValues();
        o.values = valueMap;
        return o;
    }

    protected HeaderValues headerValues(Map<String, Object> headerValueMap){
        return convertToMultiValueMap(headerValueMap);
    }

    protected PathValues pathValues(Object... values){
        PathValues o = new PathValues();
        o.pathValueList = Arrays.asList(values);
        return o;
    }

    protected MultipartValues multipartValues(Object... values){
        MultipartValues o = new MultipartValues();
        o.multiPartValueList = Arrays.asList(values);
        return o;
    }

    protected ResponseHandler responseHandler(Consumer<MockHttpServletResponse> consumer) {
        ResponseHandler o = new ResponseHandler();
        o.responseConsumer = consumer;
        return o;
    }

    static File byteArrayToFile(byte[] buff, String filePath, String fileName) {
        if ((filePath == null || "".equals(filePath))
                || (fileName == null || "".equals(fileName))) { return null; }

        FileOutputStream fos = null;

        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File destFile = new File(filePath + fileName);

        try {
            fos = new FileOutputStream(destFile);
            fos.write(buff);
            fos.close();
        } catch (IOException e) {
            log.error("Exception position : FileUtil - binaryToFile(String binaryFile, String filePath, String fileName)");
        }

        return destFile;
    }


    public class RequestValuesHandler {

        PathValues pathValues;
        ParameterValues paramValues;
        MultipartValues multipartValues;
        BodyValues bodyValues;
        HeaderValues headerValues;
        ResponseHandler responseHandler;


        public RequestValuesHandler() {}

        public RequestValuesHandler(RequestValues... values) {
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
            return this.pathValues.pathValueList;
        }

        MultiValueMap<String, String> getParams() {
            return generateParams(this.paramValues.parameterValueMap);
        }

        String getBodyString() throws Exception { return mapper.writeValueAsString(this.bodyValues.values); }

        MultiValueMap<String, String> getHeaders() {
            return generateParams(this.headerValues.headerValueMap);
        }

        Consumer<MockHttpServletResponse> getConsumerForResponse() {
            return responseHandler.responseConsumer;
        }

        /**
         * generateParams, generateConcatKeyParams, generateConcatKeyParams
         * 이거 세개가 한 세트이다!!!!
         * 이거 전체가 Map<String, Object> 를 MultiValueMap<String, String> 으로 바꾸는 애다.
         *                         이 Object는 결국 List<String>이다.
         */
        public MultiValueMap<String, String> generateParams(Map<String, Object> parameterMap) { // Map<String, Object>를 MultiValueMap으로
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

            if (!parameterMap.isEmpty()) {
                Map<String, List<String>> listValueMap = generateConcatKeyParams(parameterMap);

                multiValueMap.putAll(listValueMap);
            }
            return multiValueMap;
        }

        public Map<String, List<String>> generateConcatKeyParams(Map<String, Object> parameterMap) {
            Map<String, List<String>> concatKeyListValueMap = new LinkedHashMap<>();
            return generateConcatKeyParams(parameterMap, "", concatKeyListValueMap);
        }

        public Map<String, List<String>> generateConcatKeyParams (
                Map<String, Object> parameterMap, String prevKey, Map<String, List<String>> concatKeyListValueMap)
                // 여기서 parameterMap은 key: object혹은 key: list로 올 수 있다.
        {
            for (String key : parameterMap.keySet()) { // 이거는 혹시 계층 구조를 나타낼려고 .붙여서 계속 잇는건가..?
                String concatKey = prevKey.equals("") ? prevKey+"."+key : key;
                Object value = parameterMap.get(key);

                if (value instanceof Map) {
                    generateConcatKeyParams((Map<String, Object>) value, concatKey, concatKeyListValueMap);
                } else {
                    List<String> valueList = (value instanceof List) ? (List<String>)value : new ArrayList<>(Arrays.asList(value.toString()));
                    concatKeyListValueMap.put(concatKey, valueList.stream().map(String::valueOf).collect(Collectors.toList()));
                }
            }
            return concatKeyListValueMap;
        }
    }

    MockHttpServletResponse requestGet(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) throws Exception {
        //- Request
        ResultActions resultActions = mockMvc
                .perform(
                        RestDocumentationRequestBuilders
                                .get(url, valuesHandler.getPathArray())
                                .params(valuesHandler.getParams())
                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        //- Document or Something
        if (resultHandlerForDocument != null){
            resultActions.andDo(resultHandlerForDocument);
        }
        //- Response
        MvcResult mvcResult = resultActions.andReturn();
        if (valuesHandler.getConsumerForResponse() != null)
            valuesHandler.getConsumerForResponse().accept(mvcResult.getResponse());

        return mvcResult.getResponse();
    }
}
