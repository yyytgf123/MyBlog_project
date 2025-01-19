package lh.h.interfaces;

import lh.h.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.swing.*;

public interface MemberService {

    Member saveMember(Member member);

    void validateDuplicateMember(Member member);
}
