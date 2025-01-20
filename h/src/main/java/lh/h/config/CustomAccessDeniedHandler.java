package lh.h.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/* SecurityConfig에 권한 존재하지 않을 시 절대 경로로 redirect */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final String redirectUrl;

    public CustomAccessDeniedHandler(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.sendRedirect(redirectUrl); // 절대 경로로 리다이렉트
    }
}
