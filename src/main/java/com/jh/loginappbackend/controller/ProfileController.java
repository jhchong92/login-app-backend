package com.jh.loginappbackend.controller;

import com.jh.loginappbackend.dto.UserDto;
import com.jh.loginappbackend.mapper.UserMapper;
import com.jh.loginappbackend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = ProfileController.REQUEST_MAPPING_PATH)
@RequiredArgsConstructor
public class ProfileController {
  public static final String REQUEST_MAPPING_PATH = "/profile";

  private final UserProfileService userProfileService;

  private final UserMapper userMapper;

  @GetMapping
  public UserDto getProfile() {
    return userProfileService.getCurrentUserProfile().map(userMapper::toDto)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }
}
