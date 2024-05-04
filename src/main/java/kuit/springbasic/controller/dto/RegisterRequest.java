package kuit.springbasic.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class RegisterRequest {
  private String userId;
  private String password;
  private String name;
  private String email;
}
