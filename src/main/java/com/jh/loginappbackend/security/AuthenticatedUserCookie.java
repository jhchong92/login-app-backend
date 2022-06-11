package com.jh.loginappbackend.security;

import com.jh.loginappbackend.model.AppUser;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

public class AuthenticatedUserCookie extends Cookie {
  public static final String NAME = "AuthenticatedUser";
  private static final Pattern UID_PATTERN = Pattern.compile("uid=([A-Za-z0-9@.]*)");
  private static final Pattern HMAC_PATTERN = Pattern.compile("hmac=([A-Za-z0-9+/=]*)");

  private final Payload payload;
  private final String hmac;

  public AuthenticatedUserCookie(AppUser user, String hmacKey) {
    super(NAME, "");
    payload = new Payload(user.getEmail());
    this.hmac = calculateHmac(payload, hmacKey);

    this.setPath("/");
    this.setMaxAge((int) Duration.of(1, ChronoUnit.HOURS).toSeconds());
    this.setHttpOnly(true);
  }

  private static String calculateHmac(Payload payload, String hmacKey) {
    try {
      String algorithm = "HmacSHA512";
      Mac mac = Mac.getInstance(algorithm);
      SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKey.getBytes(StandardCharsets.UTF_8), algorithm);
      mac.init(secretKeySpec);
      byte[] hmacBytes = mac.doFinal(payload.toString().getBytes(StandardCharsets.UTF_8));
      return Base64.encodeBase64String(hmacBytes);
    } catch (NoSuchAlgorithmException | InvalidKeyException exception ) {
      throw new RuntimeException(exception);
    }
  }

  public static AppUser decode(Cookie cookie, String hmacKey) {
    if (!NAME.equals(cookie.getName())) {
      throw new IllegalArgumentException("Cookie is not AuthenticatedUserCookie");
    }
    //
    String hmac = parse(cookie.getValue(), HMAC_PATTERN).orElseThrow(() -> new IllegalArgumentException("Hmac not found in cookie"));
    String username = parse(cookie.getValue(), UID_PATTERN).orElseThrow(() -> new IllegalArgumentException(NAME + " Cookie contains no UID"));

    Payload payload = new Payload(username);
    if (!hmac.equals(calculateHmac(payload, hmacKey))) {
      throw new IllegalArgumentException("Invalid cookie hmac");
    }
    AppUser appUser = new AppUser();
    appUser.setEmail(payload.username);
    return appUser;
  }

  private static Optional<String> parse(String value, Pattern pattern) {
    Matcher matcher = pattern.matcher(value);
    if (!matcher.find())
      return Optional.empty();

    if (matcher.groupCount() < 1)
      return Optional.empty();

    String match = matcher.group(1);
    if (match == null || match.trim().isEmpty())
      return Optional.empty();

    return Optional.of(match);
  }

  @Override
  public String getValue() {
    return payload.toString()  + "&hmac=" + hmac;
  }

  @RequiredArgsConstructor
  private static class Payload {
    private final String username;

    @Override
    public String toString() {
      return "uid=" + username;
    }
  }
}

