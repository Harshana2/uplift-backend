package com.uplift.service.impl;

import com.uplift.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {
    @Autowired
    private MongoOperations mongoOperations;

    // Generate the next sequence value for a given key (e.g., "member_seq")
    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", 1),  // Increment sequence by 1
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequence.class
        );
        return counter != null ? counter.getSeq() : 1;
    }
}

