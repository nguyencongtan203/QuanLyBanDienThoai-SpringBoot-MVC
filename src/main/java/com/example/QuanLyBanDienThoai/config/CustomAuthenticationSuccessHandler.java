package com.example.QuanLyBanDienThoai.config;

import com.example.QuanLyBanDienThoai.core.SessionInfo;
import com.example.QuanLyBanDienThoai.entity.Khachhang;
import com.example.QuanLyBanDienThoai.entity.Nhanvien;
import com.example.QuanLyBanDienThoai.service.KhachHangService;
import com.example.QuanLyBanDienThoai.service.NhanVienService;
import com.example.QuanLyBanDienThoai.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private SessionService sessionService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        try {
            String email = authentication.getName();
            Optional<Khachhang> khachhang = khachHangService.findByEmail(email);
            Optional<Nhanvien> nhanvien = nhanVienService.findByTaikhoan(email);

            if (khachhang.isPresent()) {
                // Tạo session mới cho user
                SessionInfo sessionInfo = sessionService.createUserSession(khachhang.get());

                // Thêm session cookie
                sessionService.addSessionCookie(response, sessionInfo);

                // Thêm thông tin vào response header nếu cần
                response.setHeader("X-User-Name", sessionInfo.getName());
                response.setHeader("X-User-Email", sessionInfo.getEmail());

                response.sendRedirect("/home");
            }

            if (nhanvien.isPresent()) {
                // Tạo session mới cho admin
                SessionInfo sessionInfo = sessionService.createAdminSession(nhanvien.get());

                // Thêm session cookie
                sessionService.addSessionCookie(response, sessionInfo);

                // Thêm thông tin vào response header nếu cần
                response.setHeader("X-Admin-Name", sessionInfo.getName());
                response.setHeader("X-Admin-Email", sessionInfo.getEmail());

                response.sendRedirect("/admin/admin-dashboard");
            }

        } catch (Exception e) {
            response.sendRedirect("/login?error=true");
        }

    }
}
