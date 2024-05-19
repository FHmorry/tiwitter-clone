package com.example.twitterclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.twitterclone.model.User;
import com.example.twitterclone.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
    logger.info("ログイン試行: ユーザー名 = {}", user.getUsername());
    return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())
        .map(foundUser -> {
            logger.info("ログイン成功: ユーザー名 = {}", foundUser.getUsername());
            return ResponseEntity.ok().body(foundUser);
        })
        .orElseGet(() -> {
            logger.info("ログイン失敗: ユーザー名 = {}", user.getUsername());
            return null;
        });
  }
}
