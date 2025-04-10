package com.example.demo_4;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

   @GetMapping("/login")
   public String loginPage() {
       return "login"; // Retorna la vista login.html desde src/main/resources/templates/
   }

   @GetMapping("/home")
   public String homePage() {
       return "home"; // Retorna la vista home.html desde src/main/resources/templates/
   }
}
