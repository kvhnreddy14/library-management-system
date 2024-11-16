package com.librarymanagement.libraryapp.mapper;

import com.librarymanagement.libraryapp.dto.AddressDTO;
import com.librarymanagement.libraryapp.entity.PostalAddress;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.context.support.ApplicationObjectSupport;

public class AddressMapper {

    //map entity to dto
    public static AddressDTO maptoAddressDTO(PostalAddress postalAddress){
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(postalAddress.getId());
        addressDTO.setAddress(postalAddress.getAddress());
        addressDTO.setCity(postalAddress.getCity());
        addressDTO.setCountry(postalAddress.getCountry());
        addressDTO.setPostalCode(postalAddress.getPostalCode());
        return addressDTO;
    }

    public static PostalAddress maptoAddressEntity (AddressDTO dto){
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setId(dto.getId());
        postalAddress.setAddress(dto.getAddress());
        postalAddress.setCity(dto.getCity());
        postalAddress.setCountry(dto.getCountry());
        postalAddress.setPostalCode(dto.getPostalCode());
        return postalAddress;
    }


    //map dto to entity
}
