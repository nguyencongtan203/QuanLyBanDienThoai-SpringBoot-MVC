function updateCountdown() {
    // Lấy thời gian hiện tại
    const now = new Date();

    // Set thời gian kết thúc cụ thể (12:05 AM ngày 9/4/2025)
    const endDate = new Date('2025-04-15T15:41:50+07:00');

    const timeLeft = endDate - now;

    // Tính ngày
    const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));

    // Tính giờ
    const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));

    // Tính phút
    const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));

    // Tính giây
    const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

    // Cập nhật HTML
    document.querySelector('.hot-deal-countdown li:nth-child(1) h3').textContent =
        days < 10 ? '0' + days : days;

    document.querySelector('.hot-deal-countdown li:nth-child(2) h3').textContent =
        hours < 10 ? '0' + hours : hours;

    document.querySelector('.hot-deal-countdown li:nth-child(3) h3').textContent =
        minutes < 10 ? '0' + minutes : minutes;

    document.querySelector('.hot-deal-countdown li:nth-child(4) h3').textContent =
        seconds < 10 ? '0' + seconds : seconds;

    if (timeLeft < 0) {
        clearInterval(countdownInterval);
        document.querySelector('.hot-deal-countdown').innerHTML =
            '<h2>DEAL HAS ENDED!</h2>';
    }
}

const countdownInterval = setInterval(updateCountdown, 1000);
updateCountdown();

// Hàm format số thành dạng 000.000.000 đ
function formatPrice(price) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        maximumFractionDigits: 0
    }).format(price).replace('₫', 'đ');
}

// Hàm cập nhật tất cả giá
function updateAllPrices() {
    document.querySelectorAll('.formatted-price').forEach(element => {
        const price = element.getAttribute('data-price');
        if (price) {
            element.textContent = formatPrice(price);
        }
    });
}

// Chạy khi trang đã load xong
document.addEventListener('DOMContentLoaded', updateAllPrices);

//Tìm kiếm chuyển /store
document.addEventListener('DOMContentLoaded', function () {
    const hangSelect = document.getElementById('cate-brands');
    const searchForm = hangSelect.closest('form');

    function navigateToStore(brandId) {
        // Thêm loading indicator nếu cần
        console.log(`Sending request with brand:`, brandId);

        // Gửi POST request đến /store với dữ liệu JSON
        fetch('/store', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                brands: [parseInt(brandId)],
                rams: [],
                storages: []
            })
        })
            .then(response => {
                // Kiểm tra response status trước
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                // Kiểm tra content-type
                const contentType = response.headers.get("content-type");
                if (!contentType || !contentType.includes("application/json")) {
                    throw new Error("Received non-JSON response from server");
                }

                return response.json();
            })
            .then(data => {
                console.log(`Received response:`, data);

                if (data.success) {
                    // Chuyển hướng đến trang store
                    window.location.href = data.redirect || '/store';
                } else {
                    throw new Error(data.error || 'Unknown error occurred');
                }
            })
            .catch(error => {
                console.error(`Navigation error:`, error);
                alert('Có lỗi xảy ra khi chuyển trang. Vui lòng thử lại sau.');
            });
    }

    // Xử lý submit form
    searchForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const selectedBrand = hangSelect.value;
        if (selectedBrand) {
            console.log(`Form submitted with brand:`, selectedBrand);
            navigateToStore(selectedBrand);
        }
    });

    // Xử lý thay đổi select box
    hangSelect.addEventListener('change', function () {
        const selectedBrand = this.value;
        if (selectedBrand) {
            console.log(`Select changed to:`, selectedBrand);
            // Tự động submit form khi người dùng chọn
            searchForm.dispatchEvent(new Event('submit'));
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.querySelector('.search-product');
    const searchButton = document.querySelector('.search-product-btn');

    // Hàm xử lý tìm kiếm và chuyển trang
    function handleSearch(e) {
        if (e) {
            e.preventDefault();
        }

        const keyword = searchInput.value.trim();

        if (keyword) {
            // Tạo đối tượng chứa dữ liệu tìm kiếm
            const searchData = {
                keyword: keyword
            };

            // Chuyển đối tượng thành chuỗi và mã hóa để truyền qua URL
            const encodedData = encodeURIComponent(JSON.stringify(searchData));

            // Chuyển đến trang store với dữ liệu tìm kiếm
            window.location.href = `/store?search=${encodedData}`;
        }
    }

    // Xử lý sự kiện click search button
    if (searchButton) {
        searchButton.addEventListener('click', function (e) {
            e.preventDefault();
            handleSearch(e);
        });
    }

    // Xử lý sự kiện enter trong input
    if (searchInput) {
        searchInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                handleSearch(e);
            }
        });
    }
});