package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j //로그 기록을 위한 lombok 라이브러리 어노테이션
@Controller
@RequiredArgsConstructor // lombok 라이브러리의 어노테이션의 필수 의존성을 주입받는 생성자 자동 생성
// 초기화되지 않은 final 필드에 대해 생성자를 생성해줌 DI 편의성

public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showUserForm() {
        log.info("UserController.showUserForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

    @RequestMapping("/user/signup")
    public String createUserV1(@RequestParam String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email) {
        log.info("UserController.createUserV1");
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);

        return "redirect:/user/loginForm";
    }
    // public String createUserV2

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest request, Model model) {
        log.info("UserController.showUserList");

        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, Model model) {
        log.info("UserController.showUserUpdateForm");

        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "/home";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/update")
    public String updateUserV1(@RequestParam String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email) {
        log.info("UserController.updateUserV1");
        User user = new User(userId, password, name, email);
        memoryUserRepository.update(user);

        return "redirect:/user/list";
    }
    // public String updateUserV2
}
