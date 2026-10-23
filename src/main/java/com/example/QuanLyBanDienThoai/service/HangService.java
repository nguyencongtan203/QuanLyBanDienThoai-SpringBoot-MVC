package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.entity.Hang;
import com.example.QuanLyBanDienThoai.repository.HangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangService {
    @Autowired
    private HangRepository hangRepository;

    public List<Hang> getAllBrands(){
        return hangRepository.findAll();
    }
}
