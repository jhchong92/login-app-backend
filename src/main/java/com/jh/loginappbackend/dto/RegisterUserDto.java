package com.jh.loginappbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDto {
  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotBlank
  private String password;

  @NotBlank
  @Email
  private String email;

}
