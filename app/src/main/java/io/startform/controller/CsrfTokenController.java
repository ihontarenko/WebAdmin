package io.startform.controller;

import io.startform.common.i18n.Translator;
import io.startform.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@Controller
@RequestMapping("/security")
public class CsrfTokenController {

    @Autowired
    private Translator translator;

    @Autowired
    private UserRepository repository;

    @GetMapping(value = "/csrf", produces = {TEXT_PLAIN_VALUE})
    @ResponseBody
    public String getCsrfToken(HttpServletRequest request) {
        return ((CsrfToken) request.getAttribute(CsrfToken.class.getName())).getToken();
    }

    @GetMapping(value = "/text", produces = {TEXT_PLAIN_VALUE})
    @ResponseBody
    public String getText() {
        return translator.getMessage(
                "application.title"
        ) + repository.getOne("USERNAME", "ADMIN");
    }

}