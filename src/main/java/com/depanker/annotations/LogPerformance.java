package com.depanker.annotations;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.*;

/**
 * Created by depankersharma on 04/11/16.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogPerformance {
    LogLevel level();
}
