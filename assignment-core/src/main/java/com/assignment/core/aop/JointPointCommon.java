package com.assignment.core.aop;

import org.aspectj.lang.annotation.Pointcut;

public class JointPointCommon {
    @Pointcut("execution(* com.assignment.*.*(..))")
    public void pointCut() {}
}
