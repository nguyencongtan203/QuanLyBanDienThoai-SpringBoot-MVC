package com.example.QuanLyBanDienThoai.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminDetailService implements UserDetailsService {
    private final NhanVienService nhanVienService;

    public AdminDetailService(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return nhanVienService.findByTaikhoan(email)
                .map(nhanvien -> new User(
                        nhanvien.getTaikhoan(),
                        nhanvien.getMatkhau(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
