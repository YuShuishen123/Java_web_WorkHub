package java_web.userservice.controller;

import java_web.userservice.config.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class userscontroller {
    private final UserService userService;

    public userscontroller(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/test")
    public String test() {
        return "Hello World!";
    }
}
