package com.sondahum.mamas.starter

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class MamasEstateApplicationStarter extends SpringBootServletInitializer {

    static void main(String[] args) { // 이거야 뭐 이 클래스를 SpringBoot의 Starter로 실행시키겠다는거겠고
        SpringApplication.run(MamasEstateApplicationStarter, args)
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { //이게 뭔지 공부하자
        application.sources(MamasEstateApplicationStarter)
    }
}


