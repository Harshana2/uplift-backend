package com.uplift.repo;

import com.uplift.model.Coach;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoachRepo extends MongoRepository<Coach, String> {
    Coach findByUsername(String username);
}
