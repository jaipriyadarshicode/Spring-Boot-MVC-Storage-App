package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUpController {


    UserService userService;

    public SignUpController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public String signUpView(){

        return "signup";
    }

    @PostMapping()
    public String signUp(@ModelAttribute User user, Model model, RedirectAttributes redirectAttr){
        if(userService.checkUsernameAvailability(user) != null){
            model.addAttribute("usernameExist", "Username is not available. Please choose another one.");
            return "signup";
        }
        else if (userService.createUser(user) == 1){
            //model.addAttribute("success", "You successfully signed up! Please continue to the login page.");
            redirectAttr.addFlashAttribute("SuccessMessage","Sign Up Successfully");
            return "redirect:/login";
            //return "signup";
        }
        else{
            return "error";
        }
    }
}
