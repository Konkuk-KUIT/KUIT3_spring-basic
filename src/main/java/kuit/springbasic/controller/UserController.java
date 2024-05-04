package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Setter
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final MemoryUserRepository memoryUserRepository;
    private final UserSessionUtils userSessionUtils;

    @RequestMapping("/form")
    public String showUserForm(){
        return "/user/form";

    }


    @RequestMapping("/signup")
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

    @RequestMapping("/list")
    public String showUserList( HttpServletRequest request, Map<String, Object> model)  {

        if (userSessionUtils.isLoggedIn(request.getSession())) {
            model.put("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    @RequestMapping("/updateForm")
    public String showUserUpdateForm(){
        return "/user/updateForm";

    }


    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/update")
    public String updateUser(@ModelAttribute User updatinguser){
        User user = new User(updatinguser.getUserId(),
                updatinguser.getPassword(),
                updatinguser.getName(),
                updatinguser.getEmail());

        memoryUserRepository.changeUserInfo(user);

        return "redirect:/user/list";
    }

}
