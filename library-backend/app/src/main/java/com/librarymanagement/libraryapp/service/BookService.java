package com.librarymanagement.libraryapp.service;


import com.librarymanagement.libraryapp.dto.BookDTO;

import java.util.List;

public interface BookService  {

    BookDTO addBook(BookDTO bookDTO);

    List<BookDTO> getAllBooks();

    BookDTO getBookById(Long bookId);

    BookDTO updateBook(BookDTO bookDTO);

    void deleteBook(Long BookId);

    List<BookDTO> getBookByTitle(String Title);

    List<BookDTO> findBooksByCriteria(String title, String author, String isbn, String bookBarcode);

}
