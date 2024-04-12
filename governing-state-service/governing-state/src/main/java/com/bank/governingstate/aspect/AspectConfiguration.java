package com.bank.governingstate.aspect;

import com.bank.governingstate.annotations.LogMethodTimeExecution;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class AspectConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AspectConfiguration.class);

    @Around("@annotation(com.bank.governingstate.annotations.LogMethodTimeExecution)")
    public Object enableLogMethodTimeExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        boolean logMethodTimeExecution = methodSignature.getMethod().getAnnotation(LogMethodTimeExecution.class).enabled();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        if(logMethodTimeExecution) {
            logger.info("Time Taken by "+className+"."+methodName+" is:: "+stopWatch.getTotalTimeMillis()+" ms");
        }

        return result;
    }
}
