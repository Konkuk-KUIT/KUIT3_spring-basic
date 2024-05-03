package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor // 의존관계 자동주입
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

    @RequestMapping("/login")
    public ModelAndView doLogin(@RequestParam("userId") String userId, @RequestParam("password") String password,
                                HttpServletRequest request){
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null && user.isSameUser(userId, password)){
            request.getSession().setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/user/loginFailed");
    }

    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
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
