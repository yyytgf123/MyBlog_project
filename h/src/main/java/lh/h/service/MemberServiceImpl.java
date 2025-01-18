package lh.h.service;

import lh.h.entity.Member;
import lh.h.repository.MemberRepository;
import lh.h.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member create(String username, String email, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        return memberRepository.save(member);
    }

    @Override
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            member.setLoggedin(true);
            memberRepository.save(member);
            return member;
        }
        throw new RuntimeException("Invalid email or password");
    }

    @Override
    public void logout(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            member.setLoggedin(false);
            memberRepository.save(member);
        }
    }

    @Override
    public boolean isLoggedIn(String email) {
        Member member = memberRepository.findByEmail(email);
        return member != null && member.isLoggedin();
    }
}
