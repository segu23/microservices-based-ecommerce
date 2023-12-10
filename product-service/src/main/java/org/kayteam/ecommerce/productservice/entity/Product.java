package org.kayteam.ecommerce.productservice.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long uuid;

    @Nullable
    @Column(unique = true)
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

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updateAt;

    private String userUUID;

    @PreUpdate
    @PrePersist
    private void updateSlugFromName() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.name != null) {
            stringBuilder.append(UUID.randomUUID().toString().split("-")[0]);
            stringBuilder.append("-");
            stringBuilder.append(this.name.toLowerCase().replace(" ", "-"));
            this.slug = stringBuilder.toString();
        }
    }
}
