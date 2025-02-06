package com.uplift.service.impl;

import com.uplift.model.Nutrition;
import com.uplift.repo.NutritionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutritionService {

    @Autowired
    private NutritionRepo nutritionRepository;

    // Get nutrition plans by member ID
    public List<Nutrition> getNutritionByMemberId(String memberId) {
        return nutritionRepository.findByMemberId(memberId);
    }

    // Create or update a nutrition plan
    public Nutrition saveNutrition(Nutrition nutrition) {
        return nutritionRepository.save(nutrition);
    }

    // Delete a nutrition plan by ID
    public void deleteNutrition(String id) {
        nutritionRepository.deleteById(id);
    }
}
