package com.sondahum.mamas.testutil.parameters;


import org.springframework.mock.web.MockHttpServletResponse;

import java.util.function.Consumer;

public class ResponseHandler implements RequestValues {
    public Consumer<MockHttpServletResponse> responseConsumer = null;
}