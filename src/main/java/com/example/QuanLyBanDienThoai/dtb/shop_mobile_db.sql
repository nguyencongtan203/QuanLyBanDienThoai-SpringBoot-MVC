-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 08, 2025 at 03:11 PM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shop_mobile_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `cauhinh`
--

DROP TABLE IF EXISTS `cauhinh`;
CREATE TABLE IF NOT EXISTS `cauhinh` (
  `mach` int NOT NULL AUTO_INCREMENT,
  `tench` varchar(50) NOT NULL,
  PRIMARY KEY (`mach`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cauhinh`
--

INSERT INTO `cauhinh` (`mach`, `tench`) VALUES
(1, 'Hệ điều hành'),
(2, 'Chip xử lý (CPU)'),
(3, 'Tốc độ CPU'),
(4, 'Chip đồ họa (GPU)'),
(5, 'RAM'),
(6, 'Dung lượng lưu trữ'),
(7, 'Danh bạ'),
(8, 'Độ phân giải camera sau'),
(9, 'Quay phim camera sau'),
(10, 'Đèn Flash camera sau'),
(11, 'Tính năng camera sau'),
(12, 'Độ phân giải camera trước'),
(13, 'Tính năng camera trước'),
(14, 'Công nghệ màn hình'),
(15, 'Độ phân giải màn hình'),
(16, 'Màn hình'),
(17, 'Mặt kính cảm ứng'),
(18, 'Dung lượng pin'),
(19, 'Loại pin'),
(20, 'Hỗ trợ sạc tối đa'),
(21, 'Mạng di động'),
(22, 'SIM'),
(23, 'GPS'),
(24, 'Wifi'),
(25, 'Bluetooth'),
(26, 'Cổng kết nối/sạc'),
(27, 'Jack tai nghe'),
(28, 'Camera sau'),
(29, 'Camera trước');

-- --------------------------------------------------------

--
-- Table structure for table `chitietcauhinh`
--

DROP TABLE IF EXISTS `chitietcauhinh`;
CREATE TABLE IF NOT EXISTS `chitietcauhinh` (
  `masp` int NOT NULL,
  `mach` int NOT NULL,
  `giatri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  KEY `fkchitietcauhinhsanpham` (`masp`),
  KEY `fkchitietcauhinhcauhinh` (`mach`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `chitietcauhinh`
--

INSERT INTO `chitietcauhinh` (`masp`, `mach`, `giatri`) VALUES
(1, 16, 'IPS LCD 6.79\" Full HD+'),
(1, 1, 'Android 14'),
(1, 28, 'Chính 108 MP & Phụ 2 MP'),
(1, 29, '13 MP'),
(1, 2, 'MediaTek Helio G91 Ultra 8 nhân'),
(1, 5, '6 GB'),
(1, 6, '128 GB'),
(1, 22, '2 Nano SIM, Hỗ trợ 4G'),
(1, 18, '5030 mAh'),
(1, 20, '33 W'),
(4, 5, '8 GB'),
(2, 5, '6 GB'),
(2, 6, '64 GB'),
(4, 6, '128 GB'),
(3, 5, '16 GB'),
(5, 5, '12 GB'),
(6, 5, '4 GB'),
(7, 5, '3 GB'),
(3, 6, '1 TB'),
(5, 6, '256 GB'),
(6, 6, '512 GB'),
(7, 6, '256 GB'),
(8, 6, '512 GB'),
(8, 5, '12 GB'),
(9, 6, '256 GB'),
(9, 5, '12 GB'),
(2, 1, 'Android 13'),
(2, 2, 'MediaTek Helio G81-Ultra 8 nhân'),
(2, 16, 'IPS LCD 6.67\" HD+'),
(2, 28, 'Chính 50 MP & Cảm biến Flicker'),
(2, 29, '5 MP'),
(2, 22, '2 Nano SIM, Hỗ trợ 4G'),
(2, 18, '5600 mAh'),
(2, 19, 'Li-Po');

-- --------------------------------------------------------

--
-- Table structure for table `chitietdonhang`
--

DROP TABLE IF EXISTS `chitietdonhang`;
CREATE TABLE IF NOT EXISTS `chitietdonhang` (
  `madh` int NOT NULL,
  `masp` int NOT NULL,
  `soluong` int NOT NULL,
  `dongia` decimal(10,0) NOT NULL,
  `thanhtien` decimal(10,0) NOT NULL,
  PRIMARY KEY (`madh`,`masp`),
  KEY `fkchitietdonhangsanpham` (`masp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`madh`, `masp`, `soluong`, `dongia`, `thanhtien`) VALUES
(1, 1, 2, 3990000, 7980000),
(1, 3, 2, 31190000, 62380000),
(2, 1, 2, 3990000, 7980000),
(2, 3, 2, 31190000, 62380000),
(3, 2, 1, 5290000, 5290000),
(3, 3, 2, 31190000, 62380000),
(3, 4, 2, 9490000, 18980000),
(4, 2, 1, 5290000, 5290000),
(4, 3, 1, 31190000, 31190000),
(4, 4, 1, 9490000, 9490000),
(5, 2, 1, 5290000, 5290000),
(5, 3, 1, 31190000, 31190000),
(6, 3, 1, 31190000, 31190000);

-- --------------------------------------------------------

--
-- Table structure for table `chitietgiohang`
--

DROP TABLE IF EXISTS `chitietgiohang`;
CREATE TABLE IF NOT EXISTS `chitietgiohang` (
  `magh` int NOT NULL,
  `masp` int NOT NULL,
  `soluong` int NOT NULL,
  `dongia` decimal(10,0) NOT NULL,
  `thanhtien` decimal(10,0) NOT NULL,
  PRIMARY KEY (`magh`,`masp`),
  KEY `fkchitietgiohangsanpham` (`masp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `chitietgiohang`
--

INSERT INTO `chitietgiohang` (`magh`, `masp`, `soluong`, `dongia`, `thanhtien`) VALUES
(2, 5, 1, 44990000, 44990000);

-- --------------------------------------------------------

--
-- Table structure for table `chitietmau`
--

DROP TABLE IF EXISTS `chitietmau`;
CREATE TABLE IF NOT EXISTS `chitietmau` (
  `masp` int NOT NULL,
  `mamau` int NOT NULL,
  PRIMARY KEY (`masp`,`mamau`),
  KEY `fkchitietmaumau` (`mamau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `chitietmau`
--

INSERT INTO `chitietmau` (`masp`, `mamau`) VALUES
(1, 3),
(2, 3),
(1, 4),
(1, 7),
(2, 9),
(2, 10);

-- --------------------------------------------------------

--
-- Table structure for table `donhang`
--

DROP TABLE IF EXISTS `donhang`;
CREATE TABLE IF NOT EXISTS `donhang` (
  `madh` int NOT NULL AUTO_INCREMENT,
  `makh` int NOT NULL,
  `trangthai` tinyint NOT NULL,
  `ngaytao` timestamp NOT NULL,
  `tongtien` decimal(10,0) NOT NULL,
  `diachi` varchar(100) NOT NULL,
  `sdt` varchar(11) NOT NULL,
  PRIMARY KEY (`madh`),
  KEY `fkdonhangkhachhang` (`makh`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `donhang`
--

INSERT INTO `donhang` (`madh`, `makh`, `trangthai`, `ngaytao`, `tongtien`, `diachi`, `sdt`) VALUES
(1, 15, 4, '2025-05-04 12:07:41', 70360000, 'Quận 4', '0945281379'),
(2, 15, 0, '2025-05-04 12:10:15', 70360000, 'Quận 4', '0526478321'),
(3, 15, 3, '2025-05-04 12:14:42', 86650000, 'Quận 4', '0945281379'),
(4, 15, 2, '2025-05-05 17:04:36', 45970000, 'Quận 4', '0526478321'),
(5, 15, 0, '2025-05-05 17:39:50', 36480000, 'Quận 7', '0912345678'),
(6, 16, 1, '2025-05-05 17:51:39', 31190000, 'Quan7', '0312457891');

-- --------------------------------------------------------

--
-- Table structure for table `giohang`
--

DROP TABLE IF EXISTS `giohang`;
CREATE TABLE IF NOT EXISTS `giohang` (
  `magh` int NOT NULL AUTO_INCREMENT,
  `makh` int NOT NULL,
  PRIMARY KEY (`magh`),
  KEY `fkgiohangkhachhang` (`makh`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `giohang`
--

INSERT INTO `giohang` (`magh`, `makh`) VALUES
(1, 15),
(2, 16),
(3, 17);

-- --------------------------------------------------------

--
-- Table structure for table `hang`
--

DROP TABLE IF EXISTS `hang`;
CREATE TABLE IF NOT EXISTS `hang` (
  `mahang` int NOT NULL AUTO_INCREMENT,
  `tenhang` varchar(50) NOT NULL,
  PRIMARY KEY (`mahang`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `hang`
--

INSERT INTO `hang` (`mahang`, `tenhang`) VALUES
(1, 'Samsung'),
(2, 'Iphone'),
(3, 'Oppo'),
(4, 'Realme'),
(5, 'Xiaomi'),
(6, 'Vivo'),
(7, 'Honor');

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

DROP TABLE IF EXISTS `hoadon`;
CREATE TABLE IF NOT EXISTS `hoadon` (
  `mahd` int NOT NULL AUTO_INCREMENT,
  `madh` int NOT NULL,
  `manv` int NOT NULL,
  `tongtien` decimal(10,0) NOT NULL,
  `ngaytao` timestamp NOT NULL,
  PRIMARY KEY (`mahd`),
  KEY `fkhoadondonhang` (`madh`),
  KEY `fkhoadonnhanvien` (`manv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
CREATE TABLE IF NOT EXISTS `khachhang` (
  `makh` int NOT NULL AUTO_INCREMENT,
  `tenkh` varchar(50) NOT NULL,
  `diachi` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sdt` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `matkhau` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`makh`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `sdt` (`sdt`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`makh`, `tenkh`, `diachi`, `sdt`, `email`, `matkhau`) VALUES
(7, 'Tan', 'Quan 4', '09123456789', 'tan@gmail.com', '$2a$10$C0ce2fhphsjJJkfZVpNyteGHnNkuo0ST8b3gFCUYQtbIC3Ahw8gvS'),
(10, 'nguyen cong tan', NULL, NULL, 'tanboro365@gmail.com', NULL),
(15, 'Thiện', 'Quận 7', '0912345678', 'nguyenchithien@gmail.com', '$2a$10$mMJQ/UPWHpv1mrnVo7HCluFjNiIqDgfupwdM5vwx5TzyODQFRzOJC'),
(16, 'Chí Thiện Nguyễn', NULL, NULL, 'nguyenchithien2812@gmail.com', NULL),
(17, 'ThienDao', 'Quận 7', '0912345679', 'nguyenchithien21@gmail.com', '$2a$10$zWEFZdgXNOYMCgadZyCE0uhpxxixYvS8KXsLiuet2T8mv36rp.ahq');

-- --------------------------------------------------------

--
-- Table structure for table `mausanpham`
--

DROP TABLE IF EXISTS `mausanpham`;
CREATE TABLE IF NOT EXISTS `mausanpham` (
  `mamau` int NOT NULL AUTO_INCREMENT,
  `giatri` varchar(50) NOT NULL,
  PRIMARY KEY (`mamau`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `mausanpham`
--

INSERT INTO `mausanpham` (`mamau`, `giatri`) VALUES
(1, 'Nâu'),
(2, 'Bạc'),
(3, 'Xanh Dương'),
(4, 'Đen'),
(5, 'Trắng'),
(6, 'Hồng'),
(7, 'Vàng'),
(8, 'Xanh Lá'),
(9, 'Tím'),
(10, 'Xanh Lá Nhạt');

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--

DROP TABLE IF EXISTS `nhanvien`;
CREATE TABLE IF NOT EXISTS `nhanvien` (
  `manv` int NOT NULL AUTO_INCREMENT,
  `tennv` varchar(50) NOT NULL,
  `diachi` varchar(50) NOT NULL,
  `sdt` char(11) NOT NULL,
  `chucvu` tinyint NOT NULL,
  `ngaysinh` timestamp NOT NULL,
  `taikhoan` char(20) NOT NULL,
  `matkhau` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`manv`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `nhanvien`
--

INSERT INTO `nhanvien` (`manv`, `tennv`, `diachi`, `sdt`, `chucvu`, `ngaysinh`, `taikhoan`, `matkhau`) VALUES
(1, 'admin', 'quan 7', '', 0, '2025-05-07 08:12:50', 'toibingu', '$2a$10$mMJQ/UPWHpv1mrnVo7HCluFjNiIqDgfupwdM5vwx5TzyODQFRzOJC');

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

DROP TABLE IF EXISTS `sanpham`;
CREATE TABLE IF NOT EXISTS `sanpham` (
  `masp` int NOT NULL AUTO_INCREMENT,
  `mahang` int NOT NULL,
  `tinhtrang` tinyint NOT NULL,
  `tensp` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gia` decimal(10,0) NOT NULL,
  `hinhanh` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`masp`),
  KEY `fksanphamhang` (`mahang`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`masp`, `mahang`, `tinhtrang`, `tensp`, `gia`, `hinhanh`) VALUES
(1, 5, 0, 'Xiaomi Redmi 13x', 3990000, 'xiaomi-redmi-13x-titanium-thumbn-600x600.png'),
(2, 4, 0, 'Realme C75x', 5290000, 'realme-c75x-pink-thumbn-600x600.png'),
(3, 2, 0, 'iPhone 16 Pro Max', 31190000, 'iphone-16-pro-max-sa-mac-thumb-1-600x600.png'),
(4, 1, 0, 'Samsung Galaxy A56 5G', 9490000, 'samsung-galaxy-a56-5g-green-thumb-600x600.png'),
(5, 3, 0, 'OPPO Find N5 5G', 44990000, 'oppo-find-n5-black-thumb-600x600.png'),
(6, 1, 0, 'Samsung Galaxy A06', 2890000, 'samsung-galaxy-a06-4gb-64gb.png'),
(7, 2, 0, 'iPhone 15 Plus', 19590000, 'iphone-15-plus-xanh-la-128gb-thumb-600x600.png'),
(8, 7, 0, 'HONOR X5b Plus', 2490000, 'honor-x5b-plus.png'),
(9, 6, 0, 'vivo V40 5G', 12990000, 'vivo-v40-5g.png');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chitietcauhinh`
--
ALTER TABLE `chitietcauhinh`
  ADD CONSTRAINT `fkchitietcauhinhcauhinh` FOREIGN KEY (`mach`) REFERENCES `cauhinh` (`mach`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fkchitietcauhinhsanpham` FOREIGN KEY (`masp`) REFERENCES `sanpham` (`masp`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `fkchitietdonhangdonhang` FOREIGN KEY (`madh`) REFERENCES `donhang` (`madh`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fkchitietdonhangsanpham` FOREIGN KEY (`masp`) REFERENCES `sanpham` (`masp`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `chitietgiohang`
--
ALTER TABLE `chitietgiohang`
  ADD CONSTRAINT `fkchitietgiohanggiohang` FOREIGN KEY (`magh`) REFERENCES `giohang` (`magh`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fkchitietgiohangsanpham` FOREIGN KEY (`masp`) REFERENCES `sanpham` (`masp`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `chitietmau`
--
ALTER TABLE `chitietmau`
  ADD CONSTRAINT `fkchitietmaumau` FOREIGN KEY (`mamau`) REFERENCES `mausanpham` (`mamau`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fkchitietmausanpham` FOREIGN KEY (`masp`) REFERENCES `sanpham` (`masp`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `fkdonhangkhachhang` FOREIGN KEY (`makh`) REFERENCES `khachhang` (`makh`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `giohang`
--
ALTER TABLE `giohang`
  ADD CONSTRAINT `fkgiohangkhachhang` FOREIGN KEY (`makh`) REFERENCES `khachhang` (`makh`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `fkhoadondonhang` FOREIGN KEY (`madh`) REFERENCES `donhang` (`madh`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fkhoadonnhanvien` FOREIGN KEY (`manv`) REFERENCES `nhanvien` (`manv`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `fksanphamhang` FOREIGN KEY (`mahang`) REFERENCES `hang` (`mahang`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
