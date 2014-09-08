package com.xiovr.unibot.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xio4 
 * Restful controller to exchange with AngularJS
 */
@RestController
@RequestMapping(value = {"/"})
class MainController {

    @RequestMapping(value ={"/error"}, method = RequestMethod.GET)
    @ResponseStatus( HttpStatus.CREATED)
    public String error() {
        return "Error access";
    }

    @RequestMapping(value={"/test"}, method = RequestMethod.GET)
    public String index() {
        return "Greetings from Spring Boot!";
    }

}