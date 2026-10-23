package com.example.QuanLyBanDienThoai.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        // Thêm error message vào session
        HttpSession session = request.getSession();
        session.setAttribute("login_error", "Email hoặc mật khẩu không đúng!");
//        session.setAttribute("alertClass", "alert-danger");

        // Redirect về trang login
        response.sendRedirect("/login?error=true");
    }
}
