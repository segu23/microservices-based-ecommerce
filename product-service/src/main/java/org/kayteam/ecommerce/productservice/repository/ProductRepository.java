package org.kayteam.ecommerce.productservice.repository;

import org.kayteam.ecommerce.commons.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingOrDescriptionContainingOrShortDescriptionContaining(String name, String description, String shortDescription, PageRequest pageRequest);

    Optional<Product> findBySlug(String slug);

    Optional<Product> findByHistoricSlugsContaining(String slug);
}
