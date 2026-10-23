document.addEventListener('DOMContentLoaded', function () {
    // Khởi tạo price formatter
    updateAllPrices();

    // Lấy các elements
    const ramCheckboxes = document.querySelectorAll('input[type="checkbox"][id^="ram-"]');
    const storageCheckboxes = document.querySelectorAll('input[type="checkbox"][id^="storage-"]');
    const brandCheckboxes = document.querySelectorAll('input[type="checkbox"][id^="brand-"]');
    const hangSelect = document.getElementById('cate-brands');

    // Kiểm tra selected brand từ model
    function checkSelectedBrand() {
        const selectedBrand = document.getElementById('selectedBrand');
        if (selectedBrand && selectedBrand.value) {
            const brandId = selectedBrand.value;

            // Cập nhật select box
            if (hangSelect) {
                hangSelect.value = brandId;
            }

            // Cập nhật checkboxes
            brandCheckboxes.forEach(cb => {
                cb.checked = cb.value === brandId;
            });
            handleFilterChange();
        }
    }

    function handleFilterChange() {

        const selectedRams = Array.from(document.querySelectorAll('input[type="checkbox"][id^="ram-"]:checked'))
            .map(cb => cb.value);

        const selectedStorages = Array.from(document.querySelectorAll('input[type="checkbox"][id^="storage-"]:checked'))
            .map(cb => cb.value);

        const selectedBrands = [];

        // Lấy từ checkboxes
        const checkedBrands = Array.from(document.querySelectorAll('input[type="checkbox"][id^="brand-"]:checked'))
            .map(cb => parseInt(cb.value))
            .filter(value => !isNaN(value));
        selectedBrands.push(...checkedBrands);

        // Thêm giá trị từ select box nếu có và không phải option đầu tiên
        if (hangSelect && hangSelect.value && hangSelect.selectedIndex !== 0) {
            const hangValue = parseInt(hangSelect.value);
            if (!isNaN(hangValue) && !selectedBrands.includes(hangValue)) {
                selectedBrands.push(hangValue);
            }
        }

        // Nếu không có filter nào được chọn, reload trang
        if (selectedRams.length === 0 && selectedStorages.length === 0 && selectedBrands.length === 0) {
            location.reload();
            return;
        }

        // Log trạng thái filter
        console.log(`Filter request state:`, {
            rams: selectedRams,
            storages: selectedStorages,
            brands: selectedBrands,
            selectIndex: hangSelect ? hangSelect.selectedIndex : 'N/A'
        });

        // Gửi request filter
        fetch('/store/filter', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                rams: selectedRams,
                storages: selectedStorages,
                brands: selectedBrands
            })
        })
            .then(async response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(data => {
                if (!data || !Array.isArray(data.products)) {
                    throw new Error('Invalid response format');
                }
                updateProductList(data.products);
            })
            .catch(error => {
                console.error(`Error:`, error);
                alert('Có lỗi xảy ra khi lọc sản phẩm. Vui lòng thử lại sau.');
            });
    }

    // Thêm event listeners cho checkbox
    [...ramCheckboxes, ...storageCheckboxes, ...brandCheckboxes].forEach(checkbox => {
        checkbox.addEventListener('change', handleFilterChange);
    });

    // Thêm event listener cho select box
    if (hangSelect) {
        hangSelect.addEventListener('change', function () {
            if (this.selectedIndex === 0) {
                // Nếu chọn option đầu tiên, bỏ check tất cả checkbox và reload trang
                brandCheckboxes.forEach(cb => {
                    cb.checked = false;
                });
                location.reload();
                return;
            }

            // Nếu chọn option khác
            brandCheckboxes.forEach(cb => {
                cb.checked = false;
            });
            handleFilterChange();
        });
    }

    // Kiểm tra selected brand khi load trang
    checkSelectedBrand();

    // Function updateProductList giữ nguyên
    function updateProductList(products) {
        const productContainer = document.querySelector('.filter-product');
        if (!productContainer) {
            console.error(`Product container not found`);
            return;
        }

        try {
            productContainer.innerHTML = '';

            if (products.length === 0) {
                productContainer.innerHTML = `
            <div class="col-md-12 text-center">
                <h3>Không tìm thấy sản phẩm phù hợp</h3>
            </div>`;
                return;
            }

            products.forEach((product, index) => {
                const productHtml = `
            <div class="col-md-4 col-xs-6">
                <div class="product">
                    <div class="product-img">
                        <img src="/imgs/${product.hinhanh}" alt="">
                        <div class="product-label">
                            <span class="sale">-30%</span>
                            <span class="new">NEW</span>
                        </div>
                    </div>
                    <div class="product-body">
                        <p class="product-category">${product.tenhang}</p>
                        <h3 class="product-name">
                            <a href="/product/${product.masp}">${product.tensp || ''}</a>
                        </h3>
                        <h4 class="product-price">
                            <span class="formatted-price" data-price="${product.gia || 0}">${formatPrice(product.gia || 0)}</span>
                            <del class="product-old-price formatted-price" data-price="${(product.gia || 0) * 1.3}">
                                ${formatPrice((product.gia || 0) * 1.3)}
                            </del>
                        </h4>
                        <div class="product-rating">
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                        </div>
                        <div class="product-btns">
                            <button class="add-to-wishlist" data-masp="${product.masp}">
                                <i class="fa fa-heart-o"></i>
                                <span class="tooltipp">add to wishlist</span>
                            </button>
                            <button class="add-to-compare" data-masp="${product.masp}">
                                <i class="fa fa-exchange"></i>
                                <span class="tooltipp">add to compare</span>
                            </button>
                            <button class="quick-view" data-masp="${product.masp}">
                                <i class="fa fa-eye"></i>
                                <span class="tooltipp">quick view</span>
                            </button>
                        </div>
                    </div>
                    <div class="add-to-cart">
                        <button class="add-to-cart-btn" data-masp="${product.masp}">
                            <i class="fa fa-shopping-cart"></i> add to cart
                        </button>
                    </div>
                </div>
                ${(index + 1) % 3 === 0 ? '<div class="clearfix visible-lg visible-md"></div>' : ''}
                ${(index + 1) % 2 === 0 ? '<div class="clearfix visible-sm visible-xs"></div>' : ''}
            </div>
        `;

                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = productHtml.trim();
                productContainer.appendChild(tempDiv.firstElementChild);
            });

            updateAllPrices();
            console.log(`Updated product list with ${products.length} products`);
        } catch (error) {
            console.error(`Error updating product list:`, error);
            alert('Có lỗi xảy ra khi hiển thị sản phẩm');
        }
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.querySelector('.search-product');
    const searchButton = document.querySelector('.search-product-btn');
    const productContainer = document.querySelector('.filter-product');
    updateAllPrices();
    // Hàm để render sản phẩm từ DTO
    function renderProducts(products) {
        if (!productContainer) return;

        productContainer.innerHTML = '';

        if (!products || products.length === 0) {
            productContainer.innerHTML = `
        <div class="col-md-12 text-center">
            <h3>Không tìm thấy sản phẩm phù hợp</h3>
        </div>`;
            return;
        }

        products.forEach((product, index) => {
            const productHtml = `
        <div class="col-md-4 col-xs-6">
            <div class="product">
                <div class="product-img">
                    <img src="/imgs/${product.hinhanh}" alt="">
                    <div class="product-label">
                        <span class="sale">-30%</span>
                        <span class="new">NEW</span>
                    </div>
                </div>
                <div class="product-body">
                    <p class="product-category">${product.tenhang || ''}</p>
                    <h3 class="product-name">
                        <a href="/product/${product.masp}">${product.tensp || ''}</a>
                    </h3>
                    <h4 class="product-price">
                        <span class="formatted-price" data-price="${product.gia || 0}">
                            ${formatPrice(product.gia || 0)}
                        </span>
                        <del class="product-old-price formatted-price" data-price="${(product.gia || 0) * 1.3}">
                            ${formatPrice((product.gia || 0) * 1.3)}
                        </del>
                    </h4>
                    <div class="product-rating">
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                    </div>
                    <div class="product-btns">
                        <button class="add-to-wishlist" data-masp="${product.masp}">
                            <i class="fa fa-heart-o"></i>
                            <span class="tooltipp">add to wishlist</span>
                        </button>
                        <button class="add-to-compare" data-masp="${product.masp}">
                            <i class="fa fa-exchange"></i>
                            <span class="tooltipp">add to compare</span>
                        </button>
                        <button class="quick-view" data-masp="${product.masp}">
                            <i class="fa fa-eye"></i>
                            <span class="tooltipp">quick view</span>
                        </button>
                    </div>
                </div>
                <div class="add-to-cart">
                    <button class="add-to-cart-btn" data-masp="${product.masp}">
                        <i class="fa fa-shopping-cart"></i> add to cart
                    </button>
                </div>
            </div>
            ${(index + 1) % 3 === 0 ? '<div class="clearfix visible-lg visible-md"></div>' : ''}
            ${(index + 1) % 2 === 0 ? '<div class="clearfix visible-sm visible-xs"></div>' : ''}
        </div>
    `;

            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = productHtml.trim();
            productContainer.appendChild(tempDiv.firstElementChild);
        });

        // Cập nhật giá sau khi render
        updateAllPrices();
    }

    // Hàm search sản phẩm
    function searchProducts(keyword) {
        console.log(`Searching products with keyword:`, keyword);

        fetch(`/store/search?keyword=${encodeURIComponent(keyword)}`)
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                if (data.error) {
                    throw new Error(data.error);
                }
                renderProducts(data.products || []);
            })
            .catch(error => {
                console.error(`[Search error:`, error);
                productContainer.innerHTML = `
            <div class="col-md-12 text-center">
                <h3>Có lỗi xảy ra khi tìm kiếm sản phẩm</h3>
            </div>`;
            });
    }

    // Xử lý sự kiện click search button
    if (searchButton) {
        searchButton.addEventListener('click', function (e) {
            e.preventDefault();
            const keyword = searchInput.value.trim();
            if (keyword) {
                searchProducts(keyword);
            }
        });
    }

    // Xử lý sự kiện enter trong input
    if (searchInput) {
        searchInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                const keyword = this.value.trim();
                if (keyword) {
                    searchProducts(keyword);
                }
            }
        });
    }

    // Kiểm tra và xử lý dữ liệu tìm kiếm từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const searchParam = urlParams.get('search');

    if (searchParam) {
        try {
            // Giải mã và parse JSON từ URL
            const searchData = JSON.parse(decodeURIComponent(searchParam));

            if (searchData.keyword) {
                // Set giá trị vào input
                searchInput.value = searchData.keyword;
                // Thực hiện tìm kiếm
                searchProducts(searchData.keyword);
            }
        } catch (error) {
            console.error('Error parsing search data:', error);
        }
    }
});