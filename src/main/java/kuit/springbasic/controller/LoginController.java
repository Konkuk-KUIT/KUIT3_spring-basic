package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */

    @RequestMapping("/loginForm")
    public String showLoginForm(){
        return "/user/login";
    }


    /**
     * TODO: showLoginFailed
     */

    @RequestMapping("/loginFailed")
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

//    @RequestMapping("/login")
//    public ModelAndView loginV1(@RequestParam("userId") String userId, @RequestParam("passwd") String passwd, HttpServletRequest request){
//        User user = memoryUserRepository.findByUserId(userId);
//        if(user!= null && user.isSameUser(userId, passwd)){
//            request.getSession().setAttribute("user", user);
//            return new ModelAndView("redirect://");
//        }
//        return new ModelAndView("redirect://user/loginFailed");
//    }

    @RequestMapping("/login")
    public ModelAndView loginV4(@ModelAttribute User loggedinUser, HttpServletRequest request){
        User user = memoryUserRepository.findByUserId(loggedinUser.getUserId());
        if(user!= null && user.isSameUser(loggedinUser.getUserId(), loggedinUser.getPassword())){
            request.getSession().setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }

    /**
     * TODO: logout
     */

    @RequestMapping("/logout")
    public String logout(@ModelAttribute User user, HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
