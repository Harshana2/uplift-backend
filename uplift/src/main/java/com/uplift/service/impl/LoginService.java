package com.uplift.service.impl;

import com.uplift.dto.UserDTO;
import com.uplift.model.Admin;
import com.uplift.model.Coach;
import com.uplift.model.Member;
import com.uplift.repo.AdminRepo;
import com.uplift.repo.CoachRepo;
import com.uplift.repo.MemberRepo;
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

    public UserDTO login(String username, String password) {
        // Check Admin collection

        Admin admin = adminRepo.findByUsername(username);
        if (admin != null && bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            return new UserDTO( "ADMIN",username, admin.getAdminId());
        }

        // Check Coach collection
        Coach coach = coachRepo.findByUsername(username);
        if (coach != null && bCryptPasswordEncoder.matches(password, coach.getPassword())) {
            return new UserDTO( "COACH",username, coach.getCoachId());
        }

        // Check Member collection
        Member member = memberRepo.findByUsername(username);
        if (member != null && bCryptPasswordEncoder.matches(password, member.getPassword())) {
            return new UserDTO( "MEMBER",username, member.getMemberId());
        }

        // If no match is found
        throw new RuntimeException("Invalid username or password");
    }
}


