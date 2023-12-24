package org.kayteam.ecommerce.productservice.controller;

import org.kayteam.ecommerce.commons.entity.Product;
import org.kayteam.ecommerce.commons.util.ErrorMessageUtil;
import org.kayteam.ecommerce.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageUtil.formatMessage(bindingResult));
        }

        if(productService.isProduct(product)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProduct(@RequestParam("query") String query, @RequestParam("page") Integer page, @RequestParam(required = false, defaultValue = "20", name = "amount") Integer amount) {
        return ResponseEntity.ok(productService.searchProduct(query, page, amount));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Optional<Product>> getProductBySlug(@PathVariable("slug") String productSlug) {
        return ResponseEntity.ok(productService.getProductBySlug(productSlug));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageUtil.formatMessage(bindingResult));
        }

        if(productService.isProduct(Product.builder().id(id).build())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Product existingProduct = productService.getProductById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessageUtil.formatMessage(bindingResult)));
        existingProduct.setSku(product.getSku());
        existingProduct.setName(product.getName());
        existingProduct.setShortDescription(product.getShortDescription());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setOldPrice(product.getOldPrice());

        return ResponseEntity.ok(productService.saveProduct(existingProduct));
    }
}
