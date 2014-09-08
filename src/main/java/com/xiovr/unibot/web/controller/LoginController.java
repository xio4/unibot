package com.xiovr.unibot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiovr.unibot.data.service.TestService;

/**
 * @author xio4
 * Simple login controller
 */
@Controller
class LoginController {

	@Autowired
	TestService testService;
    /**
     * @return login.html template name
     */
    @RequestMapping(value ={"/login"}, method = RequestMethod.GET)
    public String login() {
    	testService.test();
        return "login";
    }
}