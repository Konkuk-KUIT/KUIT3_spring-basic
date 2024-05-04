package kuit.springbasic.controller;


import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static kuit.springbasic.util.UserSessionUtils.isLoggedIn;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String showUserForm(){
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    //@RequestMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email){
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        return "redirect:/";
    }

    @RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User user){
        memoryUserRepository.insert(user);
        return "redirect:/";
    }



    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public String showUserList(Model model, HttpServletRequest request){
       if (isLoggedIn(request.getSession())) {
           model.addAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public String showUserUpdateForm(HttpServletRequest request, Model model){
        String userId = request.getParameter("userId");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null && isLoggedIn(request.getSession())) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //@RequestMapping("/update")
    public String updateUserV1(HttpServletRequest request, @RequestParam("userId") String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email, Model model){
        User user = new User(userId, password, name, email);
        if (isLoggedIn(request.getSession())) {
            memoryUserRepository.changeUserInfo(user);
            return "redirect:/user/list";
        }
        return "redirect:/";
    }

    @RequestMapping("/update")
    public String updateUserV2(HttpServletRequest request, @ModelAttribute User user, Model model){
        if (isLoggedIn(request.getSession())) {
            memoryUserRepository.changeUserInfo(user);
            return "redirect:/user/list";
        }
        return "redirect:/";
    }



}
