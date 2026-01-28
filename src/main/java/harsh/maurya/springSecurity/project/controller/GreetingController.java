package harsh.maurya.springSecurity.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    
    @GetMapping("/hello")
    public String signIn(){
        return "hello";
    }
}
