package org.kayteam.ecommerce.productservice.service;

import org.kayteam.ecommerce.commons.entity.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

    Optional<Product> getProductBySlug(String slug);

    Page<Product> searchProduct(String name, int page, int amount);

    Product saveProduct(Product product);

    Boolean isProduct(Product product);

    Optional<Product> getProductById(Long id);
}
