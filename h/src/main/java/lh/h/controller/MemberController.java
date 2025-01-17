package lh.h.controller;

import jakarta.validation.Valid;
import lh.h.entity.Member;
import lh.h.entity.MemberCreateForm;
import lh.h.interfaces.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signUp")
    public String signup(MemberCreateForm memberCreateForm) {
        return "user/signUp";
    }

    @PostMapping("/signUp")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signUp";
        }
        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "user/signUp";
        }

        memberService.create(memberCreateForm.getUsername(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());

        return "redirect:/user/signIn";
    }
}
