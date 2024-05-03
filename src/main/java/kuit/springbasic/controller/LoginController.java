package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static kuit.springbasic.util.UserSessionUtils.isLoggedIn;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemoryUserRepository userRepository;
    /**
     * TODO: showLoginForm
     */
    @RequestMapping("/user/loginForm")
    public String showLoginForm(){
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/user/loginFailed")
    public String showLoginFailed(){
        return "/user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping("/user/login")
    public String login(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpServletRequest req){
        HttpSession session = req.getSession();
        User user = userRepository.findByUserId(userId);
        if(user != null && user.isSameUser(userId, password)){
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }


    /**
     * TODO: logout
     */
    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession();
        if(isLoggedIn(session)){
            session.removeAttribute("user");
        }
        return "redirect:/";
    }
}
