package com.example.springIt.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties("springit")
public class SpringitProperties {


    /**
     * This is our welcome msg
     */
    private String welcomeMsg = "Hello World!";


    /**
     *  This is a test msg
     */

    private String testMsg = "Test Message";


    public String getWelcomeMsg() {
        return welcomeMsg;
    }

    public void setWelcomeMsg(String welcomeMsg) {
        this.welcomeMsg = welcomeMsg;
    }

    public String getTestMsg() {
        return testMsg;
    }

    public void setTestMsg(String testMsg) {
        this.testMsg = testMsg;
    }
}
