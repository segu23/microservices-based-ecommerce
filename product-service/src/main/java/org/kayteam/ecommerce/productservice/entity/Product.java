package org.kayteam.ecommerce.productservice.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(nullable = false)
    private String uuid;

    @NotNull
    @Column(unique = true, nullable = false)
    public String slug;

    @Nullable
    public String sku;

    @NotNull(message = "El nombre del producto es requerido.")
    @NotBlank(message = "El nombre del producto es requerido.")
    @Column(nullable = false)
    public String name;

    @Nullable
    @Size(max = 180, message = "La descripción corta tiene un máximo de 180 caracteres.")
    public String shortDescription;

    public String description;

    @NotNull(message = "El precio del producto es requerido.")
    @Column(nullable = false)
    private Double price;

    @Nullable
    private Double oldPrice;
}
