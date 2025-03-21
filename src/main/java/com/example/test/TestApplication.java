// Main Spring Boot configuration class
package com.example.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration(proxyBeanMethods = false)
public class TestApplication {
    // Spring Boot configuration goes here
}