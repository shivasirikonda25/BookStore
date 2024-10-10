package com.example.bookStoreServicesApp.repository;

import com.example.bookStoreServicesApp.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByBookId(Long bookId);
}
