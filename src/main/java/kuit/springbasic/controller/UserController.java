package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Map;

import static kuit.springbasic.util.UserSessionUtils.isLoggedIn;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository userRepository;
    /**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showUserForm(){
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/signup")
    public String createUser(@ModelAttribute User user){
        userRepository.insert(user);

        return "/user/login"; // redirect:/user/login로 했을 때 오류가 나는 이유는?
    }

    /**
     * TODO: showUserList
     */

    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest req, Model model){
        HttpSession session = req.getSession();
        if(isLoggedIn(session)){
            model.addAttribute("users", userRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(HttpServletRequest req, Model model){
        String userId = req.getParameter("userId");
        User user = userRepository.findByUserId(userId);
        HttpSession session = req.getSession();
        User loggedInUser = (User) session.getAttribute("user");
        if(user != null && loggedInUser != null && user.isSameUser(loggedInUser)){
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "redirect:/user/list";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    @RequestMapping("/user/update")
    public String updateUser(@ModelAttribute User user){
        userRepository.update(user);
        return "redirect:/user/list";
    }

}
