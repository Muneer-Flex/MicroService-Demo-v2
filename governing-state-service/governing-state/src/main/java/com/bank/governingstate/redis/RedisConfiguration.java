package com.bank.governingstate.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    String redisHostName;

    @Value("${spring.redis.port}")
    String redisPort;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHostName);
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisPort));

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public CacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory, AppConfiguration appConfiguration) {
        Map<String, RedisCacheConfiguration> redisCacheConfig = new LinkedHashMap<>();

        for(Map.Entry<String, AppConfiguration.AppCacheConfig> appCacheConfig :appConfiguration.getCaches().entrySet()){
            redisCacheConfig.put(appCacheConfig.getKey(), createRedisCacheConfig(appCacheConfig.getValue()));
        }

        RedisCacheManager.RedisCacheManagerBuilder redisCacheManagerBuilder = RedisCacheManager.builder(lettuceConnectionFactory);

        if(!CollectionUtils.isEmpty(redisCacheConfig)) {
            RedisCacheConfiguration defaultRedisCacheConfig = redisCacheConfig.get("default");
            if(null != defaultRedisCacheConfig) {
                redisCacheManagerBuilder.cacheDefaults(defaultRedisCacheConfig);
                redisCacheConfig.remove("default");
            }

            if(!CollectionUtils.isEmpty(redisCacheConfig)) {
                redisCacheManagerBuilder.withInitialCacheConfigurations(redisCacheConfig);
            }
        }
        return redisCacheManagerBuilder.build();
    }

    private RedisCacheConfiguration createRedisCacheConfig(AppConfiguration.AppCacheConfig appCacheConfig) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        if(null != appCacheConfig.getTimeToLive()) {
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(appCacheConfig.getTimeToLive());
        }
        if(null != appCacheConfig.getKeyPrefix()) {
            redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(appCacheConfig.getKeyPrefix());
        }
        if(!appCacheConfig.isUseKeyPrefix()) {
            redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
        }
        if(!appCacheConfig.isCacheNullValues()) {
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        }
        return redisCacheConfiguration;
    }
}
