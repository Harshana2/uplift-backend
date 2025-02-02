package com.uplift.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.uplift.model.Member;

import java.util.List;

public interface MemberRepo extends MongoRepository<Member, String> {
    Member findByUsername(String username);
    boolean existsByUsername(String username);


    List<Member> findByCoachId(String coachId);
}
