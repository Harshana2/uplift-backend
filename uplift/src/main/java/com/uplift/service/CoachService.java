package com.uplift.service;

import com.uplift.model.Coach;


import java.util.List;

public interface CoachService {


    Coach saveCoach(Coach coach);

    Coach getCoachById(String id);

    List<Coach> getAllCoaches();

    void deleteCoach(String id);

    Coach updateCoach(String id, Coach coach);
}
