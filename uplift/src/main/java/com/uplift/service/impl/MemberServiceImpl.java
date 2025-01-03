package com.uplift.service.impl;

import com.uplift.model.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uplift.repo.MemberRepo;
import com.uplift.service.MemberService;

import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public Member saveMember(Member member) {
        if (member.getMemberId() == null || member.getMemberId().isEmpty()) {
            long seqValue = sequenceGeneratorService.generateSequence("member_seq");
            String customId = String.format("M%04d", seqValue); // Format: M001, M002, etc.
            member.setMemberId(customId);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(8);
        String encodedPassword = bCryptPasswordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        return memberRepo.save(member);
    }

    private String generateUniqueId() {
        // Example: Generate a UUID and return the first 8 characters
        return "M" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public Member getMemberById(String id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Member Not Found"));
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    @Override
    public void deleteMember(String id) {
        memberRepo.deleteById(id);
    }

    @Override
    public Member updateMember(String id, Member member) {
        if (memberRepo.existsById(id)) {
            member.setMemberId(id);
            return memberRepo.save(member);
        }
        return null; // Return null or throw an exception if not found
    }

}
