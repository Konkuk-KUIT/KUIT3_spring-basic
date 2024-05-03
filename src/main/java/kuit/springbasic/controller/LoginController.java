package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    //showLoginForm
    @GetMapping("/user/loginForm")
    public String showLoginForm() {
        log.info("ShowloginFrom");
        return "user/login";
    }

    //showLoginFailed
    @GetMapping("/user/loginFailed")
    public String showLoginFailed() {
        log.info("ShowLoginFailed");
        return "user/loginFailed";
    }
    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @PostMapping("/user/login")
    public String loginV1(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpSession session) {
        log.info("Login Click");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null && user.isSameUser(userId, password)) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            return "redirect:/user/loginFailed";
        }
    }
    //logout
    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        log.info("Logout Click");
        session.removeAttribute("user");
        return "redirect:/";
    }
}
