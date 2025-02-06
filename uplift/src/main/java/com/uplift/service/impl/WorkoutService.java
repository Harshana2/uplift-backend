package com.uplift.service.impl;

import com.uplift.model.Workout;
import com.uplift.repo.WorkoutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepo workoutRepository;

    // Get workouts by member ID
    public List<Workout> getWorkoutsByMemberId(String memberId) {
        return workoutRepository.findByMemberId(memberId);
    }

    // Create or update a workout
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    // Delete a workout by ID
    public void deleteWorkout(String id) {
        workoutRepository.deleteById(id);
    }
}