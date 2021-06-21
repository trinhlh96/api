package com.example.security.server;

import com.example.security.entity.Account;
import com.example.security.entity.Product;
import com.example.security.repository.AccountRepository;
import com.example.security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServer {
    @Autowired
    private ProductRepository productRepository;
    public Product save(String name, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return productRepository.save(product);
    }
}
