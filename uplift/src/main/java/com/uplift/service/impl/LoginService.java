package com.uplift.service.impl;

import com.uplift.dto.UserDTO;
import com.uplift.model.Admin;
import com.uplift.model.Coach;
import com.uplift.model.Member;
import com.uplift.model.Status;
import com.uplift.repo.AdminRepo;
import com.uplift.repo.CoachRepo;
import com.uplift.repo.MemberRepo;
import com.uplift.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private CoachRepo coachRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDTO login(String username, String password) {
        // Check Admin collection
        Admin admin = adminRepo.findByUsername(username);
        if (admin != null && password.equals(admin.getPassword())) {
            String token = jwtUtil.generateToken(username, "ADMIN", admin.getAdminId());  // Passing the Admin ID
            return new UserDTO("ADMIN", username, admin.getAdminId(), token);
        }

        // Check Coach collection
        Coach coach = coachRepo.findByUsername(username);
        if (coach != null && bCryptPasswordEncoder.matches(password, coach.getPassword()) && coach.getStatus() == Status.APPROVED) {
            String token = jwtUtil.generateToken(username, "COACH", coach.getCoachId());  // Passing the Coach ID
            return new UserDTO("COACH", username, coach.getCoachId(), token);
        }

        // Check Member collection
        Member member = memberRepo.findByUsername(username);
        if (member != null && bCryptPasswordEncoder.matches(password, member.getPassword()) && member.isVerified()) {
            String token = jwtUtil.generateToken(username, "MEMBER", member.getMemberId());  // Passing the Member ID
            return new UserDTO("MEMBER", username, member.getMemberId(), token);
        }

        // If no match is found
        throw new RuntimeException("Invalid username or password");
    }
}
