package lh.h.config;

import lombok.RequiredArgsConstructor;
import lh.h.interfaces.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MemberService memberService;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                /* 권한 없을시 이동 경로 -> CustomAccessDeniedHandler 사용 */
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/boards/boardDetail/boardUpdate/**", "/boards/form").hasRole("ADMIN") // ADMIN만 접근 가능
                        .anyRequest().permitAll() // 다른 모든 요청은 허용
                )
                .formLogin(form -> form
                        .loginPage("/user/userPage") // 로그인 페이지 경로
                        .usernameParameter("email")
                        .defaultSuccessUrl("/") // 로그인 성공 시 메인 페이지로 이동
                        .failureUrl("/user/loginError")
                        .permitAll() // 로그인 페이지 접근 허용
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout") // 로그아웃 처리 경로
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 메인 페이지로 이동
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll() // 로그아웃 URL 접근 허용
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UserDetails userDetails = memberService.loadUserByUsername(email);
            if (userDetails == null) {
                throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
            }
            return userDetails;
        };
    }
}