package com.bank.createcustomerprofile.filter;

import com.bank.createcustomerprofile.constants.ErrorConstants;
import com.bank.createcustomerprofile.jwt.JwtService;
import com.bank.createcustomerprofile.model.ApiExceptionResponse;
import com.bank.createcustomerprofile.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Configuration
public class AppFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AppFilter.class);

    private final JwtService jwtService;

    public AppFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (null == bearerToken || !bearerToken.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = bearerToken.substring(7);
            if (jwtService.isJwtValid(jwt)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        null,
                        null,
                        null
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            logger.error("Exception occurred during JWT Validation. Exception is:: {}", exception);

            ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                    .errorCode(ErrorConstants.CUST_003)
                    .errorDescription(exception.getMessage())
                    .createdOn(LocalDateTime.now())
                    .build();

            PrintWriter printWriter = response.getWriter();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            printWriter.print(AppUtils.convertToJson(apiExceptionResponse));
            printWriter.flush();
        }
    }
}
