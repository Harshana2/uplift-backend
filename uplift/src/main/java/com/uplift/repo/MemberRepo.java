package com.uplift.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.uplift.model.Member;

public interface MemberRepo extends MongoRepository<Member, String> {
    Member findByUsername(String username);
}
