package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.controller.dto.LoginRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

  private final MemoryUserRepository userRepository;

  @GetMapping("/login")
  public String showLoginForm() {
    return "/user/login";
  }

  @GetMapping("/login/fail")
  public String showLoginFailed() {
    return "/user/loginFailed";
  }

  @PostMapping("/login")
  public String login(@ModelAttribute LoginRequest request, HttpSession session) {
    User user = userRepository.findByUserId(request.getUserId());

    if (user == null || !user.isSameUser(user.getUserId(), user.getPassword())) {
      return "redirect:/user/login/fail";
    }
    session.setAttribute("loggedInUserId", user.getUserId());
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}

