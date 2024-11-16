package com.librarymanagement.libraryapp.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;

    private String address;

    private String country;

    private String city;

    private String postalCode;



}