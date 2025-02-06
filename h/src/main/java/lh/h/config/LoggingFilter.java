package lh.h.config;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        // 클라이언트 IP 가져오기
        String ip = req.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty()) {
            ip = ip.split(",")[0].trim(); // 여러 개의 IP가 있을 경우 첫 번째 IP 선택
        } else {
            ip = req.getRemoteAddr();
        }

        // 요청 URL과 함께 IP 로그 기록
        logger.info("Client IP: {}, Requested URL: {}", ip, req.getRequestURI());

        chain.doFilter(request, response);
    }
}
