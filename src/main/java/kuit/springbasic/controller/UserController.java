package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.loginUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Session;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository userRepository;


    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String showUserForm(){
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/signup")
    public String createUser(@ModelAttribute User newUser){
        userRepository.insert(newUser);
        return "redirect:/user/loginForm";
    }
    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public ModelAndView showUserList(HttpServletRequest req){
        HttpSession userSession = req.getSession(false);
        //String sessionId;
        if(!loginUtil.isLogin(userSession)){
            return new ModelAndView( "redirect:/user/loginForm");
        }
        ModelAndView mv = new ModelAndView( "/user/list");
        mv.addObject("users",userRepository.findAll());
        return mv;
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public ModelAndView showUUserUpdateForm(@RequestParam("userId") String id,  HttpServletRequest req){
        //String id = req.getParameter("userId");
        HttpSession userSession = req.getSession();
        if(!loginUtil.isLogin(userSession)){
            return new ModelAndView( "/user/login");
        }
        User user = (User)userSession.getAttribute("user");
        if(!id.equals(user.getUserId())){
            //잘못된 접근
            return new ModelAndView( "redirect:/");
        }
        req.setAttribute("user", userRepository.findByUserId(id));
        return new ModelAndView( "/user/updateForm");

    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/update")
    public String updateUser(@ModelAttribute User user,HttpServletRequest req){

        //String id = req.getParameter("userId");
        HttpSession userSession = req.getSession();
        if(!loginUtil.isLogin(userSession)){
            return "redirect:/user/login";
        }
        User checkUser = (User)userSession.getAttribute("user");
        if(!user.getUserId().equals(checkUser.getUserId())){
            //잘못된 접근
            return "redirect:/home";
        }

        //System.out.println(user.toString());
        userRepository.changeUserInfo(user);
        return "redirect:/user/list";
    }

}
