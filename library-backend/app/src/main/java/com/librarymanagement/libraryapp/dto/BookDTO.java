package com.librarymanagement.libraryapp.dto;


import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer publishedYear;
    private Integer noOfAvailableCopies;
    private String bookBarcode;

}
