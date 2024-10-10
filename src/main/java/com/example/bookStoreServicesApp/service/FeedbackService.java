package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.model.Feedback;
import com.example.bookStoreServicesApp.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback submitFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackByBookId(Long bookId) {
        return feedbackRepository.findByBookId(bookId);
    }
}
