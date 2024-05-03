package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {
    private final MemoryUserRepository userRepository;

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
    @RequestMapping("/login")
    public ModelAndView doLogin(@RequestParam("userId") String userId, @RequestParam("password") String password,
                                HttpServletRequest req){
        log.info("LoginController.dologin");
        User user=userRepository.findByUserId(userId);
        if(user != null && user.matchPassword(password)){
            req.getSession().setAttribute("user",user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }
    /**
     * TODO: logout
     */
    @RequestMapping("/logout")
    public ModelAndView doLogout(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        return new ModelAndView( "redirect:/");
    }
}
