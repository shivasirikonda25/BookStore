package com.example.bookStoreServicesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long bookId;

    @ManyToOne
    @JoinColumn(name = "wishlist")
    @JsonIgnore
    private Wishlist wishlist;

    public WishlistItems(Long bookId){
        this.bookId=bookId;
    }
}
