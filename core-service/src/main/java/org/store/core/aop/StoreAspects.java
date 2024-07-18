package org.store.core.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.store.core.utils.ProfilingUtils;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class StoreAspects {
    private final ProfilingUtils profilingUtils;

    @Around("execution(public * org.store.services.*.*(..))")
    public Object servicesExecutionTimeCounting(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;
        String serviceName = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        profilingUtils.addExecutionTime(serviceName, duration);
        return out;
    }
}
