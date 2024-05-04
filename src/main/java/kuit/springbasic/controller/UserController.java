package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public ModelAndView showUserForm() {
        return new ModelAndView("/user/form");
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    // createUserV1
    @RequestMapping("/signup")
    public ModelAndView createUser(@RequestParam("userId") String userId, @RequestParam("password") String userPW, @RequestParam("name") String name, @RequestParam("email") String email) {
        User user = new User(userId, userPW, name, email);
        memoryUserRepository.insert(user);
        return new ModelAndView("redirect:/user/loginForm");
    }
    // createUserV2
//    @RequestMapping("/signup")
//    public ModelAndView createUser(@ModelAttribute User user) {
//
//        memoryUserRepository.insert(user);
//
//        return new ModelAndView("redirect:/user/loginForm");
//    }

    /**
     * TODO: showUserList
     */

    @RequestMapping(value = {"/userList", "/list"})
    public ModelAndView showUserList(HttpSession session){
        if (UserSessionUtils.isLoggedIn(session)) {
            return new ModelAndView("/user/list").addObject("users", memoryUserRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }
    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public ModelAndView showUserUpdateForm(@ModelAttribute User user) {
        String userId = user.getUserId();
        User user_ = memoryUserRepository.findByUserId(userId);
        if (user_ != null) {
            return new ModelAndView("/user/updateForm").addObject("user", user);
        }
        return new ModelAndView("redirect:/");
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/update")
    public ModelAndView updateUser(@ModelAttribute User user) {

        memoryUserRepository.changeUserInfo(user);

        return new ModelAndView("redirect:/user/userList");
    }

}
