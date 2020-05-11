package com.sondahum.mamas.testutil;

import com.sondahum.mamas.MamasEstateApplicationStarter;
import com.sondahum.mamas.testutil.parameters.RequestValues;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MamasEstateApplicationStarter.class)
@AutoConfigureMockMvc
//@Transactional
public abstract class AbstractMockRequestHelper extends AbstractTestHelper {

    @Autowired
    protected MockMvc mockMvc;

    /**
     * [ GET ]
     */
    public MockHttpServletResponse requestGet(String url, RequestValues... values) throws Exception {
        return requestGet(url, null, new RequestValueContainer(values));
    }

    public MockHttpServletResponse requestGet(
            String url, ResultHandler resultHandlerForDocument, RequestValues... values) throws Exception {
        return requestGet(url, resultHandlerForDocument, new RequestValueContainer(values));
    }

    private MockHttpServletResponse requestGet(
            String url, ResultHandler resultHandlerForDocument, RequestValueContainer valuesHandler) throws Exception
    {
        MockHttpServletRequestBuilder request;
        if (valuesHandler.getPathArray() != null)
            request = RestDocumentationRequestBuilders.get(url, valuesHandler.getPathArray());
        else
            request = RestDocumentationRequestBuilders.get(url);

        if (valuesHandler.getRequestParameters() != null)
            request = request.params(valuesHandler.getRequestParameters());

        if (valuesHandler.getHeaders() != null)
            request = request.headers(new HttpHeaders(valuesHandler.getHeaders()));

        request.contentType(MediaType.APPLICATION_JSON);

        //- Request
        ResultActions resultActions = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print());

        //- Document or Something
        if (resultHandlerForDocument != null) {
            resultActions.andDo(resultHandlerForDocument);
        }
        //- Response
        MvcResult mvcResult = resultActions.andReturn();
        if (valuesHandler.getConsumerForResponse() != null)
            valuesHandler.getConsumerForResponse().accept(mvcResult.getResponse());

        return mvcResult.getResponse();
    }


    /**
     * [ POST ]
     */
    public MockHttpServletResponse requestPost(String url, RequestValues... values) throws Exception {
        RequestValueContainer requestValues = new RequestValueContainer(values);
        if (requestValues.multipartValues != null)
            return requestMultipartPost(url, null, requestValues);
        else
            return requestPost(url, null, requestValues);
    }

    public MockHttpServletResponse requestPost(
            String url, ResultHandler resultHandlerForDocument, RequestValues... values) throws Exception {
        RequestValueContainer requestValues = new RequestValueContainer(values);
        if (requestValues.multipartValues != null)
            return requestMultipartPost(url, resultHandlerForDocument, requestValues);
        else
            return requestPost(url, resultHandlerForDocument, requestValues);
    }

    private MockHttpServletResponse requestPost(
            String url, ResultHandler resultHandlerForDocument, RequestValueContainer valuesHandler) throws Exception
    {
        MockHttpServletRequestBuilder request;
        if (valuesHandler.getPathArray() != null)
            request = post(url, valuesHandler.getPathArray());
        else
            request = post(url);

        if (valuesHandler.getRequestParameters() != null)
            request = request.params(valuesHandler.getRequestParameters());
        if (valuesHandler.getRequestBody() != null)
            request = request.content(valuesHandler.getRequestBody());
        if (valuesHandler.getHeaders() != null)
            request = request.headers(new HttpHeaders(valuesHandler.getHeaders()));

        request.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //Request
        ResultActions resultActions = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print());

        //- Document or Something
        if (resultHandlerForDocument != null) {
            resultActions.andDo(resultHandlerForDocument);
        }
        //Response
        MvcResult mvcResult = resultActions.andReturn();

        return mvcResult.getResponse();
    }


    /**
     * [ MultiPart ]
     */
    private MockHttpServletResponse requestMultipartPost(
            String url, ResultHandler resultHandlerForDocument, RequestValueContainer valuesHandler) throws Exception
    {
        //Request
        ResultActions resultActions = mockMvc
                .perform(
                        fileUpload(url, valuesHandler.getMultipartFiles(), valuesHandler.getPathArray())
                                .params(valuesHandler.getRequestParameters())
                                .headers(new HttpHeaders(valuesHandler.getRequestParameters()))
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print());

        //- Document or Something
        if (resultHandlerForDocument != null) {
            resultActions.andDo(resultHandlerForDocument);
        }
        //Response
        MvcResult mvcResult = resultActions.andReturn();
        return mvcResult.getResponse();
    }

    private MockMultipartHttpServletRequestBuilder fileUpload(
            String url, List<MockMultipartFile> multipartFileList, List<Object> pathArray)
    {
        MockMultipartHttpServletRequestBuilder mockBuilder = RestDocumentationRequestBuilders.fileUpload(url, pathArray);
        multipartFileList.forEach(mockBuilder::file);

        return mockBuilder;
    }


    /**
     * [ PUT ]
     */
    public MockHttpServletResponse requestPut(String url, RequestValues... values) throws Exception {
        return requestPut(url, null, new RequestValueContainer(values));
    }

    public MockHttpServletResponse requestPut(
            String url, ResultHandler resultHandlerForDocument, RequestValues... values) throws Exception {
        return requestPut(url, resultHandlerForDocument, new RequestValueContainer(values));
    }

    private MockHttpServletResponse requestPut(
            String url, ResultHandler resultHandlerForDocument, RequestValueContainer valuesHandler) throws Exception
    {
        MockHttpServletRequestBuilder request;
        if (valuesHandler.getPathArray() != null)
            request = put(url, valuesHandler.getPathArray());
        else
            request = put(url);

        if (valuesHandler.getRequestParameters() != null)
            request = request.params(valuesHandler.getRequestParameters());
        if (valuesHandler.getRequestBody() != null)
            request = request.content(valuesHandler.getRequestBody());
        if (valuesHandler.getHeaders() != null)
            request = request.headers(new HttpHeaders(valuesHandler.getHeaders()));

        request.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //Request
        ResultActions resultActions = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print());

        //- Document or Something
        if (resultHandlerForDocument != null) {
            resultActions.andDo(resultHandlerForDocument);
        }
        //- Response
        MvcResult mvcResult = resultActions.andReturn();
        return mvcResult.getResponse();
    }

    /**
     * [ DELETE ]
     */
    public MockHttpServletResponse requestDelete(String url, RequestValues... values) throws Exception {
        return requestDelete(url, null, new RequestValueContainer(values));
    }

    public MockHttpServletResponse requestDelete(
            String url, ResultHandler resultHandlerForDocument, RequestValues... values) throws Exception {
        return requestDelete(url, resultHandlerForDocument, new RequestValueContainer(values));
    }

    private MockHttpServletResponse requestDelete(
            String url, ResultHandler resultHandlerForDocument, RequestValueContainer valuesHandler) throws Exception
    {
        MockHttpServletRequestBuilder request;
        if (valuesHandler.getPathArray() != null)
            request = delete(url, valuesHandler.getPathArray());
        else
            request = delete(url);

        if (valuesHandler.getRequestParameters() != null)
            request = request.params(valuesHandler.getRequestParameters());
        if (valuesHandler.getRequestBody() != null)
            request = request.content(valuesHandler.getRequestBody());
        if (valuesHandler.getHeaders() != null)
            request = request.headers(new HttpHeaders(valuesHandler.getHeaders()));

        request.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //- Reqeust
        ResultActions resultActions = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print());

        //- Document or Something
        if (resultHandlerForDocument != null) {
            resultActions.andDo(resultHandlerForDocument);
        }
        //- Response
        MvcResult mvcResult = resultActions.andReturn();
        return mvcResult.getResponse();
    }
}
