package com.librarymanagement.libraryapp.repository;

import com.librarymanagement.libraryapp.entity.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<PostalAddress, Long> {
}
