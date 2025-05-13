package com.assignment.core.aop;

import com.assignment.core.utils.HttpRequestUtil;
import com.assignment.core.utils.SimpleLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class LoggingAspect {
    @Around(value = "com.assignment.core.aop.JointPointCommon.pointCut()")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = HttpRequestUtil.getCurrentHttpRequest();
        Objects.requireNonNull(request);
        SimpleLogger.info("============= Start Call End Point :[{}] With method: [{}]================", request.getRequestURI(), request.getMethod());
        SimpleLogger.info("Method Name: [{}]",joinPoint.getSignature().getName());
        SimpleLogger.info("Params : [{}]", joinPoint.getArgs());
        Object result = joinPoint.proceed();
        SimpleLogger.info("============= End Call End Point :[{}]================", request.getRequestURI());
        return result;
    }
}
