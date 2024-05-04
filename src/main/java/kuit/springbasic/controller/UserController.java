package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    @GetMapping("/form")
    public String showUserForm(){
        return "/user/form";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User signUpUser){
        User user = new User(signUpUser.getUserId(), signUpUser.getPassword(), signUpUser.getName(), signUpUser.getEmail());
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public ModelAndView showUserList(HttpServletRequest request){
        if(UserSessionUtils.isLoggedIn(request.getSession())){
            return new ModelAndView("user/list").addObject("users", memoryUserRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }


    @GetMapping("/updateForm")
    public String showUserUpdateForm(){
        return "/user/updateForm";
    }

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User updateUser){
        memoryUserRepository.changeUserInfo(updateUser);
        return "redirect:/user/list";
    }

}
