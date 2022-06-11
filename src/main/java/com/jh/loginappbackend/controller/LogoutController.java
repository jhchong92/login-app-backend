package com.jh.loginappbackend.controller;

import com.jh.loginappbackend.dto.LoginUserDto;
import com.jh.loginappbackend.exception.BadCredentialsException;
import com.jh.loginappbackend.model.AppUser;
import com.jh.loginappbackend.security.AuthCookieUtil;
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
@RequestMapping(path = LogoutController.REQUEST_MAPPING_PATH)
@Slf4j
@RequiredArgsConstructor
public class LogoutController {
  public static final String REQUEST_MAPPING_PATH = "/logout";

  @Value("${auth.cookie.hmac-key}")
  private String hmacKey;

  @PostMapping
  public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
    AuthCookieUtil.getAppUserFromRequest(request, hmacKey)
        .ifPresent(user -> {
          AuthenticatedUserCookie authenticatedUserCookie = new AuthenticatedUserCookie(user, hmacKey);
          authenticatedUserCookie.setMaxAge(0); // set maxAge to 0 to delete
          response.addCookie(authenticatedUserCookie);
        });

    return new ResponseEntity<>("Logout successful", HttpStatus.OK);
  }

}
