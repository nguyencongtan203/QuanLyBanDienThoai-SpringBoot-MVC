package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.entity.Cauhinh;
import com.example.QuanLyBanDienThoai.repository.CauhinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CauHinhService {
    @Autowired
    private CauhinhRepository cauhinhRepository;

    public List<Cauhinh> getAllCauHinh() { return cauhinhRepository.findAll(); }
}
