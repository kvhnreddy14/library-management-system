package com.librarymanagement.libraryapp.service.impl;

import com.librarymanagement.libraryapp.dto.AddressDTO;
import com.librarymanagement.libraryapp.dto.MemberDTO;
import com.librarymanagement.libraryapp.entity.Member;
import com.librarymanagement.libraryapp.entity.PostalAddress;
import com.librarymanagement.libraryapp.exception.ResourceNotFoundException;
import com.librarymanagement.libraryapp.mapper.AddressMapper;
import com.librarymanagement.libraryapp.mapper.MemberMapper;
import com.librarymanagement.libraryapp.repository.AddressRepository;
import com.librarymanagement.libraryapp.repository.MemberRepository;
import com.librarymanagement.libraryapp.service.AddressService;
import com.librarymanagement.libraryapp.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private AddressRepository addressRepository;
    private MemberRepository memberRepository;
    private AddressServiceImpl addressServiceImpl;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public MemberDTO addMember(MemberDTO memberDTO) {
        //first we need to deal with postal address
        PostalAddress postalAddress = new PostalAddress();
        AddressDTO addressDTO = memberDTO.getAddress();
        if (addressDTO != null) {
            postalAddress = AddressMapper.maptoAddressEntity(addressDTO);
            postalAddress = addressRepository.save(postalAddress);
        }

        //convert memberDTO to entity
        Member memberEntity = MemberMapper.maptomemberEntity(memberDTO);

        //add the address to entity (setting postalAddress_id field in db)
        if (postalAddress != null) memberEntity.setPostalAddress(postalAddress);

        //save the entity to db
        memberEntity = memberRepository.save(memberEntity);

        //convert the entity back to memberDTO
        return MemberMapper.maptoMemberDTO(memberEntity);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberMapper::maptoMemberDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        Member member = memberOptional.orElseThrow(
                () -> new ResourceNotFoundException("Member","ID",id));
        return MemberMapper.maptoMemberDTO(member);
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDTO) {
        Optional<Member> memberOptional = memberRepository.findById(memberDTO.getId());
        Member member = memberOptional.orElseThrow(
                () -> new ResourceNotFoundException("Member","ID",memberDTO.getId()));
        updateMemberEntityFromDTO(member, memberDTO);

        member  = memberRepository.save(member);

        return MemberMapper.maptoMemberDTO(member);
    }

    @Override
    public List<MemberDTO> findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> memberRoot = cq.from(Member.class);
        List<Predicate> predicates = new ArrayList<>();
        if (id != null)
            //cb.edhoti(the perameter we need to search, what it is actually[sometimes wrapped in "%"+ +"%" because they are altered])
            predicates.add(cb.equal(memberRoot.get("id") , id));
        if (firstName != null)
            predicates.add(cb.like(cb.lower(memberRoot.get("firstName")),"%"+ firstName.toLowerCase() + "%"));
        if (lastName != null)
            predicates.add(cb.like(cb.lower(memberRoot.get("lastName")), "%"+ lastName.toLowerCase() +"%"));
        if (barcodeNumber != null)
            predicates.add(cb.like(cb.lower(memberRoot.get("barcodeNumber")),"%"+barcodeNumber.toLowerCase()+"%"));
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Member> members = entityManager.createQuery(cq).getResultList();
        return members.stream()
                .map(MemberMapper::maptoMemberDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMemberById(Long id) {
        if(!memberRepository.existsById(id)){
            throw new ResourceNotFoundException("Member","ID",id);
        }
        memberRepository.deleteById(id);
    }

    private void updateMemberEntityFromDTO(Member member, MemberDTO memberDTO) {
        if (memberDTO.getFirstName() != null)
            member.setFirstName(memberDTO.getFirstName());
        if (memberDTO.getLastName() != null)
            member.setLastName(memberDTO.getLastName());
        if (memberDTO.getDateOfBirth() != null)
            member.setDateOfBirth(LocalDate.parse(memberDTO.getDateOfBirth()));
        if (memberDTO.getEmail() != null)
            member.setEmail(memberDTO.getEmail());
        if (memberDTO.getPhone() != null)
            member.setPhoneNumber(memberDTO.getPhone());
        if (memberDTO.getMembershipStarted() != null)
            member.setMembershipStarted(LocalDate.parse(memberDTO.getMembershipStarted()));

        if (memberDTO.getMembershipEnded() != null) {
            if (memberDTO.getMembershipEnded().isEmpty()) {
                member.setMembershipEnded(null);
                member.setIsActive(true);
            } else {
                member.setMembershipEnded(LocalDate.parse(memberDTO.getMembershipEnded()));
                member.setIsActive(false);
            }
        }

        if (memberDTO.getAddress() != null){
            PostalAddress addressToUpdate;
            if (member.getPostalAddress() != null){
                addressToUpdate = member.getPostalAddress();
            } else{
                addressToUpdate = new PostalAddress();
            }
            addressServiceImpl.updateAllfields(memberDTO.getAddress() , addressToUpdate);
            addressRepository.save(addressToUpdate);
            member.setPostalAddress(addressToUpdate);
        }
    }

}