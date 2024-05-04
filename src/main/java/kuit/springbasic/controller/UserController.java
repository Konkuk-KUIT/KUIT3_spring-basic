package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final MemoryUserRepository memoryUserRepository;

// showUserForm
    @RequestMapping("/form")
    public String showSignUpForm() {
        return "/user/form";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

//    @RequestMapping("/signup")
    public String doSignUpV1(@RequestParam("userId") String userId,
                           @RequestParam("password") String password,
                           @RequestParam("name") String name,
                           @RequestParam("email") String email,
                           HttpSession httpSession) {
        log.info("UserController signupV1");
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        httpSession.setAttribute("user", user);
        return "redirect:/user/list";
    }
    @RequestMapping("/signup")
    public ModelAndView doSignUpV2(@ModelAttribute User user) {
        log.info("UserController signupV2");
        memoryUserRepository.insert(user);
        return new ModelAndView("redirect:/user/list");
    }

// showUserList
    @RequestMapping("/list")
    public ModelAndView showUserList(HttpServletRequest request) {
        log.info("UserController showUserList");
        User user = (User) request.getSession().getAttribute("user");
         if (user != null) {
             ModelAndView modelAndView = new ModelAndView("/user/list");
             modelAndView.getModel().put("users", memoryUserRepository.findAll());
             return modelAndView;
        }
         return new ModelAndView("redirect:/user/loginForm");
     }

// showUserUpdateForm
    @RequestMapping("/updateForm")
    public ModelAndView showUserUpdateForm(@RequestParam String userId, HttpServletRequest request) {
        log.info("UserController updateForm");
        User loginedUser = (User) request.getSession().getAttribute("user");
        User user = memoryUserRepository.findByUserId(userId);
        if(user != null && loginedUser != null && loginedUser.getUserId().equals(userId)) {
            ModelAndView modelAndView = new ModelAndView("/user/updateForm");
            modelAndView.getModel().put("user", user);
            return modelAndView;
        }
        return new ModelAndView("redirect:/user/list");
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

//    @RequestMapping("/update")
    public ModelAndView updateUserV1(@RequestParam String userId,
                                     @RequestParam String password,
                                     @RequestParam String name,
                                     @RequestParam String email) {
        log.info("UserController updateUserV1");
        User user = new User(userId, password, name, email);
        memoryUserRepository.changeUserInfo(user);
        return new ModelAndView("redirect:/user/list");
    }

    @RequestMapping("/update")
    public ModelAndView updateUser(@ModelAttribute User user) {
        log.info("UserController updateUser");
        memoryUserRepository.changeUserInfo(user);
        return new ModelAndView("redirect:/user/list");
    }


}
