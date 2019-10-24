package com.tony.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jiangwenjie 2019/10/24
 */
@Configuration
@ComponentScan
@Import({CommonConfiguration.class})
public class AutoConfiguration {

}
