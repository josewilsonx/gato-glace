// Mobile menu toggle
document.addEventListener('DOMContentLoaded', function() {
    const mobileMenuBtn = document.getElementById('mobile-menu-btn');
    const mobileMenu = document.getElementById('mobile-menu');
    
    if (mobileMenuBtn && mobileMenu) {
        mobileMenuBtn.addEventListener('click', function() {
            mobileMenu.classList.toggle('hidden');
        });
    }

    // Smooth scrolling for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
                
                // Close mobile menu if open
                if (mobileMenu && !mobileMenu.classList.contains('hidden')) {
                    mobileMenu.classList.add('hidden');
                }
            }
        });
    });

    // Add animation to menu items on scroll
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('swirl-in');
            }
        });
    }, { threshold: 0.1 });

    document.querySelectorAll('.menu-item, .gallery-item').forEach(item => {
        observer.observe(item);
    });

    // Form submission
    const contactForm = document.querySelector('form');
    if (contactForm) {
        contactForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Basic form validation
            const nameInput = this.querySelector('input[type="text"]');
            const emailInput = this.querySelector('input[type="email"]');
            const messageInput = this.querySelector('textarea');
            
            let isValid = true;
            
            // Validate name
            if (!nameInput.value.trim()) {
                showError(nameInput, 'Por favor, digite seu nome');
                isValid = false;
            } else {
                clearError(nameInput);
            }
            
            // Validate email
            if (!emailInput.value.trim() || !isValidEmail(emailInput.value)) {
                showError(emailInput, 'Por favor, digite um email válido');
                isValid = false;
            } else {
                clearError(emailInput);
            }
            
            // Validate message
            if (!messageInput.value.trim()) {
                showError(messageInput, 'Por favor, digite sua mensagem');
                isValid = false;
            } else {
                clearError(messageInput);
            }
            
            if (isValid) {
                // Simulate form submission
                this.classList.add('loading');
                
                setTimeout(() => {
                    alert('Mensagem enviada com sucesso! Entraremos em contato em breve.');
                    this.reset();
                    this.classList.remove('loading');
                }, 1000);
            }
        });
    }

    // Add to cart functionality
    document.querySelectorAll('.menu-item button').forEach(button => {
        button.addEventListener('click', function() {
            const product = this.closest('.menu-item');
            const productName = product.querySelector('h3').textContent;
            const productPrice = product.querySelector('span').textContent;
            
            addToCart(productName, productPrice);
        });
    });

    // Image error handling
    document.querySelectorAll('img').forEach(img => {
        img.addEventListener('error', function() {
            this.style.display = 'none';
            const parent = this.parentElement;
            if (parent) {
                parent.classList.add('bg-gray-100', 'flex', 'items-center', 'justify-center');
                parent.innerHTML = '<span class="text-gray-400">Imagem não disponível</span>';
            }
        });
    });

    // Lazy loading for images
    if ('IntersectionObserver' in window) {
        const lazyImageObserver = new IntersectionObserver((entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const img = entry.target;
                    img.src = img.dataset.src;
                    img.removeAttribute('data-src');
                    lazyImageObserver.unobserve(img);
                }
            });
        });

        document.querySelectorAll('img[data-src]').forEach(img => {
            lazyImageObserver.observe(img);
        });
    }
});

// Helper functions
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function showError(input, message) {
    clearError(input);
    const errorDiv = document.createElement('div');
    errorDiv.className = 'text-red-500 text-sm mt-1';
    errorDiv.textContent = message;
    input.parentNode.appendChild(errorDiv);
    input.classList.add('border-red-500');
}

function clearError(input) {
    input.classList.remove('border-red-500');
    const errorDiv = input.parentNode.querySelector('.text-red-500');
    if (errorDiv) {
        errorDiv.remove();
    }
}

function addToCart(name, price) {
    // Simulate adding to cart
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    cart.push({ name, price, quantity: 1 });
    localStorage.setItem('cart', JSON.stringify(cart));
    
    // Show notification
    showNotification(`${name} adicionado ao carrinho!`);
}

function showNotification(message) {
    const notification = document.createElement('div');
    notification.className = 'fixed top-20 right-4 bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg transform transition-all duration-300 z-50';
    notification.textContent = message;
    notification.style.transform = 'translateX(100%)';
    
    document.body.appendChild(notification);
    
    // Animate in
    setTimeout(() => {
        notification.style.transform = 'translateX(0)';
    }, 100);
    
    // Animate out and remove
    setTimeout(() => {
        notification.style.transform = 'translateX(100%)';
        setTimeout(() => {
            notification.remove();
        }, 300);
    }, 3000);
}

// Cart functionality
class Cart {
    constructor() {
        this.items = JSON.parse(localStorage.getItem('cart') || '[]');
    }
    
    addItem(product) {
        const existingItem = this.items.find(item => item.name === product.name);
        if (existingItem) {
            existingItem.quantity++;
        } else {
            this.items.push({ ...product, quantity: 1 });
        }
        this.save();
    }
    
    removeItem(name) {
        this.items = this.items.filter(item => item.name !== name);
        this.save();
    }
    
    updateQuantity(name, quantity) {
        const item = this.items.find(item => item.name === name);
        if (item) {
            item.quantity = quantity;
            if (item.quantity <= 0) {
                this.removeItem(name);
            } else {
                this.save();
            }
        }
    }
    
    getTotal() {
        return this.items.reduce((total, item) => {
            const price = parseFloat(item.price.replace('R$ ', '').replace(',', '.'));
            return total + (price * item.quantity);
        }, 0);
    }
    
    save() {
        localStorage.setItem('cart', JSON.stringify(this.items));
    }
    
    clear() {
        this.items = [];
        this.save();
    }
}

// Initialize cart
const cart = new Cart();

// Export for global access
window.cart = cart;
