package com.depanker.modules.service;

import com.depanker.annotations.LogPerformance;
import com.depanker.annotations.Loggable;
import org.springframework.stereotype.Service;

import static org.springframework.boot.logging.LogLevel.DEBUG;

//import static com.depanker.annotations.Loggable.LEVEL.*;

/**
 * Created by depankersharma on 03/11/16.
 */
@Service
public class MyService {

    @Loggable(level = DEBUG)
    @LogPerformance(level = DEBUG)
    public void doSomething(String str) {
        System.out.println("str = " + str);
    }
}
