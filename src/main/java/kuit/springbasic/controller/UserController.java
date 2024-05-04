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
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createUser(@RequestParam("userId") String userId,
                                     @RequestParam("password") String password,
                                     @RequestParam("name") String name,
                                     @RequestParam("email") String email) {
        log.info("Creating user: {}, {}, {}, {}", userId, password, name, email);
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        return new ModelAndView("redirect:/user/userList");
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/userList")
    public ModelAndView showUserList() {
        ModelAndView modelAndView = new ModelAndView("/user/list");
        modelAndView.addObject("users", memoryUserRepository.findAll());
        return modelAndView;
    }


    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public ModelAndView showUserUpdateForm(@RequestParam("userId")String userId) {
        User updateUser = memoryUserRepository.findByUserId(userId);

        if(updateUser !=null){
            ModelAndView modelAndView = new ModelAndView("/user/updateForm");
            modelAndView.getModel().put("user", updateUser);
            return modelAndView;
        }
        return new ModelAndView("redirect:/");
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping(value = "/update")
    public ModelAndView updateUser(@ModelAttribute User user) {
        memoryUserRepository.insert(user);
        return new ModelAndView("redirect:/user/userList");
    }

}
