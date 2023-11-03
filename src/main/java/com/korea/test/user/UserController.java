package com.korea.test.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateFrom userCreateFrom) {
        return "signup_form";
    }

    @PostMapping("/singup")
    public  String signup(@Valid UserCreateFrom userCreateFrom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign_form";
        }

        if (!userCreateFrom.getPassword().equals(userCreateFrom.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword","passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        userService.create(userCreateFrom.getUserId(),userCreateFrom.getEmail(), userCreateFrom.getNickname(), userCreateFrom.getPassword());

        return "redirect:/";

    }

}
