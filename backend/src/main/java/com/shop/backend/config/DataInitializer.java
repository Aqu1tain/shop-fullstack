package com.shop.backend.config;

import com.shop.backend.entity.Category;
import com.shop.backend.entity.Product;
import com.shop.backend.entity.Role;
import com.shop.backend.entity.User;
import com.shop.backend.repository.CategoryRepository;
import com.shop.backend.repository.ProductRepository;
import com.shop.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@shop.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
        }

        if (categoryRepository.count() == 0) {
            Category electronics = categoryRepository.save(Category.builder().name("Electronics").build());
            Category clothing = categoryRepository.save(Category.builder().name("Clothing").build());
            Category books = categoryRepository.save(Category.builder().name("Books").build());

            productRepository.save(Product.builder()
                    .name("Laptop Pro")
                    .description("High-performance laptop for professionals")
                    .price(new BigDecimal("1299.99"))
                    .category(electronics)
                    .stockQuantity(50)
                    .imageUrl("https://picsum.photos/seed/laptop/400/300")
                    .build());

            productRepository.save(Product.builder()
                    .name("Wireless Headphones")
                    .description("Premium noise-cancelling headphones")
                    .price(new BigDecimal("249.99"))
                    .category(electronics)
                    .stockQuantity(100)
                    .imageUrl("https://picsum.photos/seed/headphones/400/300")
                    .build());

            productRepository.save(Product.builder()
                    .name("Cotton T-Shirt")
                    .description("Comfortable everyday t-shirt")
                    .price(new BigDecimal("29.99"))
                    .category(clothing)
                    .stockQuantity(200)
                    .imageUrl("https://picsum.photos/seed/tshirt/400/300")
                    .build());

            productRepository.save(Product.builder()
                    .name("Programming Guide")
                    .description("Complete guide to modern programming")
                    .price(new BigDecimal("49.99"))
                    .category(books)
                    .stockQuantity(75)
                    .imageUrl("https://picsum.photos/seed/book/400/300")
                    .build());
        }
    }
}
