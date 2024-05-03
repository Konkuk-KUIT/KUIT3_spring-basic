package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor // DI 생성자 주입
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

//    @PostMapping("/login")
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

    @PostMapping("/login")
    public ModelAndView doLoginV2(@ModelAttribute User loginUser,
                                  HttpServletRequest request) {
        log.info("LoginController.doLoginV2");

        User user = memoryUserRepository.findByUserId(loginUser.getUserId());
        if (user != null && user.isSameUser(loginUser.getUserId(), loginUser.getPassword())) {
            request.getSession().setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("LoginController.doLogout");

        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
