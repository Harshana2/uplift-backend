package com.uplift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uplift.model.Member;
import com.uplift.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Member> saveMember(@RequestBody Member member) {
            Member createdMember =
                    this.memberService.saveMember(member);
            return ResponseEntity.ok(createdMember);


    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id) {
        return new ResponseEntity<>(memberService.getMemberById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllEmployees() {
        List<Member> members = this.memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteMember(@PathVariable String id) {
        this.memberService.deleteMember(id);
        return ResponseEntity.ok("Successfully Deleted "+id);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Member> updateEmployee(@PathVariable String id, @RequestBody Member member) {
        Member updatedMember = this.memberService.updateMember(id, member);
        return updatedMember != null ? ResponseEntity.ok(updatedMember) :
                ResponseEntity.notFound().build();
    }

}
