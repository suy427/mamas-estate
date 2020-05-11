package com.sondahum.mamas.testutil;

import com.sondahum.mamas.testutil.parameters.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.function.Consumer;

public class RequestValueContainer {

    protected PathValues pathValues;
    protected MockRequestParameter requestParameter;
    protected MockMultipartFiles multipartValues;
    protected MockRequestBody requestBody;
    protected MockHeader header;
    protected ResponseHandler responseHandler;

    public RequestValueContainer() {}

    public RequestValueContainer(RequestValues... values) {
        for (RequestValues valueObject : values) {
            if (valueObject instanceof PathValues)
                this.pathValues = (PathValues) valueObject;
            else if (valueObject instanceof MockRequestParameter)
                this.requestParameter = (MockRequestParameter) valueObject;
            else if (valueObject instanceof MockMultipartFiles)
                this.multipartValues = (MockMultipartFiles) valueObject;
            else if (valueObject instanceof MockRequestBody)
                this.requestBody = (MockRequestBody) valueObject;
            else if (valueObject instanceof MockHeader)
                this.header = (MockHeader) valueObject;
            else if (valueObject instanceof ResponseHandler)
                this.responseHandler = (ResponseHandler) valueObject;
        }
    }

    List<Object> getPathArray() {
        return pathValues == null ? null : pathValues.pathValueList;
    }

    MultiValueMap<String, String> getRequestParameters() {
        return requestParameter == null ? null : requestParameter.parameterValueMap;
    }

    String getRequestBody() throws Exception {
        return requestBody == null ? null : requestBody.requestBodyString;
    }

    MultiValueMap<String, String> getHeaders() {
        return header == null ? null : header.headerValueMap;
    }

    List<MockMultipartFile> getMultipartFiles() {
        return multipartValues == null ? null : multipartValues.multiPartValueList;
    }

    Consumer<MockHttpServletResponse> getConsumerForResponse() {
        return responseHandler == null ? null : responseHandler.responseConsumer;
    }
}