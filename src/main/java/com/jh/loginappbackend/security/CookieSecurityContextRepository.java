package com.jh.loginappbackend.security;

import com.jh.loginappbackend.model.AppUser;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
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
    getAppUserFromRequest(request)
        .ifPresent(appUser -> {
          context.setAuthentication(UsernamePasswordAuthenticationToken.authenticated(
              appUser.getEmail(), "", Collections.singleton(new SimpleGrantedAuthority("USER")))
          );
        });
    return () -> context;
  }

  @Override
  public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
    Authentication authentication = context.getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof AppUser) {
      AuthenticatedUserCookie authenticatedUserCookie = new AuthenticatedUserCookie((AppUser) authentication.getPrincipal(), hmacKey);
      // authenticatedUserCookie.setSecure(request.isSecure());
      response.addCookie(authenticatedUserCookie);
    }
  }

  @Override
  public boolean containsContext(HttpServletRequest request) {
    return getAppUserFromRequest(request).isPresent();
  }

  private Optional<AppUser> getAppUserFromRequest(HttpServletRequest request) {
    if (request.getCookies() == null) {
      return Optional.empty();
    }
    return Stream.of(request.getCookies())
        .filter(cookie -> cookie.getName().equals(AuthenticatedUserCookie.NAME))
        .findFirst()
        .map(this::createAppUserFromCookie);
  }

  private AppUser createAppUserFromCookie(Cookie cookie) {
    return AuthenticatedUserCookie.decode(cookie, hmacKey);
  }

  // private class SaveToCookieResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {
  //   private final HttpServletRequest request;
  //
  //   SaveToCookieResponseWrapper(HttpServletRequest request, HttpServletResponse response) {
  //     super(response, true);
  //     this.request = request;
  //   }
  //
  //   @Override
  //   protected void saveContext(SecurityContext securityContext) {
  //     HttpServletResponse response = (HttpServletResponse) getResponse();
  //     Authentication authentication = securityContext.getAuthentication();
  //
  //     // some checks, see full sample code
  //
  //     UserInfo userInfo = (UserInfo) authentication.getPrincipal();
  //     SignedUserInfoCookie cookie = new SignedUserInfoCookie(userInfo, cookieHmacKey);
  //     cookie.setSecure(request.isSecure());
  //     response.addCookie(cookie);
  //   }
  // }
}
