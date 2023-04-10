package com.example.streamsbackend.controller;

import com.example.streamsbackend.service.LoginService;
import com.example.streamsbackend.security.JwtTokenUtil;
import com.example.streamsbackend.domain.LoginUser;
import com.example.streamsbackend.domain.dto.LoggedInUserDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("api/account")
public class AccountController {
  private final LoginService loginService;

  public AccountController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> postLogin(@RequestBody LoginUser loginUser) {
    var token = JwtTokenUtil.generateToken();
    try {
      var user = loginService.login(loginUser.getUsername(), loginUser.getPassword());
      return new ResponseEntity<>(new LoggedInUserDto(token, user), HttpStatus.OK);
    } catch (Exception e) {
      if (e.getMessage().equals("Username not found")) {
        return new ResponseEntity<>("username", HttpStatus.FORBIDDEN);
      } else if (e.getMessage().equals("Wrong password")) {
        return new ResponseEntity<>("password", HttpStatus.FORBIDDEN);
      }
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> postRegister(@RequestBody LoginUser loginUser) {
    try {
      loginService.register(loginUser.getUsername(), loginUser.getPassword());
    } catch (Exception e) {
      if (e.getMessage().equals("Username already in use")) {
        return new ResponseEntity<>("username", HttpStatus.FORBIDDEN);
      }
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
