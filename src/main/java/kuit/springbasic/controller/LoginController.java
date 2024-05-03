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

import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/loginForm")
    public String showLoginForm(){
        return "/user/login";
    }



    @RequestMapping("/loginFailed")
    public String showLoginFailed(){
        return "user/loginFailed";
    }



//    @RequestMapping("/login")
//    public ModelAndView login(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpServletRequest request){
//        User user = memoryUserRepository.findByUserId(userId);
//
//        if (user != null && user.isSameUser(userId, password)) {
//            request.getSession().setAttribute("user", user);
//            return new ModelAndView("redirect:/");
//        }
//        return new ModelAndView("redirect:/user/loginFailed");
//
//    }

    @RequestMapping("/login")
    public ModelAndView login(@ModelAttribute User loggedUser, HttpServletRequest request){
        User user = memoryUserRepository.findByUserId(loggedUser.getUserId());

        if (user != null && user.isSameUser(loggedUser.getUserId(), loggedUser.getPassword())) {
            request.getSession().setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }


    /**
     * TODO: logout
     */

}
