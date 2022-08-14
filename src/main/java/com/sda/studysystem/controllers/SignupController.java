package com.sda.studysystem.controllers;

import com.sda.studysystem.exceptions.UserNotFoundException;
import com.sda.studysystem.models.Login;
import com.sda.studysystem.models.User;
import com.sda.studysystem.services.AuthorityService;
import com.sda.studysystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller to handle signup related requests
 *
 * @author Vinod John
 */
@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;


    @GetMapping
    public String showSignupPage(@ModelAttribute("user") User user, @ModelAttribute("message") String message,
                                 @ModelAttribute("messageType") String messageType, Model model) {
        model.addAttribute("authorities", authorityService.findAllAuthorities());
        return "auth/signup";
    }

    @PostMapping
    public String postSignup(User user, RedirectAttributes redirectAttributes) {
        try {
            userService.findUserByUserName(user.getUserName());
            redirectAttributes.addFlashAttribute("message", "User already exists!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/signup";
        } catch(UserNotFoundException userNotFoundException) {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("message", "Signup successful!");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/";
        }
    }
}
