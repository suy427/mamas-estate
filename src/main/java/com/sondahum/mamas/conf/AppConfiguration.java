package com.sondahum.mamas.conf;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan("com.sondahum.mamas")
public class AppConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        HandlebarsViewResolver hbsViewResolvers = new HandlebarsViewResolver();
        hbsViewResolvers.setPrefix("classpath:/templates");
        hbsViewResolvers.setSuffix(".hbs");

        registry.viewResolver(hbsViewResolvers);
    }
}
