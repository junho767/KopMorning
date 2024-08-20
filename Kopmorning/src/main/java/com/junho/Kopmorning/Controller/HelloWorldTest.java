package com.junho.Kopmorning.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
public class HelloWorldTest {
    @GetMapping("/api/hello")
    public String test(){
        return "Hello, World!! + test";
    }
}
