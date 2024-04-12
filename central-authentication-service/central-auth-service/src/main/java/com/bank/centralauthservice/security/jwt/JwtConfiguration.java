package com.bank.centralauthservice.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfiguration {

    private Map<String, JwtProperty> securityTokenBag;

    @Component
    public static class JwtProperty {
        private String secretKey;

        private Long expirationTime;

        public JwtProperty() {
            //Default Constructor
        }

        public JwtProperty(String secretKey, long expirationTime) {
            this.secretKey = secretKey;
            this.expirationTime = expirationTime;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public Long getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(Long expirationTime) {
            this.expirationTime = expirationTime;
        }
    }
}
