package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String error = null;

        if (userService.exists(user.getUsername())) {
            int result = userService.create(user);
            if (result < 0) {
                error = "There was an error. Please try again.";
            }
        } else{
            error = "The username already exists.";
        }



        if (error == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", error);
        }

        return "signup";
    }
}
