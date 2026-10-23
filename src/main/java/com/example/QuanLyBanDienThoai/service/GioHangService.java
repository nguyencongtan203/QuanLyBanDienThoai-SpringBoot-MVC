package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.entity.Giohang;
import com.example.QuanLyBanDienThoai.entity.Khachhang;
import com.example.QuanLyBanDienThoai.repository.GiohangRepository;
import com.example.QuanLyBanDienThoai.repository.KhachhangRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GioHangService {
    @Autowired
    private GiohangRepository giohangRepository;
    @Autowired
    private KhachhangRepository khachhangRepository;
    public Giohang createGioHang(int id) {
        Optional<Khachhang> existsKhachHang = khachhangRepository.findById(id);
        if(existsKhachHang.isPresent()){
            Khachhang taikhoan = existsKhachHang.get();
            Giohang giohang = new Giohang();
            giohang.setKhachHang(taikhoan);
            return giohangRepository.save(giohang);
        }
        return null;
    }
    public Giohang getGioHang(int id) {
        Optional<Giohang> existsGioHang = giohangRepository.findById(id);
        if(existsGioHang.isPresent()){
            Giohang giohang = existsGioHang.get();
            return giohang;
        }
        return null;
    }
}
