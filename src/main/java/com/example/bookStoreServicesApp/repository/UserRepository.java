package com.example.bookStoreServicesApp.repository;

import com.example.bookStoreServicesApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String name);
    public Optional<User> findByEmail(String email);

}
