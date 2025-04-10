package com.example.security.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {
 @GetMapping("/login")
 public String loginPage() {
 return "login";
 }
 @GetMapping("/home")
 public String homePage() {
 return "home";
 }
}