package com.sondahum.mamas.starter.tester

import com.sondahum.mamas.starter.MamasEstateApplicationStarter
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MamasEstateApplicationStarter.class)
@AutoConfigureMockMvc
abstract class MockTestRequestHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mvc
}
