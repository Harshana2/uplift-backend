package com.uplift.repo;

import com.uplift.model.Progress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProgressRepo extends MongoRepository<Progress, String> {
    List<Progress> findByMemberId(String memberId);
}