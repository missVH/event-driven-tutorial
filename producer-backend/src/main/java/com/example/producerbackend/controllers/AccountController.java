package com.example.producerbackend.controllers;


import com.example.producerbackend.domain.LoginUser;
import com.example.producerbackend.domain.dto.LoggedInUserDto;
import com.example.producerbackend.security.JwtTokenUtil;
import com.example.producerbackend.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
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

