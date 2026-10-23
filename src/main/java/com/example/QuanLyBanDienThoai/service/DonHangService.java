package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.core.TrangThaiDonHang;
import com.example.QuanLyBanDienThoai.entity.Chitietdonhang;
import com.example.QuanLyBanDienThoai.entity.Chitietgiohang;
import com.example.QuanLyBanDienThoai.entity.Donhang;
import com.example.QuanLyBanDienThoai.entity.Khachhang;
import com.example.QuanLyBanDienThoai.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class DonHangService {

    @Autowired
    private DonhangRepository donhangRepository;

    @Autowired
    private ChitietdonhangRepository chitietdonhangRepository;

    @Autowired
    private GiohangRepository giohangRepository;

    @Autowired
    private ChitietgiohangRepository chiTietGioHangRepository;

    @Autowired
    private KhachhangRepository khachhangRepository;

    public List<Donhang> getAllDonHang() {
        return donhangRepository.findAll();
    }

    public List<Donhang> getDonHangByMaKh(int makh) {
        return donhangRepository.findByKhachHang_Makh(makh);
    }
    
    public List<Chitietdonhang> getAllOrderDetailByMaDh(int madh) {
        return chitietdonhangRepository.findByMadh(madh);
    }

    public void placeOrder(int magh, String address, String tel) {
        // Lấy thông tin khách hàng
        Khachhang khachhang = giohangRepository.findById(magh).get().getKhachHang();

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<Chitietgiohang> giohang = chiTietGioHangRepository.findByMagh(magh);

        if (giohang == null || giohang.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống.");
        }

        // Tính tổng tiền
        BigDecimal tongtien = giohang.stream()
                .map(Chitietgiohang::getThanhtien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo đơn hàng
        Donhang donhang = new Donhang();
        donhang.setKhachhang(khachhang);
        donhang.setTrangthai(TrangThaiDonHang.HANDLING);
        donhang.setNgaytao(new Timestamp(System.currentTimeMillis()));
        donhang.setTongtien(tongtien);
        donhang.setDiachi(address);
        donhang.setSdt(tel);
        donhang = donhangRepository.save(donhang); // lưu để lấy `madh` tự sinh

        // Tạo chi tiết đơn hàng từ giỏ hàng
        for (Chitietgiohang item : giohang) {
            Chitietdonhang ct = new Chitietdonhang();
            ct.setMadh(donhang.getMadh());
            ct.setMasp(item.getSanPham().getMasp());
            ct.setSanPham(item.getSanPham());
            ct.setDonHang(donhang);
            ct.setSoluong(item.getSoluong());
            ct.setDongia(item.getDongia());
            ct.setThanhtien(item.getThanhtien());
            chitietdonhangRepository.save(ct);
        }

        // Xoá giỏ hàng sau khi đặt
        chiTietGioHangRepository.deleteAll(giohang);
   }

   public boolean cancelOrder(int madh) {
        Optional<Donhang> donhangOptional = donhangRepository.findById(madh);

        if (donhangOptional.isPresent()) {
            Donhang donhang = donhangOptional.get();

            if (donhang.getTrangthai().equals(TrangThaiDonHang.HANDLING)){
                donhang.setTrangthai(TrangThaiDonHang.CANCELLED);
                donhangRepository.save(donhang);
                return true;
            }
        }
        return false;
   }

    public boolean submitOrder(int madh) {
        Optional<Donhang> donhangOptional = donhangRepository.findById(madh);

        if (donhangOptional.isPresent()) {
            Donhang donhang = donhangOptional.get();

            if (donhang.getTrangthai().equals(TrangThaiDonHang.HANDLING)){
                donhang.setTrangthai(TrangThaiDonHang.DELINQUENT);
                donhangRepository.save(donhang);
                return true;
            }
        }
        return false;
    }

    public boolean submitOrderComplete(int madh) {
        Optional<Donhang> donhangOptional = donhangRepository.findById(madh);

        if (donhangOptional.isPresent()) {
            Donhang donhang = donhangOptional.get();

            if (donhang.getTrangthai().equals(TrangThaiDonHang.DELINQUENT)){
                donhang.setTrangthai(TrangThaiDonHang.COMPLETED);
                donhangRepository.save(donhang);
                return true;
            }
        }
        return false;
    }

    public boolean submitOrderRefund(int madh) {
        Optional<Donhang> donhangOptional = donhangRepository.findById(madh);

        if (donhangOptional.isPresent()) {
            Donhang donhang = donhangOptional.get();

            if (donhang.getTrangthai().equals(TrangThaiDonHang.DELINQUENT)){
                donhang.setTrangthai(TrangThaiDonHang.REFUND);
                donhangRepository.save(donhang);
                return true;
            }
        }
        return false;
    }
}


