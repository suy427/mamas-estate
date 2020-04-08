package com.sondahum.mamas.testutil.parameters;


import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

public class MockMultipartFiles implements RequestValues {
    public List<MockMultipartFile> multiPartValueList = new ArrayList<>();
}