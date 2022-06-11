package com.jh.loginappbackend.security;

import com.jh.loginappbackend.model.AppUser;
import java.util.Collections;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieSecurityContextRepository implements SecurityContextRepository {
  @Value("${auth.cookie.hmac-key}")
  private String hmacKey;

  @Override
  public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
    return loadContext(requestResponseHolder.getRequest()).get();
  }

  @Override
  public Supplier<SecurityContext> loadContext(HttpServletRequest request) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    AuthCookieUtil.getAppUserFromRequest(request, hmacKey)
        .ifPresent(appUser -> {
          context.setAuthentication(UsernamePasswordAuthenticationToken.authenticated(
              appUser, "", Collections.singleton(new SimpleGrantedAuthority("USER")))
          );
        });
    return () -> context;
  }

  @Override
  public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
    Authentication authentication = context.getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof AppUser) {
      AuthenticatedUserCookie authenticatedUserCookie = new AuthenticatedUserCookie((AppUser) authentication.getPrincipal(), hmacKey);
      authenticatedUserCookie.setSecure(request.isSecure());
      response.addCookie(authenticatedUserCookie);
    }
  }

  @Override
  public boolean containsContext(HttpServletRequest request) {
    return AuthCookieUtil.getAppUserFromRequest(request, hmacKey).isPresent();
  }

}
