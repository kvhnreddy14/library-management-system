package com.librarymanagement.libraryapp.service;

import com.librarymanagement.libraryapp.dto.MemberDTO;

import java.util.List;

public interface MemberService {


    MemberDTO addMember(MemberDTO memberDTO);

    List<MemberDTO> getAllMembers();

    MemberDTO getMemberById(Long id);

    MemberDTO updateMember(MemberDTO memberDTO);

    List<MemberDTO > findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber);

    void deleteMemberById(Long id);


}
