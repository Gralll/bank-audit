package com.gralll.bankaudit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gralll.bankaudit.domain.util.RateDataGenerator;

@Configuration
public class RateDataConfiguration {

    private final Logger log = LoggerFactory.getLogger(RateDataConfiguration.class);

    @Bean
    public RateDataGenerator rateDataGenerator() {
        log.debug("RATE_DATA_CREATED!");
        return new RateDataGenerator();
    }

}
