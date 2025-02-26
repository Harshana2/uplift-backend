package com.uplift.repo;

import com.uplift.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepo extends MongoRepository<Video,String> {
}
