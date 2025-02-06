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

        // 1. 공인 IP (X-Forwarded-For)
        String publicIp = req.getHeader("X-Forwarded-For");
        if (publicIp == null || publicIp.isEmpty() || "unknown".equalsIgnoreCase(publicIp)) {
            publicIp = req.getHeader("X-Real-IP"); // Nginx가 설정한 공인 IP
        }
        if (publicIp != null && publicIp.contains(",")) {
            publicIp = publicIp.split(",")[0].trim(); // 여러 개일 경우 첫 번째 (공인 IP)
        }

        // 2. 내부 IP (Local IP)
        String privateIp = req.getRemoteAddr();

        // 요청 URL과 함께 IP 로그 기록
        logger.info("Client Public IP: {}, Client Private IP: {}, Requested URL: {}",
                publicIp, privateIp, req.getRequestURI());

        chain.doFilter(request, response);
    }
}
