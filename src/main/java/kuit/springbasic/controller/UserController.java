package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.Constant;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @GetMapping("/user/form")
    public String showSignupForm(){
        return "/user/form";
    }
    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @PostMapping("/user/signup")
    public String signup(@RequestParam("userId")String userId,
                         @RequestParam("password")String password,
                         @RequestParam("name")String name,
                         @RequestParam("email")String email,
                         HttpSession httpSession){
       User user=new User(userId,password,name,email);
       memoryUserRepository.insert(user);
       httpSession.setAttribute("user",user);
       return Constant.REDIRECT+ "/user/userList";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/user/userList")
    public ModelAndView showUserList(HttpSession httpSession){
        ModelAndView mv=new ModelAndView();
        if(UserSessionUtils.isLoggedIn(httpSession)){
            mv.setViewName("/user/list");
            mv.addObject("users",memoryUserRepository.findAll());
            return mv;
        }
        mv.setViewName(Constant.REDIRECT+"/user/loginForm");
        return mv;

    }
    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/user/updateForm")
    public ModelAndView showUpdateForm(@RequestParam("userId")String userId){
        ModelAndView mv=new ModelAndView();

        User user = memoryUserRepository.findByUserId(userId);
        if(user!=null){
            mv.addObject("user",user);
            mv.setViewName("/user/updateForm");
            return mv;
        }
        mv.setViewName(Constant.REDIRECT+"/");
        return mv;
    }
    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    @PostMapping("/user/update")
    public String updateUser(@RequestParam("userId")String userId,
                                   @RequestParam("password")String password,
                                   @RequestParam("name")String name,
                                   @RequestParam("email")String email){
        User user=new User(userId,password,name,email);
        memoryUserRepository.changeUserInfo(user);
        return Constant.REDIRECT+ "/user/userList";
    }
}
