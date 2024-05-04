package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    //showUserForm
    @GetMapping("/user/form")
    public String UserForm(){
        log.info("userForm");
        return "user/form";
    }

    //signup
    //@RequestMapping("/user/signup")
    public String creteUserV1(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email) {
        log.info("creteUserV1");
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        return "redirect:/user/loginForm";
    }
    @RequestMapping("/user/signup")
    public String creteUserV1(@ModelAttribute User user){
        log.info("creteUserV1");
        memoryUserRepository.insert(user);
        return "redirect:/user/loginForm";

    }

    //showUserList
    @RequestMapping("/user/list")
    public String showUserList(Model model, HttpSession session) {
        log.info("showUserList");
        if (UserSessionUtils.isLoggedIn(session)) {
            Collection<User> users = memoryUserRepository.findAll();
            model.addAttribute("users", users);
            return "user/list";
        } else{
            return "user/login";
        }

    }

    //showUserUpdateForm
    @GetMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId, Model model) {
        log.info("updateForm");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "user/updateForm";
        } else {
            return "redirect:/user/list";
        }
    }
    //updateUser
    //@PostMapping("user/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               Model model) {
        User user = memoryUserRepository.findByUserId(userId);
        log.info("updateUserV1");
        User updatedUser = new User(userId, password, name, email);
        memoryUserRepository.changeUserInfo(updatedUser);
        return "redirect:/";
    }

    @PostMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user) {
        memoryUserRepository.changeUserInfo(user);
        return "redirect:/user/list";
    }

}
