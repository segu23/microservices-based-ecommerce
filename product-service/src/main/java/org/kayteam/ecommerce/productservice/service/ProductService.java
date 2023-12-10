package org.kayteam.ecommerce.productservice.service;

import org.kayteam.ecommerce.productservice.entity.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

    Page<Product> listProducts(int page, int amount);

    Optional<Product> getProductByName(String name);

    Page<Product> searchProduct(String name, int page, int amount);

    Product saveProduct(Product product);
}
