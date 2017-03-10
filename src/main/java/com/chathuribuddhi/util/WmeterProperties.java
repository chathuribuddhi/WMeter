package com.chathuribuddhi.util;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by CHATHURI on 2017-02-26.
 */
public class WmeterProperties {
    private static WmeterProperties instance = new WmeterProperties();
    private final static Logger logger = Logger.getLogger(WmeterProperties.class.getName());

    public static WmeterProperties getInstance() {
        return instance;
    }

    private WmeterProperties() {
    }

    public Properties getProperties(){
        Properties properties = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            File file = new File(classLoader.getResource("wmeter.properties").getFile());
            final FileInputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (Exception e) {
            logger.error("wmeter.properties file read error", e);
        }
        return properties;
    }

    public static void main(String[] args) {
        Properties prop = WmeterProperties.getInstance().getProperties();
        System.out.println(prop);
    }
}
