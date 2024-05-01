package com.example.twitterclone.controller;

import com.example.twitterclone.model.User;
import com.example.twitterclone.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        logger.info("ログイン試行: ユーザー名 = {}", username);  // ログインボタンが押されたことをログに記録
        return userService.findByUsernameAndPassword(username, password)
                .map(user -> {
                    model.addAttribute("username", user.getUsername());
                    model.addAttribute("email", user.getEmail());
                    logger.info("Login successful for user: {}", user.getUsername());  // ログ出力
                    return "userProfile";  // userProfile.htmlというビューを返す
                })
                .orElseGet(() -> {
                    logger.info("Login attempt failed for username: {}", username);  // ログイン失敗時のログ
                    return "loginError";  // ログインエラー時に表示するビュー
                });
    }
}