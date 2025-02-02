package com.uplift.service.impl;

import com.uplift.exception.UsernameAlreadyExistsException;
import com.uplift.model.Member;
import com.uplift.model.VerificationToken;
import com.uplift.repo.MemberRepo;
import com.uplift.repo.VerificationTokenRepo;
import com.uplift.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private VerificationTokenRepo tokenRepo;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Member saveMember(Member member) {
        if (memberRepo.existsByUsername(member.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists: " + member.getUsername());
        }

        // Generate custom ID if not present
        if (member.getMemberId() == null || member.getMemberId().isEmpty()) {
            long seqValue = sequenceGeneratorService.generateSequence("member_seq");
            String customId = String.format("M%04d", seqValue); // Format: M001, M002, etc.
            member.setMemberId(customId);
        }

        // Encode password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(8);
        String encodedPassword = bCryptPasswordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        // Set unverified status
        member.setVerified(false);

        // Save member to database
        Member savedMember = memberRepo.save(member);

        // Generate and save verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setMemberId(savedMember.getMemberId());
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepo.save(verificationToken);

        // Send verification email
        sendVerificationEmail(savedMember.getEmail(), token);

        return savedMember;
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
    public long getMemberCount() {
        return memberRepo.count();
    }

    @Override
    public Member updateMember(String id, Member member) {
        if (memberRepo.existsById(id)) {
            member.setMemberId(id);
            return memberRepo.save(member);
        }
        return null; // Return null or throw an exception if not found
    }

    public String verifyMember(String token) {
        Optional<VerificationToken> optionalToken = tokenRepo.findByToken(token);
        if (optionalToken.isEmpty()) {
            throw new RuntimeException("Invalid verification token.");
        }

        VerificationToken verificationToken = optionalToken.get();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification token has expired.");
        }

        // Find the member by token's memberId
        Member member = memberRepo.findById(verificationToken.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found."));

        // Mark the member as verified
        member.setVerified(true);
        memberRepo.save(member);

        // Remove the verification token
        tokenRepo.delete(verificationToken);

        return "Email verified successfully!";
    }

    private void sendVerificationEmail(String email, String token) {
        String verificationLink = "http://localhost:8080/api/members/verify?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verify Your Email");
        message.setText("Click the link below to verify your email:\n" + verificationLink);
        mailSender.send(message);
    }

    public List<Member> getMembersAssignedToCoach(String coachId) {
        // Fetch and return the members assigned to the coach by coachId
        return memberRepo.findByCoachId(coachId);
    }
}
