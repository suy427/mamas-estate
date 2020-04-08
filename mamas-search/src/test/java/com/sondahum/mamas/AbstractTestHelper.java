package com.sondahum.mamas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AbstractTestHelper {

    protected final ObjectMapper mapper = new ObjectMapper();

    public class RequestValuesHandler {

        PathValues pathValues;
        ParameterValues paramValues;
        MultipartValues multipartValues;
        BodyValues bodyValues;
        HeaderValues headerValues;
        ResponseHandler responseHandler;

        public RequestValuesHandler() {}

        public RequestValuesHandler(RequestValues[] values) {
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

        List<Object> getPathArray() {return this.pathValues.pathValueList;}
        MultiValueMap<String, String> getParams() {return this.paramValues.parameterValueMap;}
        String getBodyString() throws Exception {return mapper.writeValueAsString(this.bodyValues.values);}
        MultiValueMap<String, String> getHeaders() {return this.headerValues.headerValueMap;}
        List<MockMultipartFile> getMultipartFileList() {return this.multipartValues.multiPartValueList;}
        Consumer<MockHttpServletResponse> getConsumerForResponse() {return responseHandler.responseConsumer;}
    }

    protected class RequestValues {}

    protected class PathValues extends RequestValues {List<Object> pathValueList = new ArrayList<>();}
    protected class ParameterValues extends RequestValues {MultiValueMap<String, String> parameterValueMap = new LinkedMultiValueMap<>();} // 얘는 MultiValueMap이 될 아이다.
    protected class HeaderValues extends RequestValues {MultiValueMap<String, String> headerValueMap = new LinkedMultiValueMap<>();}
    protected class MultipartValues extends RequestValues {List<MockMultipartFile> multiPartValueList = new ArrayList<>();}
    protected class BodyValues extends RequestValues {Object values = null;}
    protected class ResponseHandler extends RequestValues {Consumer<MockHttpServletResponse> responseConsumer = null;}


    protected ParameterValues parameterValues(Object obj) throws Exception { // todo type safe하게 바꾸기
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.parameterValueMap = new MultiValueMapConverter(obj).convert();
        return parameterValues;
    }

    protected HeaderValues headerValues(Object obj) throws Exception { // todo type safe하게 바꾸기
        HeaderValues headerValues = new HeaderValues();
        headerValues.headerValueMap = new MultiValueMapConverter(obj).convert();
        return headerValues;
    }

    protected BodyValues bodyValues(Map<String, Object> valueMap) {
        BodyValues o = new BodyValues();
        o.values = valueMap;
        return o;
    }

    protected PathValues pathValues(Object... values) {
        PathValues o = new PathValues();
        o.pathValueList = Arrays.asList(values);
        return o;
    }

    protected MultipartValues multipartValues(MockMultipartFile... values) {
        MultipartValues o = new MultipartValues();
        o.multiPartValueList = Arrays.asList(values);
        return o;
    }

    protected ResponseHandler responseHandler(Consumer<MockHttpServletResponse> consumer) {
        ResponseHandler o = new ResponseHandler();
        o.responseConsumer = consumer;
        return o;
    }

    static File byteArrayToFile(byte[] buff, String filePath, String fileName) throws IOException {
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
