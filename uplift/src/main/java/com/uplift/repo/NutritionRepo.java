package com.uplift.repo;

import com.uplift.model.Nutrition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NutritionRepo extends MongoRepository<Nutrition, String> {
    List<Nutrition> findByMemberId(String memberId);
}