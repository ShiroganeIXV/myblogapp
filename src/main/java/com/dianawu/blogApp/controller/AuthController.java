package com.dianawu.blogApp.controller;

import com.dianawu.blogApp.dto.RegistrationDto;
import com.dianawu.blogApp.entity.User;
import com.dianawu.blogApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // User Registration
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        // this object hold form data
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto registrationDto
            , BindingResult result
            , Model model){
        User userExists = userService.findByEmail(registrationDto.getEmail());

        if(userExists != null && userExists.getEmail() != null && userExists.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }


        if(result.hasErrors()){
            model.addAttribute("user", registrationDto);
            return "register";
        }


        userService.saveUser(registrationDto);
        return "redirect:/register?success";
    }

}
