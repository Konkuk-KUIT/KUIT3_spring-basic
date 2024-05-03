package kuit.springbasic.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    @GetMapping("user/form")
    public String signUpForm() {
        return "user/form";
    }

    @PostMapping("/user/signupV1")
    public String signup(@RequestParam String name, @RequestParam String password) {
        log.info("UserController signupV1");
        memoryUserRepository.insert(new User(name, password));
        return "redirect:/";
    }

    @PostMapping("/user/signup")
    public String signup(@ModelAttribute User user) {
        log.info("UserController signup");
        memoryUserRepository.insert(user);
        return "redirect:/";
    }

    @GetMapping("/user/list")
    public ModelAndView userList(HttpServletRequest request) {
        log.info("UserController userList");
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("/user/list");
            modelAndView.getModel().put("users", memoryUserRepository.findAll());
            return modelAndView;
        }
        return new ModelAndView("redirect:/user/loginForm");
    }
//    @Slf4j
//    public class ListUserControllerV1 implements ControllerV1 {
//
//        @Override
//        public ModelAndView execute(Map<String, String> params){
//            log.info("ListUserControllerV1");
//
//            if (isLoggedIn) {
//                ModelAndView modelAndView = new ModelAndView("/v1/user/list");
//                modelAndView.getModel().put("users", memoryUserRepository.findAll());
//                return modelAndView;
//            }
//            return new ModelAndView(REDIRECT+"/v1/user/loginForm");
//        }

    @GetMapping("/user/updateForm")
    public ModelAndView updateForm(@RequestParam String userId, HttpServletRequest request) {
        log.info("UserController updateForm");
        User loginedUser = (User) request.getSession().getAttribute("user");
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null && loginedUser != null && loginedUser.equals(user)) {
            ModelAndView modelAndView = new ModelAndView("/user/updateForm");
            modelAndView.getModel().put("user", user);
            return modelAndView;
        }
        return new ModelAndView("redirect:/user/list");
    }
//    @Slf4j
//    public class UpdateUserFormControllerV1 implements ControllerV1 {
//        @Override
//        public ModelAndView execute(Map<String, String> params) {
//            log.info("UpdateUserFormControllerV1");
//            String userId = params.get("userId");
//            User user = MemoryUserRepository.getInstance().findUserById(userId);
//            if (user != null) {
//                ModelAndView modelAndView = new ModelAndView("/v1/user/updateForm");
//                modelAndView.getModel().put("user", user);
//                return modelAndView;
//            }
//            return new ModelAndView(REDIRECT + "/v1");
//        }
//    }

    @PostMapping("/user/updateV1")
    public String update(@RequestParam String userId,@RequestParam String password,
                         @RequestParam String name,@RequestParam String email) {
        log.info("UserController update");
        User user = new User(userId,password,name,email);
        memoryUserRepository.changeUserInfo(user);
        return "redirect:/user/list";
    }

    @PostMapping("/user/update")
    public String update(@ModelAttribute User user) {
        log.info("UserController update");
        memoryUserRepository.changeUserInfo(user);
        return "redirect:/user/list";
    }
//    @Slf4j
//    public class UpdateUserControllerV1 implements ControllerV1 {
//        public ModelAndView execute(Map<String, String> params) {
//            log.info("UpdateUserControllerV1");
//            User user = new User(params.get("userId"),
//                    params.get("password"),
//                    params.get("name"),
//                    params.get("email"));
//
//            MemoryUserRepository.getInstance().changeUserInfo(user);
//
//            return new ModelAndView(REDIRECT + "/v1/user/userList");
//        }
//    }
}
