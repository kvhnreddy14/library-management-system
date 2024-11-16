package com.librarymanagement.libraryapp.controller;


import com.librarymanagement.libraryapp.dto.CheckoutRegisterDTO;
import com.librarymanagement.libraryapp.service.CheckoutRegisterService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/registers")
@AllArgsConstructor
public class CheckoutRegisterController {
    private CheckoutRegisterService checkoutRegisterService;

    @PostMapping("createRegister")
    public ResponseEntity<CheckoutRegisterDTO> createRegister(@RequestBody CheckoutRegisterDTO checkoutRegisterDTO){
        checkoutRegisterDTO = checkoutRegisterService.createRegister(checkoutRegisterDTO);
        return new ResponseEntity<>(checkoutRegisterDTO, HttpStatus.CREATED);
    }


    @GetMapping("getAllRegisters")
    public ResponseEntity<List<CheckoutRegisterDTO>> getAllRegisters(){
        List<CheckoutRegisterDTO> registersDTO = checkoutRegisterService.getAllRegisters();
        return new ResponseEntity<>(registersDTO, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CheckoutRegisterDTO> getRegisterById(@PathVariable Long id){
        CheckoutRegisterDTO registerDTO = checkoutRegisterService.getRegisterById(id);
        return new ResponseEntity<>(registerDTO, HttpStatus.OK);
    }

    @PatchMapping("updateRegister/{id}")
    public ResponseEntity<CheckoutRegisterDTO> updateRegister(@PathVariable Long id,@RequestBody CheckoutRegisterDTO registerDTO){
        registerDTO.setId(id);
        CheckoutRegisterDTO updateRegisterDTO = checkoutRegisterService.updateRegister(registerDTO);
        return new ResponseEntity<>(updateRegisterDTO, HttpStatus.OK);
    }

    @DeleteMapping("deleteREgister/{id}")
    public ResponseEntity<String> deleteRegister(@PathVariable Long id){
        checkoutRegisterService.deteleRegister(id);
        return new ResponseEntity<>("Register is succesfully deleted", HttpStatus.OK);
    }

    @GetMapping("member/{memberId}")
    public ResponseEntity<List<CheckoutRegisterDTO>> getRegistersByMemberId(@PathVariable Long memberId){
        List<CheckoutRegisterDTO> registerDTOList  = checkoutRegisterService.searchRegisterByMemberId(memberId);
        return new ResponseEntity<>(registerDTOList, HttpStatus.OK);
    }


    @GetMapping("book/{bookId}")
    public ResponseEntity<List<CheckoutRegisterDTO>> getRegistersByBookId(@PathVariable Long bookId){
        List<CheckoutRegisterDTO> registerDTOList = checkoutRegisterService.searchRegisterByBookId(bookId);
        return new ResponseEntity<>(registerDTOList, HttpStatus.OK);
    }
}

