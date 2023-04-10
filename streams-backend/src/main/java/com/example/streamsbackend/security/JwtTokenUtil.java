package com.example.streamsbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;

@Component
public class JwtTokenUtil {
  private static byte[] secret;

  @Autowired
  public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
    JwtTokenUtil.secret = secret.getBytes();
  }

  public static String generateToken() {
    var enc = Base64.getEncoder().encode(secret);
    return Arrays.toString(enc);
  }
}
