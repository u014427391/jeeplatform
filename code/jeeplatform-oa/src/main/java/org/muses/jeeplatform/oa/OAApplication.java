package org.muses.jeeplatform.oa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OAApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAApplication.class, args);
    }

    @GetMapping("/test")
    public String test(){
        return "hello world!";
    }


}
