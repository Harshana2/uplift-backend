package com.uplift.repo;

import com.uplift.model.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VerificationTokenRepo extends MongoRepository<VerificationToken,String> {
    Optional<VerificationToken> findByToken(String token);
}
