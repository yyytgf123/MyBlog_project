package lh.h.interfaces;

import lh.h.entity.Member;

import javax.swing.*;

public interface MemberService {

    Member create(String username, String email, String JPasswordField);
}
