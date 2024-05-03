package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Setter
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;
    private final UserSessionUtils userSessionUtils;

    @RequestMapping("/user/form")
    public String showUserForm(@ModelAttribute User signningUser){
        return "/user/form";

    }


    @RequestMapping("/user/signup")
    public ModelAndView createUser(@ModelAttribute User signningUser){
        User user = new User(signningUser.getUserId(),
                signningUser.getPassword(),
                signningUser.getName(),
                signningUser.getEmail());
        memoryUserRepository.insert(user);
        return new ModelAndView( "redirect:/user/list");
    }

    /**
     * @RequestParam 이용한 createUser
     */
//    @RequestMapping("/user/signup")
//    public ModelAndView createUser(@RequestParam("userId") String userId, @RequestParam("password") String password,
//                                   @RequestParam("name") String name, @RequestParam("email") String email,
//                                   HttpServletRequest request){
//        User user = new User(userId,password,name,email);
//        memoryUserRepository.insert(user);
//        return new ModelAndView("redirect:/user/loginFailed");
//    }



    /**
     * TODO: showUserList
     */

    @RequestMapping("/user/list")
    public String showUserList( HttpServletRequest request, Map<String, Object> model)  {

        if (userSessionUtils.isLoggedIn(request.getSession())) {
            model.put("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

}
