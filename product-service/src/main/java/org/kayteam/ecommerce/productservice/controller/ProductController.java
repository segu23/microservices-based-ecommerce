package org.kayteam.ecommerce.productservice.controller;

import org.kayteam.ecommerce.productservice.entity.Product;
import org.kayteam.ecommerce.productservice.util.ErrorMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductController {


    public ResponseEntity<Product> createProduct(@RequestBody Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageUtil.formatMessage(bindingResult));
        }


    }
}
