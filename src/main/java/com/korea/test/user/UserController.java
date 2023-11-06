package com.korea.test.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateFrom userCreateFrom) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateFrom userCreateFrom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateFrom.getPassword().equals(userCreateFrom.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword","passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateFrom.getUsername(),userCreateFrom.getEmail(), userCreateFrom.getNickname(), userCreateFrom.getPassword());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("singupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

}
