package com.oppo.api.Opportunity.API.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControllerTest {
    @GetMapping("/helloworld")
    public String helloWorld(){
        return "Hello, World!";
    }
}
