package com.sondahum.mamas;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MamasEstateApplicationStarter.class)
@AutoConfigureMockMvc
public abstract class AbstractMockRequestHelper extends AbstractTestHelper {

    protected MockMvc mockMvc;

    public MockHttpServletResponse requestGet(String url, RequestValues... values) throws Exception {
        return requestGet(url, null, new RequestValuesHandler(values));
    }

    public MockHttpServletResponse requestGet(String url, ResultHandler resultHandlerForDocument, RequestValues... values) throws Exception {
        return requestGet(url, resultHandlerForDocument, new RequestValuesHandler(values));
    }

    public MockHttpServletResponse requestGet(String url, ResultHandler resultHandlerForDocument, AbstractTestHelper.RequestValuesHandler valuesHandler) throws Exception {
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
        if (resultHandlerForDocument != null) {
            resultActions.andDo(resultHandlerForDocument);
        }
        //- Response
        MvcResult mvcResult = resultActions.andReturn();
        if (valuesHandler.getConsumerForResponse() != null)
            valuesHandler.getConsumerForResponse().accept(mvcResult.getResponse());

        return mvcResult.getResponse();
    }

    public MockHttpServletResponse requestPost(String url, RequestValues... values) throws Exception {
        return requestPost(url, null, new RequestValuesHandler(values));
    }

    public MockHttpServletResponse requestPost(String url, ResultHandler resultHandlerForDocument, RequestValues... values) throws Exception {
        return requestPost(url, resultHandlerForDocument, new RequestValuesHandler(values));
    }

    public MockHttpServletResponse requestPost(String url, ResultHandler resultHandlerForDocument, AbstractTestHelper.RequestValuesHandler valuesHandler) throws Exception {
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
