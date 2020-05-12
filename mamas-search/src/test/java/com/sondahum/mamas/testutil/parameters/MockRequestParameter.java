package com.sondahum.mamas.testutil.parameters;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class MockRequestParameter implements RequestValues {
    public MultiValueMap<String, String> parameterValueMap = new LinkedMultiValueMap<>();
}