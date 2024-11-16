package com.librarymanagement.libraryapp.service.impl;

import com.librarymanagement.libraryapp.dto.AddressDTO;
import com.librarymanagement.libraryapp.entity.PostalAddress;
import com.librarymanagement.libraryapp.exception.ResourceNotFoundException;
import com.librarymanagement.libraryapp.mapper.AddressMapper;
import com.librarymanagement.libraryapp.repository.AddressRepository;
import com.librarymanagement.libraryapp.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;


    @Override
    public AddressDTO addAddress(AddressDTO addressDTO) {
        PostalAddress postalAddress = AddressMapper.maptoAddressEntity(addressDTO);
        postalAddress = addressRepository.save(postalAddress);
        return AddressMapper.maptoAddressDTO(postalAddress);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<PostalAddress> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(AddressMapper::maptoAddressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getAddressById(Long id) {
        Optional<PostalAddress> addressopt = addressRepository.findById(id);
        PostalAddress postalAddress = addressopt.orElseThrow(
                () -> new ResourceNotFoundException("Address","ID",id));
        return AddressMapper.maptoAddressDTO(postalAddress);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        Optional<PostalAddress> addressOptional = addressRepository.findById(addressDTO.getId());
        PostalAddress postalAddress = addressOptional.orElseThrow(
                () -> new ResourceNotFoundException("Address","ID",addressDTO.getId()));
        updateAllfields(addressDTO, postalAddress);
        return AddressMapper.maptoAddressDTO(postalAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        if(!addressRepository.existsById(id)){
            throw new ResourceNotFoundException("Address", "ID", id);
        }
        addressRepository.deleteById(id);
    }

    public void updateAllfields(AddressDTO addressDTO, PostalAddress postalAddress) {
        if (addressDTO.getAddress()!=null){
            postalAddress.setAddress(addressDTO.getAddress());
        }
        if (addressDTO.getCity()!= null){
            postalAddress.setCity(addressDTO.getCity());
        }
        if (addressDTO.getCountry()!= null){
            postalAddress.setCountry(addressDTO.getCountry());
        }
        if (addressDTO.getPostalCode()!= null){
            postalAddress.setPostalCode(addressDTO.getPostalCode());
        }
    }

}
