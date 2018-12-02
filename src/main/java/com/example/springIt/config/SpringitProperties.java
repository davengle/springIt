package com.example.springIt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties("springit")
@Getter
@Setter
public class SpringitProperties {


    /**
     * This is our welcome msg
     */
    private String ymlMsg = "Yml message from class";


    /**
     *  This is a test msg
     */

    private String envSpecificMsg = "Env Specific Message from class";

    /**
     /**
     *  This message will display if overwritten in properties, but not in env specific properties file or yaml file
     */

    private String propertiesMsg = "Properties Message from class";

     /**
     *  This message will display if not overwritten in properties, property specific properties file or yaml file
     */

    private String defaultMsg = "Default Message from class";
}
