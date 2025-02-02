package com.uplift.controller;

import com.uplift.model.Coach;
import com.uplift.model.Member;
import com.uplift.service.CoachService;
import com.uplift.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@CrossOrigin(origins = "http://localhost:5173")
public class CoachController {
    @Autowired
    private CoachService coachService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Coach> saveCoach(@RequestBody Coach coach) {
        Coach createdCoach =
                coachService.saveCoach(coach);
        return ResponseEntity.ok(createdCoach);


    }
    @GetMapping("/{id}")
    public ResponseEntity<Coach> getCoachById(@PathVariable String id) {
        return new ResponseEntity<>(coachService.getCoachById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> coaches = this.coachService.getAllCoaches();
        return ResponseEntity.ok(coaches);
    }
    @GetMapping("/{coachId}/assigned-members")
    public List<Member> getAssignedMembers(@PathVariable String coachId) {
        // Fetch the list of members assigned to the specified coach
        return memberService.getMembersAssignedToCoach(coachId);
    }


    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteCoach(@PathVariable String id) {
        this.coachService.deleteCoach(id);
        return ResponseEntity.ok("Successfully Deleted "+id);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Coach> updateCoach(@PathVariable String id, @RequestBody Coach coach) {
        Coach updatedCoach = this.coachService.updateCoach(id, coach);
        return updatedCoach != null ? ResponseEntity.ok(updatedCoach) :
                ResponseEntity.notFound().build();
    }
}
