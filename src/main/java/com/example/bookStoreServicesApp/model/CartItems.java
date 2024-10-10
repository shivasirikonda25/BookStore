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
public class CartItems
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long bookId;

    private double price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart")
    @JsonIgnore
    private Cart cart;

    public CartItems(Long bookId,double price,int quantity){
        this.bookId=bookId;
        this.price=price;
        this.quantity=quantity;
    }

}
