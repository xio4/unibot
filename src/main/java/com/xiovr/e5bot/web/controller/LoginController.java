package com.xiovr.e5bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiovr.e5bot.data.dao.impl.ItemNameDaoImpl;
import com.xiovr.e5bot.data.model.ItemName;
import com.xiovr.e5bot.data.service.TestService;

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