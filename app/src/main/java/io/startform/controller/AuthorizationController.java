package io.startform.controller;

import io.startform.property.HttpSecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/security")
public class AuthorizationController {

    private final HttpSecurityProperties properties;

    public AuthorizationController(HttpSecurityProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("login");

        mav.addObject("security", properties);

        return mav;
    }

}
