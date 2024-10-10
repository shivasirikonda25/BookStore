package com.example.bookStoreServicesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String houseNo;
    String street;
    String city;
    String state;
    String postalCode;

    @ManyToOne
    @JsonIgnore
   private User user;

}
