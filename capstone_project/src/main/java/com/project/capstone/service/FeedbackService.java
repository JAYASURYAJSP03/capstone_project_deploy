package com.project.capstone.service;


import com.project.capstone.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllFeedbacks();
    void saveFeedback(Feedback feedback);
}
