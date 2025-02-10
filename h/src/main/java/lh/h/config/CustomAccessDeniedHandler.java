package lh.h.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/* SecurityConfig에 권한 존재하지 않을 시 절대 경로로 redirect */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final String redirectUrl = "/user/accessError";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.sendRedirect(redirectUrl);
    }
}
