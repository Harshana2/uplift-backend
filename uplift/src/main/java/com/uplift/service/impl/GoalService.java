package com.uplift.service.impl;

import com.uplift.model.Goal;
import com.uplift.repo.GoalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepo goalRepository;

    // Get goals by member ID
    public List<Goal> getGoalsByMemberId(String memberId) {
        return goalRepository.findByMemberId(memberId);
    }

    // Create or update a goal
    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    // Delete a goal by ID
    public void deleteGoal(String id) {
        goalRepository.deleteById(id);
    }
}
