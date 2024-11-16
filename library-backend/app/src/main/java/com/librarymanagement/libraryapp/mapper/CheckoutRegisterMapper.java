package com.librarymanagement.libraryapp.mapper;

import com.librarymanagement.libraryapp.dto.CheckoutRegisterDTO;
import com.librarymanagement.libraryapp.entity.Book;
import com.librarymanagement.libraryapp.entity.CheckoutRegister;
import com.librarymanagement.libraryapp.entity.Member;
import com.librarymanagement.libraryapp.repository.BookRepository;
import com.librarymanagement.libraryapp.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@AllArgsConstructor
//component is for using the repositories like memberRepository etc
@Component
public class CheckoutRegisterMapper {

    private MemberRepository memberRepository;
    private BookRepository bookRepository;



    public CheckoutRegisterDTO maptoRegisterDTO(CheckoutRegister checkoutRegister){
        CheckoutRegisterDTO dto = new CheckoutRegisterDTO();
        dto.setId(checkoutRegister.getId());
        dto.setBookId(checkoutRegister.getBook().getId());
        dto.setMemberId(checkoutRegister.getMember().getId());


        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        dto.setCheckoutDate(checkoutRegister.getCheckoutDate().format(formatter));
        dto.setDueDate(checkoutRegister.getDueDate().format(formatter));
        //because this is not mandatory in the entity
        if (checkoutRegister.getReturnDate() != null)
            dto.setReturnDate(checkoutRegister.getReturnDate().format(formatter));

        dto.setOverdueFine(checkoutRegister.getOverdueFine());
        return dto;
    }



    //here the method is not static because --- the method cant be static if it contains non static methods or objects in it like(memberRepository etc)
    //if you make the memberRepository static then the dependency injection wont work!
    public CheckoutRegister maptoRegisterEntity(CheckoutRegisterDTO dto){
        CheckoutRegister checkoutRegister = new CheckoutRegister();
        checkoutRegister.setId(dto.getId());

        Member member = memberRepository.findById(dto.getMemberId()).get();
        checkoutRegister.setMember(member);
        Book book = bookRepository.findById(dto.getBookId()).get();
        checkoutRegister.setBook(book);

        checkoutRegister.setCheckoutDate(LocalDate.parse(dto.getCheckoutDate()));
        if (dto.getReturnDate() != null)
            checkoutRegister.setReturnDate(LocalDate.parse(dto.getReturnDate()));
        if (dto.getReturnDate() != null)
            checkoutRegister.setDueDate(LocalDate.parse(dto.getDueDate()));
        checkoutRegister.setOverdueFine(dto.getOverdueFine());

        return checkoutRegister;
    }



}
