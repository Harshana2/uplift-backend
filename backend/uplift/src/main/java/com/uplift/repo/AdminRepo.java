package com.uplift.repo;

import com.uplift.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Admin findByUsername(String username);
}
