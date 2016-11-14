package com.depanker.modules.controller;

import com.depanker.modules.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by depankersharma on 03/11/16.
 */

@RestController
public class Myclass {

    @Autowired
    MyService myService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> index() {
        String id = "{ID:123}";
        myService.doSomething(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
