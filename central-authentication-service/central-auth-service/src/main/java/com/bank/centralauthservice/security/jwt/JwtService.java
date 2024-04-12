package com.bank.centralauthservice.security.jwt;

import com.bank.centralauthservice.constants.ErrorConstants;
import com.bank.centralauthservice.constants.SecurityConstants;
import com.bank.centralauthservice.entity.Users;
import com.bank.centralauthservice.exception.JwtSecretKeyNotFoundException;
import com.bank.centralauthservice.model.AuthenticationRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final JwtConfiguration jwtConfiguration;

    public JwtService(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public String generateJwtToken(Users users, AuthenticationRequest authenticationRequest) throws Exception {
        return generateJwtToken(new LinkedHashMap<>(), users, authenticationRequest);
    }
    public String generateJwtToken(Map<String, String> claims, Users users, AuthenticationRequest authenticationRequest) throws Exception {
        return Jwts.builder()
                .setClaims(claims)//TODO: Setting Empty Claims. In Future, customized Claims can be set
                .setSubject(users.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + deriveExpiration(authenticationRequest)))
                .signWith(getSignInKey(authenticationRequest), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey(AuthenticationRequest authenticationRequest) throws Exception {
        JwtConfiguration.JwtProperty jwtProperty = fetchJwtProperty(authenticationRequest.getAccessRequestedFor());
        if (null != jwtProperty && null != jwtProperty.getSecretKey()) {
            byte[] keyBytes = Decoders.BASE64.decode(jwtProperty.getSecretKey());
            return Keys.hmacShaKeyFor(keyBytes);
        }
        logger.error("JWT Secret Key Not Found in Central Auth Configuration. Please Contact Technical Support");
        throw new JwtSecretKeyNotFoundException(ErrorConstants.AUTH_002, ErrorConstants.AUTH_OO2_DESCRIPTION);
    }

    public long deriveExpiration(AuthenticationRequest authenticationRequest) {
        JwtConfiguration.JwtProperty jwtProperty = fetchJwtProperty(authenticationRequest.getAccessRequestedFor());
        if (null != jwtProperty && null != jwtProperty.getExpirationTime()) {
            return jwtProperty.getExpirationTime();
        }
        logger.error("JWT Expiration Configuration not available for {} . So, using default JWT Expiration Time", authenticationRequest.getAccessRequestedFor());
        return SecurityConstants.DEFAULT_JWT_EXPIRATION;
    }

    private JwtConfiguration.JwtProperty fetchJwtProperty(String accessRequestedFor) {
        if (!CollectionUtils.isEmpty(jwtConfiguration.getSecurityTokenBag()) && null != accessRequestedFor) {
            Map<String, JwtConfiguration.JwtProperty> jwtConfigs = jwtConfiguration.getSecurityTokenBag();
            return jwtConfigs.get(accessRequestedFor);
        }
        return null;
    }


}
