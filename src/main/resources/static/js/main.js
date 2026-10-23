(function($) {
	"use strict"

	// Mobile Nav toggle
	$('.menu-toggle > a').on('click', function (e) {
		e.preventDefault();
		$('#responsive-nav').toggleClass('active');
	})

	// Fix cart dropdown from closing
	$('.cart-dropdown').on('click', function (e) {
		e.stopPropagation();
	});

	/////////////////////////////////////////

	// Products Slick
	$('.products-slick').each(function() {
		var $this = $(this),
				$nav = $this.attr('data-nav');

		$this.slick({
			slidesToShow: 4,
			slidesToScroll: 1,
			autoplay: true,
			infinite: true,
			speed: 300,
			dots: false,
			arrows: true,
			appendArrows: $nav ? $nav : false,
			responsive: [{
	        breakpoint: 991,
	        settings: {
	          slidesToShow: 2,
	          slidesToScroll: 1,
	        }
	      },
	      {
	        breakpoint: 480,
	        settings: {
	          slidesToShow: 1,
	          slidesToScroll: 1,
	        }
	      },
	    ]
		});
	});

	// Products Widget Slick
	$('.products-widget-slick').each(function() {
		var $this = $(this),
				$nav = $this.attr('data-nav');

		$this.slick({
			infinite: true,
			autoplay: true,
			speed: 300,
			dots: false,
			arrows: true,
			appendArrows: $nav ? $nav : false,
		});
	});

	/////////////////////////////////////////

	// Product Main img Slick
	$('#product-main-img').slick({
    infinite: true,
    speed: 300,
    dots: false,
    arrows: true,
    fade: true,
    asNavFor: '#product-imgs',
  });

	// Product imgs Slick
  $('#product-imgs').slick({
    slidesToShow: 3,
    slidesToScroll: 1,
    arrows: true,
    centerMode: true,
    focusOnSelect: true,
		centerPadding: 0,
		vertical: true,
    asNavFor: '#product-main-img',
		responsive: [{
        breakpoint: 991,
        settings: {
					vertical: false,
					arrows: false,
					dots: true,
        }
      },
    ]
  });

	// Product img zoom
	var zoomMainProduct = document.getElementById('product-main-img');
	if (zoomMainProduct) {
		$('#product-main-img .product-preview').zoom();
	}

	/////////////////////////////////////////

	// Định nghĩa các hàm format tiền tệ trước
        function formatMoney(value) {
            return Math.round(value)
                .toString()
                .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " đ";
        }

        function parseMoneyToNumber(value) {
            if (typeof value !== 'string') return 0;
            return parseInt(value.replace(/[^\d]/g, "")) || 0;
        }

        // Input number
        $('.input-number').each(function() {
            var $this = $(this),
            $input = $this.find('input[type="tel"]'),
            up = $this.find('.qty-up'),
            down = $this.find('.qty-down');

            // Format giá trị ban đầu
            if ($input.val()) {
                $input.val(formatMoney($input.val()));
            }

            down.on('click', function () {
                var currentValue = parseMoneyToNumber($input.val());
                var newValue = currentValue - 100000;
                newValue = Math.max(300000, newValue);
                $input.val(formatMoney(newValue));
                updatePriceSlider($this, newValue);
            });

            up.on('click', function () {
                var currentValue = parseMoneyToNumber($input.val());
                var newValue = currentValue + 100000;
                newValue = Math.min(45000000, newValue);
                $input.val(formatMoney(newValue));
                updatePriceSlider($this, newValue);
            });

            // Xử lý khi nhập trực tiếp vào input
            $input.on('input', function() {
                var value = parseMoneyToNumber(this.value);
                if (value) {
                    value = Math.max(300000, Math.min(45000000, value));
                    this.value = formatMoney(value);
                }
            });
        });

        var priceInputMax = document.getElementById('price-max'),
            priceInputMin = document.getElementById('price-min');

        priceInputMax.addEventListener('change', function(){
            var value = parseMoneyToNumber(this.value);
            value = Math.max(300000, Math.min(45000000, value));
            this.value = formatMoney(value);
            updatePriceSlider($(this).parent(), value);
        });

        priceInputMin.addEventListener('change', function(){
            var value = parseMoneyToNumber(this.value);
            value = Math.max(300000, Math.min(45000000, value));
            this.value = formatMoney(value);
            updatePriceSlider($(this).parent(), value);
        });

        function updatePriceSlider(elem, value) {
            if (elem.hasClass('price-min')) {
                console.log('min:', value);
                priceSlider.noUiSlider.set([value, null]);
            } else if (elem.hasClass('price-max')) {
                console.log('max:', value);
                priceSlider.noUiSlider.set([null, value]);
            }
        }

        // Price Slider
        var priceSlider = document.getElementById('price-slider');
        if (priceSlider) {
            noUiSlider.create(priceSlider, {
                start: [300000, 45000000],
                connect: true,
                step: 100000,
                format: {
                    to: function(value) {
                        return formatMoney(value);
                    },
                    from: function(value) {
                        return parseMoneyToNumber(value);
                    }
                },
                range: {
                    'min': 300000,
                    'max': 45000000
                }
            });

            priceSlider.noUiSlider.on('update', function(values, handle) {
                var value = values[handle];
                if (handle) {
                    priceInputMax.value = value;
                } else {
                    priceInputMin.value = value;
                }
            });
        }

})(jQuery);