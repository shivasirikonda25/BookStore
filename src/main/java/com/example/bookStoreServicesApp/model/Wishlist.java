package com.example.bookStoreServicesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlist_id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="users",unique=true)
    private User users;


    @OneToMany(mappedBy = "wishlist",cascade = CascadeType.ALL)
    private List<WishlistItems> wishlistItems;

    public Wishlist(User users){
        this.users=users;
    }

}


