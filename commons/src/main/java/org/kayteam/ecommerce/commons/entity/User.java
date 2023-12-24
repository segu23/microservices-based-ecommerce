package org.kayteam.ecommerce.commons.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    @NotNull(message = "El email es requerido.")
    @NotBlank(message = "El email es requerido.")
    private String email;

    @Column(length = 60)
    public String password;

    @NotNull(message = "El nombre es requerido.")
    @NotBlank(message = "El nombre es requerido.")
    public String firstName;

    @NotNull(message = "El apellido es requerido.")
    @NotBlank(message = "El apellido es requerido.")
    public String lastName;

    public String enabled;
}
