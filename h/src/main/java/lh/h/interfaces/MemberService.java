package lh.h.interfaces;

import lh.h.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.swing.*;

public interface MemberService {

    Member create(String username, String email, String JPasswordField);

    Member login(String email, String password);

    void logout(String email);

    boolean isLoggedIn(String email);
}
