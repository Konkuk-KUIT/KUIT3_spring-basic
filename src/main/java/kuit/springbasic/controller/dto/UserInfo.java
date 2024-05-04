package kuit.springbasic.controller.dto;

import kuit.springbasic.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class UserInfo {
  private String userId;
  private String email;
  private String name;

  public static UserInfo from(User user) {
    return new UserInfo(user.getUserId(), user.getPassword(), user.getName());
  }
}
