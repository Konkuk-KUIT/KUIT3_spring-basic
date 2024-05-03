package kuit.springbasic.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
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

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final MemoryUserRepository memoryUserRepository;
    @RequestMapping("/form")
    public String showUserForm() {
        log.info("UserController.showUserForm");
        return "/user/form";
    }
    @RequestMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId, @RequestParam("password") String password,
                               @RequestParam("name") String name, @RequestParam("email") String email) {
        log.info("UserController.createUserV1");
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        return "redirect:/user/userList";
    }

    @RequestMapping("/v2/signup")
    public String createUserV2(@ModelAttribute User newbie, HttpServletRequest req) {
        log.info("UserController.createUserV2");
        User user = new User(newbie.getUserId(), newbie.getPassword(), newbie.getName(), newbie.getEmail());
        memoryUserRepository.insert(user);
        return "redirect:/user/userList";
    }

    @RequestMapping("/userList")
    public String showUserListV1(HttpServletRequest req) {
        log.info("UserController.showUserListV1");
        HttpSession session = req.getSession();
        if(UserSessionUtils.isLoggedIn(session)) {
            req.setAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }
    @RequestMapping("/v2/userList")
    public ModelAndView showUserListV2(HttpServletRequest req) {
        log.info("UserController.showUserListV2");
        HttpSession session = req.getSession();
        if(UserSessionUtils.isLoggedIn(session)) {
            ModelAndView mav = new ModelAndView("/user/list");
            mav.addObject("users", memoryUserRepository.findAll());
            return mav;
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    @RequestMapping("/updateForm")
    public String showUpdateUserForm(@RequestParam("userId") String userId, HttpServletRequest req) {
        User user = memoryUserRepository.findByUserId(userId);
        if(user != null) {
            req.setAttribute("user", user);
            return "/user/updateForm";
        }
        return "redirect:/";
    }

    //입력되는 값들을 넘겨줘야 하기 때문에 setAttribute 사용해주기.
    @RequestMapping("/v1/update")
    public String updateUserV1(HttpServletRequest req) {
        log.info("UserController.updateUserV1");
        User user = new User(req.getParameter("userId"), req.getParameter("password"),
                req.getParameter("name"), req.getParameter("email"));
        memoryUserRepository.changeUserInfo(user);
        return "redirect:/";
    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User originUser, HttpServletRequest req) {
        log.info("UserController.updateUserV2");
        User user = new User(originUser.getUserId(), originUser.getPassword(), originUser.getName(), originUser.getEmail());
        memoryUserRepository.update(user);
        return "redirect:/";
    }



    /**
     * TODO: showUserForm
     * 완료
     */

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam : 완료
     * createUserV2 : @ModelAttribute : 완료
     */

    /**
     * TODO: showUserList
     * 완료
     */

    /**
     * TODO: showUserUpdateForm
     * 완료
     */

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

}
