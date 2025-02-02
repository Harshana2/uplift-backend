package com.uplift.service.impl;

import com.uplift.model.Coach;
import com.uplift.model.Status;
import com.uplift.repo.CoachRepo;
import com.uplift.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachServiceImpl implements CoachService {
    @Autowired
    private CoachRepo coachRepo;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Coach saveCoach(Coach coach) {
        if (coach.getCoachId() == null || coach.getCoachId().isEmpty()) {
            long seqValue = sequenceGeneratorService.generateSequence("coach_seq");
            String customId = String.format("C%04d", seqValue);
            coach.setCoachId(customId);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(8);
        String encodedPassword = bCryptPasswordEncoder.encode(coach.getPassword());
        coach.setPassword(encodedPassword);
        coach.setStatus(Status.PENDING_APPROVAL);
        return coachRepo.save(coach);


    }

    @Override
    public Coach getCoachById(String id) {
        return coachRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Coach Not Found"));
    }

    @Override
    public List<Coach> getAllCoaches() {
        return coachRepo.findAll();
    }

    @Override
    public void deleteCoach(String id) {
        coachRepo.deleteById(id);
    }

    @Override
    public Coach updateCoach(String id, Coach coach) {
        if (coachRepo.existsById(id)) {
            coach.setCoachId(id);
            return coachRepo.save(coach);
        }
        return null; // Return null or throw an exception if not found
    }

    public boolean updateCoachStatus(String coachId, Status status) {
        // Find the coach by their ID
        Optional<Coach> coachOpt = coachRepo.findById(coachId);

        if (coachOpt.isPresent()) {
            Coach coach = coachOpt.get();
            coach.setStatus(status);  // Set the new status for the coach
            coachRepo.save(coach);  // Save the updated coach object
            return true;  // Successfully updated
        } else {
            return false;  // Coach not found
        }
    }
}
