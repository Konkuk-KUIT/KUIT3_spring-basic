package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/user/form")
    public String showUserForm(@ModelAttribute User signningUser){
        return "/user/form";

    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
//    @RequestMapping("/user/signup")
//    public ModelAndView createUser(@ModelAttribute User signningUser){
//        User user = new User(signningUser.getUserId(),
//                signningUser.getPassword(),
//                signningUser.getName(),
//                signningUser.getEmail());
//        memoryUserRepository.insert(user);
//        return new ModelAndView( "redirect:/user/userList");
//    }

    @RequestMapping("/user/signup")
    public ModelAndView createUser(@RequestParam("userId") String userId, @RequestParam("password") String password,
                                   @RequestParam("name") String name, @RequestParam("email") String email,
                                   HttpServletRequest request){
        User user = new User(userId,password,name,email);
        memoryUserRepository.insert(user);
        return new ModelAndView("redirect:/user/loginFailed");

    }



    /**
     * TODO: showUserList
     */

    /**
     * TODO: showUserUpdateForm
     */

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

}
