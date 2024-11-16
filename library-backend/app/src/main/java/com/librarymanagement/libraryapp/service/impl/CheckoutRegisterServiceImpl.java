package com.librarymanagement.libraryapp.service.impl;

import com.librarymanagement.libraryapp.dto.CheckoutRegisterDTO;
import com.librarymanagement.libraryapp.entity.CheckoutRegister;
import com.librarymanagement.libraryapp.exception.ResourceNotFoundException;
import com.librarymanagement.libraryapp.mapper.CheckoutRegisterMapper;
import com.librarymanagement.libraryapp.repository.CheckoutRegisterRepository;
import com.librarymanagement.libraryapp.service.CheckoutRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor    //this creates contructors for only "final"
public class CheckoutRegisterServiceImpl implements CheckoutRegisterService{

    private final CheckoutRegisterRepository checkoutRegisterRepository;
    //as the mapper has non-static methods in it, we need to inject it this way
    private final CheckoutRegisterMapper checkoutRegisterMapper;

    @Value("${library.loanPeriodInDays}")
    private int loanPeriodInDays; //here comes an autowiring error if RequiredArgsContructor is not used because this is just a property from app.properties

    @Value("${library.overdueFineRate}")
    private double overdueFineRate;


    @Override
    public CheckoutRegisterDTO createRegister(CheckoutRegisterDTO checkoutRegisterDTO) {
        CheckoutRegister register = checkoutRegisterMapper.maptoRegisterEntity(checkoutRegisterDTO);

        LocalDate duedate = calculateDuedate(register.getCheckoutDate());
        register.setDueDate(duedate);

        register = checkoutRegisterRepository.save(register);
        return checkoutRegisterMapper.maptoRegisterDTO(register);
    }

    @Override
    public List<CheckoutRegisterDTO> getAllRegisters() {
        List<CheckoutRegister> registers = checkoutRegisterRepository.findAll();
        return registers.stream()
                .map(checkoutRegisterMapper::maptoRegisterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CheckoutRegisterDTO getRegisterById(Long id) {
        CheckoutRegister register = checkoutRegisterRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("CheckoutRegister","ID",id));
        return checkoutRegisterMapper.maptoRegisterDTO(register);
    }

    @Override
    public CheckoutRegisterDTO updateRegister(CheckoutRegisterDTO registerDTO) {
        //find existing register by id.
        Optional<CheckoutRegister> registerOptional = checkoutRegisterRepository.findById(registerDTO.getId());
        CheckoutRegister register = registerOptional.orElseThrow(
                () -> new ResourceNotFoundException("CheckoutRegister","ID",registerDTO.getId()));
        //do partial update
        partialUpdate(register, registerDTO);
        //calc overdue fine
        calculateOverdueFIne(register);
        //save updated register in DB
        CheckoutRegister updatedCheckoutRegister = checkoutRegisterRepository.save(register);
        return checkoutRegisterMapper.maptoRegisterDTO(updatedCheckoutRegister);
    }

    @Override
    public void deteleRegister(Long id) {
        if(!checkoutRegisterRepository.existsById(id)){
            throw new ResourceNotFoundException("CheckoutRegister","ID",id);
        }
        checkoutRegisterRepository.deleteById(id);
    }

    @Override
    public List<CheckoutRegisterDTO> searchRegisterByBookId(Long bookId) {
        List<CheckoutRegister> registerList = checkoutRegisterRepository.findByBookId(bookId);
        return registerList.stream()
                .map(checkoutRegisterMapper::maptoRegisterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CheckoutRegisterDTO> searchRegisterByMemberId(Long memberId) {
        List<CheckoutRegister> registerList = checkoutRegisterRepository.findByMemberId(memberId);
        return registerList.stream()
                .map(checkoutRegisterMapper::maptoRegisterDTO)
                .collect(Collectors.toList());
    }

    private void calculateOverdueFIne(CheckoutRegister register) {
        if(register.getReturnDate() != null && register.getReturnDate().isAfter(register.getDueDate())) {
            Long daysOverdue = ChronoUnit.DAYS.between(register.getDueDate(), register.getReturnDate());
            double overdueFine = daysOverdue * overdueFineRate;
            register.setOverdueFine(overdueFine);
        }
    }

    private void partialUpdate(CheckoutRegister register, CheckoutRegisterDTO registerDTO) {
        // the agent can prolong the due date or update the return date.
        if(registerDTO.getDueDate() != null)
            register.setCheckoutDate(LocalDate.parse(registerDTO.getCheckoutDate()));
        if(registerDTO.getReturnDate() != null)
            register.setReturnDate(LocalDate.parse(registerDTO.getReturnDate()));
    }

    private LocalDate calculateDuedate(LocalDate checkoutDate) {
        return checkoutDate.plusDays(loanPeriodInDays);
    }
}
