package com.example.bookStoreServicesApp.repository;

import com.example.bookStoreServicesApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{
    List<Address> findByUserId(Long id);
}