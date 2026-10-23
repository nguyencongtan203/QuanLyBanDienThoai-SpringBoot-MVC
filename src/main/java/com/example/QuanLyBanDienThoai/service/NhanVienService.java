package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.dto.request.KhachHangLoginRequest;
import com.example.QuanLyBanDienThoai.dto.request.NhanVienLoginRequest;
import com.example.QuanLyBanDienThoai.entity.Khachhang;
import com.example.QuanLyBanDienThoai.entity.Nhanvien;
import com.example.QuanLyBanDienThoai.repository.NhanvienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class NhanVienService {
    @Autowired
    private NhanvienRepository nhanvienRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<Nhanvien> findByTaikhoan(String taikhoan){
        return nhanvienRepository.findByTaikhoan(taikhoan);
    }

    public Boolean loginAccount(NhanVienLoginRequest request){
        Optional<Nhanvien> accountOpt = nhanvienRepository.findByTaikhoan(request.getTaikhoan());

        if (accountOpt.isPresent()) {
            Nhanvien account = accountOpt.get();
            if (passwordEncoder.matches(request.getMatkhau(), account.getMatkhau())) {
                return true; // Đăng nhập thành công
            }
        }
        return false; // Sai email hoặc mật khẩu
    }

}
