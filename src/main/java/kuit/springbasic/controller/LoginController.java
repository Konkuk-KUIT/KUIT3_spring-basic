package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemoryUserRepository memoryUserRepository;


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

    //@RequestMapping("/user/login")
    public String loginV1(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpServletRequest request){
        User user = memoryUserRepository.findByUserId(userId);

        if (user != null && user.isSameUser(userId, password)) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";

    }

    //@RequestMapping("/user/login")
    public String loginV2(@RequestParam String userId, @RequestParam String password, HttpServletRequest request){
        User user = memoryUserRepository.findByUserId(userId);

        if (user != null && user.isSameUser(userId, password)) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";

    }

    //@RequestMapping("/user/login")
    public String loginV3(String userId, String password, HttpServletRequest request){

        User user = memoryUserRepository.findByUserId(userId);

        if (user != null && user.isSameUser(userId, password)) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";

    }

    @RequestMapping("/user/login")
    public String loginV4(@ModelAttribute User user, HttpServletRequest request){

        User repoUser = memoryUserRepository.findByUserId(user.getUserId());

        if (repoUser != null && repoUser.isSameUser(user.getUserId(), user.getPassword())) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";

    }

    /**
     * TODO: logout
     */
    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

}
