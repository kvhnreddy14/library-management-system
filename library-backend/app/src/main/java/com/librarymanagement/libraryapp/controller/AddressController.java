package com.librarymanagement.libraryapp.controller;


import com.librarymanagement.libraryapp.dto.AddressDTO;
import com.librarymanagement.libraryapp.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/address")
@AllArgsConstructor
public class AddressController {

    private AddressService addressService;

    @PostMapping ("addAddress")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO){
        addressDTO = addressService.addAddress(addressDTO);
        return new ResponseEntity<>(addressDTO, HttpStatus.CREATED);
    }

    @GetMapping("getAllAddresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        List<AddressDTO> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id){
        AddressDTO addressDTO = addressService.getAddressById(id);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @PatchMapping("updateAddress/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO){
        addressDTO.setId(id);
        addressDTO = addressService.updateAddress(addressDTO);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @DeleteMapping("deleteAddress/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return new ResponseEntity<>("Address Successfully Deleted", HttpStatus.OK);
    }








}
