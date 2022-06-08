package com.jh.loginappbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterUserDto {
  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotBlank
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
      message = "contain minimum 8 characters, at least one letter and one number"
  )
  private String password;

  @NotBlank
  @Email
  private String email;

}
