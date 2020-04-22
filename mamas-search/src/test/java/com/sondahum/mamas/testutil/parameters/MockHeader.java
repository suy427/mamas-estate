package com.sondahum.mamas.testutil.parameters;


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class MockHeader implements RequestValues {
    public MultiValueMap<String, String> headerValueMap = new LinkedMultiValueMap<>();
}