package lh.h.controller;

import jakarta.validation.Valid;
import lh.h.entity.Member;
import lh.h.entity.MemberCreateForm;
import lh.h.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 및 로그인 페이지
    @GetMapping("/signin")
    public String showSignInPage(MemberCreateForm memberCreateForm, Model model) {
        return "user/signin";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/signin";
        }

        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "Passwords do not match.");
            return "user/signin";
        }

        memberService.create(memberCreateForm.getUsername(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());
        model.addAttribute("successMessage", "Sign up successful. Please log in.");
        return "user/signin";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password, Model model) {
        try {
            Member member = memberService.login(email, password);
            model.addAttribute("member", member);
            return "redirect:/";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user/signin";
        }
    }
}
