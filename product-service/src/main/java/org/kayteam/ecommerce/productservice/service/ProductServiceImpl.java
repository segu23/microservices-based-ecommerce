package org.kayteam.ecommerce.productservice.service;

import org.kayteam.ecommerce.productservice.entity.Product;
import org.kayteam.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> listProducts(int page, int amount) {
        return productRepository.findAll(PageRequest.of(page, amount));
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return Optional.empty();
    }

    @Override
    public Page<Product> searchProduct(String name, int page, int amount) {
        return null;
    }

    @Override
    public Product saveProduct(Product product) {
        return null;
    }
}
