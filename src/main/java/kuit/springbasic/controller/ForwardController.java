package kuit.springbasic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class ForwardController {
    @RequestMapping("/loginForm")
    public String showLoginForm(){
        return "/user/login";
    }

    @RequestMapping("/loginFailed")
    public String showLoginFailed(){
        return "/user/loginFailed";
    }
    @RequestMapping("/form")
    public String showUserForm(){
        return "/user/form";
    }
}
