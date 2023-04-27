package io.startform.controller;

import io.startform.utils.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RequestMapping("/poll")
public class PollController {

    @Autowired
    private RequestHelper helper;

    @GetMapping(value = "/ip", produces = {TEXT_PLAIN_VALUE})
    public @ResponseBody
    String getCsrfToken() {
        return helper.getClientIPAddress();
    }

}
