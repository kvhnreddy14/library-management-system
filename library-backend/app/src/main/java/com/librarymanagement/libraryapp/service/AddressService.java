package com.librarymanagement.libraryapp.service;

import com.librarymanagement.libraryapp.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO addAddress(AddressDTO addressDTO);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long id);

    AddressDTO updateAddress(AddressDTO addressDTO);

    void deleteAddress(Long id);

}
