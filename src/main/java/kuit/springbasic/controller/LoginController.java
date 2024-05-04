package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    @GetMapping("/loginForm")
    public String showLoginForm(){
        return "/user/login";
    }

    @GetMapping("/loginFailed")
    public String showLoginFailed(){
        return "/user/loginFailed";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute User loggedinUser, HttpServletRequest request){
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

    @GetMapping("/logout")
    public String logout(@ModelAttribute User user, HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

}
