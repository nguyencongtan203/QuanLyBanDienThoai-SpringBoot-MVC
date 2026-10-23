package com.example.QuanLyBanDienThoai.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomAdminSessionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/admin")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                // Đặt cookie session riêng cho admin
                Cookie adminSessionCookie = new Cookie("ADMIN_SESSION", session.getId());
                adminSessionCookie.setPath("/admin");
                adminSessionCookie.setHttpOnly(true);
                adminSessionCookie.setSecure(request.isSecure());
                response.addCookie(adminSessionCookie);
            }
        }
        filterChain.doFilter(request, response);
    }
}
