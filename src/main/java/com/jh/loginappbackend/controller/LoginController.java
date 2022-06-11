package com.jh.loginappbackend.controller;

import com.jh.loginappbackend.dto.LoginUserDto;
import com.jh.loginappbackend.exception.BadCredentialsException;
import com.jh.loginappbackend.model.AppUser;
import com.jh.loginappbackend.security.AuthenticatedUserCookie;
import com.jh.loginappbackend.service.LoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = LoginController.REQUEST_MAPPING_PATH)
@Slf4j
@RequiredArgsConstructor
public class LoginController {
  public static final String REQUEST_MAPPING_PATH = "/login";

  private final LoginService loginService;

  @Value("${auth.cookie.hmac-key}")
  private String hmacKey;

  @PostMapping
  public ResponseEntity<String> login(
      @Validated @RequestBody LoginUserDto loginUserDto, HttpServletRequest request, HttpServletResponse response) {
    try {
      Authentication authentication = loginService.login(loginUserDto);
      AuthenticatedUserCookie authenticatedUserCookie = new AuthenticatedUserCookie((AppUser) authentication.getPrincipal(), hmacKey);
      response.addCookie(authenticatedUserCookie);
      authenticatedUserCookie.setSecure(request.isSecure());
      return new ResponseEntity<>("Login successful", HttpStatus.OK);
    } catch (AuthenticationException exception) {
      throw new BadCredentialsException();
    }
  }

}
