package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/loginForm")
    public String showLoginForm() {
        return "/user/login";
    }

    @RequestMapping("/loginFailed")
    public String showLoginFailed() {
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
    public ModelAndView doLoginV1(@RequestParam("userId") String userId, @RequestParam("password") String password,
                                  HttpServletRequest request) {
        log.info("LoginController.doLoginV1");

        User user = memoryUserRepository.findByUserId(userId);

        if (user != null && user.isSameUser(userId, password)) {
            request.getSession().setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }

    // @ModelAttribute
    // DTO 기본 생성자와 Setter 이용하는 방식
    @RequestMapping("/login")
    public ModelAndView doLoginV4(@ModelAttribute User loggedinUser, HttpServletRequest request) {
        log.info("LoginController.doLoginV4");

        User user = memoryUserRepository.findByUserId(loggedinUser.getUserId());

        if (user != null && user.isSameUser(loggedinUser.getUserId(), loggedinUser.getPassword())) {
            request.getSession().setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }

    @RequestMapping("/logout")
    public ModelAndView doLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return new ModelAndView("redirect:/");
    }
}