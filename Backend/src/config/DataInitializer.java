package com.shop.config;

import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initialize(ProductRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                Product p1 = new Product();
                p1.setName("Laptop");
                p1.setDescription("Dell Laptop");
                p1.setPrice(55000);
                p1.setStock(20);

                Product p2 = new Product();
                p2.setName("Mobile");
                p2.setDescription("Samsung Mobile");
                p2.setPrice(25000);
                p2.setStock(30);

                Product p3 = new Product();
                p3.setName("Headphones");
                p3.setDescription("Wireless Headphones");
                p3.setPrice(2000);
                p3.setStock(50);

                repository.save(p1);
                repository.save(p2);
                repository.save(p3);
            }
        };
    }
}