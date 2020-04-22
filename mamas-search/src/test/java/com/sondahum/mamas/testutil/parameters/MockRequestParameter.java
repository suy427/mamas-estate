package com.sondahum.mamas.testutil.parameters;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class MockRequestParameter implements RequestValues {
    public MultiValueMap<String, String> parameterValueMap = new LinkedMultiValueMap<>();
} // 얘는 MultiValueMap이 될 아이다.