package com.librarymanagement.libraryapp.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "postalAddresses")
@Data
public class PostalAddress {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    private String country;

    @Column(nullable = false)
    private String postalCode;



}
