package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;
    @RequestMapping("/loginForm")
    public String showLoginForm(){
        return "/user/login";
    }

    @RequestMapping("/loginFailed")
    public String showLoginFailed(){
        return "/user/loginFailed";
    }

    //@RequestMapping("/login")
    public ModelAndView doLoginV1(@RequestParam("userId") String userId,
                                @RequestParam("password")String password,
                                HttpServletRequest request){
        log.info("LoginController.dologinV1");
        User user = memoryUserRepository.findByUserId(userId);
        if(user !=null && user.isSameUser(userId,password)){
            request.getSession().setAttribute("user",user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");

    }

    //@RequestMapping("/login")
    public ModelAndView doLoginV2(@RequestParam String userId, @RequestParam String password, HttpServletRequest request){
        log.info("LoginController.dologinV2");
        User user = memoryUserRepository.findByUserId(userId);
        if(user !=null && user.isSameUser(userId,password)){
            request.getSession().setAttribute("user",user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");

    }

    //@RequestMapping("/login")
    public ModelAndView doLoginV3(String userId, String password, HttpServletRequest request){
        log.info("LoginController.dologinV3");
        User user = memoryUserRepository.findByUserId(userId);
        if(user !=null && user.isSameUser(userId,password)){
            request.getSession().setAttribute("user",user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");

    }


    // 이 방법은 getter, setter가 있어야함
    @RequestMapping("/login")
    public ModelAndView doLoginV4(@ModelAttribute User loggedinUser, HttpServletRequest request){
        log.info("LoginController.dologinV4");
        User user = memoryUserRepository.findByUserId(loggedinUser.getUserId());
        if(user != null && user.isSameUser(loggedinUser.getUserId(), loggedinUser.getPassword())){
            request.getSession().setAttribute("user",user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }

    @RequestMapping("/loginFaild")
    public ModelAndView logout(@ModelAttribute User loggedoutUser, HttpServletRequest request){
        log.info("LogOutController");
        User user = memoryUserRepository.findByUserId(loggedoutUser.getUserId());
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }


    /**
     * TODO: logout
     */

}
