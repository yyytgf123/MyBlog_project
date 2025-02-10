package lh.h.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

/* 인증되지 않은 사용자가 접근할 때 실행 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String redirectUrl = "/user/accessError";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.sendRedirect(redirectUrl);
    }
}
