package fr.pala.accounting.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class HomeController {

    @RequestMapping("/helloworld")
    @ResponseBody
    public String writeHello() {
        return "Hello World";
    }

}
