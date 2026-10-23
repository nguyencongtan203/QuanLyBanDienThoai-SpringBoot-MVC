package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.dto.request.KhachHangCreationRequest;
import com.example.QuanLyBanDienThoai.dto.request.KhachHangLoginRequest;
import com.example.QuanLyBanDienThoai.entity.Chitietgiohang;
import com.example.QuanLyBanDienThoai.entity.Giohang;
import com.example.QuanLyBanDienThoai.entity.Khachhang;
import com.example.QuanLyBanDienThoai.repository.GiohangRepository;
import com.example.QuanLyBanDienThoai.repository.KhachhangRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KhachHangService {
    @Autowired
    private KhachhangRepository khachhangRepository;
    @Autowired
    private GiohangRepository giohangRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public List<Khachhang> getAllKhachHang(){
        return khachhangRepository.findAll();
    }

    public Boolean loginAccount(KhachHangLoginRequest request){
        Optional<Khachhang> accountOpt = khachhangRepository.findByEmail(request.getEmail());

        if (accountOpt.isPresent()) {
            Khachhang account = accountOpt.get();
            if (passwordEncoder.matches(request.getMatkhau(), account.getMatkhau())) {
                return true; // Đăng nhập thành công
            }
        }
        return false; // Sai email hoặc mật khẩu
    }

    public String registerAccount(KhachHangCreationRequest request) {
        // Kiểm tra tài khoản đã tồn tại chưa
        Optional<Khachhang>  existingAccount = khachhangRepository.findByEmail(request.getEmail());
        if (existingAccount.isPresent()) {
            return "Tài khoản đã tồn tại!";
        } else {
            Optional<Khachhang> existingSDT = khachhangRepository.findBySdt(request.getSdt());
            if(existingSDT.isPresent()){
                return "Đã có tài khoản sử dụng số điện thoại này";
            } else {
                if (request.getSdt().length() > 11) {
                    return "Số điện thoại tối đa 11 số!";
                } else if (request.getSdt().length() < 10) {
                    return "Số điện thoại phải có ít nhất 10 số!";
                }
                String[] validPrefixes = {"03", "05", "07", "08", "09"};
                boolean hasValidPrefix = false;
                for (String prefix : validPrefixes) {
                    if (request.getSdt().startsWith(prefix)) {
                        hasValidPrefix = true;
                        break;
                    }
                }

                if (!hasValidPrefix) {
                    return "Số điện thoại không hợp lệ! Phải bắt đầu bằng 03, 05, 07, 08 hoặc 09";
                }
            }
        }
        // Tạo đối tượng khách hàng mới
        Khachhang newKhachHang = new Khachhang();
        newKhachHang.setTenkh(request.getTenkh());
        newKhachHang.setDiachi(request.getDiachi());
        newKhachHang.setSdt(request.getSdt());
        newKhachHang.setEmail(request.getEmail());

        // Mã hóa mật khẩu
        newKhachHang.setMatkhau(passwordEncoder.encode(request.getMatkhau()));

        // Lưu vào database
        khachhangRepository.save(newKhachHang);

        return "Đăng ký thành công!";
    }

    public Optional<Khachhang> findByEmail(String email){
        return khachhangRepository.findByEmail(email);
    }

    public void processOAuthPostLogin(String email, String name,String phoneNumber, String locale) {
        Optional<Khachhang> existUser = khachhangRepository.findByEmail(email);

        if (existUser.isEmpty()) {
            Khachhang newKhachHang = new Khachhang();
            newKhachHang.setEmail(email);
            newKhachHang.setTenkh(name);
            newKhachHang.setDiachi(locale);
            newKhachHang.setSdt(phoneNumber);

            try {
                khachhangRepository.save(newKhachHang);
                Giohang giohang = new Giohang();
                giohang.setKhachHang(newKhachHang);
                giohangRepository.save(giohang);
            } catch (Exception e) {
                // Log the error
                e.printStackTrace();
                throw new RuntimeException("Không thể tạo tài khoản mới: " + e.getMessage());
            }
        }
    }
}
