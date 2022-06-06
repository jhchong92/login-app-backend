package com.jh.loginappbackend.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class UserDto {
  private UUID id;
  private String username;
  private String lastName;
  private String firstName;
}
