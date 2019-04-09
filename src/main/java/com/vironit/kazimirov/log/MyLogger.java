package com.vironit.kazimirov.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class MyLogger {
    private static final Logger LOG =Logger.getLogger(MyLogger.class);


    @Pointcut("execution(* *(..))&&within(com.vironit.kazimirov.controller.*)")
    private void allMethods() {
    }

    @Around("allMethods()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();
        System.err.println("WE HERE");


        LOG.info("Call method " + methodName);

        Object output = joinPoint.proceed();
        System.err.println("WE HERE");

        LOG.info("Method "+ methodName+" end ");

        return output;
    }
}


