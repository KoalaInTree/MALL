package com.mmall.controller.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by CJ on 2018/1/1.
 */
@Controller
public class TopController {

    @RequestMapping(value = "about.do")
    public String about(){
        return "portal/about";
    }

    @RequestMapping(value = "register.do")
    public String register(){
        return "portal/register";
    }

}
