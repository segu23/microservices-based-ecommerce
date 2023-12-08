package org.kayteam.ecommerce.productservice.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String uuid;

    @NotNull
    public String slug;
    @Nullable
    public String sku;
    @NotNull
    public String name;
    public String shortDescription;
    public String description;
    @NotNull
    private Double price;
    private Double oldPrice;
}
