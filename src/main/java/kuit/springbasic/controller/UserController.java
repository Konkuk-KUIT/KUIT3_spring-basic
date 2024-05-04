package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/list")
    public ModelAndView showUserList(HttpServletRequest request) {
        if (UserSessionUtils.isLoggedIn(request.getSession())) {
            return new ModelAndView("/user/list")
                    .addObject("users", memoryUserRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    @RequestMapping("/form")
    public String showUserForm() {
        return "/user/form";
    }

    @RequestMapping("/signup")
    public ModelAndView createUser(@RequestParam("userId") String userId, @RequestParam("password") String password,
                                 @RequestParam("name") String name, @RequestParam("email") String email) {
        User user = new User(userId, password, name, email);

        memoryUserRepository.addUser(user);

        return new ModelAndView("redirect:/user/loginForm");
    }

    @RequestMapping("/updateForm")
    public String showUserUpdateForm() {
        return "/user/updateForm";
    }

    @RequestMapping("/update")
    public ModelAndView updateUser(@ModelAttribute User updatedUser, HttpServletRequest request) {
        memoryUserRepository.changeUserInfo(updatedUser);

        // 세션에 존재하는 user 정보도 갱신해야 함
        request.getSession().setAttribute("user", updatedUser);

        return new ModelAndView("redirect:/user/list");
    }
}
