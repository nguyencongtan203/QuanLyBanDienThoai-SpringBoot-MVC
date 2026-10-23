package com.example.QuanLyBanDienThoai.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
public class CustomUserSessionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/admin")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                // Đặt cookie session riêng cho user
                Cookie userSessionCookie = new Cookie("USER_SESSION", session.getId());
                userSessionCookie.setPath("/");
                userSessionCookie.setHttpOnly(true);
                userSessionCookie.setSecure(request.isSecure());
                response.addCookie(userSessionCookie);
            }
        }
        filterChain.doFilter(request, response);
    }
}
