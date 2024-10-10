package com.example.bookStoreServicesApp.repository;

import com.example.bookStoreServicesApp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
