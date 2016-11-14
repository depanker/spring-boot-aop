package com.depanker.util;

import com.depanker.annotations.Loggable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/**
 * Created by depankersharma on 03/11/16.
 */
@Component
@Aspect
public class MyLogger {

    /**
     * Handle to the log file
     */
    private final Log log = LogFactory.getLog(getClass());

    public MyLogger() {
    }

    @AfterReturning(value = "execution(* com.depanker.modules..*.*(..)) " +
            "&& @annotation(com.depanker.annotations.Loggable) && @annotation(logme) ", returning = "returnValue",
            argNames = "joinPoint,returnValue,logme")
    public void logMethodAccessAfter(JoinPoint joinPoint, Object returnValue, Loggable logme) {

        logBasedOnLevel(logme.level(), "***** Completed: " + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + " *****" + returnValue);
    }

    @Before("execution(* com.depanker.modules..*.*(..))" +
            " && @annotation(com.depanker.annotations.Loggable) && @annotation(logme)")
    public void logMethodAccessBefore(JoinPoint joinPoint, Loggable logme) {
        logBasedOnLevel(logme.level(), "***** Starting: " + joinPoint.getSignature().getName() + " *****" + Arrays.asList(joinPoint.getArgs()));
    }

    @Around("execution(* com.depanker.modules..*.*(..)) && @annotation(com.depanker.annotations.LogPerformance)" +
            "&& @annotation(logme)")
    public void logPerformance(ProceedingJoinPoint joinPoint, Loggable logme) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            //Do Noting
        }
        stopWatch.stop();

        StringBuffer logMessage = new StringBuffer();

        logMessage.append(" execution time: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");
        logBasedOnLevel(logme.level(), logMessageComposer(joinPoint, logMessage.toString()));
    }

    private String logMessageComposer(JoinPoint joinPoint, String extraMessage) {
        StringBuffer logMessage = new StringBuffer();
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");
        // append args
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            logMessage.append(args[i]).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }

        logMessage.append(")");
        logMessage.append(" "+extraMessage);
        return logMessage.toString();
    }

    private void logBasedOnLevel(LogLevel level, String message) {
        logBasedOnLevel(level, message, null);
    }

    private void logBasedOnLevel(LogLevel level, String message, Throwable object) {
        switch (level) {
            case TRACE:
                log.trace(message);
                break;
            case DEBUG:
                log.debug(message);
                break;
            case INFO:
                log.info(message);
                break;
            case WARN:
                log.warn(message);
                break;
            case ERROR:
                if (object != null) {
                    log.error(message, object);
                } else {
                    log.error(message);
                }
                break;
        }
    }
}
