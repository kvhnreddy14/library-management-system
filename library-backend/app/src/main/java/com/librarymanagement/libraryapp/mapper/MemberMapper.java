package com.librarymanagement.libraryapp.mapper;

import com.librarymanagement.libraryapp.dto.MemberDTO;
import com.librarymanagement.libraryapp.entity.Member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class MemberMapper {

    // map entity to dto

    public static MemberDTO maptoMemberDTO(Member member){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setActive(member.getIsActive());
        memberDTO.setPhone(member.getPhoneNumber());
        memberDTO.setFirstName(member.getFirstName());
        memberDTO.setLastName(member.getLastName());
        memberDTO.setMemberBarcode(member.getMemberBarcode());

        //we need to store LocalDate as String
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
           //to avoid null pointer excemption
        if (member.getDateOfBirth() != null)
            memberDTO.setDateOfBirth(member.getDateOfBirth().format(formatter));

        if (member.getPostalAddress() != null)
            memberDTO.setAddress(AddressMapper.maptoAddressDTO(member.getPostalAddress()));

        if (member.getMembershipStarted() != null)
            memberDTO.setMembershipStarted(member.getMembershipStarted().format(formatter));

        if (member.getMembershipEnded() != null)
            memberDTO.setMembershipEnded(member.getMembershipEnded().format(formatter));

        return memberDTO;
    }


    public static Member maptomemberEntity(MemberDTO dto){
        Member member = new Member();
        member.setId(dto.getId());
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setEmail(dto.getEmail());
        member.setPhoneNumber(dto.getPhone());
        member.setMemberBarcode(dto.getMemberBarcode());
        member.setIsActive(dto.isActive());

        //only some variables need to be checked for null pointer excemption because the remaining are flagged as require in the entity design itself.
        member.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        member.setMembershipStarted(LocalDate.parse(dto.getMembershipStarted()));
        if (dto.getMembershipEnded() != null)
             member.setMembershipEnded((LocalDate.parse(dto.getMembershipEnded())));
        if (dto.getAddress() != null)
            member.setPostalAddress(AddressMapper.maptoAddressEntity(dto.getAddress()));

        return member;
    }


}
