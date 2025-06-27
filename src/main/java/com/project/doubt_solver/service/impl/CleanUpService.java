package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CleanUpService {

    @Autowired
     private UserRepository userRepo;

    @Scheduled(cron = "0 0 * * * ?")
    public void cleanTable(){
         userRepo.resetAllCounts();
    }
}
