package com.example.QuanLyBanDienThoai.repository;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomOAuth2User extends OAuth2User {
    String getEmail();
    String getName();
}
