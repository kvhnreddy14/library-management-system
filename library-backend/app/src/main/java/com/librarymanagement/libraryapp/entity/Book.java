package com.librarymanagement.libraryapp.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="books")

@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@EqualsAndHashCode     //all these things can be replaced with @data

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String isbn;

    @Column
    private String publisher;

    @Column(nullable = false)
    private Integer publishedYear;

    @Column(nullable = false)
    private Integer noOfAvailableCopies;

    @Column
    private String bookBarcode;





}
