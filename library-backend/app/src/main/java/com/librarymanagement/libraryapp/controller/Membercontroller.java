package com.librarymanagement.libraryapp.controller;

import com.librarymanagement.libraryapp.dto.MemberDTO;
import com.librarymanagement.libraryapp.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/members")
@AllArgsConstructor
public class Membercontroller{
    private MemberService memberService;

    @PostMapping("addMember")
    //http://localhost:8080/api/members/addMember
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO memberDTO){
        MemberDTO savedMember = memberService.addMember(memberDTO);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }


    @GetMapping("getAllMembers")
    public ResponseEntity<List<MemberDTO>> getAllMembers(){
        List<MemberDTO> memberDTOList = memberService.getAllMembers();
        return new ResponseEntity<>(memberDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id){
        MemberDTO memberDTO = memberService.getMemberById(id);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PatchMapping("updateMember/{id}")
    public ResponseEntity<MemberDTO> updateMemberById(@PathVariable Long id,@RequestBody MemberDTO memberDTO){
        memberDTO.setId(id);
        memberDTO = memberService.updateMember(memberDTO);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @DeleteMapping("deleteMember/{id}")
    public ResponseEntity<String> deleteMemberById(@PathVariable Long id){
        memberService.deleteMemberById(id);
        return new ResponseEntity<>("Member succesfully deleted", HttpStatus.OK);
    }

    @PatchMapping("searchMemberByCriteria")
    public ResponseEntity<List<MemberDTO>> searchMemberByCriteria(@RequestParam(required = false) Long id,
                                                                  @RequestParam(required = false) String barcodeNumber,
                                                                  @RequestParam(required = false) String firstName,
                                                                  @RequestParam(required = false) String lastName){
        List<MemberDTO> memberDTOList =  memberService.findMembersByCriteria(id, firstName, lastName, barcodeNumber);
        return new ResponseEntity<>(memberDTOList, HttpStatus.OK);
    }

}
