package org.kayteam.ecommerce.commons.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

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

    @Nullable
    private Integer actualDiscount;

    private Integer stock;

    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private Date updateAt;

    private String productOwnerUUID;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    private Set<String> historicSlugs = new HashSet<>();

    @PreUpdate
    @PrePersist
    private void updateSlugFromName() {
        if (this.name != null) {
            Random random = new Random();
            String productNameWithoutSpaces = this.name.toLowerCase().replace(" ", "-");
            this.slug = getHistoricSlugs().stream()
                    .filter(historicSlug -> historicSlug.replaceFirst("MBE-\\d+-", "")
                            .equalsIgnoreCase(productNameWithoutSpaces))
                    .findFirst()
                    .orElse(String.format("MBE-%s-%s", random.nextInt(10000000), productNameWithoutSpaces));
            getHistoricSlugs().add(this.slug);
        }
        if (this.price != null && this.oldPrice != null) {
            actualDiscount = Math.toIntExact(Math.round(Math.floor(100 - (price * 100) / oldPrice)));
        } else {
            this.actualDiscount = null;
        }
    }
}
