package com.uplift.controller;

import com.uplift.model.Member;
import com.uplift.model.Status;
import com.uplift.repo.MemberRepo;
import com.uplift.response.ApiResponse;
import com.uplift.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
   @Autowired
    private MemberRepo memberRepo;

   @Autowired
   private CoachService coachService;
    @PutMapping("/assign-coach")
    public ResponseEntity<?> assignCoach(@RequestBody Map<String, String> request) {
        String memberId = request.get("memberId");
        String coachId = request.get("coachId");

        // Find the member in the database
        Optional<Member> optionalMember = memberRepo.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setCoachId(coachId);
            memberRepo.save(member);
            return ResponseEntity.ok("Coach assigned successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
    }

    @PutMapping("/coaches/{coachId}/status")
    public ResponseEntity<?> updateCoachStatus(@PathVariable String coachId, @RequestBody Map<String, Status> request) {
        Status status = request.get("status");

        boolean success = coachService.updateCoachStatus(coachId, status);
        if (success) {
            return ResponseEntity.ok(new ApiResponse(true, "Coach status updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Failed to update coach status"));
        }
    }

}
