package org.kayteam.ecommerce.productservice.service;

import org.kayteam.ecommerce.productservice.entity.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

    Page<Product> listProducts(int page, int amount);

    Optional<Product> getProductBySlug(String slug);

    Page<Product> searchProduct(String name, int page, int amount);

    Product saveProduct(Product product);

    Boolean isProduct(Product product);

    Product updateProduct(Product product);
}
