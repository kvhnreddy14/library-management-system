package com.librarymanagement.libraryapp.mapper;

import com.librarymanagement.libraryapp.dto.BookDTO;
import com.librarymanagement.libraryapp.entity.Book;

public class BookMapper {

    //map book to dto
    public static BookDTO maptoBookDTO(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setPublishedYear(book.getPublishedYear());
        bookDTO.setNoOfAvailableCopies(book.getNoOfAvailableCopies());
        bookDTO.setBookBarcode(book.getBookBarcode());
        return bookDTO;
    }
    //map dto to book
    public static Book maptoBookEntity(BookDTO bookDTO){
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublisher(bookDTO.getPublisher());
        book.setPublishedYear(bookDTO.getPublishedYear());
        book.setNoOfAvailableCopies(bookDTO.getNoOfAvailableCopies());
        book.setBookBarcode(bookDTO.getBookBarcode());
        return book;

    }

}