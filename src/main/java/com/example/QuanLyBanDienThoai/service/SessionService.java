package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.core.SessionInfo;
import com.example.QuanLyBanDienThoai.entity.Khachhang;
import com.example.QuanLyBanDienThoai.entity.Nhanvien;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {
    private final Map<String, SessionInfo> adminSessions = new ConcurrentHashMap<>();
    private final Map<String, SessionInfo> userSessions = new ConcurrentHashMap<>();

    // Thời gian timeout cho session (1 giờ)
    private static final int SESSION_TIMEOUT = 3600;

    public SessionInfo createUserSession(Khachhang khachhang) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setSessionId(generateSessionId());
        sessionInfo.setEmail(khachhang.getEmail());
        sessionInfo.setName(khachhang.getTenkh());
        sessionInfo.setRole("USER");
        sessionInfo.setPhoneNumber(khachhang.getSdt());
        sessionInfo.setAddress(khachhang.getDiachi());
        sessionInfo.setCartId(khachhang.getGioHang().getMagh()+"");

        userSessions.put(sessionInfo.getSessionId(), sessionInfo);
        return sessionInfo;
    }

    public SessionInfo createAdminSession(Nhanvien nhanvien) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setSessionId(generateSessionId());
        sessionInfo.setEmail(nhanvien.getTaikhoan());
        sessionInfo.setName(nhanvien.getTennv());
        sessionInfo.setRole("ADMIN");

        adminSessions.put(sessionInfo.getSessionId(), sessionInfo);
        return sessionInfo;
    }

    public SessionInfo getUserSession(String sessionId) {
        return userSessions.get(sessionId);
    }

    public SessionInfo getAdminSession(String sessionId) {
        return adminSessions.get(sessionId);
    }

    public void invalidateUserSession(String sessionId) {
        userSessions.remove(sessionId);
    }

    public void invalidateAdminSession(String sessionId) {
        adminSessions.remove(sessionId);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public void addSessionCookie(HttpServletResponse response, SessionInfo sessionInfo) {
        Cookie sessionCookie;
        if ("ADMIN".equals(sessionInfo.getRole())) {
            sessionCookie = new Cookie("ADMIN_SESSION", sessionInfo.getSessionId());
            sessionCookie.setPath("/admin");
        } else {
            sessionCookie = new Cookie("USER_SESSION", sessionInfo.getSessionId());
            sessionCookie.setPath("/");
        }

        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true); // Sử dụng cho HTTPS
        sessionCookie.setMaxAge(SESSION_TIMEOUT);
        response.addCookie(sessionCookie);
    }

    public void removeSessionCookie(HttpServletResponse response, String role) {
        Cookie sessionCookie;
        if ("ADMIN".equals(role)) {
            sessionCookie = new Cookie("ADMIN_SESSION", null);
            sessionCookie.setPath("/admin");
        } else {
            sessionCookie = new Cookie("USER_SESSION", null);
            sessionCookie.setPath("/");
        }

        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
    }
}
