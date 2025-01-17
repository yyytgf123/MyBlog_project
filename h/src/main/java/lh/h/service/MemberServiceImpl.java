package lh.h.service;

import lh.h.entity.Member;
import lh.h.interfaces.MemberService;
import lh.h.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member create(String username, String email, String PasswordField) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(PasswordField));
        this.memberRepository.save(member);

        return member;
    }
}