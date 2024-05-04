package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;


    @RequestMapping("/loginForm")
    public ModelAndView showLoginForm() {

        ModelAndView modelAndView = new ModelAndView("user/login");

        return modelAndView;
    }

    @RequestMapping("/loginFailed")
    public ModelAndView showLoginFailedForm() {

        ModelAndView modelAndView = new ModelAndView("user/loginFailed");

        return modelAndView;
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    //loginV1
    @RequestMapping("/login")
    public ModelAndView Login(@RequestParam("userId") String Id, @RequestParam("password") String pw,  HttpSession session) {
        User user_ = memoryUserRepository.findByUserId(Id);

        if (user_ != null && user_.isSameUser(Id, pw)) {
            session.setAttribute("user", user_);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }
    // loginV4
//    @RequestMapping("/login")
//    public ModelAndView Login(@ModelAttribute User user, HttpSession session) {
//        String userId = user.getUserId();
//        String password = user.getPassword();
//        User user_ = memoryUserRepository.findByUserId(userId);
//
//        if (user_ != null && user_.isSameUser(userId, password)) {
//            session.setAttribute("user", user_);
//            return new ModelAndView("redirect:/");
//        }
//        return new ModelAndView("redirect:/user/loginFailed");
//    }
    @RequestMapping("/logout")
    public ModelAndView Logout(@ModelAttribute User user, HttpSession session) {
        session.removeAttribute("user");
        return new ModelAndView("redirect:/");
    }

}
