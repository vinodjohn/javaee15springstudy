package com.sda.studysystem.controllers;

import com.sda.studysystem.models.Login;
import com.sda.studysystem.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller to handle login related requests
 *
 * @author Vinod John
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;


    @GetMapping
    public String showLoginPage(@ModelAttribute("login") Login login, @ModelAttribute("message") String message,
                                @ModelAttribute("messageType") String messageType) {
        return "auth/login";
    }

    @PostMapping
    public String postLogin(Login login, RedirectAttributes redirectAttributes) {
        if (loginService.isLoginValid(login)) {
            redirectAttributes.addFlashAttribute("message", "Login successful!");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("message", "Invalid username or password!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/login";
        }
    }
}
