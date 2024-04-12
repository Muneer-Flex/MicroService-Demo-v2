package com.bank.governingstate.redis;

import lombok.Data;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "application")
@Data
public class AppConfiguration {

    @NestedConfigurationProperty
    public Map<String, AppCacheConfig> caches;

    @Component
    public static class AppCacheConfig extends CacheProperties.Redis {

    }
}
