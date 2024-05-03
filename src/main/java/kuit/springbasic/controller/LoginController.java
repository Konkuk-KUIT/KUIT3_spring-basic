package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/loginForm")
    public String showLoginForm() {
        log.info("LoginController.loginForm");
        return "/user/login";
    }

    @RequestMapping("/login_failed")
    public String showLoginFailed() {
        log.info("LoginController.loginFailed");
        return "/user/loginFailed";
    }

    @RequestMapping("/v1/login")
    public String doLoginV1(@RequestParam("userId") String userId,
                            @RequestParam("password") String password, HttpServletRequest req) {
        log.info("LoginController.doLoginV1");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null && user.isSameUser(userId, password)) {
            req.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/login_failed";
    }

    @RequestMapping("/login")
    public String doLoginV2(@ModelAttribute User accessUser, HttpServletRequest req) {
        log.info("LoginController.doLoginV2");
        User user = memoryUserRepository.findByUserId(accessUser.getUserId());
        if (user != null && user.isSameUser(accessUser.getUserId(), accessUser.getPassword())) {
            req.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/login_failed";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session =req.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }





    /**
     * TODO: showLoginForm
     */

    /**
     * TODO: showLoginFailed
     */

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */

    /**
     * TODO: logout
     */

}
