package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.entity.Chitietdonhang;
import com.example.QuanLyBanDienThoai.repository.ChitietdonhangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietDonHangService {
    @Autowired
    ChitietdonhangRepository chitietdonhangRepository;

    public List<Chitietdonhang> getChiTietDonHangByMaDh(int madh) {
        return chitietdonhangRepository.findByMadh(madh);
    }
}
