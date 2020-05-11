package com.sondahum.mamas.testutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondahum.mamas.testutil.parameters.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AbstractTestHelper {

    protected final ObjectMapper mapper = new ObjectMapper();

    // todo
    protected MockRequestParameter requestParameters(Object requestParameterBean) throws Exception {
        MockRequestParameter parameterValues = new MockRequestParameter();
        parameterValues.parameterValueMap = new MultiValueMapConverter().convert(requestParameterBean);
        return parameterValues;
    }
    protected MockRequestParameter requestParameters(Map<String, Object> requestParameterMap) throws Exception {
        MockRequestParameter parameterValues = new MockRequestParameter();
        parameterValues.parameterValueMap = new MultiValueMapConverter().convert(requestParameterMap);
        return parameterValues;
    }

    protected MockRequestParameter requestParameters(List<Object> requestParameterList) throws Exception {
        MockRequestParameter parameterValues = new MockRequestParameter();
        parameterValues.parameterValueMap = new MultiValueMapConverter().convert(requestParameterList);
        return parameterValues;
    }

    protected MockHeader headers(Object headerBean) throws Exception { // todo type safe하게 바꾸기
        MockHeader headerValues = new MockHeader();
        headerValues.headerValueMap = new MultiValueMapConverter().convert(headerBean);
        return headerValues;
    }

    protected MockRequestBody requestBody(Object requestBodyBean) throws JsonProcessingException {
        MockRequestBody o = new MockRequestBody();
        o.requestBodyString = mapper.writeValueAsString(requestBodyBean);
        return o;
    }
    // todo 위에께 있으면 이건 필요 없으려나... objectMapper를 검증하자!!
    protected MockRequestBody requestBody(Map<String, Object> requestBodyMap) throws JsonProcessingException {
        MockRequestBody o = new MockRequestBody();
        o.requestBodyString = mapper.writeValueAsString(requestBodyMap);
        return o;
    }

    protected PathValues pathValues(Object... values) {
        PathValues o = new PathValues();
        o.pathValueList = Arrays.asList(values);
        return o;
    }

    protected MockMultipartFiles multipartFiles(MockMultipartFile... values) {
        MockMultipartFiles o = new MockMultipartFiles();
        o.multiPartValueList = Arrays.asList(values);
        return o;
    }

    protected ResponseHandler responseHandler(Consumer<MockHttpServletResponse> consumer) {
        ResponseHandler o = new ResponseHandler();
        o.responseConsumer = consumer;
        return o;
    }

    protected File byteArrayToFile(byte[] buff, String filePath, String fileName) throws IOException {
        if ((filePath == null || "".equals(filePath)) || (fileName == null || "".equals(fileName))) {
            return null;
        }
        FileOutputStream fos = null;
        File fileDir = new File(filePath);

        if (!fileDir.mkdirs())
            throw new IOException();

        File destFile = new File(filePath + fileName);

        fos = new FileOutputStream(destFile);
        fos.write(buff);
        fos.close();

        return destFile;
    }
}
