package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import static kuit.springbasic.util.UserSessionUtils.getUserFromSession;
import static kuit.springbasic.util.UserSessionUtils.isLoggedIn;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @GetMapping("/form")
    public String showUserForm() {
        log.info("UserController.showUserForm");

        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
//    @PostMapping("/signup")
//    public ModelAndView createUser(@RequestParam("userId") String userId, @RequestParam("password") String password,
//                                   @RequestParam("name") String name, @RequestParam("email") String email) {
//        log.info("LoginController.signUpV1");
//
//        User user = new User(userId, password, name, email);
//        memoryUserRepository.insert(user);
//
//        return new ModelAndView("redirect:/");
//    }

    @PostMapping("/signup")
    public ModelAndView createUser(@ModelAttribute User newUser) {
        log.info("UserController.signUpV2");

        User user = new User(newUser.getUserId(), newUser.getPassword(), newUser.getName(), newUser.getEmail());
        memoryUserRepository.insert(user);

        return new ModelAndView("redirect:/");
    }


    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public ModelAndView showUserList(HttpServletRequest request) {
        log.info("UserController.showUserList");

        HttpSession session = request.getSession();
        if (isLoggedIn(session)) {
            return new ModelAndView("redirect:/user/list");
        }
        return new ModelAndView("user/login");

    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public ModelAndView showUserUpdateForm(HttpServletRequest request) {
        log.info("UserController.showUserUpdateForm");
        ModelAndView modelAndView = new ModelAndView();

        HttpSession session = request.getSession();
        User loginUser = getUserFromSession(session);
        if (loginUser != null) {
            modelAndView.addObject("users",loginUser);
            modelAndView.setViewName("user/updateForm");
            return modelAndView;
        }
        return new ModelAndView("redirect:/user/loginForm");

    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @RequestMapping("/update")
//    public String updateUserV1(@RequestParam("userId") String userId, @RequestParam("password") String password,
//                             @RequestParam("name") String name, @RequestParam("email") String email){
//        log.info("UserController.updateUserV1");
//
//        User user = new User(userId, password, name, email);
//        memoryUserRepository.update(user);
//        return "redirect:/";
//    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User user){
        log.info("UserController.updateUserV2");

        memoryUserRepository.update(user);
        return "redirect:/";
    }
}
