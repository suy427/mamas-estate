package com.sondahum.mamas.conf;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;

public class AppInitializer extends AbstractDispatcherServletInitializer {
    private final AnnotationConfigWebApplicationContext applicationContext =
            new AnnotationConfigWebApplicationContext();

    public AppInitializer() {
        applicationContext.getEnvironment().setActiveProfiles(System.getProperty("app.profile"));
        applicationContext.register(AppConfiguration.class);
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {
                new CharacterEncodingFilter("UTF-8", true),
                new DelegatingFilterProxy("springSecurityFilterChain")};
    }

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        return applicationContext;
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return applicationContext;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
