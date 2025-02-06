package com.uplift.service.impl;

import com.uplift.model.Progress;
import com.uplift.repo.ProgressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepo progressRepository;

    // Get progress by member ID
    public List<Progress> getProgressByMemberId(String memberId) {
        return progressRepository.findByMemberId(memberId);
    }

    // Create or update progress
    public Progress saveProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    // Delete progress by ID
    public void deleteProgress(String id) {
        progressRepository.deleteById(id);
    }
}
