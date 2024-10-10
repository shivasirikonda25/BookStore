package com.example.bookStoreServicesApp.model;

import com.example.bookStoreServicesApp.enumator.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private Date order_date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "cart_id")
//    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<Book> books;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonIgnore
    private User user;
}