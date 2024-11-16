package com.librarymanagement.libraryapp.controller;



import com.librarymanagement.libraryapp.dto.BookDTO;
import com.librarymanagement.libraryapp.service.BookService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")

@AllArgsConstructor
public class BookController {


    //get Logger form org.slf4j in suggestions
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private BookService bookService;



    @PostMapping("addBook")
    //http://localhost:8080/api/books/addbook
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO){
        logger.info("Adding book...");
        BookDTO savedBookDTO = bookService.addBook(bookDTO);
        //in order logger to work, make sure that there is a "toString()" method in the class you passed(@Data automatically provides this method)
        logger.info("The added book is : {}",savedBookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);

    }

    @GetMapping("allBooks")
    public ResponseEntity<List<BookDTO>>  getAllBooks(){
        List<BookDTO> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long bookId){
        BookDTO bookDTO = bookService.getBookById(bookId);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);

    }

    @PatchMapping("updateBook/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, BookDTO bookDTO){
        bookDTO.setId(id);
        bookDTO = bookService.updateBook(bookDTO);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @DeleteMapping("deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>("Book Successfully Deleted", HttpStatus.OK);
    }

    @GetMapping("searchByTitle")
    //http://localhost:8080/api/books/searchByTitle?title=mahabaratham&author=krishna
    public ResponseEntity<List<BookDTO>> searchByTitle(@RequestParam String title) {
        List<BookDTO> books = bookService.getBookByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("searchBook")
    public ResponseEntity<List<BookDTO>> searchBook(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String author,
                                                    @RequestParam(required = false) String isbn,
                                                    @RequestParam(required = false) String bookBarcode){
        List<BookDTO> books = bookService.findBooksByCriteria(title, author, isbn, bookBarcode);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }





}
