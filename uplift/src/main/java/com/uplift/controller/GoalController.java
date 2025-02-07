package com.uplift.controller;

import com.uplift.model.Goal;
import com.uplift.service.impl.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:5173")
public class GoalController {

    @Autowired
    private GoalService goalService;

    // Get goals by member ID
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Goal>> getGoalsByMemberId(@PathVariable String memberId) {
        List<Goal> goals = goalService.getGoalsByMemberId(memberId);
        return ResponseEntity.ok(goals);
    }

    // Create or update a goal
    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Goal savedGoal = goalService.saveGoal(goal);
        return ResponseEntity.ok(savedGoal);
    }

    // Delete a goal by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable String id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}
