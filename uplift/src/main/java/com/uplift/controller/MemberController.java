package com.uplift.controller;

import com.uplift.model.Member;
import com.uplift.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Member> saveMember(@RequestBody Member member) {
        try {
            // Save the member object
            Member createdMember = memberService.saveMember(member);
            return ResponseEntity.ok(createdMember);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id) {
        return new ResponseEntity<>(memberService.getMemberById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = this.memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable String id) {
        this.memberService.deleteMember(id);
        return ResponseEntity.ok("Successfully Deleted " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable String id, @RequestBody Member member) {
        Member updatedMember = this.memberService.updateMember(id, member);
        return updatedMember != null ? ResponseEntity.ok(updatedMember) :
                ResponseEntity.notFound().build();
    }

    // New endpoint for email verification
    @GetMapping("/verify")
    public ResponseEntity<String> verifyMember(@RequestParam("token") String token) {
        try {
            String responseMessage = memberService.verifyMember(token);
            return ResponseEntity.ok(responseMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
