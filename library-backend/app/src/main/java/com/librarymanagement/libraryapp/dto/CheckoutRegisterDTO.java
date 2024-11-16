package com.librarymanagement.libraryapp.dto;

import com.librarymanagement.libraryapp.entity.Book;
import com.librarymanagement.libraryapp.entity.Member;
import lombok.Data;

@Data
public class CheckoutRegisterDTO {
    private Long id;

    //because in the frontend we only pass bood id and memberid but not the whole book and member.
    private Long memberId;
    private Long bookId;

    private String checkoutDate;
    private String dueDate;
    private String returnDate;
    private Double overdueFine;

}
