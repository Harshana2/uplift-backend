package com.uplift.repo;

import com.uplift.model.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkoutRepo extends MongoRepository<Workout, String> {
    List<Workout> findByMemberId(String memberId);
}
