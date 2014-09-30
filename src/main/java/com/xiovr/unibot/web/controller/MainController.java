/**
 * Copyright (c) 2014 xio4
 * Universal bot for lineage-like games (Archeage, Lineage2 etc)
 *
 * This file is part of Unibot.
 *
 * Unibot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
    public String error() {
        return "error";
    }

    @RequestMapping(value={"/test"}, method = RequestMethod.GET)
    public String index() {
        return "Greetings from Spring Boot!";
    }

}