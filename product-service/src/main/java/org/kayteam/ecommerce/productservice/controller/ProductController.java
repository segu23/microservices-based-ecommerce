package org.kayteam.ecommerce.productservice.controller;

import org.kayteam.ecommerce.productservice.entity.Product;
import org.kayteam.ecommerce.productservice.service.ProductService;
import org.kayteam.ecommerce.productservice.util.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageUtil.formatMessage(bindingResult));
        }

        if(productService.isProduct(product)){
            return;
        }

        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProduct(@RequestParam("name") String name,@RequestParam("page") Integer page, @RequestParam("amount") Integer amount){
        return ResponseEntity.ok(productService.searchProduct(name, page, amount));
    }

    @GetMapping
    public ResponseEntity<Product> getProduct(@RequestParam("slug") String productSlug){
        productService.
    }
}
