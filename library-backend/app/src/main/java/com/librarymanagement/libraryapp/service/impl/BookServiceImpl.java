package com.librarymanagement.libraryapp.service.impl;

import com.librarymanagement.libraryapp.dto.BookDTO;
import com.librarymanagement.libraryapp.entity.Book;
import com.librarymanagement.libraryapp.exception.ResourceNotFoundException;
import com.librarymanagement.libraryapp.mapper.BookMapper;
import com.librarymanagement.libraryapp.repository.BookRepository;
import com.librarymanagement.libraryapp.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {


    private BookRepository bookRepository;
    private EntityManager entityManager;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = BookMapper.maptoBookEntity(bookDTO);
        book = bookRepository.save(book);
        return BookMapper.maptoBookDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> allbooks = bookRepository.findAll();
        return allbooks.stream()
                .map(BookMapper::maptoBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () ->new ResourceNotFoundException("Book","ID",bookId));
        return BookMapper.maptoBookDTO(book);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        Optional<Book> optbook = bookRepository.findById(bookDTO.getId());
        Book book = optbook.orElseThrow(
                () ->new ResourceNotFoundException("Book","ID", bookDTO.getId()));
        updateChanges(bookDTO , book);
        book = bookRepository.save(book);
        return BookMapper.maptoBookDTO(book);
    }



    @Override
    public void deleteBook(Long bookId) {
        if(!bookRepository.existsById(bookId)){
            throw new ResourceNotFoundException("Book", "ID" , bookId);
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookDTO> getBookByTitle(String Title) {
        List<Book> books = bookRepository.getBookByTitleContainsIgnoreCase(Title);
        return books.stream()
                .map(BookMapper::maptoBookDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<BookDTO> findBooksByCriteria(String title, String author, String isbn, String bookBarcode){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        if(title != null && !title.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("title")),"%"+ title.toLowerCase() +"%"));
        }
        if(author != null && !author.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("author")),"%"+ author.toLowerCase() +"%"));
        }
        if(isbn != null && !isbn.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("isbn")),"%"+ isbn.toLowerCase() +"%"));
        }
        if(bookBarcode != null && !bookBarcode.isEmpty()){
            predicates.add(cb.equal(cb.lower(book.get("bookBarcode")),"%"+ bookBarcode.toLowerCase() +"%"));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Book> books = entityManager.createQuery(cq).getResultList();
        return books.stream()
                .map(BookMapper::maptoBookDTO)
                .collect(Collectors.toList());
    }










    private void updateChanges(BookDTO bookDTO, Book book) {
        if (bookDTO.getTitle() !=null){
            book.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getAuthor() !=null){
            book.setAuthor(bookDTO.getAuthor());
        }
        if (bookDTO.getIsbn() !=null){
            book.setIsbn(bookDTO.getIsbn());
        }
        if (bookDTO.getPublisher() !=null){
            book.setPublisher(bookDTO.getPublisher());
        }
        if (bookDTO.getPublishedYear() !=null){
            book.setPublishedYear(bookDTO.getPublishedYear());
        }
        if (bookDTO.getBookBarcode() !=null){
            book.setBookBarcode(bookDTO.getBookBarcode());
        }
        if (bookDTO.getNoOfAvailableCopies() !=null){
            book.setNoOfAvailableCopies(bookDTO.getNoOfAvailableCopies());
        }
    }

}
