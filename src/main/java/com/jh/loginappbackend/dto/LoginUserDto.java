package com.jh.loginappbackend.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDto {
  @NotBlank
  private String username;
  @NotBlank
  private String password;
}
