package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final MemoryUserRepository memoryUserRepository;
    //@RequestMapping("signup")
    public ModelAndView CreateUserV1(@RequestParam("userId")String userId, @RequestParam("password")String password,@RequestParam("name")String name, @RequestParam("email")String email){
        log.info("LoginContorrler.CreateUserV1");
        User user = new User(userId,password,name,email);
        memoryUserRepository.insert(user);
        return new ModelAndView("redirect:/user/userList");
    }
    @RequestMapping("/signup")
    public ModelAndView CreateUserV2(@ModelAttribute User signUpUser){
        log.info("LoginContorrler.CreateUserV2");
        memoryUserRepository.insert(signUpUser);
        return new ModelAndView("redirect:/user/list");
    }

    @RequestMapping("/list")
    public String showUserList(HttpServletRequest request, Model model){
        if (UserSessionUtils.isLoggedIn(request.getSession())){
            model.addAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "/user/login";
    }

    @RequestMapping("/updateForm")
    public String showUserUpdateForm(@ModelAttribute User logginedUser, Model model){
       if(logginedUser != null){
           model.addAttribute("user", logginedUser);
           return "/user/updateForm";
       }
        return "redirect:/";
    }

    @RequestMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }

}
