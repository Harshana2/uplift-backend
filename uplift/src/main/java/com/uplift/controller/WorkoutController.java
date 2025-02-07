package com.uplift.controller;

import com.uplift.model.Workout;
import com.uplift.service.impl.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin(origins = "http://localhost:5173")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    // Get workouts by member ID
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Workout>> getWorkoutsByMemberId(@PathVariable String memberId) {
        List<Workout> workouts = workoutService.getWorkoutsByMemberId(memberId);
        return ResponseEntity.ok(workouts);
    }

    // Create or update a workout
    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        Workout savedWorkout = workoutService.saveWorkout(workout);
        return ResponseEntity.ok(savedWorkout);
    }

    // Delete a workout by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable String id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
