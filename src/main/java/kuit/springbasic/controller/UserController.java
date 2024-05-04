package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final MemoryUserRepository memoryUserRepository;
    @RequestMapping("/form")
    public String showForm(){
        return "/user/form";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/signup")
    public ModelAndView createUser(@RequestParam("userId") String userId,
                                   @RequestParam("password") String password,
                                   @RequestParam("name") String name,
                                   @RequestParam("email") String email) {
        log.info("CreateUserController.createUser");

        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);

        // Redirect to userList page
        return new ModelAndView("redirect:/v1/user/userList");
    }


    /**
     * TODO: showUserList
     */
    @RequestMapping("/userList")
    public String showList(){return "/user/list";}

    /**
     * TODO: showUserUpdateForm
     */

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

}
