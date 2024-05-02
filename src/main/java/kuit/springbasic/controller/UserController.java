package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import static kuit.springbasic.util.UserSessionUtils.isLoggedIn;

@Controller
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {


    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/form")
    public String showUserForm() {
        return "/user/form";
    }

    @RequestMapping("/list")
    public ModelAndView showUserList(ModelAndView mav, HttpServletRequest request) {
        if (isLoggedIn(request.getSession())){
            mav.addObject("users", memoryUserRepository.findAll());
            mav.setViewName("/user/list");
            return mav;
        }
        return new ModelAndView("redirect:/user/loginForm");
    }
    @RequestMapping("/updateForm")
    public String showUserUpdateForm() {
        return "/user/updateForm";
    }

//    @RequestMapping("/signup")
//    public ModelAndView createUserV1(@RequestParam("userId") String userId, @RequestParam("password") String password,
//                                     @RequestParam("name") String name, @RequestParam("email") String email) {
//        log.info("createUserV1");
//        User user = new User(userId, password, name, email);
//        memoryUserRepository.insert(user);
//        return new ModelAndView("redirect:/user/list");
//    }
//
//    @RequestMapping("/update")
//    public ModelAndView updateUserV1(@RequestParam("userId") String userId, @RequestParam("password") String password,
//                                     @RequestParam("name") String name, @RequestParam("email") String email) {
//        log.info("updateUserV1");
//        User user = new User(userId, password, name, email);
//        memoryUserRepository.changeUserInfo(user);
//        return new ModelAndView("redirect:/user/list");
//    }

    @RequestMapping("/signup")
    public ModelAndView createUserV2(@ModelAttribute User createUser) {
        log.info("createUserV2");
        User user = new User(createUser.getUserId(),
                createUser.getPassword(),
                createUser.getName(),
                createUser.getEmail());
        memoryUserRepository.insert(user);
        return new ModelAndView("redirect:/user/list");
    }
    @RequestMapping("/update")
    public ModelAndView updateUserV2(@ModelAttribute User updateUser){
        log.info("updateUserV2");
        User user = new User(updateUser.getUserId(),
                updateUser.getPassword(),
                updateUser.getName(),
                updateUser.getEmail());
        memoryUserRepository.changeUserInfo(user);
        return new ModelAndView("redirect:/user/list");
    }
}
