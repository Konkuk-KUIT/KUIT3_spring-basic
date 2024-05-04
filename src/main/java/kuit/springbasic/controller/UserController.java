package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final MemoryUserRepository userRepository;

    @GetMapping("/form")
    public String showUserForm() {
        return "/user/form";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user) {
        userRepository.insert(user);

        return "redirect:/user/loginForm";
    }

    @GetMapping("/list")
    public String showUserList(Model model, HttpServletRequest req) {
        if(UserSessionUtils.isLoggedIn(req.getSession())){
            model.addAttribute("users", userRepository.findAll());

            return "/user/list";
        }

        return "redirect:/user/loginForm";
    }

    @GetMapping("/updateForm")
    public String showUserUpdateForm(Model model, @RequestParam String userId, @SessionAttribute User user) {
        User user2 = userRepository.findByUserId(userId);
        if (user2 != null && user2.isSameUser(user)) {
            model.addAttribute("user", user);

            return "/user/updateForm";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {

        userRepository.changeUserInfo(user);

        return "redirect:/user/userList";
    }
}
