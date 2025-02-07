package com.uplift.repo;

import com.uplift.model.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GoalRepo extends MongoRepository<Goal, String> {
    List<Goal> findByMemberId(String memberId);
}