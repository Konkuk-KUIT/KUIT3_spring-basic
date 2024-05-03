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
//UserController에서도 /user prefix 쓰는데 여기서 다 받아버려도 되나 -> 정상작동 확인함
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @GetMapping("/loginFailed")
    public String loginFailed() {
        return "/user/loginFailed";
    }

    @PostMapping("/loginV1")
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
    // @ModelAttribute : 기본생성자와 Setter 이용해 알아서 인자를 객체로 만들어줌
    public ModelAndView doLoginV2(@ModelAttribute User loggedinUser, HttpServletRequest request) {
        log.info("LoginController.doLoginV2");
        User user = memoryUserRepository.findByUserId(loggedinUser.getUserId());
        if (user != null && user.isSameUser(loggedinUser.getUserId(), loggedinUser.getPassword())) {
            request.getSession().setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("LoginController.logout");
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
