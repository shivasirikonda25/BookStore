package com.example.bookStoreServicesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Size(min = 7, message = "Password cannot be empty")
    @NotBlank(message = "Password cannot be empty")
//    @JsonIgnore
    private String password;

//    @JsonIgnore
    @NotBlank(message = "Define the role ")
    private String role;

    @OneToOne(mappedBy = "users")
//    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Address> addressList=new ArrayList<>();


    @OneToOne(mappedBy = "users")
    private Wishlist wishlist;

}
