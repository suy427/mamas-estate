package com.sondahum.mamas;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MamasEstateApplicationStarter.class)
public abstract class AbstractTestHelper {

    protected MockMvc mockMvc;
    protected final ObjectMapper mapper = new ObjectMapper();

    private class RequestValues {}
    private class PathValues extends RequestValues { List<Object> pathValueList = new ArrayList<>();}
    private class ParameterValues extends RequestValues {MultiValueMap<String, String> parameterValueMap = new LinkedMultiValueMap<>();} // 얘는 MultiValueMap이 될 아이다.
    private class HeaderValues extends RequestValues {MultiValueMap<String, String> headerValueMap = new LinkedMultiValueMap<>();}
    private class MultipartValues extends RequestValues {List<MockMultipartFile> multiPartValueList = new ArrayList<>();}
    private class BodyValues extends RequestValues {Object values = null;}
    private class ResponseHandler extends RequestValues {Consumer<MockHttpServletResponse> responseConsumer = null;}

    protected ParameterValues parameterValues(Map<String, String> parameterValueMap){
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.parameterValueMap = convertToMultiValueMap(parameterValueMap);
        return parameterValues;
    }

    protected HeaderValues headerValues(Map<String, String> headerValueMap){
        HeaderValues headerValues = new HeaderValues();
        headerValues.headerValueMap = convertToMultiValueMap(headerValueMap);
        return headerValues;
    }

    protected BodyValues bodyValues(Map<String, Object> valueMap){
        BodyValues o = new BodyValues();
        o.values = valueMap;
        return o;
    }


    // todo map --> multivaluemap
    private MultiValueMap<String, String> convertToMultiValueMap(Map<String, String> map) {
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

    protected PathValues pathValues(Object... values){
        PathValues o = new PathValues();
        o.pathValueList = Arrays.asList(values);
        return o;
    }

    protected MultipartValues multipartValues(MockMultipartFile... values){
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

        List<Object> getPathArray() { return this.pathValues.pathValueList; }
        MultiValueMap<String, String> getParams() { return this.paramValues.parameterValueMap; }
        String getBodyString() throws Exception { return mapper.writeValueAsString(this.bodyValues.values); }
        MultiValueMap<String, String> getHeaders() { return this.headerValues.headerValueMap; }
        List<MockMultipartFile> getMultipartFileList(){ return this.multipartValues.multiPartValueList; }
        Consumer<MockHttpServletResponse> getConsumerForResponse() { return responseHandler.responseConsumer; }

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

    MockHttpServletResponse requestPost(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) throws Exception {
        //Request
        ResultActions resultActions = mockMvc
                .perform(
                        post(url, valuesHandler.getPathArray())
                                .params(valuesHandler.getParams())
                                .content(valuesHandler.getBodyString())
                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
//                    .with(securityContext(secc))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        //- Document or Something
        if (resultHandlerForDocument != null) {
            resultActions.andDo(resultHandlerForDocument);
        }
        //Response
        MvcResult mvcResult = resultActions.andReturn();

        return mvcResult.getResponse();
    }
}
