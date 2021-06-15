package com.sondahum.mamas;


import ch.qos.logback.classic.util.ContextInitializer;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class MamasEstate {

    public static void main(String[] args) throws LifecycleException {
        String appHome = System.getProperty("app.home");
        String appDist = System.getProperty("app.dist");
        String profile = System.getProperty("app.profile");

        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, appDist+"/conf/"+profile+"logback.xml");

        Tomcat was = new Tomcat();
        was.addWebapp("", new File(appHome).getAbsolutePath());

        was.start();
        was.getServer().await();
    }
}