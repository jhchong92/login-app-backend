package com.jh.loginappbackend.service;

import com.jh.loginappbackend.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final AuthenticationManager authenticationManager;
  public boolean login(LoginUserDto loginUserDto) throws AuthenticationException {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginUserDto.getUsername(), loginUserDto.getPassword()
    ));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    return true;
  }
}
