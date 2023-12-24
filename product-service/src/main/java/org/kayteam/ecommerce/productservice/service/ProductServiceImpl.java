package org.kayteam.ecommerce.productservice.service;

import org.kayteam.ecommerce.commons.entity.Product;
import org.kayteam.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> getProductBySlug(String slug) {
        return productRepository.findByHistoricSlugsContaining(slug);
    }

    @Override
    public Page<Product> searchProduct(String query, int page, int amount) {
        return productRepository.findByNameContainingOrDescriptionContainingOrShortDescriptionContaining(query, query, query, PageRequest.of(page, amount));
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Boolean isProduct(Product product) {
        return productRepository.existsById(product.getId());
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
