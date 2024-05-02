package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

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
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

//    @RequestMapping("/signUp")
//    public String createUserV1(@RequestParam("userId") String userId,
//                             @RequestParam("password") String password,
//                             @RequestParam("name") String name,
//                             @RequestParam("email") String email){
//        User user = new User(userId, password, name, email);
//        memoryUserRepository.insert(user);
//        return "redirect://user/userList";
//    }

    @RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User signUpUser){
        User user = new User(signUpUser.getUserId(), signUpUser.getPassword(), signUpUser.getName(), signUpUser.getEmail());
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */

    @RequestMapping("/list")
    public String showUserList(){
        return "/user/list";
    }

    /**
     * TODO: showUserUpdateForm
     */

    @RequestMapping("/updateForm")
    public String showUserUpdateForm(){
        return "/user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

//    @RequestMapping("/update")
//    public String updateUserV1(@RequestParam("userId") String userId,
//                             @RequestParam("passwd") String passwd,
//                             @RequestParam("name") String name,
//                             @RequestParam("email") String email){
//        User user = new User(userId, passwd, name, email);
//        memoryUserRepository.changeUserInfo(user);
//        return "redirect://user/userList";
//    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User updateUser){
        User user = new User(updateUser.getUserId(), updateUser.getPassword(), updateUser.getName(), updateUser.getEmail());
        memoryUserRepository.changeUserInfo(user);
        return "redirect:/user/list";
    }

}
