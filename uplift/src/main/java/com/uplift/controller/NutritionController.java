package com.uplift.controller;

import com.uplift.model.Nutrition;
import com.uplift.service.impl.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "http://localhost:5173")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    // Get nutrition plans by member ID
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Nutrition>> getNutritionByMemberId(@PathVariable String memberId) {
        List<Nutrition> nutritionPlans = nutritionService.getNutritionByMemberId(memberId);
        return ResponseEntity.ok(nutritionPlans);
    }

    // Create or update a nutrition plan
    @PostMapping
    public ResponseEntity<Nutrition> createNutrition(@RequestBody Nutrition nutrition) {
        Nutrition savedNutrition = nutritionService.saveNutrition(nutrition);
        return ResponseEntity.ok(savedNutrition);
    }

    // Delete a nutrition plan by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutrition(@PathVariable String id) {
        nutritionService.deleteNutrition(id);
        return ResponseEntity.noContent().build();
    }
}
