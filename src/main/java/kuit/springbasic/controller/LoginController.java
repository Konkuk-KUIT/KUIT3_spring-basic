package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import kuit.springbasic.db.MemoryUserRepository;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {
    private final MemoryUserRepository memoryUserRepository;

    @GetMapping("/loginForm")
    public String showLoginForm() {
        return "/user/login";
    }

    @GetMapping("/loginFailed")
    public String showLoginFailed() {
        return "/user/loginFailed";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request){
        User user2 = memoryUserRepository.findByUserId(user.getUserId());

        if (user2 != null && user2.isSameUser(user.getUserId(), user.getPassword())) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }


    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}




