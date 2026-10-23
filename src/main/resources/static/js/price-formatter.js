function formatPrice(price) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        maximumFractionDigits: 0
    }).format(price).replace('₫', 'đ');
}

function updateAllPrices() {
    document.querySelectorAll('.formatted-price').forEach(element => {
        const price = element.getAttribute('data-price');
        if (price) {
            element.textContent = formatPrice(price);
        }
    });
}

// Export để file khác có thể sử dụng
window.formatPrice = formatPrice;
window.updateAllPrices = updateAllPrices;