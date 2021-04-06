package guru.users.borisovich.controller;

import guru.users.borisovich.property.WebSecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/authorization")
public class AuthorizationController {

    private final WebSecurityProperties properties;

    public AuthorizationController(WebSecurityProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("login");

        mav.addObject("security", properties);

        return mav;
    }

}
