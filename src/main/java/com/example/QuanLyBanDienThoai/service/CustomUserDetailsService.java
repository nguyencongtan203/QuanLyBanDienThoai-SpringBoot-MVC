package com.example.QuanLyBanDienThoai.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final KhachHangService khachHangService;

    // Constructor injection
    public CustomUserDetailsService(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return khachHangService.findByEmail(email)
                .map(khachHang -> new User(
                        khachHang.getEmail(),
                        khachHang.getMatkhau(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
