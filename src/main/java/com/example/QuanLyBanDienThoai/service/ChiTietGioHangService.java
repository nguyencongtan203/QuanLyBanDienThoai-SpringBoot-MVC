package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.entity.Chitietgiohang;
import com.example.QuanLyBanDienThoai.entity.Giohang;
import com.example.QuanLyBanDienThoai.entity.Sanpham;
import com.example.QuanLyBanDienThoai.repository.ChitietgiohangRepository;
import com.example.QuanLyBanDienThoai.repository.GiohangRepository;
import com.example.QuanLyBanDienThoai.repository.SanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ChiTietGioHangService {
    @Autowired
    private ChitietgiohangRepository chitietgiohangRepository;
    @Autowired
    private GiohangRepository giohangRepository;
    @Autowired
    private SanphamRepository sanphamRepository;
    public List<Chitietgiohang> getAllCartDetail(){
        return chitietgiohangRepository.findAll();
    }

    public List<Chitietgiohang> getAllCartDetailByIdCart(int magh){
        return chitietgiohangRepository.findByMagh(magh);
    }

    public Optional<Chitietgiohang> getByIdCartAndIdProduct(int magh, int masp){
        return chitietgiohangRepository.findByMaghAndMasp(magh,masp);
    }
    public void addToCart(int magh, int masp, int quantity) {
        Giohang giohang = giohangRepository.findById(magh).orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại"));
        Sanpham sanpham = sanphamRepository.findById(masp).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        Optional<Chitietgiohang> chitietgiohangOptional = getByIdCartAndIdProduct(magh, masp);
        System.out.println("ma gio hang: "+ giohang.getMagh());
        System.out.println("ma san pham: " + sanpham.getMasp());
        if (chitietgiohangOptional.isPresent()) {
            Chitietgiohang chitietgiohang = chitietgiohangOptional.get();
            chitietgiohang.setSoluong(chitietgiohang.getSoluong() + quantity);
            chitietgiohang.setThanhtien(chitietgiohang.getDongia().multiply(BigDecimal.valueOf(chitietgiohang.getSoluong())));
            chitietgiohangRepository.save(chitietgiohang);
        } else {
            Chitietgiohang chitietgiohang = new Chitietgiohang();
            chitietgiohang.setMagh(magh); // set ma gio hang truoc
            chitietgiohang.setMasp(masp); // set ma san pham truoc
            chitietgiohang.setGioHang(giohang);
            chitietgiohang.setSanPham(sanpham);
            chitietgiohang.setSoluong(quantity);
            chitietgiohang.setDongia(sanpham.getGia());
            chitietgiohang.setThanhtien(sanpham.getGia().multiply(BigDecimal.valueOf(quantity)));
            chitietgiohangRepository.save(chitietgiohang);
        }
    }
    public void removeProductFromCart(int magh, int masp) {
        Optional<Chitietgiohang> chitietgiohangOptional = getByIdCartAndIdProduct(magh, masp);

        chitietgiohangOptional.ifPresent(chitietgiohang -> chitietgiohangRepository.delete(chitietgiohang));
    }

    public BigDecimal tinhTongTien(List<Chitietgiohang> chitietgiohangList) {
        BigDecimal tong = BigDecimal.ZERO;
        for (Chitietgiohang item : chitietgiohangList) {
            tong = tong.add(item.getThanhtien());
        }
        return tong;
    }
    public void updateQuantity(int magh, int masp, String action) {
        Optional<Chitietgiohang> optional = getByIdCartAndIdProduct(magh, masp);
        if (optional.isPresent()) {
            Chitietgiohang chitiet = optional.get();
            int currentQuantity = chitiet.getSoluong();

            if ("increase".equals(action)) {
                chitiet.setSoluong(currentQuantity + 1);

            } else if ("decrease".equals(action)) {
                // Giảm nhưng không nhỏ hơn 1
                if (currentQuantity > 1) {
                    chitiet.setSoluong(currentQuantity - 1);
                }
            }
            BigDecimal dongia = chitiet.getDongia();
            int soluong = chitiet.getSoluong();
            chitiet.setThanhtien(dongia.multiply(BigDecimal.valueOf(soluong)));
            chitietgiohangRepository.save(chitiet);
        }
    }
}
