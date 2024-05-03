package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    @GetMapping("/form")
    public String showUserForm() {
        return "/user/form";
    }


    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user){
        memoryUserRepository.insert(user);
        return "redirect:/user/loginForm";
    }



    @GetMapping("/list")
    public String showUserList(HttpSession session, Model model) {
        if(UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "/user/login";
    }


    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, Model model){
        User user = memoryUserRepository.findByUserId(userId);
        if(user != null) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "redirect:/";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }

}
