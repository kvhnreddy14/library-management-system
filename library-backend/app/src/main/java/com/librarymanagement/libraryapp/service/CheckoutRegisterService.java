package com.librarymanagement.libraryapp.service;

import com.librarymanagement.libraryapp.dto.CheckoutRegisterDTO;

import java.util.List;

public interface CheckoutRegisterService {
    CheckoutRegisterDTO createRegister (CheckoutRegisterDTO checkoutRegisterDTO);

    List<CheckoutRegisterDTO> getAllRegisters();

    CheckoutRegisterDTO getRegisterById(Long id);

    CheckoutRegisterDTO updateRegister(CheckoutRegisterDTO registerDTO);

    void deteleRegister(Long id);

    List<CheckoutRegisterDTO> searchRegisterByBookId(Long bookId);

    List<CheckoutRegisterDTO> searchRegisterByMemberId(Long memberId);

}
