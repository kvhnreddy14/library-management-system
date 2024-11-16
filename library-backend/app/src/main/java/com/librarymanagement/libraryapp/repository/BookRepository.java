package com.librarymanagement.libraryapp.repository;

import com.librarymanagement.libraryapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // we have inherited all CRUD operations.


    //this method's implementation automatically is created by the jparepository..just by using the name.
    //but this cant be used for complex methods
    List<Book> getBookByTitleContainsIgnoreCase(String title);

    //for complex methods we have t write methods manually in SERVCE.

}
