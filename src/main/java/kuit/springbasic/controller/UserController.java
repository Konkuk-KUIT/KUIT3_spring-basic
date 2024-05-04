package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.controller.dto.RegisterRequest;
import kuit.springbasic.controller.dto.UserInfo;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final MemoryUserRepository userRepository;

  @GetMapping("/form")
  public String showUserForm() {
    return "/user/form";
  }

  @PostMapping("/register")
  public String register(@ModelAttribute RegisterRequest request) {
    userRepository.insert(User.from(request));
    return "redirect:/user/login";
  }

  @GetMapping("/list")
  public String showUserList(Model model) {
    List<UserInfo> userInfoList = new ArrayList<>();
    userRepository.findAll().forEach(x -> userInfoList.add(UserInfo.from(x)));
    model.addAttribute("users", userInfoList);
    return "/user/list";
  }

  @GetMapping("/update")
  public String showUserUpdateForm(HttpSession session, Model model) {
    String userIdOfSession = (String) session.getAttribute("loggedInUserId");
    User user = userRepository.findByUserId(userIdOfSession);
    model.addAttribute("user", user);
    return "/user/updateForm";
  }

  @PostMapping("/update")
  public String updateUser(@ModelAttribute RegisterRequest request) {
    userRepository.changeUserInfo(User.from(request));
    return "redirect:/user/list";
  }
}
