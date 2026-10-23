package com.example.QuanLyBanDienThoai.core;

import com.example.QuanLyBanDienThoai.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class SessionFilter extends OncePerRequestFilter {
    @Autowired
    private SessionService sessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                SessionInfo sessionInfo = null;

                if (requestUri.startsWith("/admin") && "ADMIN_SESSION".equals(cookie.getName())) {
                    sessionInfo = sessionService.getAdminSession(cookie.getValue());
                    if (sessionInfo != null && "ADMIN".equals(sessionInfo.getRole())) {
                        sessionInfo.updateLastAccessTime();
                        addSessionAttributesToRequest(request, sessionInfo);
                    }
                } else if ("USER_SESSION".equals(cookie.getName())) {
                    sessionInfo = sessionService.getUserSession(cookie.getValue());
                    if (sessionInfo != null && "USER".equals(sessionInfo.getRole())) {
                        sessionInfo.updateLastAccessTime();
                        addSessionAttributesToRequest(request, sessionInfo);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private void addSessionAttributesToRequest(HttpServletRequest request, SessionInfo sessionInfo) {
        // Thêm các thuộc tính session vào request attributes
        request.setAttribute("email", sessionInfo.getEmail());
        request.setAttribute("name", sessionInfo.getName());

        if ("USER".equals(sessionInfo.getRole())) {
            request.setAttribute("giohang", sessionInfo.getCartId());
            request.setAttribute("locale", sessionInfo.getAddress());
            request.setAttribute("phone_number", sessionInfo.getPhoneNumber());
        } else {
            request.setAttribute("taikhoan", sessionInfo.getEmail());
            request.setAttribute("tennv", sessionInfo.getName());
        }
    }
}
