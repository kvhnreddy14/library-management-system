package com.librarymanagement.libraryapp.repository;

import com.librarymanagement.libraryapp.entity.CheckoutRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRegisterRepository extends JpaRepository<CheckoutRegister, Long> {

    //by naming convention this will perform search for member_id
    List<CheckoutRegister> findByMemberId(Long memberId);

    List<CheckoutRegister> findByBookId(Long bookId);



}
