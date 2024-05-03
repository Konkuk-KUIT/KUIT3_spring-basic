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
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

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
    public ModelAndView createUser(@RequestParam("userId") String userId, @RequestParam("password") String password,
                                   @RequestParam("name") String name, @RequestParam("email") String email){

        User user = new User(userId, password,name,email);
        memoryUserRepository.insert(user);
        return new ModelAndView("redirect:/user/list");
    }

    // 이것은 왜 안될까?? 해결
//    @RequestMapping("/list")
//    public String showUserList(HttpServletRequest request, Model model){
//        HttpSession session = request.getSession();
//        model modelAndView = new ModelAndView();
//        if(UserSessionUtils.isLoggedIn(session)){
//            modelAndView.addObject("users", memoryUserRepository.findAll());
//            return "/user/list";
//        }
//        return "redirect:/user/loginForm";
//    }


    /**
     * TODO: showUserList
     */

    @RequestMapping("/list")
    public ModelAndView showUserList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView();

        if(UserSessionUtils.isLoggedIn(session)) {
            modelAndView.addObject("users", memoryUserRepository.findAll());
            modelAndView.setViewName("user/list"); // ModelAndView로 view 이름 설정
        } else {
            modelAndView.setViewName("redirect:/user/loginForm"); // 로그인이 되어있지 않을 경우, 로그인 폼으로 리디렉션
        }

        return modelAndView; // ModelAndView 객체를 반환
    }


    /**
     * TODO: showUserUpdateForm
     */

    @RequestMapping("/updateForm")
    public ModelAndView showUserUpdaLteForm(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)) {
            // 로그인한 사람의 정보가 나오는데 이게 맞는건가?, 타인의 정보 수정 눌러도 자신의 정보가 뜬다 (문제상황) 질문
            modelAndView.addObject("users", memoryUserRepository.findByUserId(session.getId()));
            modelAndView.setViewName("user/updateForm");
        } else{
            modelAndView.setViewName("redirect:/user/loginForm");
        }
        return modelAndView;
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    @RequestMapping("/update")
    public String updateUser(@RequestParam("userId") String userId, @RequestParam("password") String password,
                             @RequestParam("name") String name, @RequestParam("email") String email){
        User user = new User(userId, password, name, email);
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }
}
