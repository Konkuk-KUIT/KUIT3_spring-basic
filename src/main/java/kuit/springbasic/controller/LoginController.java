package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;
    /**
     * TODO: showLoginForm
     */
    @GetMapping("/user/loginForm")
    public String showLoginForm(){
        return "/user/form";
    }
    /**
     * TODO: showLoginFailed
     */
    @GetMapping("/user/loginFailed")
    public String showLoginFailed(){
        return "/user/login_failed";
    }
    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */

    @PostMapping("/user/login")
    public String login(@RequestParam("userId")String userId,
                        @RequestParam("password")String password,
                        HttpSession session){
        User user= memoryUserRepository.findByUserId(userId);
        if (user != null && user.isSameUser(userId, password)) {
            session.setAttribute("user", user);
            return Constant.REDIRECT+"/";
        }
        return Constant.REDIRECT+"/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @GetMapping("/user/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("user");

        return Constant.REDIRECT+"/";
    }
}
