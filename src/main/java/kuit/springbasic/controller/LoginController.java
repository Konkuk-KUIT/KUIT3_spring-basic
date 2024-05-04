package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository userRepository;

    @GetMapping("/loginForm")

    public String showLoginForm() {
        return "/user/login";
    }

    @GetMapping("/loginFailed")
    public String showLoginFailed() {
        return "/user/loginFailed";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpServletRequest req) {
        User user = userRepository.findByUserId(userId);
        HttpSession session = req.getSession();

        if (user != null && user.isSameUser(userId, password)) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }
}
