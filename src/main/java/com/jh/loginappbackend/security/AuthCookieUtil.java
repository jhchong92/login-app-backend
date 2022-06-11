package com.jh.loginappbackend.security;

import com.jh.loginappbackend.model.AppUser;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthCookieUtil {

  public Optional<AppUser> getAppUserFromRequest(HttpServletRequest request, String hmacKey) {
    if (request.getCookies() == null) {
      return Optional.empty();
    }
    return Stream.of(request.getCookies())
        .filter(cookie -> cookie.getName().equals(AuthenticatedUserCookie.NAME))
        .findFirst()
        .map(cookie -> AuthenticatedUserCookie.decode(cookie, hmacKey));
  }

}
