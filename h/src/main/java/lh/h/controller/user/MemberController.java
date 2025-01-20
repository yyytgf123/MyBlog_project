package lh.h.controller.user;

import jakarta.validation.Valid;
import lh.h.dto.MemberFormDto;
import lh.h.entity.Member;
import lh.h.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/userPage")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "user/userPage";
    }

    @PostMapping("/signup")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Error: " + error.getDefaultMessage());
            });

            return "user/userPage";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userPage";
        }

        return "redirect:/";
    }

    @GetMapping("/loginError")
    public String loginError() {
        return "user/loginError";
    }

    @GetMapping("/accessError")
    public String accessErrorPage() {
        return "user/accessError";
    }
}